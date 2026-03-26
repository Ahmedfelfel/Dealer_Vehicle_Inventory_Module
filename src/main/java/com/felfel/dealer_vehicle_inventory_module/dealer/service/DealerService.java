package com.felfel.dealer_vehicle_inventory_module.dealer.service;

import com.felfel.dealer_vehicle_inventory_module.dealer.Data.Dealer;
import com.felfel.dealer_vehicle_inventory_module.dealer.Data.SubscriptionType;
import com.felfel.dealer_vehicle_inventory_module.dealer.dto.DealerPatchRequestDto;
import com.felfel.dealer_vehicle_inventory_module.dealer.dto.DealerPostRequestDto;
import com.felfel.dealer_vehicle_inventory_module.dealer.repository.DealerRepo;
import com.felfel.dealer_vehicle_inventory_module.system.exception.OpjectNotFoundException;
import com.felfel.dealer_vehicle_inventory_module.system.jdbc.CustomGlobalQuery;
import com.felfel.dealer_vehicle_inventory_module.tenant.TenantContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DealerService {
    private final DealerRepo dealerRepo;
    private final CustomGlobalQuery customGlobalQuery;
    private final String OBJECT_TYPE="dealer";

    public DealerService(DealerRepo dealerRepo, CustomGlobalQuery customGlobalQuery) {
        this.dealerRepo = dealerRepo;
        this.customGlobalQuery = customGlobalQuery;
    }

    public List<Dealer> findAll() {
        return this.dealerRepo.findAll();
    }

    public Dealer findById(UUID id) {
        checkDealerNotBelongToOtherTenant(id, TenantContext.getCurrentTenant());
        return this.dealerRepo.findById(id)
                .orElseThrow(() -> new OpjectNotFoundException(OBJECT_TYPE, id.toString()));
    }

    public Dealer add(DealerPostRequestDto newDealer) {
        Dealer addedDealer = new Dealer(
                UUID.randomUUID(),
                TenantContext.getCurrentTenant(),
                newDealer.name(),
                newDealer.email(),
                newDealer.subscriptionType()
        );
        return dealerRepo.save(addedDealer);
    }

    public Dealer update(UUID id, DealerPatchRequestDto updatedDealer) {
        checkDealerNotBelongToOtherTenant(id,TenantContext.getCurrentTenant());
        return this.dealerRepo.findById(id)
                .map(oldDealer->
                {
                    if (updatedDealer.email()!=null)
                        oldDealer.setEmail(updatedDealer.email());
                    if (updatedDealer.name()!=null)
                        oldDealer.setName(updatedDealer.name());
                    if (updatedDealer.subscriptionType()!=null)
                        oldDealer.setSubscriptionType(updatedDealer.subscriptionType());
                    return this.dealerRepo.save(oldDealer);
                })
                .orElseThrow(() -> new OpjectNotFoundException(OBJECT_TYPE,id.toString()));
    }

    public void delete(UUID id) {
        checkDealerNotBelongToOtherTenant(id,TenantContext.getCurrentTenant());
        this.dealerRepo.findById(id)
                .orElseThrow(()->new OpjectNotFoundException(OBJECT_TYPE,id.toString()));
        this.dealerRepo.deleteById(id);
    }
    public void checkDealerNotBelongToOtherTenant(UUID id, String currentTent)
    {
        if(this.customGlobalQuery.isDealerExistsInOtherTenant(id,currentTent))
            throw new AccessDeniedException("Cross-tenant access blocked");
    }

    public List<UUID> findIdsBySubscription(SubscriptionType subscription) {
        return dealerRepo.findBySubscriptionType(subscription)
                .stream()
                .map(Dealer::getId)
                .toList();
    }

    public Page<Dealer> findAll(Pageable pageable) {
        return this.dealerRepo.findAll(pageable);
    }
}
