package com.sit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sit.configHelper.SitClients;
import com.sit.configHelper.TenantContext;
import com.sit.security.jwt.JWTConfigurer;
import com.sit.security.jwt.TokenProvider;
import com.sit.web.rest.vm.LoginVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    @Inject
    private TokenProvider tokenProvider;

    @Inject
    private AuthenticationManager authenticationManager;

    @Inject
    private SitClients sitClients;

    @PostMapping("/authenticate")
    @Timed
    public ResponseEntity<?> authorize(@Valid @RequestBody LoginVM loginVM, HttpServletRequest request, HttpServletResponse response) {
     log.debug("authorize Method in UserJWTController. Login vm is:" + loginVM.toString());
       StringBuffer strUrl = request.getRequestURL();
       log.debug("full url:" + strUrl);
         String subDomainString = strUrl.substring(strUrl.indexOf("/")+2,strUrl.indexOf("."));
        log.debug("subDomainString:" + subDomainString);
       HashMap<String, String> allSitClientsFromXML =  sitClients.getClients();
        log.debug("all sit clients from xml:" + sitClients.getClients().toString());

        String tenantId = sitClients.getClients().get(subDomainString);
        log.debug("found tenantId is:" + tenantId);
        if (tenantId == null) {
            log.debug("NO TENANT DB DEFINED IN XML FOR: " + subDomainString);
           return new ResponseEntity<>(Collections.singletonMap("AuthenticationException","NO TENANT DB DEFINED"), HttpStatus.UNAUTHORIZED);
        }
        TenantContext.setCurrentTenant(tenantId);

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        try {
            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
            String jwt = tokenProvider.createToken(authentication, rememberMe);
            response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
            return ResponseEntity.ok(new JWTToken(jwt));
        } catch (AuthenticationException exception) {
            return new ResponseEntity<>(Collections.singletonMap("AuthenticationException",exception.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
        }
    }
}
