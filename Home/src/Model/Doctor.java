package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Doctor extends Person {
    // Atributte
    private String spesialisasi;
    private String nomorSTR;
    private List<Schedule> daftarJadwalKetersediaan;
    private List<Appointment> daftarJanjiTemuPasien;

    // Constructor
    public Doctor(String userID, String nama, String alamat, String nomorTelepon, String email, String password, String spesialisasi, String nomorSTR) {
        super(userID, nama, alamat, nomorTelepon, email, password);
        this.spesialisasi = spesialisasi;
        this.nomorSTR = nomorSTR;
        this.daftarJadwalKetersediaan = new ArrayList<>();
        this.daftarJanjiTemuPasien = new ArrayList<>();
    }

    public String getSpesialisasi() {
        return spesialisasi;
    }

    public void setSpesialisasi(String spesialisasi) {
        this.spesialisasi = spesialisasi;
    }

    public String getNomorSTR() {
        return nomorSTR;
    }

    public void setNomorSTR(String nomorSTR) {
        this.nomorSTR = nomorSTR;
    }

    public List<Schedule> getDaftarJadwalKetersediaan() {
        return daftarJadwalKetersediaan;
    }

    public List<Appointment> getDaftarJanjiTemuPasien() {
        return daftarJanjiTemuPasien;
    }
    
    public Prescription buatResep(Patient pasien, List<MedicineUsage> detailPenggunaanObat, String catatanTambahan) {
        if (pasien == null || detailPenggunaanObat == null || detailPenggunaanObat.isEmpty()) {
            System.out.println("Tidak bisa membuat resep: pasien atau detail obat tidak lengkap.");
            return null;
        }

        // Membuat ID Resep yang unik
        String prescriptionID = "PRES-" + pasien.getUserID() + "-" + System.currentTimeMillis() % 100000;

        Prescription resepBaru = new Prescription(prescriptionID, this, pasien, LocalDate.now(), catatanTambahan);

        for (MedicineUsage mu : detailPenggunaanObat) {
            resepBaru.tambahObatKeResep(mu);
        }

        if (pasien.getDaftarResepObat() != null) {
            pasien.tambahResep(resepBaru);
        }

        System.out.println("Dr. " + getNama() + " berhasil membuat resep ID: " + resepBaru.getPrescriptionID() + " untuk pasien " + pasien.getNama() + ".");
        return resepBaru;
    }

    // Metode lihatRekamMedisPasien dan lakukanKonsultasi juga perlu implementasi yang lebih baik
    public void lihatRekamMedisPasien(Patient pasien) {
        if (pasien != null && pasien.getMedicalRecord() != null) {
            System.out.println("Dr. " + getNama() + " melihat rekam medis untuk pasien " + pasien.getNama() + ":");
            System.out.println(pasien.getMedicalRecord().toString());
        } else {
            System.out.println("Tidak dapat menampilkan rekam medis.");
        }
    }

    public void lakukanKonsultasi(Patient pasien, String diagnosis, String penanganan, Doctor dokterPemeriksa) {
        System.out.println("Dr. " + getNama() + " melakukan konsultasi dengan pasien " + pasien.getNama() + ".");
        if (pasien != null && pasien.getMedicalRecord() != null) {
            pasien.getMedicalRecord().tambahRiwayatKunjungan(LocalDate.now(), diagnosis, penanganan, dokterPemeriksa);
            System.out.println("Catatan konsultasi ditambahkan ke rekam medis.");
        } else {
            System.out.println("Tidak dapat menambahkan catatan konsultasi ke rekam medis.");
        }
    }

    // Function untuk menambah jadwal ketersediaan dokter
    public void tambahJadwalKetersediaan(Schedule jadwal) {
        if (jadwal != null && jadwal.getDokter().equals(this)) {
            this.daftarJadwalKetersediaan.add(jadwal);
            System.out.println("Jadwal baru ditambahkan untuk Dr. " + getNama() + " pada " + jadwal.getHari() + ", " + jadwal.getJamMulai() + " - " + jadwal.getJamSelesai());
        } else {
            System.out.println("Gagal menambahkan jadwal: jadwal tidak valid atau bukan untuk dokter ini.");
        }
    }

    // Function untuk menghapus jadwal yang dokter tidak bisa
    public void hapusJadwalKetersediaan(Schedule jadwal) {
        if (this.daftarJadwalKetersediaan.remove(jadwal)) {
            System.out.println("Jadwal Dr. " + getNama() + " pada " + jadwal.getHari() + " telah dihapus.");
        } else {
            System.out.println("Gagal menghapus jadwal: jadwal tidak ditemukan.");
        }
    }

    // Function untuk memperlihatkan semua jadwal dokter yang tersedia
    public void lihatSemuaJadwalKetersediaan() {
        if (daftarJadwalKetersediaan.isEmpty()) {
            System.out.println("Dr. " + getNama() + " belum memiliki jadwal ketersediaan.");
            return;
        }
        System.out.println("Jadwal Ketersediaan Dr. " + getNama() + " (" + this.spesialisasi + "):");
        for (Schedule jadwal : daftarJadwalKetersediaan) {
            System.out.println("- " + jadwal.toString());
        }
    }

    // Function untuk menambahkan janji temu dokter
    public void tambahJanjiTemu(Appointment appointment) {
        if (appointment != null && appointment.getDokter().equals(this)) {
            this.daftarJanjiTemuPasien.add(appointment);
            System.out.println("Janji temu baru dengan " + appointment.getPasien().getNama() + " ditambahkan untuk Dr. " + getNama());
        } else {
            System.out.println("Gagal menambahkan janji temu: data tidak valid.");
        }
    }

    // Function untuk memperlihatkan semua janji temu dokter
    public void lihatSemuaJanjiTemu() {
        if (daftarJanjiTemuPasien.isEmpty()) {
            System.out.println("Dr. " + getNama() + " tidak memiliki janji temu saat ini.");
            return;
        }
        System.out.println("Daftar Janji Temu Dr. " + getNama() + ":");
        for (Appointment janji : daftarJanjiTemuPasien) {
            System.out.println("- ID: " + janji.getAppointmentID() + " | Pasien: " + janji.getPasien().getNama() + ", Waktu: " + janji.getWaktuJanji() + ", Status: " + janji.getStatus());
        }
    }

    @Override
    public String toString() {
        return "Doctor{" +
               "userID='" + getUserID() + '\'' +
               ", nama='" + getNama() + '\'' +
               ", spesialisasi='" + spesialisasi + '\'' +
               ", nomorSTR='" + nomorSTR + '\'' +
               ", email='" + getEmail() + '\'' +
               '}';
    }
}
