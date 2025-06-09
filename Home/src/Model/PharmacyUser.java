package Model;

public class PharmacyUser extends Person {
    // Constructor
    public PharmacyUser(String userID, String nama, String alamat, String nomorTelepon, String email, String password) {
        super(userID, nama, alamat, nomorTelepon, email, password);
    }

    // Function untuk konfirmasi proses order
    public void processOrder(String orderID) {
        System.out.println("Pengguna Farmasi " + this.getNama() + " memproses pesanan dengan ID: " + orderID);
    }
    
    // Function untuk konfirmasi memeriksa stok obat
    public void checkMedicineStock(String medicineName) {
        System.out.println("Pengguna Farmasi " + this.getNama() + " memeriksa stok obat: " + medicineName);
    }
    
    // Function untuk konfirmasi memberikan obat kepada pasien
    public void dispenseMedicine(String medicineName, String patientName) {
        System.out.println("Pengguna Farmasi " + this.getNama() + " memberikan obat " + medicineName + " kepada pasien " + patientName);
    }
    
    @Override
    public String toString() {
        return "PharmacyUser{" +
                "userID='" + this.getUserID() + '\'' +
                ", nama='" + this.getNama() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                '}';
    }
}