package com.felfel.dealer_vehicle_inventory_module.admin.service;

import com.felfel.dealer_vehicle_inventory_module.admin.Dto.DealerCountBySubscriptionResponse;
import com.felfel.dealer_vehicle_inventory_module.system.jdbc.CustomGlobalQuery;
import org.springframework.stereotype.Service;

/**
 * Service class for administrative operations.
 * Provides global statistics across all tenants.
 */
@Service
public class AdminService {
    private final CustomGlobalQuery customGlobalQuery;

    /**
     * Constructs the AdminService.
     *
     * @param customGlobalQuery Repository for cross-tenant queries.
     */
    public AdminService(CustomGlobalQuery customGlobalQuery) {
        this.customGlobalQuery = customGlobalQuery;
    }

    /**
     * Counts all dealers in the system grouped by their subscription type.
     * This operation is global and ignores tenant boundaries.
     *
     * @return A response DTO containing counts for BASIC and PREMIUM subscriptions.
     */
    public DealerCountBySubscriptionResponse countDealersBySubscription() {
        long basicCount = customGlobalQuery.countBasicDealers();
        long premiumCount = customGlobalQuery.countPremiumDealers();

        return new DealerCountBySubscriptionResponse(basicCount, premiumCount);
    }
}
