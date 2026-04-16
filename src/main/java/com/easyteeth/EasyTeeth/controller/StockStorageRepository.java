package com.easyteeth.EasyTeeth.controller;

import com.easyteeth.EasyTeeth.model.StockStorage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockStorageRepository extends JpaRepository<StockStorage, Long> {

    List<StockStorage> findByStorageId(Long storageId);

    List<StockStorage> findByUtensilId(Long utensilId);

    boolean existsByUtensilIdAndStorageId(Long utensilId, Long storageId);

    Optional<StockStorage> findByUtensilIdAndStorageId(Long utensilId, Long storageId);
}
