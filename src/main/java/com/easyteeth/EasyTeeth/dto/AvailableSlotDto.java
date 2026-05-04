package com.easyteeth.EasyTeeth.dto;

import com.easyteeth.EasyTeeth.model.*;
import com.easyteeth.EasyTeeth.controller.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

public class AvailableSlotDto {
    private LocalDate date;
    private String dayOfWeek;
    private List<TimeSlot> timeSlots;

    public AvailableSlotDto() {}

    public AvailableSlotDto(LocalDate date, String dayOfWeek, List<TimeSlot> timeSlots) {
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.timeSlots = timeSlots;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public static class TimeSlot {
        private String period; // "MORNING" or "AFTERNOON"
        private String startTime;
        private String endTime;
        private List<AppointmentSlot> appointmentSlots; // Individual 55-min slots

        public TimeSlot() {
            this.appointmentSlots = new ArrayList<>();
        }

        public TimeSlot(String period, java.time.LocalTime startTime, java.time.LocalTime endTime) {
            this.period = period;
            this.startTime = startTime != null ? String.format("%02d:%02d", startTime.getHour(), startTime.getMinute()) : "";
            this.endTime = endTime != null ? String.format("%02d:%02d", endTime.getHour(), endTime.getMinute()) : "";
            this.appointmentSlots = new ArrayList<>();
        }

        public TimeSlot(String period, java.time.LocalTime startTime, java.time.LocalTime endTime, List<AppointmentSlot> appointmentSlots) {
            this.period = period;
            this.startTime = startTime != null ? String.format("%02d:%02d", startTime.getHour(), startTime.getMinute()) : "";
            this.endTime = endTime != null ? String.format("%02d:%02d", endTime.getHour(), endTime.getMinute()) : "";
            this.appointmentSlots = appointmentSlots != null ? appointmentSlots : new ArrayList<>();
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public List<AppointmentSlot> getAppointmentSlots() {
            return appointmentSlots;
        }

        public void setAppointmentSlots(List<AppointmentSlot> appointmentSlots) {
            this.appointmentSlots = appointmentSlots;
        }

        // Deprecated but kept for backward compatibility
        @Deprecated
        public int getAvailableSlots() {
            return (int) appointmentSlots.stream().filter(AppointmentSlot::isAvailable).count();
        }
    }

    public static class AppointmentSlot {
        private String slotStart; // e.g., "13:00"
        private String slotEnd;   // e.g., "13:55"
        private boolean available;

        public AppointmentSlot() {}

        public AppointmentSlot(java.time.LocalTime slotStart, java.time.LocalTime slotEnd, boolean available) {
            this.slotStart = slotStart != null ? String.format("%02d:%02d", slotStart.getHour(), slotStart.getMinute()) : "";
            this.slotEnd = slotEnd != null ? String.format("%02d:%02d", slotEnd.getHour(), slotEnd.getMinute()) : "";
            this.available = available;
        }

        public AppointmentSlot(String slotStart, String slotEnd, boolean available) {
            this.slotStart = slotStart;
            this.slotEnd = slotEnd;
            this.available = available;
        }

        public String getSlotStart() {
            return slotStart;
        }

        public void setSlotStart(String slotStart) {
            this.slotStart = slotStart;
        }

        public String getSlotEnd() {
            return slotEnd;
        }

        public void setSlotEnd(String slotEnd) {
            this.slotEnd = slotEnd;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }
    }
}
