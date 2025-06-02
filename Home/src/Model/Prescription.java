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

    public LocalDate getTanggalResep() {
        return tanggalResep;
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

    String prescriptionDateStr = (tanggalResep != null) ? tanggalResep.format(dateFormatter) : "N/A";
    String doctorNameStr = (doctor != null ? doctor.getNama() : "N/A");
    String doctorSpecialistStr = (doctor != null ? doctor.getSpesialisasi() : "N/A");
    String patientNameStr = (patient != null ? patient.getNama() : "N/A");
    String catatanDokterStr = (catatanTambahanDokter == null || catatanTambahanDokter.isEmpty()) ? "Tidak ada" : catatanTambahanDokter;

    String result = "Prescription{\n";
    result += "  prescriptionID='" + prescriptionID + "',\n";
    result += "  tanggalResep=" + prescriptionDateStr + ",\n";
    result += "  doctor=" + doctorNameStr + " (Spesialis: " + doctorSpecialistStr + "),\n";
    result += "  patient=" + patientNameStr + ",\n";
    result += "  statusResep='" + statusResep + "',\n";
    result += "  catatanDokter='" + catatanDokterStr + "',\n";
    result += "  daftarObatDalamResep=[\n";

    if (daftarObatDalamResep.isEmpty()) {
        result += "    (Tidak ada obat dalam resep ini)\n";
    } else {
        for (int i = 0; i < daftarObatDalamResep.size(); i++) {
            MedicineUsage mu = daftarObatDalamResep.get(i);
            result += "    - " + mu.toString();
            if (i < daftarObatDalamResep.size() - 1) {
                result += ",\n"; // Tambahkan koma untuk semua kecuali item terakhir
            } else {
                result += "\n"; // Baris baru untuk item terakhir
            }
        }
    }
    result += "  ]\n";
    result += "}";
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
