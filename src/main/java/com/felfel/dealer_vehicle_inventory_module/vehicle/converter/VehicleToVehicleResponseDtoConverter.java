package com.felfel.dealer_vehicle_inventory_module.vehicle.converter;

import com.felfel.dealer_vehicle_inventory_module.vehicle.Data.Vehicle;
import com.felfel.dealer_vehicle_inventory_module.vehicle.dto.VehicleResponseDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class VehicleToVehicleResponseDtoConverter implements Converter<Vehicle, VehicleResponseDto> {
    @Override
    public VehicleResponseDto convert(Vehicle source) {
        return new VehicleResponseDto(
                source.getId(),
                source.getDealerId(),
                source.getModel(),
                source.getPrice(),
                source.getStatus()
        );
    }
}
