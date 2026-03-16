package com.easyteeth.EasyTeeth.controller;
import com.easyteeth.EasyTeeth.model.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BackgroundRepository extends JpaRepository<Background, Long> {

    List<Background> findByPatientId(Long patientId);

    boolean existsByPatientId(Long patientId);
    
    void deleteByPatientId(Long patientId);
}