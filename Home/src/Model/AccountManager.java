package Model;

import Model.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class AccountManager {
    // Attribute
    private List<Person> daftarSemuaAkun;

    // Constructor
    public AccountManager() {
        this.daftarSemuaAkun = new ArrayList<>();
    }

    // Function untuk menambahkan akun baru
    public void tambahAkun(Person person) {
        if (person == null) {
            System.out.println("Gagal menambahkan akun: objek Person tidak boleh null.");
            return;
        }
        // Cek duplikasi userID sebelum menambahkan
        if (cariAkunByUserID(person.getUserID()) != null) {
            System.out.println("Gagal menambahkan akun: UserID '" + person.getUserID() + "' sudah terdaftar.");
            return;
        }
        this.daftarSemuaAkun.add(person);
        System.out.println("Akun untuk " + person.getNama() + " (ID: " + person.getUserID() + ") berhasil ditambahkan.");
    }

    // Function untuk mencari akun berdasarkan UserID
    public Person cariAkunByUserID(String userID) {
        if (userID == null || userID.trim().isEmpty()) {
            return null;
        }
        for (Person person : daftarSemuaAkun) {
            if (person.getUserID().equals(userID)) {
                return person;
            }
        }
        return null;
    }

    // Function untuk menghapus akun berdasarkan UserID
    public boolean hapusAkun(String userID) {
        if (userID == null || userID.trim().isEmpty()) {
            System.out.println("Gagal menghapus akun: UserID tidak valid.");
            return false;
        }
        Iterator<Person> iterator = daftarSemuaAkun.iterator();
        while (iterator.hasNext()) {
            Person person = iterator.next();
            if (person.getUserID().equals(userID)) {
                iterator.remove();
                System.out.println("Akun dengan UserID '" + userID + "' (Nama: " + person.getNama() + ") berhasil dihapus.");
                return true;
            }
        }
        System.out.println("Gagal menghapus akun: UserID '" + userID + "' tidak ditemukan.");
        return false;
    }

    public List<Person> getSemuaAkun() {
        return new ArrayList<>(this.daftarSemuaAkun);
    }

    // Function untuk verifikasi login
    public Person verifikasiLogin(String userID, String inputPassword) {
        Person person = cariAkunByUserID(userID);
        if (person != null) {
            if (person.login(inputPassword)) {
                return person;
            }
        } else {
            System.out.println("Login gagal: UserID '" + userID + "' tidak ditemukan.");
        }
        return null;
    }

    // Function untuk menampilkan detail semua akun
    public void tampilkanDetailSemuaAkun() {
        if (daftarSemuaAkun.isEmpty()) {
            System.out.println("Tidak ada akun yang terdaftar.");
            return;
        }
        System.out.println("\n--- Daftar Semua Akun Pengguna ---");
        for (Person person : daftarSemuaAkun) {
            System.out.println(person.toString());
        }
        System.out.println("---------------------------------");
    }
}