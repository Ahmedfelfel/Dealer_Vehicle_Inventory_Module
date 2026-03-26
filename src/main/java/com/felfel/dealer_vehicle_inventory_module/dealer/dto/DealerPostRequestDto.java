package com.felfel.dealer_vehicle_inventory_module.dealer.dto;

import com.felfel.dealer_vehicle_inventory_module.dealer.Data.SubscriptionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * The type Dealer post request dto.
 */
public record DealerPostRequestDto(
        @NotBlank(message = "name is required")
        String name,
        @NotBlank(message = "email is required")
        String email,
        @NotNull(message = "subscriptionType is required")
        SubscriptionType subscriptionType) {
}
