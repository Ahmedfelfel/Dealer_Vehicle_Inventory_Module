package com.felfel.dealer_vehicle_inventory_module.vehicle.dto;
import com.felfel.dealer_vehicle_inventory_module.vehicle.Data.VehicleStatus;
import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;
import java.util.UUID;

public record VehiclePatchRequestDto(

                                  UUID dealerId,
                                  String model,
                                  BigDecimal price,
                                  VehicleStatus status) {
}
