package com.felfel.dealer_vehicle_inventory_module.dealer.service;

import com.felfel.dealer_vehicle_inventory_module.dealer.Data.Dealer;
import com.felfel.dealer_vehicle_inventory_module.dealer.Data.SubscriptionType;
import com.felfel.dealer_vehicle_inventory_module.dealer.dto.DealerPatchRequestDto;
import com.felfel.dealer_vehicle_inventory_module.dealer.dto.DealerPostRequestDto;
import com.felfel.dealer_vehicle_inventory_module.dealer.repository.DealerRepo;
import com.felfel.dealer_vehicle_inventory_module.system.exception.ObjectNotFoundException;
import com.felfel.dealer_vehicle_inventory_module.system.jdbc.CustomGlobalQuery;
import com.felfel.dealer_vehicle_inventory_module.tenant.TenantContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The type Dealer service.
 */
@Service
public class DealerService {
    private final DealerRepo dealerRepo;
    private final CustomGlobalQuery customGlobalQuery;
    private final String OBJECT_TYPE="dealer";

    /**
     * Instantiates a new Dealer service.
     *
     * @param dealerRepo        the dealer repo
     * @param customGlobalQuery the custom global query
     */
    public DealerService(DealerRepo dealerRepo, CustomGlobalQuery customGlobalQuery) {
        this.dealerRepo = dealerRepo;
        this.customGlobalQuery = customGlobalQuery;
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<Dealer> findAll() {
        return this.dealerRepo.findAll();
    }

    /**
     * Find by id dealer.
     *
     * @param id the id
     * @return the dealer
     */
    public Dealer findById(UUID id) {
        checkDealerNotBelongToOtherTenant(id, TenantContext.getCurrentTenant());
        return this.dealerRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(OBJECT_TYPE, id.toString()));
    }

    /**
     * Add dealer.
     *
     * @param newDealer the new dealer
     * @return the dealer
     */
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

    /**
     * Update dealer.
     *
     * @param id            the id
     * @param updatedDealer the updated dealer
     * @return the dealer
     */
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
                .orElseThrow(() -> new ObjectNotFoundException(OBJECT_TYPE,id.toString()));
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    public void delete(UUID id) {
        checkDealerNotBelongToOtherTenant(id,TenantContext.getCurrentTenant());
        this.dealerRepo.findById(id)
                .orElseThrow(()->new ObjectNotFoundException(OBJECT_TYPE,id.toString()));
        this.dealerRepo.deleteById(id);
    }

    /**
     * Check dealer not belong to other tenant.
     *
     * @param id          the id
     * @param currentTent the current tent
     */
    public void checkDealerNotBelongToOtherTenant(UUID id, String currentTent)
    {
        if(this.customGlobalQuery.isDealerExistsInOtherTenant(id,currentTent))
            throw new AccessDeniedException("Cross-tenant access blocked");
    }

    /**
     * Find ids by subscription list.
     *
     * @param subscription the subscription
     * @return the list
     */
    public List<UUID> findIdsBySubscription(SubscriptionType subscription) {
        return dealerRepo.findBySubscriptionType(subscription, Pageable.unpaged())
                .stream()
                .map(Dealer::getId)
                .toList();
    }

    /**
     * Find all page.
     *
     * @param pageable     the pageable
     * @param subscription the subscription
     * @return the page
     */
    public Page<Dealer> findAll(Pageable pageable, SubscriptionType subscription) {
        if (subscription != null) {
            return this.dealerRepo.findBySubscriptionType(subscription, pageable);
        }
        return this.dealerRepo.findAll(pageable);
    }
}
