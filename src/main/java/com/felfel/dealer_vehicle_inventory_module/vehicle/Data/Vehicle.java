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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle implements Serializable {

    @Id
    private UUID id;
    @TenantId
    private String  tenantId;

    private UUID dealerId;

    private String model;

    private BigDecimal price ;

    private VehicleStatus status;

}
