package com.easyteeth.EasyTeeth.controller;

import com.easyteeth.EasyTeeth.model.StockBox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StockBoxRepository extends JpaRepository<StockBox, Long> {

    List<StockBox> findByUtensilId(Long utensilId);

    List<StockBox> findByBoxId(Long boxId);

    List<StockBox> findByStocked(boolean stocked);

    List<StockBox> findByDay(LocalDate day);

    List<StockBox> findByDayBetween(LocalDate startDay, LocalDate endDay);

    List<StockBox> findByBoxIdAndDay(Long boxId, LocalDate day);

    boolean existsByUtensilIdAndBoxId(Long utensilId, Long boxId);

    Optional<StockBox> findByUtensilIdAndBoxIdAndDay(Long utensilId, Long boxId, LocalDate day);

    
    void deleteByDay(LocalDate day);
}
