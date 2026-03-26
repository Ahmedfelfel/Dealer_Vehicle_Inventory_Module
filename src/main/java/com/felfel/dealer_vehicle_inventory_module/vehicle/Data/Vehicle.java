package com.felfel.dealer_vehicle_inventory_module.vehicle.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TenantId;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Entity representing a Vehicle in the inventory system.
 * Vehicles are tenant-scoped and belong to a specific dealer.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle implements Serializable {

    /**
     * Unique identifier for the vehicle.
     */
    @Id
    private UUID id;

    /**
     * Tenant identifier for multi-tenancy support.
     */
    @TenantId
    private String  tenantId;

    /**
     * Unique identifier for the dealer that owns this vehicle.
     */
    private UUID dealerId;

    /**
     * Model of the vehicle.
     */
    private String model;

    /**
     * Price of the vehicle.
     */
    private BigDecimal price ;

    /**
     * Current status of the vehicle (e.g., AVAILABLE, SOLD).
     */
    private VehicleStatus status;
}
