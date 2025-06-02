package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Patient extends Person {
    // Atribut
    private LocalDate tanggalLahir;
    private String jenisKelamin;
    private String golonganDarah;
    private MedicalRecord medicalRecord;
    private List<Appointment> daftarJanjiTemu;
    private List<Prescription> daftarResepObat;

    // Constructor
    public Patient(String userID, String nama, String alamat, String nomorTelepon, String email, String password,
                   LocalDate tanggalLahir, String jenisKelamin, String golonganDarah) {
        super(userID, nama, alamat, nomorTelepon, email, password);
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
        this.golonganDarah = golonganDarah;
        this.medicalRecord = new MedicalRecord(this);
        this.daftarJanjiTemu = new ArrayList<>();
        this.daftarResepObat = new ArrayList<>();
    }

    // Getter untuk medicalRecord
    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getGolonganDarah() {
        return golonganDarah;
    }

    public void setGolonganDarah(String golonganDarah) {
        this.golonganDarah = golonganDarah;
    }

    public List<Appointment> getDaftarJanjiTemu() {
        return daftarJanjiTemu;
    }

    public List<Prescription> getDaftarResepObat() {
        return daftarResepObat;
    }

    // Function untuk membuat janji temu dengan dokter
    public Appointment buatJanjiTemu(Doctor doctor, LocalDateTime waktuJanji, String keluhan) {
        System.out.println("Pasien " + getNama() + " meminta janji temu dengan Dr. " + doctor.getNama() +
                           " pada " + waktuJanji + " dengan keluhan: " + keluhan);
        System.out.println("Fungsi buatJanjiTemu perlu implementasi lebih lanjut dengan AppointmentService.");
        return null;
    }

    // Function untuk menambah janji temu ke dalam list
    public void tambahJanjiTemu(Appointment appointment) {
        if (appointment != null && appointment.getPasien().equals(this)) {
            this.daftarJanjiTemu.add(appointment);
        }
    }

    // Function untuk membatalkan janji temu
    public boolean batalkanJanjiTemu(Appointment appointment) {
        if (appointment == null || !this.daftarJanjiTemu.contains(appointment)) {
            System.out.println("Gagal membatalkan: Janji temu tidak ditemukan.");
            return false;
        }

        System.out.println("Janji temu ID " + appointment.getAppointmentID() + " dengan Dr. " +
                           appointment.getDokter().getNama() + " telah diminta untuk dibatalkan oleh " + getNama() + ".");
        System.out.println("Fungsi batalkanJanjiTemu perlu implementasi lebih lanjut.");
        return true;
    }

    // Function untuk melihat semua list janji temu yang ada
    public void lihatDaftarJanjiTemu() {
        if (daftarJanjiTemu.isEmpty()) {
            System.out.println(getNama() + " tidak memiliki janji temu terjadwal.");
            return;
        }
        System.out.println("Daftar Janji Temu untuk " + getNama() + ":");
        for (Appointment app : daftarJanjiTemu) {
            System.out.println("- Dr. " + app.getDokter().getNama() + " pada " + app.getWaktuJanji() + " (Status: " + app.getStatus() + ")");
        }
    }

    // Function untuk melihat rekam medisnya sendiri
    public void lihatInformasiRekamMedis() {
        System.out.println("Rekam Medis untuk Pasien: " + getNama());
        if (this.medicalRecord != null) {
            System.out.println(this.medicalRecord.toString());
        } else {
            System.out.println("Belum ada data rekam medis.");
        }
    }

    // Function untuk menambahkan resep dari dokter ke list
    public void tambahResep(Prescription prescription) {
        if (prescription != null && prescription.getPasien().equals(this)) {
            this.daftarResepObat.add(prescription);
        }
    }

    // Function untuk melihat semua resep yang didapatkan
    public void lihatDaftarResep() {
        if (daftarResepObat.isEmpty()) {
            System.out.println(getNama() + " tidak memiliki resep obat saat ini.");
            return;
        }
        System.out.println("Daftar Resep Obat untuk " + getNama() + ":");
        for (Prescription prescription : daftarResepObat) {
            System.out.println("- Resep dari Dr. " + prescription.getDokter().getNama() + " tanggal " + prescription.getTanggalResep());
        }
    }

    @Override
    public String toString() {
        return "Patient{" +
               "userID='" + getUserID() + '\'' +
               ", nama='" + getNama() + '\'' +
               ", tanggalLahir=" + tanggalLahir +
               ", jenisKelamin='" + jenisKelamin + '\'' +
               ", email='" + getEmail() + '\'' +
               '}';
    }
}
