package com.sit.configHelper;

import com.sit.security.jwt.JWTConfigurer;
import com.sit.security.jwt.TokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by colem on 1/3/2017.
 */
@Component
public class TenantInterceptor extends HandlerInterceptorAdapter {

    private final Logger log = LoggerFactory.getLogger(TenantInterceptor.class);

    private TokenProvider tokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
        throws Exception {

        log.info("preHandle firing in TenantInterceptor");
        boolean tenantSet = false;
        String jwt = "";
        //MTC get jwt string
        String bearerToken = req.getHeader(JWTConfigurer.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            jwt = bearerToken.substring(7, bearerToken.length());
        }
        log.info("bearer token is: " + jwt);

        //validate jwt string
        if (StringUtils.hasText(jwt)) {
            if (this.tokenProvider.validateToken(jwt)) {
                //MTC get tenant id
                String tid = this.tokenProvider.getTenantId(jwt);
                log.info("received Tenant ID is: " + tid + " ...setting current tenant context here");
                TenantContext.setCurrentTenant(tid);
                tenantSet = true;
                log.info("current tenant id is:" + TenantContext.getCurrentTenant());
            }
        }

        return tenantSet;
    }

    @Override
    public void postHandle(
        HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
        throws Exception {

        log.info("postHandle firing in TenantInterceptor");
        TenantContext.clear();
    }

}
