package com.felfel.dealer_vehicle_inventory_module.dealer.repository;

import com.felfel.dealer_vehicle_inventory_module.dealer.Data.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DealerRepo extends JpaRepository<Dealer, UUID> {
}
