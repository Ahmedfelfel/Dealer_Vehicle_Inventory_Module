//package com.felfel.dealer_vehicle_inventory_module.system;
//
//
//import com.felfel.dealer_vehicle_inventory_module.vehicle.Data.Vehicle;
//import com.felfel.dealer_vehicle_inventory_module.vehicle.Data.VehicleStatus;
//import com.felfel.dealer_vehicle_inventory_module.vehicle.repository.VehicleRepo;
////import com.felfel.dealer_vehicle_inventory_module.dealer.repository.DealerRepo;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Component;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.UUID;
//
///**
// * The type Db data initializer.
// */
//@SuppressWarnings("RedundantThrows")
//@Component
//@Profile("dev")
//public class DBDataInitializer implements CommandLineRunner {
//
//    private final VehicleRepo vehicleRepo;
//
//    //private final DealerRepo dealerRepo;
//
//
//    public DBDataInitializer(VehicleRepo vehicleRepo//, DealerRepo dealerRepo
//     )
//    {
//        this.vehicleRepo = vehicleRepo;
//       // this.dealerRepo = dealerRepo;
//    }
//
//
//
//    @Override
//    public void run(String... args) throws Exception {
//        // Initialize your database with sample data here
//        Vehicle v1 = new Vehicle(
//                UUID.randomUUID(),
//                "ABC",
//                UUID.randomUUID(),
//                "model1",
//                BigDecimal.valueOf(2199.99),
//                VehicleStatus.AVAILABLE
//        );
//        Vehicle v2 = new Vehicle(
//                UUID.randomUUID(),
//                "XYZ",
//                UUID.randomUUID(),
//                "model2",
//                BigDecimal.valueOf(1999.99),
//                VehicleStatus.AVAILABLE
//        );
//        Vehicle v3 = new Vehicle(
//                UUID.randomUUID(),
//                "KLM",
//                UUID.randomUUID(),
//                "model3",
//                BigDecimal.valueOf(2499.99),
//                VehicleStatus.AVAILABLE
//        );
//        vehicleRepo.saveAll(List.of(v1, v2, v3));
//    }
//}
