package Model;

import java.util.Date;

public class MedicalRecord {
    private int id;
    private String patientName;
    private String diagnosis;
    private String medications;
    private Date visitDate;

    public MedicalRecord(int id, String patientName, String diagnosis, String medications, Date visitDate) {
        this.id = id;
        this.patientName = patientName;
        this.diagnosis = diagnosis;
        this.medications = medications;
        this.visitDate = visitDate;
    } // asking for each of the parameters
  
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDiagnosis() {
        return diagnosis;
    }
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getMedications() {
        return medications;
    }
    public void setMedications(String medications) {
        this.medications = medications;
    }

    public Date getVisitDate() {
        return visitDate;
    }
    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    } // getter setter for Medical Record

    @Override
    public String toString() {
        return "MedicalRecord{" +
                "id=" + id +
                ", patientName='" + patientName + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                ", medications='" + medications + '\'' +
                ", visitDate=" + visitDate +
                '}';
    } // Medical Record Report so Patient can see
  // to string used for displaying information fast and simple
}
