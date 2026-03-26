package com.felfel.dealer_vehicle_inventory_module.vehicle.dto;
import com.felfel.dealer_vehicle_inventory_module.vehicle.Data.VehicleStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * The type Vehicle post request dto.
 */
public record VehiclePostRequestDto(
                                  @NotNull(message = "dealerId is required")
                                  UUID dealerId,
                                  @NotBlank(message = "model is required")
                                  String model,
                                  @NotNull(message = "price is required")
                                  BigDecimal price,
                                  @NotNull(message = "status is required")
                                  VehicleStatus status) {
}
