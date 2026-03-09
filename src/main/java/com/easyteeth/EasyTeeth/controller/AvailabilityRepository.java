package com.easyteeth.EasyTeeth.controller;
import com.easyteeth.EasyTeeth.model.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

	boolean existsByDayOfWeekAndMorningAndAfternoon(String DayOfWeek, boolean Morning, boolean Afternoon);


}