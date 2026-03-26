package com.felfel.dealer_vehicle_inventory_module.system;


import com.felfel.dealer_vehicle_inventory_module.dealer.Data.Dealer;
import com.felfel.dealer_vehicle_inventory_module.dealer.Data.SubscriptionType;
import com.felfel.dealer_vehicle_inventory_module.dealer.repository.DealerRepo;
import com.felfel.dealer_vehicle_inventory_module.tenant.TenantContext;
import com.felfel.dealer_vehicle_inventory_module.vehicle.Data.Vehicle;
import com.felfel.dealer_vehicle_inventory_module.vehicle.Data.VehicleStatus;
import com.felfel.dealer_vehicle_inventory_module.vehicle.repository.VehicleRepo;
//import com.felfel.dealer_vehicle_inventory_module.dealer.repository.DealerRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Component to initialize the database with sample data during application startup.
 * Active only in the 'dev' profile.
 */
@SuppressWarnings("RedundantThrows")
@Component
@Profile("dev")
public class DBDataInitializer implements CommandLineRunner {

    private final VehicleRepo vehicleRepo;
    private final DealerRepo dealerRepo;

    /**
     * Constructs the data initializer.
     *
     * @param vehicleRepo The vehicle repository.
     * @param dealerRepo  The dealer repository.
     */
    public DBDataInitializer(VehicleRepo vehicleRepo, DealerRepo dealerRepo)
    {
        this.vehicleRepo = vehicleRepo;
        this.dealerRepo = dealerRepo;
    }

    /**
     * Executes the initialization logic.
     * Creates sample dealers and vehicles for tenants 'ABC' and 'XYZ'.
     */
    @Override
    public void run(String... args) throws Exception {
        // Initialize your database with sample data here
        Dealer d1 = new Dealer(
                UUID.randomUUID(),
                "ABC",
                "ahmed",
                "@1",
                SubscriptionType.BASIC
        );
        Dealer d2 = new Dealer(
                UUID.randomUUID(),
                "ABC",
                "ahmed",
                "@2",
                SubscriptionType.PREMIUM
        );
        Dealer d3 = new Dealer(
                UUID.randomUUID(),
                "XYZ",
                "felfel",
                "@3",
                SubscriptionType.PREMIUM
        );



        Vehicle v1 = new Vehicle(
                UUID.randomUUID(),
                "ABC",
                d1.getId(),
                "model1",
                BigDecimal.valueOf(2199.99),
                VehicleStatus.AVAILABLE
        );
        Vehicle v2 = new Vehicle(
                UUID.randomUUID(),
                "XYZ",
                d3.getId(),
                "model2",
                BigDecimal.valueOf(1999.99),
                VehicleStatus.AVAILABLE
        );
        Vehicle v3 = new Vehicle(
                UUID.randomUUID(),
                "ABC",
                d2.getId(),
                "model3",
                BigDecimal.valueOf(2499.99),
                VehicleStatus.AVAILABLE
        );
        Vehicle v4 = new Vehicle(
                UUID.randomUUID(),
                "ABC",
                d1.getId(),
                "model4",
                BigDecimal.valueOf(2699.99),
                VehicleStatus.AVAILABLE
        );
        Vehicle v5 = new Vehicle(
                UUID.randomUUID(),
                "XYZ",
                d3.getId(),
                "model5",
                BigDecimal.valueOf(1899.99),
                VehicleStatus.AVAILABLE
        );
        Vehicle v6 = new Vehicle(
                UUID.randomUUID(),
                "ABC",
                d2.getId(),
                "model6",
                BigDecimal.valueOf(2799.99),
                VehicleStatus.AVAILABLE
        );

        // Group by tenant to save safely
        try {
            TenantContext.setCurrentTenant("ABC");
            dealerRepo.save(d1);
            dealerRepo.save(d2);
            vehicleRepo.save(v1);
            vehicleRepo.save(v3);
            vehicleRepo.save(v4);
            vehicleRepo.save(v6);

            TenantContext.setCurrentTenant("XYZ");
            dealerRepo.save(d3);
            vehicleRepo.save(v2);
            vehicleRepo.save(v5);
        } finally {
            TenantContext.clear();
        }
    }
}
