package com.felfel.dealer_vehicle_inventory_module.dealer.Data;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TenantId;

import java.io.Serializable;
import java.util.UUID;

/**
 * Entity representing a Dealer in the inventory system.
 * Dealers are tenant-scoped and have a specific subscription type.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dealer implements Serializable {
    /**
     * Unique identifier for the dealer.
     */
    @Id
    private UUID id;

    /**
     * Tenant identifier for multi-tenancy support.
     */
    @TenantId
    private String tenantId;

    /**
     * Name of the dealer.
     */
    private String name;

    /**
     * Email address of the dealer.
     */
    private String email;

    /**
     * Type of subscription the dealer has (e.g., BASIC, PREMIUM).
     */
    private SubscriptionType subscriptionType;
}
