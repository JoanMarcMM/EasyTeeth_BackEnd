package com.easyteeth.EasyTeeth.controller;
import com.easyteeth.EasyTeeth.model.*;
import java.time.LocalDateTime;

public class AppointmentRequest {
    private String motive;
    private LocalDateTime date;

    private Long patientId;
    private Long boxId;
    private Long odontologistId;
    private Long treatmentId;

    public AppointmentRequest() {}

    public String getMotive() { return motive; }
    public void setMotive(String motive) { this.motive = motive; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public Long getBoxId() { return boxId; }
    public void setBoxId(Long boxId) { this.boxId = boxId; }

    public Long getOdontologistId() { return odontologistId; }
    public void setOdontologistId(Long odontologistId) { this.odontologistId = odontologistId; }

    public Long getTreatmentId() { return treatmentId; }
    public void setTreatmentId(Long treatmentId) { this.treatmentId = treatmentId; }
}