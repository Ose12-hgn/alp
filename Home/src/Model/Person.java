package Model;

public abstract class Person {
    // Attribute
    private String userID;
    private String nama;
    private String alamat;
    private String nomorTelepon;
    private String email;
    private String password;

    // Constructor
    public Person(String userID, String nama, String alamat, String nomorTelepon, String email, String password) {
        this.userID = userID;
        this.nama = nama;
        this.alamat = alamat;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.password = password;
    }
    
    public String getNama() {
        return nama;
    }

    public String getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Function untuk konfirmasi login
    public boolean login(String inputPassword) {
        System.out.println(this.nama + " mencoba login.");
        return true;
    }

    // Function untuk konfirmasi logout
    public void logout() {
        System.out.println(this.nama + " telah logout.");
    }

    // Function untuk update profil info yang baru
    public void updateProfil(String namaBaru, String alamatBaru, String teleponBaru, String emailBaru) {
        this.nama = namaBaru;
        this.alamat = alamatBaru;
        this.nomorTelepon = teleponBaru;
        this.email = emailBaru;
        System.out.println("Profil " + this.userID + " telah diperbarui.");
    }

    @Override
    public String toString() {
        return "Person{" +
                "userID='" + userID + '\'' +
                ", nama='" + nama + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
