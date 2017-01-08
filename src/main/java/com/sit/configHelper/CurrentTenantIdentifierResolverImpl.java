package com.sit.configHelper;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

/**
 * Created by colem on 1/3/2017.
 */
@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

    final public static String DEFAULT_TENANT = "sit1";

    @Override
    public String resolveCurrentTenantIdentifier() {
        return TenantContext.getCurrentTenant();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
