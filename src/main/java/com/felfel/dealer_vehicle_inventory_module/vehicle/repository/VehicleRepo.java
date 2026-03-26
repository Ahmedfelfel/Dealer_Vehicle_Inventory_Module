package com.felfel.dealer_vehicle_inventory_module.vehicle.repository;

import com.felfel.dealer_vehicle_inventory_module.vehicle.Data.Vehicle;
import com.felfel.dealer_vehicle_inventory_module.vehicle.Data.VehicleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * The interface Vehicle repo.
 */
@Repository
public interface VehicleRepo extends JpaRepository<Vehicle, UUID> {

    /**
     * Find by dealer id in page.
     *
     * @param dealerIds the dealer ids
     * @param pageable  the pageable
     * @return the page
     */
    Page<Vehicle> findByDealerIdIn(List<UUID> dealerIds, Pageable pageable);

    /**
     * Find by dealer id in and model containing ignore case page.
     *
     * @param dealerIds the dealer ids
     * @param model     the model
     * @param pageable  the pageable
     * @return the page
     */
    Page<Vehicle> findByDealerIdInAndModelContainingIgnoreCase(List<UUID> dealerIds, String model, Pageable pageable);

    /**
     * Find by dealer id in and status page.
     *
     * @param dealerIds the dealer ids
     * @param status    the status
     * @param pageable  the pageable
     * @return the page
     */
    Page<Vehicle> findByDealerIdInAndStatus(List<UUID> dealerIds, VehicleStatus status, Pageable pageable);

    /**
     * Find by dealer id in and price greater than equal page.
     *
     * @param dealerIds the dealer ids
     * @param priceMin  the price min
     * @param pageable  the pageable
     * @return the page
     */
    Page<Vehicle> findByDealerIdInAndPriceGreaterThanEqual(List<UUID> dealerIds, BigDecimal priceMin, Pageable pageable);

    /**
     * Find by model containing ignore case page.
     *
     * @param model    the model
     * @param pageable the pageable
     * @return the page
     */
    Page<Vehicle> findByModelContainingIgnoreCase(String model, Pageable pageable);

    /**
     * Find by status page.
     *
     * @param status   the status
     * @param pageable the pageable
     * @return the page
     */
    Page<Vehicle> findByStatus(VehicleStatus status, Pageable pageable);

    /**
     * Find by price greater than equal page.
     *
     * @param priceMin the price min
     * @param pageable the pageable
     * @return the page
     */
    Page<Vehicle> findByPriceGreaterThanEqual(BigDecimal priceMin, Pageable pageable);

    /**
     * Find by price less than equal page.
     *
     * @param priceMax the price max
     * @param pageable the pageable
     * @return the page
     */
    Page<Vehicle> findByPriceLessThanEqual(BigDecimal priceMax, Pageable pageable);

    /**
     * Find by dealer id in and price less than equal page.
     *
     * @param dealerIds the dealer ids
     * @param priceMax  the price max
     * @param pageable  the pageable
     * @return the page
     */
    Page<Vehicle> findByDealerIdInAndPriceLessThanEqual(List<UUID> dealerIds, BigDecimal priceMax, Pageable pageable);
}

