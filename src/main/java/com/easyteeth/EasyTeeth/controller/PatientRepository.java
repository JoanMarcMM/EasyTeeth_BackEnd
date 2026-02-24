package com.easyteeth.EasyTeeth.controller;
import com.easyteeth.EasyTeeth.model.*;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

	List<Patient>findByNameContainingIgnoreCase(String name);
	List<Patient>findByDniContainingIgnoreCase(String dni);
	List<Patient>findBySsnContainingIgnoreCase(String ssn);


}