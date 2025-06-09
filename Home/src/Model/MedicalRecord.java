package Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecord {
    // Attribute
    private String recordID;
    private Patient patient;
    private List<DiagnosisEntry> riwayatKunjungan; // Kelas baru untuk detail kunjungan

    // Kelas inner atau kelas terpisah untuk entri diagnosis
    public static class DiagnosisEntry {
        LocalDate tanggalKunjungan;
        String diagnosis;
        String penangananAtauObat;
        Doctor dokterPemeriksa;

        public DiagnosisEntry(LocalDate tanggalKunjungan, String diagnosis, String penangananAtauObat, Doctor dokterPemeriksa) {
            this.tanggalKunjungan = tanggalKunjungan;
            this.diagnosis = diagnosis;
            this.penangananAtauObat = penangananAtauObat;
            this.dokterPemeriksa = dokterPemeriksa;
        }

        @Override
        public String toString() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
            String kunjunganStr = (tanggalKunjungan != null) ? tanggalKunjungan.format(formatter) : "N/A";
            String dokterStr = (dokterPemeriksa != null) ? dokterPemeriksa.getNama() : "N/A";
            return "Tanggal: " + kunjunganStr +
                    ", Diagnosis: '" + diagnosis + '\'' +
                    ", Penanganan/Obat: '" + penangananAtauObat + '\'' +
                    ", Oleh Dr.: " + dokterStr;
        }
    }

    // Constructor
    public MedicalRecord(Patient patient) {
        if (patient == null) {
            System.out.println("Pasien tidak boleh null untuk MedicalRecord.");
        } else {
            this.recordID = "MR-" + patient.getUserID() + "-" + (System.currentTimeMillis() % 10000);
            this.patient = patient;
            this.riwayatKunjungan = new ArrayList<>();
        }
    }

    public String getRecordID() {
        return recordID;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getPatientName() {
        return (this.patient != null) ? this.patient.getNama() : "N/A";
    }

    public List<DiagnosisEntry> getRiwayatKunjungan() {
        return new ArrayList<>(riwayatKunjungan);
    }

    // Function untuk menambah riwayat kunjungan
    public void tambahRiwayatKunjungan(LocalDate tanggal, String diagnosis, String penanganan, Doctor dokter) {
        if (tanggal == null || diagnosis == null || diagnosis.trim().isEmpty()) {
            System.out.println("Tanggal dan diagnosis tidak boleh kosong untuk menambah riwayat.");
            return;
        }
        this.riwayatKunjungan.add(new DiagnosisEntry(tanggal, diagnosis, penanganan, dokter));
        System.out.println("Riwayat kunjungan baru ditambahkan untuk pasien: " + this.patient.getNama());
    }

    @Override
    public String toString() {
        String recordStr = "MedicalRecord{\n";
        recordStr += "  recordID='" + recordID + "',\n";
        recordStr += "  patientName='" + getPatientName() + "',\n";
        recordStr += "  Riwayat Kunjungan Medis: [\n";
        if (riwayatKunjungan.isEmpty()) {
            recordStr += "    (Belum ada riwayat kunjungan)\n";
        } else {
            for (DiagnosisEntry entry : riwayatKunjungan) {
                recordStr += "    - " + entry.toString() + "\n";
            }
        }
        recordStr += "  ]\n";
        recordStr += "}";
        return recordStr;
    }
}
