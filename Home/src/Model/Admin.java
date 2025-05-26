package Model;

import java.util.List;

public class Admin extends Person {

    public Admin(String userID, String nama, String alamat, String nomorTelepon, String email, String password) {
        super(userID, nama, alamat, nomorTelepon, email, password);
    }

    public Doctor createDoctorAccount(String userID, String nama, String alamat, String nomorTelepon, String email, String password,
                                      String spesialisasi, String nomorSTR) {
        Doctor newDoctor = new Doctor(userID, nama, alamat, nomorTelepon, email, password, spesialisasi, nomorSTR);
        System.out.println("Akun Dokter baru telah dibuat untuk Dr. " + nama + " (ID: " + userID + ") oleh Admin " + getNama() + ".");
        return newDoctor;
    }

    public void manageDoctorAccount(Doctor doctor, String action) {
        if (doctor != null) {
            System.out.println("Admin " + getNama() + " melakukan tindakan '" + action + "' pada akun Dr. " + doctor.getNama() + ".");
        } else {
            System.out.println("Gagal melakukan tindakan: objek dokter tidak valid.");
        }
    }

    public PharmacyUser createPharmacyUserAccount(String userID, String nama, String alamat, String nomorTelepon, String email, String password) {
        PharmacyUser newPharmacyUser = new PharmacyUser(userID, nama, alamat, nomorTelepon, email, password);
        System.out.println("Akun Pengguna Farmasi baru telah dibuat untuk " + nama + " (ID: " + userID + ") oleh Admin " + getNama() + ".");
        return newPharmacyUser;
    }

    public void managePharmacyUserAccount(PharmacyUser pharmacyUser, String action) {
        if (pharmacyUser != null) {
            System.out.println("Admin " + getNama() + " melakukan tindakan '" + action + "' pada akun Pengguna Farmasi " + pharmacyUser.getNama() + ".");
        } else {
            System.out.println("Gagal melakukan tindakan: objek pengguna farmasi tidak valid.");
        }
    }

    public boolean processAppointmentChangeRequest(Appointment appointment, String newDateTimeInfo) {
        if (appointment == null) {
            System.out.println("Gagal memproses: data janji temu tidak valid.");
            return false;
        }

        System.out.println("Admin " + getNama() + " menerima permintaan perubahan untuk Janji Temu ID: " + appointment.getAppointmentID());
        System.out.println("Pasien: " + appointment.getPasien().getNama());
        System.out.println("Dokter: " + appointment.getDokter().getNama());
        System.out.println("Waktu Awal: " + appointment.getWaktuJanji());
        System.out.println("Usulan Waktu Baru: " + newDateTimeInfo);
        System.out.println("Permintaan perubahan diteruskan kepada Dr. " + appointment.getDokter().getNama() + " untuk persetujuan.");
        return true;
    }

    public void addPharmacyBranch(Pharmacy pharmacy) {
        if (pharmacy != null) {
            System.out.println("Admin " + getNama() + " menambahkan cabang farmasi baru: " + pharmacy.getNamaFarmasi() + " (ID: " + pharmacy.getPharmacyID() + ").");
        } else {
            System.out.println("Gagal menambahkan farmasi: data tidak valid.");
        }
    }


    @Override
    public String toString() {
        return "Admin{" +
               "userID='" + getUserID() + '\'' +
               ", nama='" + getNama() + '\'' +
               ", email='" + getEmail() + '\'' +
               '}';
    }
}
