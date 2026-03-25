package com.felfel.dealer_vehicle_inventory_module.vehicle.repository;

import com.felfel.dealer_vehicle_inventory_module.vehicle.Data.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface VehicleRepo extends JpaRepository<Vehicle, UUID> {
    boolean existsById(UUID id);
}

