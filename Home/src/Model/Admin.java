package Model;

public class Admin extends Person {
    // Constructor
    public Admin(String userID, String nama, String alamat, String nomorTelepon, String email, String password) {
        super(userID, nama, alamat, nomorTelepon, email, password);
    }

    // Function untuk membuat akun dokter yang baru
    public Doctor createDoctorAccount(String userID, String nama, String alamat, String nomorTelepon, String email, String password, String spesialisasi, String nomorSTR) {
        Doctor newDoctor = new Doctor(userID, nama, alamat, nomorTelepon, email, password, spesialisasi, nomorSTR);
        System.out.println("Akun Dokter baru telah dibuat untuk Dr. " + nama + " (ID: " + userID + ") oleh Admin " + this.getNama() + ".");
        return newDoctor;
    }

    // Function untuk konfirmasi kegiatan kepada dokter
    public void manageDoctorAccount(Doctor doctor, String action) {
        if (doctor != null) {
            System.out.println("Admin " + this.getNama() + " melakukan tindakan '" + action + "' pada akun Dr. " + doctor.getNama() + ".");
        } else {
            System.out.println("Gagal melakukan tindakan: objek dokter tidak valid.");
        }
    }

    // Function untuk membuat akun farmasi yang baru
    public PharmacyUser createPharmacyUserAccount(String userID, String nama, String alamat, String nomorTelepon, String email, String password) {
        PharmacyUser newPharmacyUser = new PharmacyUser(userID, nama, alamat, nomorTelepon, email, password);
        System.out.println("Akun Pengguna Farmasi baru telah dibuat untuk " + nama + " (ID: " + userID + ") oleh Admin " + this.getNama() + ".");
        return newPharmacyUser;
    }

    // Function untuk konfirmasi kegiatan kepada farmasi
    public void managePharmacyUserAccount(PharmacyUser pharmacyUser, String action) {
        if (pharmacyUser != null) {
            System.out.println("Admin " + this.getNama() + " melakukan tindakan '" + action + "' pada akun Pengguna Farmasi " + pharmacyUser.getNama() + ".");
        } else {
            System.out.println("Gagal melakukan tindakan: objek pengguna farmasi tidak valid.");
        }
    }

    // Function untuk mengubah janji temu (Appointment)
    public boolean processAppointmentChangeRequest(Appointment appointment, String newDateTimeInfo) {
        if (appointment == null) {
            System.out.println("Gagal memproses: data janji temu tidak valid.");
            return false;
        }

        System.out.println("Admin " + this.getNama() + " menerima permintaan perubahan untuk Janji Temu ID: " + appointment.getAppointmentID());
        System.out.println("Pasien: " + appointment.getPasien().getNama());
        System.out.println("Dokter: " + appointment.getDokter().getNama());
        System.out.println("Waktu Awal: " + appointment.getWaktuJanji());
        System.out.println("Usulan Waktu Baru: " + newDateTimeInfo);
        System.out.println("Permintaan perubahan diteruskan kepada Dr. " + appointment.getDokter().getNama() + " untuk persetujuan.");
        return true;
    }

    // Function untuk menambah farmasi yang baru
    public void addPharmacyBranch(Pharmacy pharmacy) {
        if (pharmacy != null) {
            System.out.println("Admin " + this.getNama() + " menambahkan cabang farmasi baru: " + pharmacy.getNamaFarmasi() + " (ID: " + pharmacy.getPharmacyID() + ").");
        } else {
            System.out.println("Gagal menambahkan farmasi: data tidak valid.");
        }
    }

    @Override
    public String toString() {
        return "Admin{" +
                "userID='" + this.getUserID() + '\'' +
                ", nama='" + this.getNama() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                '}';
    }
}
