package com.easyteeth.EasyTeeth.seeder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.easyteeth.EasyTeeth.controller.AppointmentRepository;
import com.easyteeth.EasyTeeth.controller.StockBoxRepository;
import com.easyteeth.EasyTeeth.model.Appointment;
import com.easyteeth.EasyTeeth.model.StockBox;
import com.easyteeth.EasyTeeth.model.Treatment;
import com.easyteeth.EasyTeeth.model.TreatmentUtensil;

@Configuration
public class DataInitializer {

    @Bean
    ApplicationRunner seedDatabase(SeedService seedService, SeedServiceTest seedServiceTest ){
        return args -> {

            seedService.seedUsersIfMissing();
            seedService.seedToothIfMissing();
            seedService.seedSideIfMissing();
            seedService.seedBoxIfMissing();
            seedService.seedStorageIfMissing();
            seedService.seedTreatmentsIfMissing();
            seedService.seedSpecialitiesIfMissingWithTreatments();
            seedService.seedPathologyIfMissingWithTreatments();
            seedService.seedSuppliersIfMissing();
            seedService.seedUtensilsIfMissingWithTreatments();
            seedService.seedAvailabilityIfMissing();
            
            seedServiceTest.seedPatientsIfMissing();
            seedServiceTest.seedOdontologistsIfMissing();
            seedServiceTest.seedAppointmentsIfMissing();
            
            seedServiceTest.seedOdontogramsIfMissing();
            seedServiceTest.seedBackgroundsIfMissing();
        };
    }

    @Bean
    ApplicationRunner cleanupOldStockBox(StockBoxRepository stockBoxRepository) {
        return args -> {
            try {
                LocalDate today = LocalDate.now();
                List<StockBox> allStockBoxes = stockBoxRepository.findAll();
                
                for (StockBox stockBox : allStockBoxes) {
                    if (stockBox.getDay() != null && stockBox.getDay().isBefore(today)) {
                        stockBoxRepository.deleteById(stockBox.getId());
                    }
                }
                
                System.out.println("StockBox cleanup completed: removed entries with past dates");
            } catch (Exception e) {
                System.err.println("Error during StockBox cleanup: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }

   
}