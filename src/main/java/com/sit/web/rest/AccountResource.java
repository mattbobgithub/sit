package com.sit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sit.domain.User;
import com.sit.repository.master.UserRepository;
import com.sit.security.SecurityUtils;
import com.sit.service.CompanyService;
import com.sit.service.MailService;
import com.sit.service.SitUserService;
import com.sit.service.UserService;
import com.sit.service.dto.SitUserDTO;
import com.sit.web.rest.util.HeaderUtil;
import com.sit.web.rest.vm.KeyAndPasswordVM;
import com.sit.web.rest.vm.ManagedUserVM;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    @Inject
    private MailService mailService;

    @Inject
    private SitUserService sitUserService;

    @Inject
    private CompanyService companyService;

    /**
     * POST  /register : register the user.
     *
     * @param managedUserVM the managed user View Model
     * @return the ResponseEntity with status 201 (Created) if the user is registered or 400 (Bad Request) if the login or e-mail is already in use
     */
    @PostMapping(path = "/register",
                    produces={MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Timed
    public ResponseEntity<?> registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {
        log.debug("############# registerAccount REST service reached with : " + managedUserVM.toString());
        //MTC set sitid from companyId upon registering from form input.
        // ...change to some type of URL routing property later
        Long defaultCompanyIdForTenant = companyService.findAll().get(0).getId();
        managedUserVM.setSitid(defaultCompanyIdForTenant);
        managedUserVM.setCompanyId(defaultCompanyIdForTenant);
        log.debug(" register account default company and sitid set to:" + defaultCompanyIdForTenant);
        //set default store and workroom
        managedUserVM.setStoreId(new Long(1));
        managedUserVM.setWorkroomId(new Long(1));

        HttpHeaders textPlainHeaders = new HttpHeaders();
        textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);

         ResponseEntity re =  userRepository.findOneByLogin(managedUserVM.getLogin().toLowerCase())
            .map(user -> new ResponseEntity<>("login already in use", textPlainHeaders, HttpStatus.BAD_REQUEST))
            .orElseGet(() -> userRepository.findOneByEmail(managedUserVM.getEmail())
                .map(user -> new ResponseEntity<>("e-mail address already in use", textPlainHeaders, HttpStatus.BAD_REQUEST))
                .orElseGet(() -> {
                    User user = userService
                        // MTC change to different method
//                        .createUser(managedUserVM.getLogin(), managedUserVM.getPassword(),
//                            managedUserVM.getFirstName(), managedUserVM.getLastName(),
//                            managedUserVM.getEmail().toLowerCase(), managedUserVM.getLangKey(),
//                            managedUserVM.getSitid());
                    .createUser(managedUserVM);

                    SitUserDTO sitUserDTO = new SitUserDTO(managedUserVM);
                    sitUserService.save(sitUserDTO);

                    // MTC - don't use activation
                    // mailService.sendActivationEmail(user);
                    return new ResponseEntity<>(HttpStatus.CREATED);
                })


        );




        return re;


    }

    /**
     * GET  /activate : activate the registered user.
     *
     * @param key the activation key
     * @return the ResponseEntity with status 200 (OK) and the activated user in body, or status 500 (Internal Server Error) if the user couldn't be activated
     */
    @GetMapping("/activate")
    @Timed
    public ResponseEntity<String> activateAccount(@RequestParam(value = "key") String key) {
        return userService.activateRegistration(key)
            .map(user -> new ResponseEntity<String>(HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @GetMapping("/authenticate")
    @Timed
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * GET  /account : get the current user.
     *
     * @return the ResponseEntity with status 200 (OK) and the current user in body, or status 500 (Internal Server Error) if the user couldn't be returned
     */
//    @GetMapping("/account")
//    @Timed
//    public ResponseEntity<UserDTO> getAccount() {
//        return Optional.ofNullable(userService.getUserWithAuthorities())
//            .map(user -> new ResponseEntity<>(new UserDTO(user), HttpStatus.OK))
//           .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
//
//    }

    // MTC try to return manageduservm instead of UserDTO
    @GetMapping("/account")
    @Timed
    public ResponseEntity<ManagedUserVM> getAccount() {
        log.debug("!!! getAccount service reached");
        //get currently logged in user
        User userObj = userService.getUserWithAuthorities();
        if (userObj!= null){
            //get sitUserDTO to create ManagedUserEntity
            SitUserDTO sitUserDTO = sitUserService.findOne(userObj.getId());
            ManagedUserVM muvm = new ManagedUserVM(userObj,sitUserDTO);
            //null these fields when sending to client, post will re-assign
            muvm.setId(null);
            muvm.setCompanyId(null);
            muvm.setSitid(null);
            log.debug("getting muvm from getAccount (id,companyid, and sitid hardcoded to null for client side current logged in user) :" + muvm.toString());
            return new ResponseEntity<>(muvm,HttpStatus.OK);
        }else
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

//    @GetMapping("/accountNavDetails")
//    @Timed
//    public ResponseEntity<UserDTO> getAccountNavDetails() {
//        return Optional.ofNullable(userService.getUserWithAuthorities())
//            .map(user -> new ResponseEntity<>(new UserDTO(user), HttpStatus.OK))
//            .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
//
//    }



//    @PostMapping("/account")
//    @Timed
//    public ResponseEntity<String> saveAccount(@Valid @RequestBody UserDTO userDTO) {
//        log.debug("saveAccount rest service reached");
//
//        Optional<User> existingUser = userRepository.findOneByEmail(userDTO.getEmail());
//        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userDTO.getLogin()))) {
//            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("user-management", "emailexists", "Email already in use")).body(null);
//        }
//        return userRepository
//            .findOneByLogin(SecurityUtils.getCurrentUserLogin())
//            .map(u -> {
//                userService.updateUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
//                    userDTO.getLangKey(), userDTO.getSitid());
//                return new ResponseEntity<String>(HttpStatus.OK);
//            })
//            .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
//
//    }

//MTC - UPDATED to use MANAGED USER VM instead of USERDTO
    @PostMapping("/account")
    @Timed
    public ResponseEntity<String> saveAccount(@Valid @RequestBody ManagedUserVM muvm) {
        log.debug("saveAccount rest service reached");
        Long defaultCompanyIdForTenant = companyService.findAll().get(0).getId();
        muvm.setSitid(defaultCompanyIdForTenant);
        muvm.setCompanyId(defaultCompanyIdForTenant);
        log.debug(" register account default company and sitid set to:" + defaultCompanyIdForTenant);
        //check if exists
        Optional<User> existingUser = userRepository.findOneByEmail(muvm.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(muvm.getLogin()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("user-management", "emailexists", "Email already in use")).body(null);
        }


        try {
            Optional<User> usr = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin());
            muvm.setId(usr.get().getId());

            userService.updateUser(
                muvm.getId(),
                muvm.getLogin(),
                muvm.getFirstName(),
                muvm.getLastName(),
                muvm.getEmail(),
                muvm.isActivated(),
                muvm.getLangKey(),
                userService.getAuthoritiesStringsFromUserType(muvm.getUserType()),
                muvm.getSitid()
            );

            //MTC now update sitUser
            SitUserDTO sitUserDTO = new SitUserDTO(muvm);
            sitUserDTO = sitUserService.save(sitUserDTO);

            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (Exception e){
       return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
        //now update user
//        return userRepository
//            .findOneByLogin(SecurityUtils.getCurrentUserLogin())
//            .map(u -> {
//                userService.updateUser(muvm.getFirstName(), muvm.getLastName(), muvm.getEmail(),
//                    muvm.getLangKey(), muvm.getSitid());
//                return new ResponseEntity<String>(HttpStatus.OK);
//            })
//            .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

    }

    /**
     * POST  /account/change_password : changes the current user's password
     *
     * @param password the new password
     * @return the ResponseEntity with status 200 (OK), or status 400 (Bad Request) if the new password is not strong enough
     */
    @PostMapping(path = "/account/change_password",
        produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity<?> changePassword(@RequestBody String password) {
        if (!checkPasswordLength(password)) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
        }
        userService.changePassword(password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * POST   /account/reset_password/init : Send an e-mail to reset the password of the user
     *
     * @param mail the mail of the user
     * @return the ResponseEntity with status 200 (OK) if the e-mail was sent, or status 400 (Bad Request) if the e-mail address is not registered
     */
    @PostMapping(path = "/account/reset_password/init",
        produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity<?> requestPasswordReset(@RequestBody String mail) {
        return userService.requestPasswordReset(mail)
            .map(user -> {
                mailService.sendPasswordResetMail(user);
                return new ResponseEntity<>("e-mail was sent", HttpStatus.OK);
            }).orElse(new ResponseEntity<>("e-mail address not registered", HttpStatus.BAD_REQUEST));
    }

    /**
     * POST   /account/reset_password/finish : Finish to reset the password of the user
     *
     * @param keyAndPassword the generated key and the new password
     * @return the ResponseEntity with status 200 (OK) if the password has been reset,
     * or status 400 (Bad Request) or 500 (Internal Server Error) if the password could not be reset
     */
    @PostMapping(path = "/account/reset_password/finish",
        produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity<String> finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
        }
        return userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey())
              .map(user -> new ResponseEntity<String>(HttpStatus.OK))
              .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private boolean checkPasswordLength(String password) {
        return (!StringUtils.isEmpty(password) &&
            password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH);
    }
}
