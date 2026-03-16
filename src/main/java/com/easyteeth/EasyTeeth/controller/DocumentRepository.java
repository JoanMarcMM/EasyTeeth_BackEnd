package com.easyteeth.EasyTeeth.controller;
import com.easyteeth.EasyTeeth.model.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {

	List<Document>findByNameContainingIgnoreCase(String name);
	List<Document>findByCreationDate(LocalDateTime CreationDate);
	List<Document> findByCreationDateBetween(LocalDateTime start, LocalDateTime end);
	List<Document> findByPatientId(Long patientId);

	void deleteByPatientId(Long patientId);
}