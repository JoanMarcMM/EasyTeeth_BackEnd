package com.easyteeth.EasyTeeth.controller;
import com.easyteeth.EasyTeeth.model.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	List<Appointment>findByDate(LocalDateTime date);
	List<Appointment> findByDateBetween(LocalDateTime start, LocalDateTime end);
	List<Appointment> findByPatientId(Long patientId);
	List<Appointment> findByTreatmentId(Long treatmentId);
	List<Appointment> findByBoxId(Long boxId);
	List<Appointment> findByOdontologistId(Long odontologistId);


}