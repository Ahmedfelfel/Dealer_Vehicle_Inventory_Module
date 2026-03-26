package com.felfel.dealer_vehicle_inventory_module.vehicle.service;

import com.felfel.dealer_vehicle_inventory_module.dealer.Data.SubscriptionType;
import com.felfel.dealer_vehicle_inventory_module.dealer.service.DealerService;
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
    private final DealerService dealerService;

    public VehicleService(VehicleRepo vehicleRepo, CustomGlobalQuery customGlobalQuery, DealerService dealerService) {
        this.vehicleRepo = vehicleRepo;
        this.customGlobalQuery = customGlobalQuery;
        this.dealerService = dealerService;
    }

    public List<Vehicle> findAll() {
        return this.vehicleRepo.findAll();
    }
    public List<Vehicle> findAll(SubscriptionType subscription) {
        List<UUID> dealerIds = dealerService.findIdsBySubscription(subscription);
        if (dealerIds.isEmpty()) return List.of();
        return vehicleRepo.findByDealerIdIn(dealerIds);
    }

    public Vehicle findById(UUID id) {
        checkVehicleNotBelongToOtherTenant(id,TenantContext.getCurrentTenant());
        return this.vehicleRepo.findById(id)
                .orElseThrow(() -> new OpjectNotFoundException(OBJECT_TYPE,id.toString()));
    }

    public Vehicle add( VehiclePostRequestDto newVehicle) {
        this.dealerService.checkDealerNotBelongToOtherTenant(newVehicle.dealerId(),TenantContext.getCurrentTenant());
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
        this.checkVehicleNotBelongToOtherTenant(id,TenantContext.getCurrentTenant());
        return this.vehicleRepo.findById(id)
                .map(oldVehicle->
                {
                    if (updatedVehicle.dealerId()!=null){
                        this.dealerService.checkDealerNotBelongToOtherTenant(updatedVehicle.dealerId(),TenantContext.getCurrentTenant());
                        oldVehicle.setDealerId(updatedVehicle.dealerId());}
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
        checkVehicleNotBelongToOtherTenant(id,TenantContext.getCurrentTenant());
        this.vehicleRepo.findById(id)
                .orElseThrow(()->new OpjectNotFoundException(OBJECT_TYPE,id.toString()));
        this.vehicleRepo.deleteById(id);
    }

    public void checkVehicleNotBelongToOtherTenant(UUID id, String currentTent)
    {
         if(this.customGlobalQuery.isVehicleExistsInOtherTenant(id,currentTent))
             throw new AccessDeniedException("Cross-tenant access blocked");
    }


}
