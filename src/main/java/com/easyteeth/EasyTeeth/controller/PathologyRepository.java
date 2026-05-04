package com.easyteeth.EasyTeeth.controller;
import com.easyteeth.EasyTeeth.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PathologyRepository extends JpaRepository<Pathology, Long> {

	Optional<Pathology> findByName(String name);
    boolean existsByName(String name);
	
	@Query("SELECT p FROM Pathology p LEFT JOIN FETCH p.treatments WHERE p.id = :id")
	Optional<Pathology> findByIdWithTreatments(@Param("id") Long id);
}