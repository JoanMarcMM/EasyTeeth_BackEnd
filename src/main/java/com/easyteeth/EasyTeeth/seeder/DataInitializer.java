package com.easyteeth.EasyTeeth.seeder;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        };
    }
}