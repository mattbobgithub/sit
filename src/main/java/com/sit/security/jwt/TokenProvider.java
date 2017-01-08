package com.sit.security.jwt;

import com.sit.config.JHipsterProperties;
import com.sit.configHelper.TenantContext;
import com.sit.repository.master.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";

    private String secretKey;

    private long tokenValidityInMilliseconds;

    private long tokenValidityInMillisecondsForRememberMe;

    @Inject
    private UserRepository userRepository;

    @Inject
    private JHipsterProperties jHipsterProperties;

    @PostConstruct
    public void init() {
        this.secretKey =
            jHipsterProperties.getSecurity().getAuthentication().getJwt().getSecret();

        this.tokenValidityInMilliseconds =
            1000 * jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();
        this.tokenValidityInMillisecondsForRememberMe =
            1000 * jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSecondsForRememberMe();
    }

    public String createToken(Authentication authentication, Boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        } else {
            validity = new Date(now + this.tokenValidityInMilliseconds);
        }
        ///////////////////// BEGIN SET TENANTID IN JWT ////////////////////////////////////////
        String headerTenantId = "";
        TenantContext.setCurrentTenant(TenantContext.DEFAULT_TENANT);
        log.debug("creating JWT token with TENANT ID for user:" + authentication.getName());
        Optional<com.sit.domain.User> user = userRepository.findOneByLogin(authentication.getName());
        if(user.isPresent()){
            headerTenantId = "sit" + user.get().getSitid();
        }
        else{
            log.debug("TOKEN CREATE FAILURE - cannot find tenant id for user: " + authentication.getName());
            return null;
        }

        ///////////////////// END SET TENANTID IN JWT ////////////////////////////////////////
        log.debug("creating JWT token with TENANT ID of:" + headerTenantId + " for user:" + authentication.getName());
        log.debug("set tenant id now during token creation, set again when checking token?");
        TenantContext.setCurrentTenant(headerTenantId);

        return Jwts.builder()
            .setSubject(authentication.getName())
            .claim(AUTHORITIES_KEY, authorities)
            //MTC add claim for tenantId to token builder
            .claim("TENANTID",headerTenantId)
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .setExpiration(validity)
            .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody();

        Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());


        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    //MTC add method to get TenantID
    public String getTenantId(String token){
        log.info("getting tenant id from token (TokenProvider.getTenantId method):" + token);
        Claims claims = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody();

        return claims.get("TENANTID").toString();
    }

    public boolean validateToken(String authToken) {
        log.info("validating token");
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.info("Invalid JWT signature: " + e.getMessage());
            return false;
        }
    }
}
