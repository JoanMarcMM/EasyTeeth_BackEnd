package com.easyteeth.EasyTeeth.controller;
import com.easyteeth.EasyTeeth.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UtensilRepository extends JpaRepository<Utensil, Long> {

	boolean existsByNameAndModel(String name, String model);
    Optional<Utensil> findByNameAndModel(String name, String model);

}