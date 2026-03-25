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

    public boolean isVehicleExistsInOtherTenant(UUID Id, String tenantId) {
        var sql = "SELECT COUNT(*) FROM VEHICLE  WHERE ID = ? AND TENANT_ID != ?";
        var count = jdbc.queryForObject(sql, Integer.class, Id, tenantId);
        return count != null && count > 0;
    }
}
