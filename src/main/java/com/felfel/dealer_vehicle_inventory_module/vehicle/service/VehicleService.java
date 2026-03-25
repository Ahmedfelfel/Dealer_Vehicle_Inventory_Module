package com.felfel.dealer_vehicle_inventory_module.vehicle.service;

import com.felfel.dealer_vehicle_inventory_module.system.exception.OpjectNotFoundException;
import com.felfel.dealer_vehicle_inventory_module.system.jdbc.CustomGlobalQuery;
import com.felfel.dealer_vehicle_inventory_module.tenant.TenantContext;
import com.felfel.dealer_vehicle_inventory_module.vehicle.Data.Vehicle;
import com.felfel.dealer_vehicle_inventory_module.vehicle.dto.VehiclePatchRequestDto;
import com.felfel.dealer_vehicle_inventory_module.vehicle.dto.VehiclePostRequestDto;
import com.felfel.dealer_vehicle_inventory_module.vehicle.repository.VehicleRepo;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class VehicleService {

    private final VehicleRepo vehicleRepo;
    private final CustomGlobalQuery customGlobalQuery;
    private final String OBJECT_TYPE="vehicle";

    public VehicleService(VehicleRepo vehicleRepo, CustomGlobalQuery customGlobalQuery) {
        this.vehicleRepo = vehicleRepo;
        this.customGlobalQuery = customGlobalQuery;
    }

    public List<Vehicle> findAll() {
        return this.vehicleRepo.findAll();
    }

    public Vehicle findById(UUID id) {
        checkVehicleNotBelongToCurrentTent(id,TenantContext.getCurrentTenant());
        return this.vehicleRepo.findById(id)
                .orElseThrow(() -> new OpjectNotFoundException(OBJECT_TYPE,id.toString()));
    }

    public Vehicle add( VehiclePostRequestDto newVehicle) {
        Vehicle addedVehicle = new Vehicle(
                UUID.randomUUID(),
                TenantContext.getCurrentTenant(),
                newVehicle.dealerId(),
                newVehicle.model(),
                newVehicle.price(),
                newVehicle.status()
        );
        return vehicleRepo.save(addedVehicle);
    }

    public Vehicle update(UUID id, VehiclePatchRequestDto updatedVehicle) {
        checkVehicleNotBelongToCurrentTent(id,TenantContext.getCurrentTenant());
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
        checkVehicleNotBelongToCurrentTent(id,TenantContext.getCurrentTenant());
        this.vehicleRepo.findById(id)
                .orElseThrow(()->new OpjectNotFoundException(OBJECT_TYPE,id.toString()));
        this.vehicleRepo.deleteById(id);
    }

    public void checkVehicleNotBelongToCurrentTent(UUID id, String currentTent)
    {
         if(this.customGlobalQuery.isVehicleExistsInOtherTenant(id,currentTent))
             throw new AccessDeniedException("Cross-tenant access blocked");
    }
    public boolean isReferencedDealerBelongToCurrentTent(UUID referencedDealerId,String currentTent)
    {
        return true;
    }
}
