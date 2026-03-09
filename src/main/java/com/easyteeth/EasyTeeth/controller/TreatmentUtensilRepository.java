package com.easyteeth.EasyTeeth.controller;
import com.easyteeth.EasyTeeth.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TreatmentUtensilRepository extends JpaRepository<TreatmentUtensil, Long> {
    boolean existsByTreatmentAndUtensil(Treatment treatment, Utensil utensil);
    
    List<TreatmentUtensil> findByUtensilId(Long utensilId);

    List<TreatmentUtensil> findByTreatmentId(Long treatmentId);

    Optional<TreatmentUtensil> findByUtensilIdAndTreatmentId(Long utensilId, Long treatmentId);
    
}