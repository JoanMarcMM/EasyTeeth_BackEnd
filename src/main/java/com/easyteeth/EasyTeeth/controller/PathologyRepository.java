package com.easyteeth.EasyTeeth.controller;
import com.easyteeth.EasyTeeth.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PathologyRepository extends JpaRepository<Pathology, Long> {

	Optional<Pathology> findByName(String name);
    boolean existsByName(String name);
	
}