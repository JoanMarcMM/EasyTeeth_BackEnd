package com.easyteeth.EasyTeeth.controller;

import com.easyteeth.EasyTeeth.model.*;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByPatientId(Long patientId);

    List<Image> findByType(String type);
}