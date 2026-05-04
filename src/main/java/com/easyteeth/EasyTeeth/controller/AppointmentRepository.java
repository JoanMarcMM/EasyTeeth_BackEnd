package com.easyteeth.EasyTeeth.controller;

import com.easyteeth.EasyTeeth.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDate(LocalDateTime date);

    List<Appointment> findByDateBetween(LocalDateTime start, LocalDateTime end);

    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByTreatmentId(Long treatmentId);

    List<Appointment> findByBoxId(Long boxId);

    List<Appointment> findByOdontologistId(Long odontologistId);

    @Query("""
        SELECT a
        FROM Appointment a
        JOIN FETCH a.patient p
        JOIN FETCH a.box b
        JOIN FETCH a.odontologist o
        JOIN FETCH a.treatment t
        WHERE a.date >= :start
          AND a.date < :end
          AND (:boxId IS NULL OR b.id = :boxId)
        ORDER BY a.date ASC
    """)
    List<Appointment> findCalendarAppointments(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("boxId") Long boxId
    );

    @Query("""
        SELECT a
        FROM Appointment a
        WHERE a.odontologist.id = :odontologistId
          AND a.date >= :start
          AND a.date < :end
        ORDER BY a.date ASC
    """)
    List<Appointment> findByOdontologistIdAndDateBetween(
            @Param("odontologistId") Long odontologistId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("""
        SELECT a
        FROM Appointment a
        WHERE a.odontologist.id = :odontologistId
          AND a.box.id = :boxId
          AND a.date >= :start
          AND a.date < :end
        ORDER BY a.date ASC
    """)
    List<Appointment> findByOdontologistIdAndBoxIdAndDateBetween(
            @Param("odontologistId") Long odontologistId,
            @Param("boxId") Long boxId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("""
        SELECT a
        FROM Appointment a
        WHERE a.box.id = :boxId
          AND a.date >= :start
          AND a.date < :end
        ORDER BY a.date ASC
    """)
    List<Appointment> findByBoxIdAndDateBetween(
            @Param("boxId") Long boxId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    void deleteByPatientId(Long patientId);
}