package com.felfel.dealer_vehicle_inventory_module.system.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository for custom global SQL queries.
 * Bypasses standard multi-tenancy filters when necessary (e.g., cross-tenant validation).
 */
@Repository
public class CustomGlobalQuery {
    private final JdbcTemplate jdbc;

    /**
     * Constructs the CustomGlobalQuery repository.
     *
     * @param jdbc JdbcTemplate for raw SQL execution.
     */
    public CustomGlobalQuery(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Checks if a vehicle with the given ID exists in any tenant other than the specified one.
     * Used to prevent cross-tenant ID collisions or access attempts.
     *
     * @param id       Vehicle ID.
     * @param tenantId The current tenant ID.
     * @return true if it exists elsewhere, false otherwise.
     */
    public boolean isVehicleExistsInOtherTenant(UUID id, String tenantId) {
        var sql = "SELECT COUNT(*) FROM VEHICLE  WHERE ID = ? AND TENANT_ID != ?";
        var count = jdbc.queryForObject(sql, Integer.class, id, tenantId);
        return count != null && count > 0;
    }

    /**
     * Checks if a dealer with the given ID exists in any tenant other than the specified one.
     *
     * @param id       Dealer ID.
     * @param tenantId The current tenant ID.
     * @return true if it exists elsewhere, false otherwise.
     */
    public boolean isDealerExistsInOtherTenant(UUID id, String tenantId) {
        var sql = "SELECT COUNT(*) FROM DEALER  WHERE ID = ? AND TENANT_ID != ?";
        var count = jdbc.queryForObject(sql, Integer.class, id, tenantId);
        return count != null && count > 0;
    }

    /**
     * Global count of BASIC dealers.
     *
     * @return count.
     */
    public long countBasicDealers() {
        String sql = "SELECT COUNT(*) FROM dealer WHERE subscription_type = 0";
        Long count = jdbc.queryForObject(sql, Long.class);
        return count == null ? 0 : count;
    }

    /**
     * Global count of PREMIUM dealers.
     *
     * @return count.
     */
    public long countPremiumDealers() {
        String sql = "SELECT COUNT(*) FROM dealer WHERE subscription_type = 1";
        Long count = jdbc.queryForObject(sql, Long.class);
        return count == null ? 0 : count;
    }
}
