package com.felfel.dealer_vehicle_inventory_module.vehicle.controller;

import com.felfel.dealer_vehicle_inventory_module.dealer.Data.SubscriptionType;
import com.felfel.dealer_vehicle_inventory_module.system.Result;
import com.felfel.dealer_vehicle_inventory_module.vehicle.Data.Vehicle;
import com.felfel.dealer_vehicle_inventory_module.vehicle.converter.VehicleToVehicleResponseDtoConverter;
import com.felfel.dealer_vehicle_inventory_module.vehicle.dto.VehiclePatchRequestDto;
import com.felfel.dealer_vehicle_inventory_module.vehicle.dto.VehiclePostRequestDto;
import com.felfel.dealer_vehicle_inventory_module.vehicle.dto.VehicleResponseDto;
import com.felfel.dealer_vehicle_inventory_module.vehicle.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final VehicleToVehicleResponseDtoConverter vehicleToVehicleResponseDtoConverter;

    public VehicleController(VehicleService vehicleService, VehicleToVehicleResponseDtoConverter vehicleToVehicleResponseDtoConverter) {
        this.vehicleService = vehicleService;
        this.vehicleToVehicleResponseDtoConverter = vehicleToVehicleResponseDtoConverter;
    }

    @GetMapping
    public Result findAllVehicles(
            @RequestParam(required = false) SubscriptionType subscription) {

        List<Vehicle> vehicles = (subscription != null)
                ? vehicleService.findAll(subscription)
                : vehicleService.findAll();

        List<VehicleResponseDto> dtos = vehicles.stream()
                .map(vehicleToVehicleResponseDtoConverter::convert)
                .toList();

        return new Result(true, HttpStatus.OK.value(), "find all success", dtos);
    }

    @GetMapping("/{id}")
    public Result findVehicleById(@PathVariable UUID id)
    {
        Vehicle foundVehicle = vehicleService.findById(id);
        VehicleResponseDto foundVehicleDto = vehicleToVehicleResponseDtoConverter.convert(foundVehicle);
        return new Result(true,HttpStatus.OK.value(), "find one success",foundVehicleDto);
    }

    @PostMapping("")
    public Result addVehicle(@Valid @RequestBody VehiclePostRequestDto newVehicle)
    {
        Vehicle addedVehicle = vehicleService.add(newVehicle);
        VehicleResponseDto addedVehicleDto = vehicleToVehicleResponseDtoConverter.convert(addedVehicle);
        return new Result(true,HttpStatus.CREATED.value(), "add success",addedVehicleDto);

    }
    @PatchMapping("/{id}")
    public Result updateVehicle(@PathVariable UUID id, @RequestBody VehiclePatchRequestDto updatedVehicle)
    {
        Vehicle updatedVehicleData = vehicleService.update(id, updatedVehicle);
        VehicleResponseDto updatedVehicleDto = vehicleToVehicleResponseDtoConverter.convert(updatedVehicleData);
        return new Result(true,HttpStatus.OK.value(), "update success",updatedVehicleDto);
    }
    @DeleteMapping("/{id}")
    public Result deleteVehicle(@PathVariable UUID id)
    {
        vehicleService.delete(id);
        return new Result(true,HttpStatus.NO_CONTENT.value(), "delete success",null);

    }
}
