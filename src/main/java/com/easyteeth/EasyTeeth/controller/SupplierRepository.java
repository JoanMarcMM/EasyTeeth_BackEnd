package com.easyteeth.EasyTeeth.controller;
import com.easyteeth.EasyTeeth.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

	Optional<Supplier> findByName(String name);
    boolean existsByName(String name);
    
    List<Supplier> findByNameContainingIgnoreCase(String name);
    
    List<Supplier> findByEmail(String email);

}