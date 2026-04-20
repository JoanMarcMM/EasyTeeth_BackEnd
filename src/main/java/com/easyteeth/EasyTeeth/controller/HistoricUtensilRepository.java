package com.easyteeth.EasyTeeth.controller;

import com.easyteeth.EasyTeeth.model.HistoricUtensil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistoricUtensilRepository extends JpaRepository<HistoricUtensil, Long> {

    boolean existsByNameAndModel(String name, String model);

    Optional<HistoricUtensil> findByNameAndModel(String name, String model);

    List<HistoricUtensil> findByNameContainingIgnoreCase(String name);

    List<HistoricUtensil> findByBrand(String brand);

    List<HistoricUtensil> findBySupplierId(Long supplierId);
}
