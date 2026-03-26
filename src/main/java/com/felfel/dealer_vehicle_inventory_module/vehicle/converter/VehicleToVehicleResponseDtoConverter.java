package com.felfel.dealer_vehicle_inventory_module.vehicle.converter;

import com.felfel.dealer_vehicle_inventory_module.vehicle.Data.Vehicle;
import com.felfel.dealer_vehicle_inventory_module.vehicle.dto.VehicleResponseDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converter that maps a {@link Vehicle} entity to a {@link VehicleResponseDto}.
 */
@Component
public class VehicleToVehicleResponseDtoConverter implements Converter<Vehicle, VehicleResponseDto> {
    /**
     * Converts a Vehicle entity to its response DTO representation.
     *
     * @param source The Vehicle entity.
     * @return The converted VehicleResponseDto.
     */
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
