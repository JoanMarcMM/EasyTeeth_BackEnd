package com.easyteeth.EasyTeeth.controller;
import com.easyteeth.EasyTeeth.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {

	Optional<Treatment> findByName(String name);
    boolean existsByName(String name);
    @Query("SELECT t FROM Treatment t LEFT JOIN FETCH t.specialities WHERE t.id = :id")
    Optional<Treatment> findByIdWithSpecialities(@Param("id") Long id);

}