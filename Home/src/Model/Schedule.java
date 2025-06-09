package Model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Queue;

public class Schedule {
    // Attribute
    private String scheduleID;
    private Doctor doctor;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isActive;
    private String notes;
    private Queue<Appointment> dailyAppointmentQueue;

    // Constructor
    public Schedule(String scheduleID, Doctor doctor, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, boolean isActive, String notes) {
        if (scheduleID == null || scheduleID.trim().isEmpty()) {
            System.out.println("Schedule ID tidak boleh null atau kosong.");
        }
        if (doctor == null) {
            System.out.println("Dokter tidak boleh null.");
        }
        if (dayOfWeek == null) {
            System.out.println("Hari tidak boleh null.");
        }
        if (startTime == null || endTime == null) {
            System.out.println("Waktu mulai dan selesai tidak boleh null.");
        }
        if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
            System.out.println("Waktu mulai harus sebelum waktu selesai.");
        } else {
            this.scheduleID = scheduleID;
            this.doctor = doctor;
            this.dayOfWeek = dayOfWeek;
            this.startTime = startTime;
            this.endTime = endTime;
            this.isActive = isActive;
            this.notes = (notes == null) ? "" : notes;
            this.dailyAppointmentQueue = new LinkedList<>();
        }
    }
    
    public Queue<Appointment> getDailyAppointmentQueue() {
        return dailyAppointmentQueue;
    }

    public void setDailyAppointmentQueue(Queue<Appointment> newQueue) {
        this.dailyAppointmentQueue = newQueue;
    }

    public String getScheduleID() {
        return scheduleID;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Doctor getDokter() {
        return doctor;
    }

    public String getHari() {
        return dayOfWeek.toString();
    }

    public String getJamMulai() {
        return startTime.toString();
    }

    public String getJamSelesai() {
        return endTime.toString();
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getNotes() {
        return notes;
    }

    public void setDoctor(Doctor doctor) {
        if (doctor == null) {
            System.out.println("Dokter tidak boleh null.");
        } else {
            this.doctor = doctor;
        }
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        if (dayOfWeek == null) {
            System.out.println("Hari tidak boleh null.");
        } else {
            this.dayOfWeek = dayOfWeek;
        }
    }

    public void setStartTime(LocalTime startTime) {
        if (startTime == null || (this.endTime != null && (startTime.isAfter(this.endTime) || startTime.equals(this.endTime)))) {
            System.out.println("Waktu mulai tidak valid atau harus sebelum waktu selesai.");
        } else {
            this.startTime = startTime;
        }
    }

    public void setEndTime(LocalTime endTime) {
        if (endTime == null || (this.startTime != null && (endTime.isBefore(this.startTime) || endTime.equals(this.startTime)))) {
            System.out.println("Waktu selesai tidak valid atau harus setelah waktu mulai.");
        } else {
            this.endTime = endTime;
        }
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setNotes(String notes) {
        this.notes = (notes == null) ? "" : notes;
    }

    // Function untuk mengecek apakah tanggal dan waktu berada dalam jadwal
    public boolean isWithinSchedule(LocalDateTime dateTime) {
        if (dateTime == null || !this.isActive) {
            return false;
        }
        return dateTime.getDayOfWeek().equals(this.dayOfWeek) && !dateTime.toLocalTime().isBefore(this.startTime) && (dateTime.toLocalTime().isBefore(this.endTime) || dateTime.toLocalTime().equals(this.endTime));
    }

    @Override
    public String toString() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String scheduleDetails = "Schedule{" +
                "scheduleID='" + scheduleID + '\'' +
                ", doctor=" + (doctor != null ? doctor.getNama() : "N/A") +
                ", dayOfWeek=" + dayOfWeek +
                ", startTime=" + (startTime != null ? startTime.format(timeFormatter) : "N/A") +
                ", endTime=" + (endTime != null ? endTime.format(timeFormatter) : "N/A") +
                ", isActive=" + isActive +
                ", notes='" + notes + '\'' +
                ", Antrean Hari Ini (size)=" + (dailyAppointmentQueue.size()) +
                '}';
        return scheduleDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return scheduleID.equals(schedule.scheduleID);
    }

    @Override
    public int hashCode() {
        return scheduleID.hashCode();
    }
}
