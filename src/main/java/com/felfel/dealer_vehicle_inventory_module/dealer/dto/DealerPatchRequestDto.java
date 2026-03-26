package com.felfel.dealer_vehicle_inventory_module.dealer.dto;

import com.felfel.dealer_vehicle_inventory_module.dealer.Data.SubscriptionType;

/**
 * The type Dealer patch request dto.
 */
public record DealerPatchRequestDto(
        String name,
        String email,
        SubscriptionType subscriptionType) {
}
