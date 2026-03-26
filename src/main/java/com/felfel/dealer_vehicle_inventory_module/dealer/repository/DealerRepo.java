package com.felfel.dealer_vehicle_inventory_module.dealer.repository;

import com.felfel.dealer_vehicle_inventory_module.dealer.Data.Dealer;
import com.felfel.dealer_vehicle_inventory_module.dealer.Data.SubscriptionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * The interface Dealer repo.
 */
@Repository
public interface DealerRepo extends JpaRepository<Dealer, UUID> {
    /**
     * Find by subscription type page.
     *
     * @param subscription the subscription
     * @param pageable     the pageable
     * @return the page
     */
    Page<Dealer> findBySubscriptionType(SubscriptionType subscription, Pageable pageable);
}
