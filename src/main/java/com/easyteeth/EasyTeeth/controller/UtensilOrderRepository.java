package com.easyteeth.EasyTeeth.controller;

import com.easyteeth.EasyTeeth.model.UtensilOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface UtensilOrderRepository extends JpaRepository<UtensilOrder, Long> {

    List<UtensilOrder> findByArrived(boolean arrived);

    List<UtensilOrder> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);
}
