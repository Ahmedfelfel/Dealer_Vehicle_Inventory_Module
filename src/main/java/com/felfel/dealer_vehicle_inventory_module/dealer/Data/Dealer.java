package com.felfel.dealer_vehicle_inventory_module.dealer.Data;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dealer implements Serializable {
    @Id
    private UUID id;

    private String tenantId;

    private String name;

    private String email;

    private SubscriptionType subscriptionType;

}
