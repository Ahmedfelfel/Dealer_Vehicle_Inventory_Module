package com.felfel.dealer_vehicle_inventory_module.vehicle.service;

import com.felfel.dealer_vehicle_inventory_module.dealer.Data.SubscriptionType;
import com.felfel.dealer_vehicle_inventory_module.dealer.service.DealerService;
import com.felfel.dealer_vehicle_inventory_module.system.exception.OpjectNotFoundException;
import com.felfel.dealer_vehicle_inventory_module.system.jdbc.CustomGlobalQuery;
import com.felfel.dealer_vehicle_inventory_module.tenant.TenantContext;
import com.felfel.dealer_vehicle_inventory_module.vehicle.Data.Vehicle;
import com.felfel.dealer_vehicle_inventory_module.vehicle.Data.VehicleStatus;
import com.felfel.dealer_vehicle_inventory_module.vehicle.dto.VehiclePatchRequestDto;
import com.felfel.dealer_vehicle_inventory_module.vehicle.dto.VehiclePostRequestDto;
import com.felfel.dealer_vehicle_inventory_module.vehicle.repository.VehicleRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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


    public Page<Vehicle> findAll(
            Pageable pageable,
            SubscriptionType subscription,
            String model,
            VehicleStatus status,
            BigDecimal priceMin,
            BigDecimal priceMax
    ) {

        if (subscription != null) {
            return findAllWithSubscription(pageable, subscription, model, status, priceMin, priceMax);
        }

        return findAllWithoutSubscription(pageable, model, status, priceMin, priceMax);
    }

    private Page<Vehicle> findAllWithSubscription(
            Pageable pageable,
            SubscriptionType subscription,
            String model,
            VehicleStatus status,
            BigDecimal priceMin,
            BigDecimal priceMax
    ) {
        List<UUID> dealerIds = dealerService.findIdsBySubscription(subscription);

        if (dealerIds.isEmpty()) {
            return Page.empty(pageable);
        }

        if (model != null && !model.isBlank()) {
            return vehicleRepo.findByDealerIdInAndModelContainingIgnoreCase(dealerIds, model, pageable);
        }

        if (status != null) {
            return vehicleRepo.findByDealerIdInAndStatus(dealerIds, status, pageable);
        }

        if (priceMin != null) {
            return vehicleRepo.findByDealerIdInAndPriceGreaterThanEqual(dealerIds, priceMin, pageable);
        }

        if (priceMax != null) {
            return vehicleRepo.findByDealerIdInAndPriceLessThanEqual(dealerIds, priceMax, pageable);
        }

        return vehicleRepo.findByDealerIdIn(dealerIds, pageable);
    }

    private Page<Vehicle> findAllWithoutSubscription(
            Pageable pageable,
            String model,
            VehicleStatus status,
            BigDecimal priceMin,
            BigDecimal priceMax
    ) {
        if (model != null && !model.isBlank()) {
            return vehicleRepo.findByModelContainingIgnoreCase(model, pageable);
        }

        if (status != null) {
            return vehicleRepo.findByStatus(status, pageable);
        }

        if (priceMin != null) {
            return vehicleRepo.findByPriceGreaterThanEqual(priceMin, pageable);
        }

        if (priceMax != null) {
            return vehicleRepo.findByPriceLessThanEqual(priceMax, pageable);
        }

        return vehicleRepo.findAll(pageable);
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
