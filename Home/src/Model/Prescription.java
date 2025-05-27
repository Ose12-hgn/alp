package Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Prescription {
    private String prescriptionID; // ID for meds recipe
    private Doctor doctor;
    private Patient patient;
    private LocalDate tanggalResep;
    private List<MedicineUsage> daftarObatDalamResep;
    private String statusResep; // Example: "New, In Progress, Done"
    private String catatanTambahanDokter; // Notes from doctor

    // Constructor
    public Prescription(String prescriptionID, Doctor doctor, Patient patient, LocalDate tanggalResep, String catatanTambahanDokter) {
        if (prescriptionID == null || prescriptionID.trim().isEmpty() || doctor == null || patient == null || tanggalResep == null) {
            throw new IllegalArgumentException("Parameter ID, Dokter, Pasien, dan Tanggal Resep tidak boleh null atau kosong.");
        }
        this.prescriptionID = prescriptionID;
        this.doctor = doctor;
        this.patient = patient;
        this.tanggalResep = tanggalResep;
        this.daftarObatDalamResep = new ArrayList<>();
        this.statusResep = "BARU"; // Status for recipe
        this.catatanTambahanDokter = (catatanTambahanDokter == null) ? "" : catatanTambahanDokter;
    }

    public String getPrescriptionID() {
        return prescriptionID;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public LocalDate getTanggalResep() {
        return tanggalResep;
    }

    public List<MedicineUsage> getDaftarObatDalamResep() {
        // taking list so that it can't be modified by other person
        return new ArrayList<>(daftarObatDalamResep);
    }

    public String getStatusResep() {
        return statusResep;
    }

    public String getCatatanTambahanDokter() {
        return catatanTambahanDokter;
    }
  
    public void setStatusResep(String statusResep) {
        if (statusResep == null || statusResep.trim().isEmpty()) {
            System.out.println("Status resep tidak boleh null atau kosong.");
            return;
        }
        // Changing transision
        this.statusResep = statusResep;
        System.out.println("Status resep ID " + this.prescriptionID + " diubah menjadi " + statusResep);
    }

    public void setCatatanTambahanDokter(String catatanTambahanDokter) {
        this.catatanTambahanDokter = (catatanTambahanDokter == null) ? "" : catatanTambahanDokter;
    } //Getter Setter

    // Processing Meds Recipe
    public void tambahObatKeResep(MedicineUsage medicineUsage) {
        if (medicineUsage != null) {
            this.daftarObatDalamResep.add(medicineUsage);
            System.out.println("Obat '" + medicineUsage.getMedicine().getNamaItem() + "' ditambahkan ke resep ID " + this.prescriptionID);
        } else {
            System.out.println("Gagal menambahkan obat: MedicineUsage tidak boleh null.");
        }
    }

    public boolean hapusObatDariResep(MedicineUsage medicineUsage) {
        if (medicineUsage != null && this.daftarObatDalamResep.remove(medicineUsage)) {
            System.out.println("Obat '" + medicineUsage.getMedicine().getNamaItem() + "' dihapus dari resep ID " + this.prescriptionID);
            return true;
        } else {
            System.out.println("Gagal menghapus obat: Obat tidak ditemukan dalam resep atau input tidak valid.");
            return false;
        }
    }

    // Other Method
    public boolean isResepKosong() {
        return this.daftarObatDalamResep.isEmpty();
    }

    public boolean isKadaluarsa(int durasiBerlakuResepDalamHari) {
        if (durasiBerlakuResepDalamHari <= 0) {
            return false; // Duration of expired
        }
        return LocalDate.now().isAfter(this.tanggalResep.plusDays(durasiBerlakuResepDalamHari));
    }

    @Override
public String toString() {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");

    String prescriptionDate = (tanggalResep != null) ? tanggalResep.format(dateFormatter) : "N/A";
    String doctorName = (doctor != null) ? doctor.getNama() : "N/A";
    String doctorSpecialist = (doctor != null) ? doctor.getSpesialisasi() : "N/A";
    String patientName = (patient != null) ? patient.getNama() : "N/A";
    String catatan = (catatanTambahanDokter == null || catatanTambahanDokter.isEmpty()) ? "Tidak ada" : catatanTambahanDokter;

    String daftarObatText = "";
    if (daftarObatDalamResep.isEmpty()) {
        daftarObatText = "    (Tidak ada obat dalam resep ini)\n";
    } else {
        for (MedicineUsage mu : daftarObatDalamResep) {
            daftarObatText += "    - " + mu.toString() + "\n";
        }
    }

    String result =
        "Prescription{\n" +
        "  prescriptionID='" + prescriptionID + "',\n" +
        "  tanggalResep=" + prescriptionDate + ",\n" +
        "  doctor=" + doctorName + " (Spesialis: " + doctorSpecialist + "),\n" +
        "  patient=" + patientName + ",\n" +
        "  statusResep='" + statusResep + "',\n" +
        "  catatanDokter='" + catatan + "',\n" +
        "  daftarObatDalamResep=[\n" +
        daftarObatText +
        "  ]\n" +
        "}";

    return result;
}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prescription that = (Prescription) o;
        return prescriptionID.equals(that.prescriptionID);
    }

    @Override
    public int hashCode() {
        return prescriptionID.hashCode();
    }
}
