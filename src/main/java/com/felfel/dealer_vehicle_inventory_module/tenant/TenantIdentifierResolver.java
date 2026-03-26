package com.felfel.dealer_vehicle_inventory_module.tenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

/**
 * The type Tenant identifier resolver.
 */
@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {
    @Override
    public Object resolveCurrentTenantIdentifier() {
       // return TenantContext.getCurrentTenant();
        String tenant = TenantContext.getCurrentTenant();
        return (tenant != null) ? tenant : "__BOOTSTRAP__";
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }
}
