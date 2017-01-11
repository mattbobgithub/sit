package com.sit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sit.config.Constants;
import com.sit.domain.User;
import com.sit.repository.master.UserRepository;
import com.sit.security.AuthoritiesConstants;
import com.sit.service.MailService;
import com.sit.service.SitUserService;
import com.sit.service.UserService;
import com.sit.service.dto.SitUserDTO;
import com.sit.web.rest.util.HeaderUtil;
import com.sit.web.rest.util.PaginationUtil;
import com.sit.web.rest.vm.ManagedUserVM;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing users.
 *
 * <p>This class accesses the User entity, and needs to fetch its collection of authorities.</p>
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between User and Authority,
 * and send everything to the client side: there would be no View Model and DTO, a lot less code, and an outer-join
 * which would be good for performance.
 * </p>
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities, because people will
 * quite often do relationships with the user, and we don't want them to get the authorities all
 * the time for nothing (for performance reasons). This is the #1 goal: we should not impact our users'
 * application because of this use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is not a real issue as
 * we have by default a second-level cache. This means on the first HTTP call we do the n+1 requests,
 * but then all authorities come from the cache, so in fact it's much better than doing an outer join
 * (which will get lots of data from the database, for each HTTP call).</li>
 * <li> As this manages users, for security reasons, we'd rather have a DTO layer.</li>
 * </ul>
 * <p>Another option would be to have a specific JPA entity graph to handle this case.</p>
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private MailService mailService;

    @Inject
    private UserService userService;

    @Inject
    private SitUserService sitUserService;

    /**
     * POST  /users  : Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link.
     * The user needs to be activated on creation.
     * </p>
     *
     * @param managedUserVM the user to create
     * @return the ResponseEntity with status 201 (Created) and with body the new user, or with status 400 (Bad Request) if the login or email is already in use
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/users")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<?> createUser(@RequestBody ManagedUserVM managedUserVM) throws URISyntaxException {
        log.debug("REST request to save User : {}", managedUserVM);

        //Lowercase the user login before comparing with database
        if (userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase()).isPresent()) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert("userManagement", "userexists", "Login already in use"))
                .body(null);
        } else if (userRepository.findOneByEmail(managedUserVM.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert("userManagement", "emailexists", "Email already in use"))
                .body(null);
        } else {
            User newUser = userService.createUser(managedUserVM);

            //MTC update SitUser object
            SitUserDTO sitUserDTO = new SitUserDTO(managedUserVM);
            sitUserService.save(sitUserDTO);

            mailService.sendCreationEmail(newUser);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getLogin()))
                .headers(HeaderUtil.createAlert( "A user is created with identifier " + newUser.getLogin(), newUser.getLogin()))
                .body(newUser);
        }
    }

    /**
     * PUT  /users : Updates an existing User.
     *
     * @param managedUserVM the user to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated user,
     * or with status 400 (Bad Request) if the login or email is already in use,
     * or with status 500 (Internal Server Error) if the user couldn't be updated
     */
    @PutMapping("/users")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<ManagedUserVM> updateUser(@RequestBody ManagedUserVM managedUserVM) {
        log.debug("REST request to update User : {}", managedUserVM);
        Optional<User> existingUser = userRepository.findOneByEmail(managedUserVM.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(managedUserVM.getId()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("userManagement", "emailexists", "E-mail already in use")).body(null);
        }
        existingUser = userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(managedUserVM.getId()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("userManagement", "userexists", "Login already in use")).body(null);
        }
        //MTC update SitUser object first, then user in both tenant and master dbs
        SitUserDTO sitUserDTO = new SitUserDTO(managedUserVM);
        sitUserDTO = sitUserService.save(sitUserDTO);

        userService.updateUser(managedUserVM.getId(), managedUserVM.getLogin(), managedUserVM.getFirstName(),
            managedUserVM.getLastName(), managedUserVM.getEmail(), managedUserVM.isActivated(),
            managedUserVM.getLangKey(), managedUserVM.getAuthorities(), managedUserVM.getSitid());


        return ResponseEntity.ok()
            .headers(HeaderUtil.createAlert("A user is updated with identifier " + managedUserVM.getLogin(), managedUserVM.getLogin()))
            .body(new ManagedUserVM(userService.getUserWithAuthorities(managedUserVM.getId()),sitUserDTO));
    }

    /**
     * GET  /users : get all users.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     * @throws URISyntaxException if the pagination headers couldn't be generated
     */
    @GetMapping("/users")
    @Timed
    public ResponseEntity<List<ManagedUserVM>> getAllUsers(@ApiParam Pageable pageable)
        throws URISyntaxException {
        Page<User> page = userRepository.findAllWithAuthorities(pageable);
        List<ManagedUserVM> managedUserVMs = page.getContent().stream()
            .map(ManagedUserVM::new)
            .collect(Collectors.toList());

        //MTC crude way to update muvm with SitUser props
        for (ManagedUserVM muvm: managedUserVMs) {
            managedUserVMs.set(managedUserVMs.indexOf(muvm), this.updateManagedUserVMWithSitUser(muvm));
        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        return new ResponseEntity<>(managedUserVMs, headers, HttpStatus.OK);
    }

    /**
     * GET  /users/:login : get the "login" user.
     *
     * @param login the login of the user to find
     * @return the ResponseEntity with status 200 (OK) and with body the "login" user, or with status 404 (Not Found)
     */
    @GetMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    public ResponseEntity<ManagedUserVM> getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        log.info("getting sitUser and combining with userDTO to send to client as ManagedUserVM");
        Optional<ManagedUserVM> optmuvm = userService.getUserWithAuthoritiesByLogin(login).map(ManagedUserVM::new);
        if (optmuvm.isPresent()){
            ManagedUserVM muvm = this.updateManagedUserVMWithSitUser(optmuvm.get());


//        return userService.getUserWithAuthoritiesByLogin(login)
//                .map(ManagedUserVM::new)
//                .map(managedUserVM -> new ResponseEntity<>(managedUserVM, HttpStatus.OK))
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

            return new ResponseEntity<>(muvm, HttpStatus.OK);
        }
        else
        {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * DELETE /users/:login : delete the "login" User.
     *
     * @param login the login of the user to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {
        log.debug("REST request to delete User: {}", login);
      //MTC - delete SitUser happens in userService
        userService.deleteUser(login);

        return ResponseEntity.ok().headers(HeaderUtil.createAlert( "A user is deleted with identifier " + login, login)).build();
    }



    private ManagedUserVM updateManagedUserVMWithSitUser(ManagedUserVM muvm){
        SitUserDTO sitUserDto = sitUserService.findOne(muvm.getId());
        if (sitUserDto!=null) {
            muvm.setCompanyId(sitUserDto.getCompanyId());
            muvm.setUserType(sitUserDto.getUserType());
            muvm.setFitterIndicator(sitUserDto.getFitterIndicator());
            muvm.setManagerApprovalCode(sitUserDto.getManagerApprovalCode());
            muvm.setStoreId(sitUserDto.getStoreId());
            muvm.setWorkroomId(sitUserDto.getWorkroomId());
        }
        return muvm;
    }
}
