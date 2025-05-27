package Model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Schedule {
    private String scheduleID; // ID for Schedule
    private Doctor doctor;
    private DayOfWeek dayOfWeek; // the Day
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isActive; // is the Schedule active
    private String notes; // Notes for patient
    private Queue q = new Queue;

    // Constructor
    public Schedule(String scheduleID, Doctor doctor, DayOfWeek dayOfWeek,
                    LocalTime startTime, LocalTime endTime, boolean isActive, String notes) {
        if (doctor == null || dayOfWeek == null || startTime == null || endTime == null || scheduleID == null || scheduleID.trim().isEmpty()) {
            throw new IllegalArgumentException("Parameter untuk Schedule tidak boleh null atau kosong.");
        }
        if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
            throw new IllegalArgumentException("Waktu mulai harus sebelum waktu selesai.");
        }
        this.scheduleID = scheduleID;
        this.doctor = doctor;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isActive = isActive;
        this.notes = (notes == null) ? "" : notes;
    }

    public String getScheduleID() {
        return scheduleID;
    }

    public Doctor getDoctor() {
        return doctor;
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

    public Queue getQueue() {
        return q;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getNotes() {
        return notes;
    }

    public void setDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new IllegalArgumentException("Dokter tidak boleh null.");
        }
        this.doctor = doctor;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        if (dayOfWeek == null) {
            throw new IllegalArgumentException("Hari tidak boleh null.");
        }
        this.dayOfWeek = dayOfWeek;
    }

    public void setStartTime(LocalTime startTime) {
        if (startTime == null || (this.endTime != null && (startTime.isAfter(this.endTime) || startTime.equals(this.endTime)))) {
            throw new IllegalArgumentException("Waktu mulai tidak valid atau harus sebelum waktu selesai.");
        }
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        if (endTime == null || (this.startTime != null && (endTime.isBefore(this.startTime) || endTime.equals(this.startTime)))) {
            throw new IllegalArgumentException("Waktu selesai tidak valid atau harus setelah waktu mulai.");
        }
        this.endTime = endTime;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Queue setQueue(Queue Queue) {
        this.q == Queue
    }

    public void setNotes(String notes) {
        this.notes = (notes == null) ? "" : notes;
    } // Getter Setter

    public boolean isWithinSchedule(LocalDateTime dateTime) {
        if (dateTime == null || !this.isActive) {
            return false;
        }
        // checking day and what time beetwen startTime and endTime
        return dateTime.getDayOfWeek() == this.dayOfWeek &&
               !dateTime.toLocalTime().isBefore(this.startTime) &&
               !dateTime.toLocalTime().isAfter(this.endTime); // Supposedly right at endTime if Patient make appointments in the end of the day
    }


    @Override
    public String toString() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return "Schedule{" +
               "scheduleID='" + scheduleID + '\'' +
               ", doctor=" + (doctor != null ? doctor.getNama() : "N/A") +
               ", dayOfWeek=" + dayOfWeek +
               ", startTime=" + (startTime != null ? startTime.format(timeFormatter) : "N/A") +
               ", endTime=" + (endTime != null ? endTime.format(timeFormatter) : "N/A") +
               ", isActive=" + isActive +
               ", notes='" + notes + '\'' +
               '}';
    }

    // Override equals dan hashCode if Schedule will be stored in one Set or used as key in Map
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
