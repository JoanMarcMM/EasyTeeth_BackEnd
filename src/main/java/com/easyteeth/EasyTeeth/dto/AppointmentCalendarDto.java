package com.easyteeth.EasyTeeth.dto;
import com.easyteeth.EasyTeeth.model.*;
import com.easyteeth.EasyTeeth.controller.*;


import java.time.LocalDateTime;

public class AppointmentCalendarDto {

    private Long id;
    private String motive;
    private LocalDateTime start;
    private int durationMinutes;

    private Long boxId;
    private int boxNum;

    private Long treatmentId;
    private String treatmentName;

    private Long patientId;
    private String patientFullName;

    private Long odontologistId;
    private String odontologistFullName;

    public AppointmentCalendarDto() {
    }

    public AppointmentCalendarDto(
            Long id,
            String motive,
            LocalDateTime start,
            int durationMinutes,
            Long boxId,
            int boxNum,
            Long treatmentId,
            String treatmentName,
            Long patientId,
            String patientFullName,
            Long odontologistId,
            String odontologistFullName
    ) {
        this.id = id;
        this.motive = motive;
        this.start = start;
        this.durationMinutes = durationMinutes;
        this.boxId = boxId;
        this.boxNum = boxNum;
        this.treatmentId = treatmentId;
        this.treatmentName = treatmentName;
        this.patientId = patientId;
        this.patientFullName = patientFullName;
        this.odontologistId = odontologistId;
        this.odontologistFullName = odontologistFullName;
    }

    public Long getId() {
        return id;
    }

    public String getMotive() {
        return motive;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public Long getBoxId() {
        return boxId;
    }

    public int getBoxNum() {
        return boxNum;
    }

    public Long getTreatmentId() {
        return treatmentId;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public Long getPatientId() {
        return patientId;
    }

    public String getPatientFullName() {
        return patientFullName;
    }

    public Long getOdontologistId() {
        return odontologistId;
    }

    public String getOdontologistFullName() {
        return odontologistFullName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMotive(String motive) {
        this.motive = motive;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }

    public void setBoxNum(int boxNum) {
        this.boxNum = boxNum;
    }

    public void setTreatmentId(Long treatmentId) {
        this.treatmentId = treatmentId;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public void setPatientFullName(String patientFullName) {
        this.patientFullName = patientFullName;
    }

    public void setOdontologistId(Long odontologistId) {
        this.odontologistId = odontologistId;
    }

    public void setOdontologistFullName(String odontologistFullName) {
        this.odontologistFullName = odontologistFullName;
    }
}