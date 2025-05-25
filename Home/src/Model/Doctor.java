package Model;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends Person {
    private String spesialisasi;
    private String nomorSTR;
    private List<Schedule> daftarJadwalKetersediaan;
    private List<Appointment> daftarJanjiTemuPasien;

    public Doctor(String userID, String nama, String alamat, String nomorTelepon, String email, String password,
                  String spesialisasi, String nomorSTR) {
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

    public void tambahJadwalKetersediaan(Schedule jadwal) {
        if (jadwal != null && jadwal.getDokter().equals(this)) {
            this.daftarJadwalKetersediaan.add(jadwal);
            System.out.println("Jadwal baru ditambahkan untuk Dr. " + getNama() + " pada " + jadwal.getHari() + ", " + jadwal.getJamMulai() + " - " + jadwal.getJamSelesai());
        } else {
            System.out.println("Gagal menambahkan jadwal: jadwal tidak valid atau bukan untuk dokter ini.");
        }
    }

    public void hapusJadwalKetersediaan(Schedule jadwal) {
        if (this.daftarJadwalKetersediaan.remove(jadwal)) {
            System.out.println("Jadwal Dr. " + getNama() + " pada " + jadwal.getHari() + " telah dihapus.");
        } else {
            System.out.println("Gagal menghapus jadwal: jadwal tidak ditemukan.");
        }
    }

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

    public void tambahJanjiTemu(Appointment appointment) {
        if (appointment != null && appointment.getDokter().equals(this)) {
            this.daftarJanjiTemuPasien.add(appointment);
            System.out.println("Janji temu baru dengan " + appointment.getPasien().getNama() + " ditambahkan untuk Dr. " + getNama());
        } else {
            System.out.println("Gagal menambahkan janji temu: data tidak valid.");
        }
    }
  
    public void lihatSemuaJanjiTemu() {
        if (daftarJanjiTemuPasien.isEmpty()) {
            System.out.println("Dr. " + getNama() + " tidak memiliki janji temu saat ini.");
            return;
        }
        System.out.println("Daftar Janji Temu Dr. " + getNama() + ":");
        for (Appointment janji : daftarJanjiTemuPasien) {
            System.out.println("- Pasien: " + janji.getPasien().getNama() + ", Waktu: " + janji.getWaktuJanji() + ", Status: " + janji.getStatus());
        }
    }

    public Prescription buatResep(Patient pasien, List<String> detailObat) {
        System.out.println("Dr. " + getNama() + " membuat resep untuk pasien " + pasien.getNama() + ".");
        System.out.println("Detail Obat: " + detailObat);
        return null;
    }

    public void lihatRekamMedisPasien(Patient pasien) {
        System.out.println("Dr. " + getNama() + " melihat rekam medis untuk pasien " + pasien.getNama() + ".");
    }

    public void lakukanKonsultasi(Patient pasien, String catatanKonsultasi) {
        System.out.println("Dr. " + getNama() + " melakukan konsultasi dengan pasien " + pasien.getNama() + ".");
        System.out.println("Catatan Konsultasi: " + catatanKonsultasi);
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
