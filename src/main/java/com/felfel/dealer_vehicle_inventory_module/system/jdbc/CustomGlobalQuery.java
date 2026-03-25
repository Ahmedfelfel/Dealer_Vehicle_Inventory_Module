package com.felfel.dealer_vehicle_inventory_module.system.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class CustomGlobalQuery {
    private final JdbcTemplate jdbc;

    public CustomGlobalQuery(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public boolean isVehicleExistsInOtherTenant(UUID id, String tenantId) {
        var sql = "SELECT COUNT(*) FROM VEHICLE  WHERE ID = ? AND TENANT_ID != ?";
        var count = jdbc.queryForObject(sql, Integer.class, id, tenantId);
        return count != null && count > 0;
    }

    public boolean isDealerExistsInOtherTenant(UUID id, String tenantId) {
        var sql = "SELECT COUNT(*) FROM DEALER  WHERE ID = ? AND TENANT_ID != ?";
        var count = jdbc.queryForObject(sql, Integer.class, id, tenantId);
        return count != null && count > 0;
    }
}
