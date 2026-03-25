package com.felfel.dealer_vehicle_inventory_module.dealer.dto;

import com.felfel.dealer_vehicle_inventory_module.dealer.Data.SubscriptionType;

import java.util.UUID;

public record DealerResponseDto(
        UUID id,
        String name,
        String email,
        SubscriptionType subscriptionType) {
}
