package com.felfel.dealer_vehicle_inventory_module.vehicle.service;

import com.felfel.dealer_vehicle_inventory_module.system.exception.OpjectNotFoundException;
import com.felfel.dealer_vehicle_inventory_module.vehicle.Data.Vehicle;
import com.felfel.dealer_vehicle_inventory_module.vehicle.dto.VehiclePatchRequestDto;
import com.felfel.dealer_vehicle_inventory_module.vehicle.dto.VehiclePostRequestDto;
import com.felfel.dealer_vehicle_inventory_module.vehicle.repository.VehicleRepo;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class VehicleService {
    VehicleRepo vehicleRepo;
    private final String OBJECT_TYPE="vehicle";

    public VehicleService(VehicleRepo vehicleRepo) {
        this.vehicleRepo = vehicleRepo;
    }

    public List<Vehicle> findAll() {
        List<Vehicle> vehicles = this.vehicleRepo.findAll();
        if(vehicles.isEmpty())
        {
            throw new OpjectNotFoundException(OBJECT_TYPE);
        }
        return vehicles;
    }

    public Vehicle findById(UUID id) {
        return this.vehicleRepo.findById(id)
                .orElseThrow(() -> new OpjectNotFoundException(OBJECT_TYPE,id.toString()));
    }

    public Vehicle add( VehiclePostRequestDto newVehicle) {
        Vehicle addedVehicle = new Vehicle(
                UUID.randomUUID(),
                null,
                newVehicle.dealerId(),
                newVehicle.model(),
                newVehicle.price(),
                newVehicle.status()
        );
        return vehicleRepo.save(addedVehicle);
    }

    public Vehicle update(UUID id, VehiclePatchRequestDto updatedVehicle) {
        return this.vehicleRepo.findById(id)
                .map(oldVehicle->
                {
                    if (updatedVehicle.dealerId()!=null)
                        oldVehicle.setDealerId(updatedVehicle.dealerId());
                    if (updatedVehicle.model()!=null)
                        oldVehicle.setModel(updatedVehicle.model());
                    if (updatedVehicle.price()!=null)
                        oldVehicle.setPrice(updatedVehicle.price());
                    if (updatedVehicle.status()!=null)
                        oldVehicle.setStatus(updatedVehicle.status());
                    return this.vehicleRepo.save(oldVehicle);
                })
                .orElseThrow(() -> new OpjectNotFoundException(OBJECT_TYPE,id.toString()));
    }

    public void delete(UUID id) {
        this.vehicleRepo.findById(id)
                .orElseThrow(()->new OpjectNotFoundException(OBJECT_TYPE,id.toString()));
        this.vehicleRepo.deleteById(id);
    }
}
