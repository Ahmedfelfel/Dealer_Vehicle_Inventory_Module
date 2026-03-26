package com.felfel.dealer_vehicle_inventory_module.vehicle.controller;

import com.felfel.dealer_vehicle_inventory_module.dealer.Data.SubscriptionType;
import com.felfel.dealer_vehicle_inventory_module.system.Result;
import com.felfel.dealer_vehicle_inventory_module.vehicle.Data.Vehicle;
import com.felfel.dealer_vehicle_inventory_module.vehicle.Data.VehicleStatus;
import com.felfel.dealer_vehicle_inventory_module.vehicle.converter.VehicleToVehicleResponseDtoConverter;
import com.felfel.dealer_vehicle_inventory_module.vehicle.dto.VehiclePatchRequestDto;
import com.felfel.dealer_vehicle_inventory_module.vehicle.dto.VehiclePostRequestDto;
import com.felfel.dealer_vehicle_inventory_module.vehicle.dto.VehicleResponseDto;
import com.felfel.dealer_vehicle_inventory_module.vehicle.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * The type Vehicle controller.
 */
@RestController
@RequestMapping("vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final VehicleToVehicleResponseDtoConverter vehicleToVehicleResponseDtoConverter;

    /**
     * Instantiates a new Vehicle controller.
     *
     * @param vehicleService                       the vehicle service
     * @param vehicleToVehicleResponseDtoConverter the vehicle to vehicle response dto converter
     */
    public VehicleController(VehicleService vehicleService, VehicleToVehicleResponseDtoConverter vehicleToVehicleResponseDtoConverter) {
        this.vehicleService = vehicleService;
        this.vehicleToVehicleResponseDtoConverter = vehicleToVehicleResponseDtoConverter;
    }

    /**
     * Find all vehicles result.
     *
     * @param pageable     the pageable
     * @param subscription the subscription
     * @param model        the model
     * @param status       the status
     * @param priceMin     the price min
     * @param priceMax     the price max
     * @return the result
     */
    @GetMapping
    public Result findAllVehicles(
            Pageable pageable,
            @RequestParam(required = false) SubscriptionType subscription,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) VehicleStatus status,
            @RequestParam(required = false) BigDecimal priceMin,
            @RequestParam(required = false) BigDecimal priceMax
    ) {
        Page<Vehicle> vehicles = vehicleService.findAll(
                pageable,
                subscription,
                model,
                status,
                priceMin,
                priceMax
        );

        Page<VehicleResponseDto> dtos = vehicles
                .map(vehicleToVehicleResponseDtoConverter::convert);

        return new Result(
                true,
                HttpStatus.OK.value(),
                "find all success",
                dtos
        );
    }


    /**
     * Find vehicle by id result.
     *
     * @param id the id
     * @return the result
     */
    @GetMapping("/{id}")
    public Result findVehicleById(@PathVariable UUID id)
    {
        Vehicle foundVehicle = vehicleService.findById(id);
        VehicleResponseDto foundVehicleDto = vehicleToVehicleResponseDtoConverter.convert(foundVehicle);
        return new Result(true,HttpStatus.OK.value(), "find one success",foundVehicleDto);
    }

    /**
     * Add vehicle result.
     *
     * @param newVehicle the new vehicle
     * @return the result
     */
    @PostMapping("")
    public Result addVehicle(@Valid @RequestBody VehiclePostRequestDto newVehicle)
    {
        Vehicle addedVehicle = vehicleService.add(newVehicle);
        VehicleResponseDto addedVehicleDto = vehicleToVehicleResponseDtoConverter.convert(addedVehicle);
        return new Result(true,HttpStatus.CREATED.value(), "add success",addedVehicleDto);

    }

    /**
     * Update vehicle result.
     *
     * @param id             the id
     * @param updatedVehicle the updated vehicle
     * @return the result
     */
    @PatchMapping("/{id}")
    public Result updateVehicle(@PathVariable UUID id, @RequestBody VehiclePatchRequestDto updatedVehicle)
    {
        Vehicle updatedVehicleData = vehicleService.update(id, updatedVehicle);
        VehicleResponseDto updatedVehicleDto = vehicleToVehicleResponseDtoConverter.convert(updatedVehicleData);
        return new Result(true,HttpStatus.OK.value(), "update success",updatedVehicleDto);
    }

    /**
     * Delete vehicle result.
     *
     * @param id the id
     * @return the result
     */
    @DeleteMapping("/{id}")
    public Result deleteVehicle(@PathVariable UUID id)
    {
        vehicleService.delete(id);
        return new Result(true,HttpStatus.NO_CONTENT.value(), "delete success",null);

    }
}
