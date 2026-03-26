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

@Repository
public interface VehicleRepo extends JpaRepository<Vehicle, UUID> {

    Page<Vehicle> findByDealerIdIn(List<UUID> dealerIds, Pageable pageable);

    Page<Vehicle> findByDealerIdInAndModelContainingIgnoreCase(List<UUID> dealerIds, String model, Pageable pageable);

    Page<Vehicle> findByDealerIdInAndStatus(List<UUID> dealerIds, VehicleStatus status, Pageable pageable);

    Page<Vehicle> findByDealerIdInAndPriceGreaterThanEqual(List<UUID> dealerIds, BigDecimal priceMin, Pageable pageable);

    Page<Vehicle> findByModelContainingIgnoreCase(String model, Pageable pageable);

    Page<Vehicle> findByStatus(VehicleStatus status, Pageable pageable);

    Page<Vehicle> findByPriceGreaterThanEqual(BigDecimal priceMin, Pageable pageable);

    Page<Vehicle> findByPriceLessThanEqual(BigDecimal priceMax, Pageable pageable);

    Page<Vehicle> findByDealerIdInAndPriceLessThanEqual(List<UUID> dealerIds, BigDecimal priceMax, Pageable pageable);
}

