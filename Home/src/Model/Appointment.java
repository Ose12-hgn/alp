package Model;

import java.time.LocalDateTime;

public class Appointment {
    private String appointmentID;
    private Patient pasien;
    private Doctor dokter;
    private LocalDateTime waktuJanji;
    private String status;
    private String keluhanAwal;
    private String catatanDokter;

    public Appointment(String appointmentID, Patient pasien, Doctor dokter, LocalDateTime waktuJanji, String keluhanAwal) {
        if (pasien == null || dokter == null || waktuJanji == null || appointmentID == null || appointmentID.trim().isEmpty()) {
            throw new IllegalArgumentException("Tidak boleh kosong.");
        }
        this.appointmentID = appointmentID;
        this.pasien = pasien;
        this.dokter = dokter;
        this.waktuJanji = waktuJanji;
        this.keluhanAwal = keluhanAwal;
        this.status = "DIAJUKAN";
        this.catatanDokter = "";
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public Patient getPasien() {
        return pasien;
    }

    public Doctor getDokter() {
        return dokter;
    }

    public LocalDateTime getWaktuJanji() {
        return waktuJanji;
    }

    public String getStatus() {
        return status;
    }

    public String getKeluhanAwal() {
        return keluhanAwal;
    }

    public String getCatatanDokter() {
        return catatanDokter;
    }

    public void setWaktuJanji(LocalDateTime waktuJanji) {
        if (waktuJanji == null) {
            System.out.println("Waktu janji tidak boleh null.");
            return;
        }
        this.waktuJanji = waktuJanji;
        System.out.println("Waktu janji temu ID " + this.appointmentID + " diubah menjadi " + waktuJanji);
    }

    public void setStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            System.out.println("Status janji temu tidak boleh null atau kosong.");
            return;
        }
        this.status = status;
        System.out.println("Status janji temu ID " + this.appointmentID + " diubah menjadi " + status);
    }

    public void setKeluhanAwal(String keluhanAwal) {
        this.keluhanAwal = keluhanAwal;
    }

    public void setCatatanDokter(String catatanDokter) {
        this.catatanDokter = catatanDokter;
        System.out.println("Catatan dokter ditambahkan/diperbarui untuk janji temu ID " + this.appointmentID);
    }

    public void konfirmasiJanjiTemu() {
        if ("DIAJUKAN".equals(this.status) || "MINTA_UBAH_DISETUJUI".equals(this.status)) {
            setStatus("DIJADWALKAN");
            System.out.println("Janji temu ID " + this.appointmentID + " telah dikonfirmasi dan dijadwalkan.");
        } else {
            System.out.println("Janji temu ID " + this.appointmentID + " tidak dapat dikonfirmasi karena status saat ini: " + this.status);
        }
    }

    public void selesaikanJanjiTemu(String catatanAkhir) {
        if ("DIJADWALKAN".equals(this.status)) {
            setStatus("SELESAI");
            setCatatanDokter(catatanAkhir);
            System.out.println("Janji temu ID " + this.appointmentID + " telah selesai.");
        } else {
            System.out.println("Janji temu ID " + this.appointmentID + " tidak dapat diselesaikan karena status saat ini: " + this.status);
        }
    }

    public void batalkanJanjiTemu(String olehSiapa) {
        if (!"SELESAI".equals(this.status) && !"DIBATALKAN_PASIEN".equals(this.status) && !"DIBATALKAN_DOKTER".equals(this.status) && !"DIBATALKAN_ADMIN".equals(this.status)) {
            if ("PASIEN".equalsIgnoreCase(olehSiapa)) {
                setStatus("DIBATALKAN_PASIEN");
            } else if ("DOKTER".equalsIgnoreCase(olehSiapa)) {
                setStatus("DIBATALKAN_DOKTER");
            } else if ("ADMIN".equalsIgnoreCase(olehSiapa)) {
                setStatus("DIBATALKAN_ADMIN");
            } else {
                System.out.println("Pembatal tidak valid: " + olehSiapa);
                return;
            }
            System.out.println("Janji temu ID " + this.appointmentID + " telah dibatalkan oleh " + olehSiapa + ".");
        } else {
            System.out.println("Janji temu ID " + this.appointmentID + " sudah selesai atau sudah dibatalkan sebelumnya.");
        }
    }

    @Override
    public String toString() {
        return "Appointment{" +
               "appointmentID='" + appointmentID + '\'' +
               ", pasien=" + (pasien != null ? pasien.getNama() : "N/A") +
               ", dokter=" + (dokter != null ? dokter.getNama() : "N/A") +
               ", waktuJanji=" + waktuJanji +
               ", status='" + status + '\'' +
               ", keluhanAwal='" + keluhanAwal + '\'' +
               ", catatanDokter='" + (catatanDokter != null && !catatanDokter.isEmpty() ? catatanDokter : "Belum ada") + '\'' +
               '}';
    }
  
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return appointmentID.equals(that.appointmentID);
    }

    @Override
    public int hashCode() {
        return appointmentID.hashCode();
    }
}
