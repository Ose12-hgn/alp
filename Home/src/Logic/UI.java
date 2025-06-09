package Logic;

import Model.*;
import Model.PaymentInfo.ItemQuantity;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class UI {
    private Scanner scan;
    private AccountManager accountManager;
    private Pharmacy pharmacy;
    private ArrayList<Pharmacy> daftarFarmasi = new ArrayList<>();
    private int totalResep = 0;
    private int totalJadwal = 0;
    private int totalJanjiTemu = 0;

    public UI () {
        this.scan = new Scanner(System.in);
        this.accountManager = new AccountManager();
        this.pharmacy = new Pharmacy("FARMASI0", "Farmasiku", "JL. Kesehatan No. 1");
        this.daftarFarmasi.add(pharmacy);
        this.accountManager.tambahAkun(new Admin("ADMIN", "Admin", "JL. Kesehatan No. 1", "08123456789", "admin@email.com", "admin"));
    }   


    public void start() {
        System.out.println("SELAMAT DATANG DI MYCLINIC!");
        System.out.println("\r\n" + //
                        "███╗░░░███╗██╗░░░██╗   ░█████╗░██╗░░░░░██╗███╗░░██╗██╗░█████╗░\r\n" + //
                        "████╗░████║╚██╗░██╔╝   ██╔══██╗██║░░░░░██║████╗░██║██║██╔══██╗\r\n" + //
                        "██╔████╔██║░╚████╔╝░   ██║░░╚═╝██║░░░░░██║██╔██╗██║██║██║░░╚═╝\r\n" + //
                        "██║╚██╔╝██║░░╚██╔╝░░   ██║░░██╗██║░░░░░██║██║╚████║██║██║░░██╗\r\n" + //
                        "██║░╚═╝░██║░░░██║░░░   ╚█████╔╝███████╗██║██║░╚███║██║╚█████╔╝\r\n" + //
                        "╚═╝░░░░░╚═╝░░░╚═╝░░░   ░╚════╝░╚══════╝╚═╝╚═╝░░╚══╝╚═╝░╚════╝░");
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Login");
            System.out.println("2. Daftar Akun Baru");
            System.out.println("3. Keluar");
            System.out.print("Pilih opsi: ");
            int pilihan;
            try {
                pilihan = scan.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Input harus berupa angka. Silakan coba lagi.");
                pilihan = -1;
                scan.nextLine();
                continue;
            }
            scan.nextLine();

            switch (pilihan) {
                case 1:
                    login();
                    break;
                case 2:
                    daftarAkunBaru();
                    break;
                case 3:
                    System.out.println("Terima kasih telah menggunakan MyClinic!");
                    return;
                default:
                    System.out.println("Pilihan tidak valid, silakan coba lagi.");
            }
        }
    }

    private void login() {
        System.out.print("Masukkan UserID: ");
        String userID = scan.nextLine();
        System.out.print("Masukkan Password: ");
        String password = scan.nextLine();

        if (userID.equals("MANAJER") && password.equals("manajer")) {
            // Login khusus untuk Manajer Farmasi
            System.out.println("Login berhasil! Selamat datang, Manajer");
            managerMenu();
        }

        Person akun = accountManager.verifikasiLogin(userID, password);
        if (akun != null && akun.getPassword().equals(password)) {
            System.out.println("Login berhasil! Selamat datang, " + akun.getNama() + ".");
            // Lanjutkan ke menu utama sesuai jenis akun
            if (akun instanceof Doctor) {
                doctorMenu((Doctor) akun);
            } else if (akun instanceof PharmacyUser) {
                pharmacyUserMenu((PharmacyUser) akun);
            } else if (akun instanceof Patient) {
                patientMenu((Patient) akun);
            } else if (akun instanceof Admin) {
                adminMenu((Admin) akun);
            } else {
                System.out.println("Akun tidak dikenali.");
            }
        } else {
            System.out.println("Jika belum memiliki akun, silakan daftar akun baru.");
        }
    }

    private void daftarAkunBaru() {
        // Hanya bisa membuat akun pasien
        // Untuk membuat akun dokter atau farmasi, harus melalui admin
        
        System.out.println("Selamat datang pasien baru!");
        System.out.println("Silakan isi data berikut untuk membuat akun baru:");
        System.out.print("Masukkan UserID: ");
        String userID = scan.nextLine();
        System.out.print("Masukkan Nama: ");
        String nama = scan.nextLine();
        System.out.print("Masukkan Alamat: ");
        String alamat = scan.nextLine();
        System.out.print("Masukkan Nomor Telepon: ");
        String nomorTelepon = scan.nextLine();
        System.out.print("Masukkan Email: ");
        String email = scan.nextLine();
        System.out.print("Masukkan Password: ");
        String password = scan.nextLine();
        System.out.print("Masukkan Tanggal Lahir (YYYY-MM-DD): ");
        String tanggalLahirInput = scan.nextLine();
        System.out.print("Masukkan Jenis Kelamin (L/P): ");
        String jenisKelamin = scan.nextLine();
        System.out.print("Masukkan Golongan Darah: ");
        String golonganDarah = scan.nextLine();
        LocalDate tanggalLahir;
        try {
            tanggalLahir = LocalDate.parse(tanggalLahirInput);
        } catch (Exception e) {
            System.out.println("Format tanggal tidak valid. Gunakan format YYYY-MM-DD.");
            return;
        }

        // Membuat akun pasien baru
        Patient pasienBaru = new Patient(userID, nama, alamat, nomorTelepon, email, password, tanggalLahir, jenisKelamin, golonganDarah);
        accountManager.tambahAkun(pasienBaru);
        System.out.println("Akun pasien baru telah dibuat untuk " + nama + " (ID: " + userID + "). Silakan login untuk melanjutkan.");
    }

    private void adminMenu(Admin admin) {
        while (true) { 
            System.out.println("Selamat datang, " + admin.getNama() + "!");
            System.out.println("Menu Admin:");
            System.out.println("1. Buat Akun Dokter");
            System.out.println("2. Kelola Akun Dokter");
            System.out.println("3. Buat Akun Farmasi");
            System.out.println("4. Kelola Akun Farmasi");
            System.out.println("5. Buka Cabang Farmasi");
            System.out.println("6. Kelola Janji Temu");
            System.out.println("7. Keluar");
            System.out.print("Pilih opsi: ");
            int pilihan;
            try {
                pilihan = scan.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Input harus berupa angka. Silakan coba lagi.");
                pilihan = -1;
                scan.nextLine();
                continue;
            }
            scan.nextLine();
            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan UserID Dokter: ");
                    String userID = scan.nextLine();
                    System.out.print("Masukkan Nama Dokter: ");
                    String nama = scan.nextLine();
                    System.out.print("Masukkan Alamat Dokter: ");
                    String alamat = scan.nextLine();
                    System.out.print("Masukkan Nomor Telepon Dokter: ");
                    String nomorTelepon = scan.nextLine();
                    System.out.print("Masukkan Email Dokter: ");
                    String email = scan.nextLine();
                    System.out.print("Masukkan Password Dokter: ");
                    String password = scan.nextLine();
                    System.out.print("Masukkan Spesialisasi Dokter: ");
                    String spesialisasi = scan.nextLine();
                    System.out.print("Masukkan Nomor STR Dokter: ");
                    String nomorSTR = scan.nextLine();
                
                    Doctor dokterBaru = admin.createDoctorAccount(userID, nama, alamat, nomorTelepon, email, password, spesialisasi, nomorSTR);
                    accountManager.tambahAkun(dokterBaru);
                    admin.manageDoctorAccount(dokterBaru, "BUAT AKUN BARU");
                    break;
                case 2:
                    System.out.print("Masukkan UserID Dokter yang ingin dikelola: ");
                    String userIDDokter = scan.nextLine();
                    Doctor dokter = (Doctor) accountManager.cariAkunByUserID(userIDDokter);
                    if (dokter != null) {
                        System.out.println("Kelola Akun Dokter: " + dokter.getNama());
                        System.out.println("1. Ubah Spesialisasi");
                        System.out.println("2. Ubah Nomor STR");
                        System.out.println("3. Hapus Akun Dokter");
                        System.out.println("4. Kembali");

                        System.out.print("Pilih opsi: ");
                        int subPilihan;
                        try {
                            subPilihan = scan.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Input harus berupa angka. Silakan coba lagi.");
                            subPilihan = -1;
                            scan.nextLine();
                            continue;
                        }
                        scan.nextLine();

                        switch (subPilihan) {
                            case 1:
                                System.out.print("Masukkan Spesialisasi Baru: ");
                                String spesialisasiBaru = scan.nextLine();
                                dokter.setSpesialisasi(spesialisasiBaru);
                                System.out.println("Spesialisasi Dokter telah diubah.");
                                break;
                            case 2:
                                System.out.print("Masukkan Nomor STR Baru: ");
                                String nomorSTRBaru = scan.nextLine();
                                dokter.setNomorSTR(nomorSTRBaru);
                                System.out.println("Nomor STR Dokter telah diubah.");
                                break;
                            case 3:
                                accountManager.hapusAkun(dokter.getUserID());
                                System.out.println("Akun Dokter " + dokter.getNama() + " telah dihapus.");
                                break;
                            case 4:
                                return;
                            default:
                                System.out.println("Pilihan tidak valid, silakan coba lagi.");
                        }
                        admin.manageDoctorAccount(dokter, "KELOLA AKUN");
                    } else {
                        System.out.println("Dokter dengan UserID " + userIDDokter + " tidak ditemukan.");
                    }
                    break;
                case 3:
                    System.out.print("Masukkan UserID Farmasi: ");
                    String userIDFarmasi = scan.nextLine(); 
                    System.out.print("Masukkan Nama Farmasi: ");
                    String namaFarmasi = scan.nextLine();
                    System.out.print("Masukkan Alamat Farmasi: ");
                    String alamatFarmasi = scan.nextLine();
                    System.out.print("Masukkan Nomor Telepon Farmasi: ");
                    String nomorTeleponFarmasi = scan.nextLine();
                    System.out.print("Masukkan Email Farmasi: ");
                    String emailFarmasi = scan.nextLine();
                    System.out.print("Masukkan Password Farmasi: ");
                    String passwordFarmasi = scan.nextLine();
                    PharmacyUser farmasiBaru = admin.createPharmacyUserAccount(userIDFarmasi, namaFarmasi, alamatFarmasi, nomorTeleponFarmasi, emailFarmasi, passwordFarmasi);
                    accountManager.tambahAkun(farmasiBaru);
                    admin.managePharmacyUserAccount(farmasiBaru, "BUAT AKUN BARU");
                    break;
                case 4:
                    System.out.print("Masukkan UserID Farmasi yang ingin dikelola: ");
                    String userIDFarmasiKelola = scan.nextLine();
                    PharmacyUser farmasi = (PharmacyUser) accountManager.cariAkunByUserID(userIDFarmasiKelola);
                    if (farmasi != null) {
                        System.out.println("Kelola Akun Farmasi: " + farmasi.getNama());
                        System.out.println("1. Ubah Alamat");
                        System.out.println("2. Ubah Nomor Telepon");
                        System.out.println("3. Hapus Akun Farmasi");
                        System.out.println("4. Kembali");
                        System.out.print("Pilih opsi: ");
                        int subPilihan;
                        try {
                            subPilihan = scan.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Input harus berupa angka. Silakan coba lagi.");
                            subPilihan = -1;
                            scan.nextLine();
                            continue;
                        }
                        scan.nextLine();

                        switch (subPilihan) {
                            case 1:
                                System.out.print("Masukkan Alamat Baru: ");
                                String alamatBaru = scan.nextLine();
                                farmasi.setAlamat(alamatBaru);
                                System.out.println("Alamat Farmasi telah diubah.");
                                break;
                            case 2:
                                System.out.print("Masukkan Nomor Telepon Baru: ");
                                String nomorTeleponBaru = scan.nextLine();
                                farmasi.setNomorTelepon(nomorTeleponBaru);
                                System.out.println("Nomor Telepon Farmasi telah diubah.");
                                break;
                            case 3:
                                accountManager.hapusAkun(farmasi.getUserID());
                                System.out.println("Akun Farmasi " + farmasi.getNama() + " telah dihapus.");
                                break;
                            case 4:
                                return;
                            default:
                                System.out.println("Pilihan tidak valid, silakan coba lagi.");
                        }
                        admin.managePharmacyUserAccount(farmasi, "KELOLA AKUN");
                    } else {
                        System.out.println("Farmasi dengan UserID " + userIDFarmasiKelola + " tidak ditemukan.");
                    }
                    break;
                case 5:
                    System.out.print("Masukkan Nama Cabang Farmasi: ");
                    String namaCabang = scan.nextLine();
                    System.out.print("Masukkan Alamat Cabang Farmasi: ");
                    String alamatCabang = scan.nextLine();
                    Pharmacy cabangBaru = new Pharmacy(("FARMASI" + daftarFarmasi.size()), namaCabang, alamatCabang);
                    daftarFarmasi.add(cabangBaru);
                    admin.addPharmacyBranch(cabangBaru);
                    System.out.println("Cabang farmasi baru telah ditambahkan: " + namaCabang);
                    break;
                case 6:
                    System.out.println("Kelola Janji Temu:");
                    System.out.println("1. Lihat Semua Janji Temu");
                    System.out.println("2. Batalkan Janji Temu");
                    System.out.println("3. Setujui Janji Temu");
                    System.out.println("4. Ubah Jadwal Janji Temu");
                    System.out.println("5. Kembali");
                    System.out.print("Pilih opsi: ");
                    int subPilihanJanji;
                    try {
                        subPilihanJanji = scan.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Input harus berupa angka. Silakan coba lagi.");
                        subPilihanJanji = -1;
                        scan.nextLine();
                        continue;
                    }
                    scan.nextLine();
                    switch (subPilihanJanji) {
                        case 1:
                            System.out.print("Masukkan UserID untuk melihat janji temu: ");
                            String userIDJanji = scan.nextLine();
                            Person akun = accountManager.cariAkunByUserID(userIDJanji);
                            if (akun != null) {
                                if (akun instanceof Doctor) {
                                    Doctor dok = (Doctor) akun;
                                    dok.lihatSemuaJanjiTemu();
                                } else if (akun instanceof Patient) {
                                    Patient pasien = (Patient) akun;
                                    pasien.lihatDaftarJanjiTemu();

                                }
                            } else {
                                System.out.println("Akun dengan UserID " + userIDJanji + " tidak ditemukan.");
                            }
                            break;
                        case 2:
                            System.out.print("Masukkan UserID untuk melihat janji temu: ");
                            String userIDJanjiBatal = scan.nextLine();
                            Person akunBatal = accountManager.cariAkunByUserID(userIDJanjiBatal);
                            if (akunBatal != null) {
                                if (akunBatal instanceof Doctor) {
                                    Doctor dok = (Doctor) akunBatal;
                                    dok.lihatSemuaJanjiTemu();
                                    System.out.print("Masukkan ID Janji Temu yang ingin dibatalkan: ");
                                    String idJanjiBatal = scan.nextLine();
                                    for (Appointment janji : dok.getDaftarJanjiTemuPasien()) {
                                        if (janji.getAppointmentID().equals(idJanjiBatal)) {
                                            janji.batalkanJanjiTemu("ADMIN");
                                            System.out.println("Janji Temu " + idJanjiBatal + " telah dibatalkan.");
                                            break;
                                        }
                                    }
                                } else if (akunBatal instanceof Patient) {
                                    Patient pasien = (Patient) akunBatal;
                                    pasien.lihatDaftarJanjiTemu();
                                    System.out.print("Masukkan ID Janji Temu yang ingin dibatalkan: ");
                                    String idJanjiBatal = scan.nextLine();
                                    for (Appointment janji : pasien.getDaftarJanjiTemu()) {
                                        if (janji.getAppointmentID().equals(idJanjiBatal)) {
                                            janji.batalkanJanjiTemu("ADMIN");
                                            System.out.println("Janji Temu " + idJanjiBatal + " telah dibatalkan.");
                                            break;
                                        }
                                    }
                                    System.out.println("Janji Temu dengan ID " + idJanjiBatal + " tidak ditemukan.");
                                }
                            } else {
                                System.out.println("Akun dengan UserID " + userIDJanjiBatal + " tidak ditemukan.");
                            }
                            break;
                        case 3:
                            System.out.print("Masukkan UserID untuk melihat janji temu: ");
                            String userIDJanjiSetujui = scan.nextLine();
                            Person akunSetujui = accountManager.cariAkunByUserID(userIDJanjiSetujui);
                            if (akunSetujui != null) {
                                if (akunSetujui instanceof Doctor) {
                                    Doctor dok = (Doctor) akunSetujui;
                                    dok.lihatSemuaJanjiTemu();
                                    System.out.print("Masukkan ID Janji Temu yang ingin disetujui: ");
                                    String idJanjiSetujui = scan.nextLine();
                                    for (Appointment janji : dok.getDaftarJanjiTemuPasien()) {
                                        if (janji.getAppointmentID().equals(idJanjiSetujui)) {
                                            janji.konfirmasiJanjiTemu();
                                            System.out.println("Janji Temu " + idJanjiSetujui + " telah disetujui.");

                                            for (Schedule jadwal : janji.getDokter().getDaftarJadwalKetersediaan()) {
                                                if (jadwal.isWithinSchedule(janji.getWaktuJanji())) {
                                                    List<Appointment> tempList = new ArrayList<>(jadwal.getDailyAppointmentQueue());
                                                    tempList.add(janji);
                                                    tempList.sort(Comparator.comparing(Appointment::getWaktuJanji));
                                                    jadwal.getDailyAppointmentQueue().clear();
                                                    for (Appointment a : tempList) {
                                                        jadwal.getDailyAppointmentQueue().add(a);
                                                    }
                                                    System.out.println("Janji Temu " + idJanjiSetujui + " telah ditambahkan ke antrian harian dokter.");
                                                }
                                            }
                                            break;
                                        }
                                    }
                                } else {
                                    System.out.println("Akun dengan UserID " + userIDJanjiSetujui + " bukan dokter.");
                                }
                            } else {
                                System.out.println("Akun dengan UserID " + userIDJanjiSetujui + " tidak ditemukan.");
                            }
                            break;
                        case 4:
                            System.out.print("Masukkan UserID untuk melihat janji temu: ");
                            String userIDJanjiUbah = scan.nextLine();
                            Person akunUbah = accountManager.cariAkunByUserID(userIDJanjiUbah);
                            if (akunUbah != null) {
                                if (akunUbah instanceof Doctor) {
                                    Doctor dok = (Doctor) akunUbah;
                                    dok.lihatSemuaJanjiTemu();
                                    System.out.print("Masukkan ID Janji Temu yang ingin diubah jadwalnya: ");
                                    String idJanjiUbah = scan.nextLine();
                                    for (Appointment janji : dok.getDaftarJanjiTemuPasien()) {
                                        if (janji.getAppointmentID().equals(idJanjiUbah)) {
                                            System.out.print("Masukkan Waktu Baru (YYYY-MM-DD HH:MM): ");
                                            String waktuBaruInput = scan.nextLine();
                                            LocalDateTime waktuBaru;
                                            boolean nabrak = false;
                                            try {
                                                waktuBaru = LocalDateTime.parse(waktuBaruInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                                            } catch (Exception e) {
                                                System.out.println("Format waktu tidak valid. Gunakan format YYYY-MM-DD HH:MM.");
                                                return;
                                            }
                                            for (Appointment app : dok.getDaftarJanjiTemuPasien()) {
                                                if (app.getStatus().equals("DIBATALKAN_PASIEN") || app.getStatus().equals("DIBATALKAN_DOKTER") || app.getStatus().equals("DIBATALKAN_ADMIN") || app.getStatus().equals("SELESAI")) {
                                                    continue;
                                                } else if (app.getWaktuJanji().equals(waktuBaru)) {
                                                    nabrak = true;
                                                    System.out.println("Waktu janji sudah ada, silakan pilih waktu lain.");
                                                    break;
                                                } else if (app.getPasien().equals(janji.getPasien()) && app.getWaktuJanji().isEqual(waktuBaru)) {
                                                    nabrak = true;
                                                    System.out.println("Pasien ini sudah memiliki janji pada waktu tersebut.");
                                                    break;
                                                } else if (app.getDokter().equals(janji.getDokter()) && app.getWaktuJanji().isEqual(waktuBaru)) {
                                                    nabrak = true;
                                                    System.out.println("Dokter ini sudah memiliki janji pada waktu tersebut.");
                                                    break;
                                                } else if (app.getWaktuJanji().isAfter(waktuBaru.minusMinutes(30)) && app.getWaktuJanji().isBefore(waktuBaru.plusMinutes(30))) {
                                                    nabrak = true;
                                                    System.out.println("Ada janji temu lain yang bentrok dengan waktu ini, silakan pilih waktu lain.");
                                                    break;
                                                }
                                                for (Appointment a : janji.getPasien().getDaftarJanjiTemu()) {
                                                    if (a.getWaktuJanji().equals(waktuBaru)) {
                                                        nabrak = true;
                                                        System.out.println("Pasien ini sudah memiliki janji lain pada waktu tersebut.");
                                                        break;
                                                    }
                                                }
                                            }
                                            for (Schedule jadwal : dok.getDaftarJadwalKetersediaan()) {
                                                if (!jadwal.isActive() && !jadwal.isWithinSchedule(waktuBaru)) {
                                                    nabrak = true;
                                                    System.out.println("Dokter tidak tersedia pada waktu ini, silakan pilih waktu lain.");
                                                    break;
                                                }
                                            }
                                            nabrak = dok.getDaftarJadwalKetersediaan().isEmpty();
                                            if (nabrak) {
                                                System.out.println("Gagal mengubah jadwal.");
                                                return;
                                            }
                                            janji.setWaktuJanji(waktuBaru);
                                            janji.setStatus("DIAJUKAN");
                                            admin.processAppointmentChangeRequest(janji, waktuBaruInput);
                                            break;
                                        }
                                    }
                                } else if (akunUbah instanceof Patient) {
                                    Patient pasien = (Patient) akunUbah;
                                    pasien.lihatDaftarJanjiTemu();
                                    System.out.print("Masukkan ID Janji Temu yang ingin diubah jadwalnya: ");
                                    String idJanjiUbah = scan.nextLine();
                                    for (Appointment janji : pasien.getDaftarJanjiTemu()) {
                                        if (janji.getAppointmentID().equals(idJanjiUbah)) {
                                            System.out.print("Masukkan Waktu Baru (YYYY-MM-DD HH:MM): ");
                                            String waktuBaruInput = scan.nextLine();
                                            LocalDateTime waktuBaru;
                                            boolean nabrak = false;
                                            try {
                                                waktuBaru = LocalDateTime.parse(waktuBaruInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                                            } catch (Exception e) {
                                                System.out.println("Format waktu tidak valid. Gunakan format YYYY-MM-DD HH:MM.");
                                                return;
                                            }
                                            for (Appointment app : pasien.getDaftarJanjiTemu()) {
                                                if (app.getStatus().equals("DIBATALKAN_PASIEN") || app.getStatus().equals("DIBATALKAN_DOKTER") || app.getStatus().equals("DIBATALKAN_ADMIN") || app.getStatus().equals("SELESAI")) {
                                                    continue;
                                                } else if (app.getWaktuJanji().equals(waktuBaru)) {
                                                    nabrak = true;
                                                    System.out.println("Waktu janji sudah ada, silakan pilih waktu lain.");
                                                    break;
                                                } else if (app.getPasien().equals(janji.getPasien()) && app.getWaktuJanji().isEqual(waktuBaru)) {
                                                    nabrak = true;
                                                    System.out.println("Pasien ini sudah memiliki janji pada waktu tersebut.");
                                                    break;
                                                } else if (app.getDokter().equals(janji.getDokter()) && app.getWaktuJanji().isEqual(waktuBaru)) {
                                                    nabrak = true;
                                                    System.out.println("Dokter ini sudah memiliki janji pada waktu tersebut.");
                                                    break;
                                                } else if (app.getWaktuJanji().isAfter(waktuBaru.minusMinutes(30)) && app.getWaktuJanji().isBefore(waktuBaru.plusMinutes(30))) {
                                                    nabrak = true;
                                                    System.out.println("Ada janji temu lain yang bentrok dengan waktu ini, silakan pilih waktu lain.");
                                                    break;
                                                }
                                                for (Appointment a : janji.getDokter().getDaftarJanjiTemuPasien()) {
                                                    if (a.getWaktuJanji().equals(waktuBaru)) {
                                                        nabrak = true;
                                                        System.out.println("Dokter ini sudah memiliki janji lain pada waktu tersebut.");
                                                        break;
                                                    }
                                                }
                                            }
                                            for (Schedule jadwal : janji.getDokter().getDaftarJadwalKetersediaan()) {
                                                if (!jadwal.isActive() && !jadwal.isWithinSchedule(waktuBaru)) {
                                                    nabrak = true;
                                                    System.out.println("Dokter tidak tersedia pada waktu ini, silakan pilih waktu lain.");
                                                    break;
                                                }
                                            }
                                            nabrak = janji.getDokter().getDaftarJadwalKetersediaan().isEmpty();
                                            if (nabrak) {
                                                System.out.println("Gagal mengubah jadwal.");
                                                return;
                                            }
                                            janji.setWaktuJanji(waktuBaru);
                                            janji.setStatus("DIAJUKAN");
                                            admin.processAppointmentChangeRequest(janji, waktuBaruInput);
                                            break;
                                        }
                                    }
                                }
                            } else {
                                System.out.println("Akun dengan UserID " + userIDJanjiUbah + " tidak ditemukan.");
                            }
                        break;
                        case 5:
                            System.out.println("Kembali ke menu utama.");
                            return;
                        default:
                            System.out.println("Pilihan tidak valid, silakan coba lagi.");
                        }
                        break;
                case 7:
                    admin.logout();
                    System.out.println("Kembali ke menu utama.");
                    return;
                default:
                    System.out.println("Pilihan tidak valid, silakan coba lagi.");
            }
        }
    }

    private void doctorMenu(Doctor dokter) {
        while (true) { 
            System.out.println("Selamat datang, Dr. " + dokter.getNama() + "!");
            System.out.println("Menu Dokter:");
            System.out.println("1. Tambah Jadwal Ketersediaan");
            System.out.println("2. Hapus Jadwal Ketersediaan");
            System.out.println("3. Lihat Semua Jadwal Ketersediaan");
            System.out.println("4. Konfirmasi Janji Temu Pasien");
            System.out.println("5. Batalkan Janji Temu Pasien");
            System.out.println("6. Lihat Semua Janji Temu");
            System.out.println("7. Buat Resep Obat untuk Pasien");
            System.out.println("8. Lihat Rekaman Medis Pasien");
            System.out.println("9. Lakukan Konsultasi Pasien");
            System.out.println("10. Keluar");
            System.out.print("Pilih opsi: ");
            int pilihan;
            try {
                pilihan = scan.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Input harus berupa angka. Silakan coba lagi.");
                pilihan = -1;
                scan.nextLine();
                continue;
            }
            scan.nextLine();
            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan Hari (Senin, Selasa, ...): ");
                    String hari = scan.nextLine();
                    try {
                        DayOfWeek dayOfWeek = konversiHariKeDayOfWeek(hari);
                        if (dayOfWeek == null) {
                            throw new IllegalArgumentException("Hari tidak valid");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Hari tidak valid. Gunakan format: Senin, Selasa, ...");
                        continue;
                    }
                    System.out.print("Masukkan Jam Mulai (HH:MM): ");
                    String jamMulai = scan.nextLine();
                    try {
                        LocalTime.parse(jamMulai);
                    } catch (DateTimeParseException e) {
                        System.out.println("Format jam tidak valid. Gunakan format HH:MM.");
                        continue;
                    }
                    System.out.print("Masukkan Jam Selesai (HH:MM): ");
                    String jamSelesai = scan.nextLine();
                    try {
                        LocalTime.parse(jamSelesai);
                    } catch (DateTimeParseException e) {
                        System.out.println("Format jam tidak valid. Gunakan format HH:MM.");
                        continue;
                    }
                    System.out.print("Masukkan catatan tambahan (opsional): ");
                    String catatan = scan.nextLine(); // Catatan tambahan, bisa diabaikan jika tidak diperlukan
                    Schedule jadwalBaru = new Schedule(("SCH" + totalJadwal), dokter, konversiHariKeDayOfWeek(hari), LocalTime.parse(jamMulai), LocalTime.parse(jamSelesai), true, catatan);
                    totalJadwal++;
                    dokter.tambahJadwalKetersediaan(jadwalBaru);
                    break;
                case 2:
                    dokter.lihatSemuaJadwalKetersediaan();
                    if (dokter.getDaftarJadwalKetersediaan().isEmpty()) {
                        break;
                    }
                    System.out.print("Masukkan ID Jadwal yang ingin dihapus: ");
                    String idJadwal = scan.nextLine();
                    for (Schedule jadwal : dokter.getDaftarJadwalKetersediaan()) {
                        if (jadwal.getScheduleID().equals(idJadwal)) {
                            dokter.hapusJadwalKetersediaan(jadwal);
                            System.out.println("Jadwal " + idJadwal + " telah dihapus.");
                            break;
                        } else {
                            System.out.println("Jadwal dengan ID " + idJadwal + " tidak ditemukan.");
                        }
                    }
                    break;
                case 3:
                    dokter.lihatSemuaJadwalKetersediaan();
                    break;
                case 4:
                    dokter.lihatSemuaJanjiTemu();
                    System.out.print("Masukkan ID Janji Temu yang ingin dikonfirmasi: ");
                    String idJanjiTemu = scan.nextLine();
                    for (Appointment janji : dokter.getDaftarJanjiTemuPasien()) {
                        if (janji.getAppointmentID().equals(idJanjiTemu)) {
                            janji.setStatus("MINTA_UBAH_DISETUJUI");
                            System.out.println("Janji Temu " + idJanjiTemu + " telah dikonfirmasi. Silakan tunggu persetujuan admin untuk mengubah jadwal.");
                            break;
                        }
                    }
                    break;
                case 5:
                    dokter.lihatSemuaJanjiTemu();
                    System.out.print("Masukkan ID Janji Temu yang ingin dibatalkan: ");
                    String idJanjiTemuBatalkan = scan.nextLine();
                    for (Appointment janji : dokter.getDaftarJanjiTemuPasien()) {
                        if (janji.getAppointmentID().equals(idJanjiTemuBatalkan)) {
                            if (janji.getStatus().equals("DIJADWALKAN")) {
                                System.out.println("Janji Temu dengan ID " + idJanjiTemuBatalkan + " tidak dapat dibatalkan karena statusnya DIJADWALKAN.");
                                return;
                            }
                            janji.batalkanJanjiTemu("DOKTER");
                            System.out.println("Janji Temu " + idJanjiTemuBatalkan + " telah dibatalkan.");
                            return;
                        }
                    }
                    System.out.println("Janji Temu dengan ID " + idJanjiTemuBatalkan + " tidak ditemukan.");
                    break;
                case 6:
                    dokter.lihatSemuaJanjiTemu();
                    break;
                case 7:
                    System.out.print("Masukkan ID Pasien yang ingin dibuat resepnya: ");
                    String userIDPasienResep = scan.nextLine();
                    Patient pasienResep = (Patient) accountManager.cariAkunByUserID(userIDPasienResep);
                    if (pasienResep != null) {
                        List<MedicineUsage> daftarObat = new ArrayList<>();
                        OUTER:
                        while (true) {
                            System.out.println("1. Tambah Obat ke Resep");
                            System.out.println("2. Selesai Menambahkan Obat");
                            System.out.print("Pilih opsi: ");
                            int opsi;
                            try {
                                opsi = scan.nextInt();
                            } catch (InputMismatchException e) {
                                System.out.println("Input harus berupa angka. Silakan coba lagi.");
                                opsi = -1;
                                scan.nextLine();
                                continue;
                            }
                            scan.nextLine();
                            switch (opsi) {
                                case 1:
                                    pharmacy.tampilkanInventaris();
                                    System.out.print("Masukkan ID Obat: ");
                                    String idObatResep = scan.nextLine();
                                    Medicine obat = (Medicine) pharmacy.cariItemByID(idObatResep);
                                    if (obat != null) {
                                        System.out.print("Masukkan dosis obat (misal: 5ml): ");
                                        String dosis = scan.nextLine();
                                        System.out.print("Masukkan frekuensi obat (misal: 2x sehari): ");
                                        String frekuensi = scan.nextLine();
                                        System.out.print("Masukkan durasi obat (misal: 7 hari): ");
                                        String durasi = scan.nextLine();
                                        System.out.print("Masukkan aturan penggunaan (misal: sebelum makan): ");
                                        String aturanPenggunaan = scan.nextLine();
                                        System.out.print("Masukkan jumlah obat yang diresepkan: ");
                                        int jumlah = -1;
                                        while (jumlah < 0) {
                                            try {
                                                jumlah = scan.nextInt();
                                            } catch (InputMismatchException e) {
                                                System.out.println("Input tidak valid. Silakan coba lagi.");
                                                scan.nextLine();
                                            }
                                        }
                                        scan.nextLine();
                                        daftarObat.add(new MedicineUsage(obat, dosis, frekuensi, durasi, aturanPenggunaan, jumlah));
                                    } else {
                                        System.out.println("Obat dengan ID " + idObatResep + " tidak ditemukan.");
                                    }
                                    break;
                                case 2:
                                    if (daftarObat.isEmpty()) {
                                        System.out.println("Tidak ada obat yang ditambahkan ke resep. Resep tidak dapat dibuat.");
                                        break OUTER;
                                    } else {
                                        System.out.print("Masukkan catatan dokter (opsional): ");
                                        String catatanDokter = scan.nextLine();
                                        dokter.buatResep(pasienResep, daftarObat, catatanDokter);
                                        System.out.println("Resep berhasil dibuat untuk pasien " + pasienResep.getNama() + ".");
                                        totalResep++;
                                    }
                                    break OUTER;
                                default:
                                    System.out.println("Pilihan tidak valid, silakan coba lagi.");
                                    break;
                            }
                        }
                    } else {
                        System.out.println("Pasien dengan UserID " + userIDPasienResep + " tidak ditemukan.");
                    }
                    break;
                case 8:
                    System.out.print("Masukkan UserID Pasien: ");
                    String userIDPasienRekam = scan.nextLine();
                    Patient pasienRekam = (Patient) accountManager.cariAkunByUserID(userIDPasienRekam);
                    if (pasienRekam != null) {
                        dokter.lihatRekamMedisPasien(pasienRekam);
                    } else {
                        System.out.println("Pasien dengan UserID " + userIDPasienRekam + " tidak ditemukan.");
                    }
                    break;
                case 9:
                    System.out.print("Masukkan Schedule ID untuk konsultasi: ");
                    String scheduleID = scan.nextLine();
                    Schedule jadwalKonsultasi = null;
                    for (Schedule jadwal : dokter.getDaftarJadwalKetersediaan()) {
                        if (jadwal.getScheduleID().equals(scheduleID) && jadwal.isActive()) {
                            jadwalKonsultasi = jadwal;
                            break;
                        }
                    }
                    if (jadwalKonsultasi == null) {
                        System.out.println("Jadwal dengan ID " + scheduleID + " tidak ditemukan atau tidak aktif.");
                    } else {
                        System.out.println("Jadwal Konsultasi " + jadwalKonsultasi.getScheduleID() + " ditemukan.");
                        Appointment pasienKonsultasi = jadwalKonsultasi.getDailyAppointmentQueue().poll(); // Ambil janji temu pertama dari antrian
                        System.out.println("Pasien yang akan dikonsultasi: " + (pasienKonsultasi != null ? pasienKonsultasi.getPasien().getNama() : "Tidak ada pasien yang menunggu.") + " pada janji temu ID: " + (pasienKonsultasi != null ? pasienKonsultasi.getAppointmentID() : "Tidak ada"));
                        System.out.println("Keluhan Awal: " + (pasienKonsultasi != null ? pasienKonsultasi.getKeluhanAwal() : "Tidak ada keluhan awal."));
                        System.out.print("Masukkan Diagnosis: ");
                        String diagnosis = scan.nextLine();
                        System.out.print("Masukkan Penanganan: ");
                        String penanganan = scan.nextLine();
                        if (pasienKonsultasi == null) {
                            System.out.println("Tidak ada pasien yang menunggu untuk konsultasi pada jadwal ini.");
                        } else {
                            for (Appointment janji : pasienKonsultasi.getPasien().getDaftarJanjiTemu()) {
                                if (janji.getAppointmentID().equals(pasienKonsultasi.getAppointmentID())) {
                                    System.out.print("Masukkan catatan dokter (opsional): ");
                                    String catatanDokter = scan.nextLine();
                                    janji.selesaikanJanjiTemu(catatanDokter);
                                }
                            }
                            dokter.lakukanKonsultasi(pasienKonsultasi.getPasien(), diagnosis, penanganan, dokter);
                            System.out.println("Konsultasi berhasil dilakukan untuk pasien " + pasienKonsultasi.getPasien().getNama() + ".");
                        }
                    }
                    break;
                case 10:
                    dokter.logout();
                    System.out.println("Kembali ke menu utama.");
                    return;
                default:
                    System.out.println("Pilihan tidak valid, silakan coba lagi.");
            }
        }
    }

    private void pharmacyUserMenu(PharmacyUser farmasi) {
        while (true) { 
            System.out.println("Pilih cabang farmasi (Ketik 0 untuk keluar):");
            for (int i = 0; i < daftarFarmasi.size(); i++) {
                System.out.println((i + 1) + ". " + daftarFarmasi.get(i).getNamaFarmasi());
            }
            System.out.print("Pilih nomor cabang (1-" + daftarFarmasi.size() + "): ");
            int pilihanCabang;
            try {
                pilihanCabang = scan.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Input harus berupa angka. Silakan coba lagi.");
                pilihanCabang = -1;
                scan.nextLine();
                continue;
            }
            scan.nextLine();
            if (pilihanCabang < 0 || pilihanCabang > daftarFarmasi.size()) {
                System.out.println("Pilihan tidak valid, kembali ke menu utama.");
            } else if (pilihanCabang == 0) {
                System.out.println("Kembali ke menu utama.");
                return;
            } 
            while (true) {
                Pharmacy pharmacy = daftarFarmasi.get(pilihanCabang - 1);
                System.out.println("Selamat datang di Farmasi " + farmasi.getNama() + "!");
                System.out.println("Menu Pengguna Farmasi:");
                System.out.println("1. Lihat Semua Items");
                System.out.println("2. Tambah Obat Baru");
                System.out.println("3. Tambah Peralatan Baru");
                System.out.println("4. Hapus Item");
                System.out.println("5. Update Item");
                System.out.println("6. Proses Penjualan");
                System.out.println("7. Keluar");
                System.out.print("Pilih opsi: ");
                int pilihan;
                try {
                    pilihan = scan.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Input harus berupa angka. Silakan coba lagi.");
                    pilihan = -1;
                    scan.nextLine();
                    continue;
                }
                scan.nextLine();

                switch (pilihan) {
                    case 1:
                        pharmacy.tampilkanInventaris();
                        break;
                    case 2:
                        System.out.print("Masukkan ID Obat: ");
                        String idObat = scan.nextLine();
                        System.out.print("Masukkan Nama Obat: ");
                        String namaObat = scan.nextLine();
                        System.out.print("Masukkan Deskripsi Obat: ");
                        String deskripsiObat = scan.nextLine();
                        System.out.print("Masukkan Harga Obat: ");
                        double hargaObat = -1;
                        try {
                            hargaObat = scan.nextDouble();
                        } catch (InputMismatchException e) {
                            System.out.println("Harga obat harus berupa angka. Silakan coba lagi.");
                            scan.nextLine();
                            break;
                        }
                        scan.nextLine();
                        System.out.print("Masukkan Jumlah Stok Obat: ");
                        int stokObat = -1;
                        try {
                            stokObat = scan.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Stok obat tidak valid. Silakan coba lagi.");
                            scan.nextLine();
                            break;
                        }
                        scan.nextLine(); // Clear the newline character
                        System.out.print("Masukkan Tanggal Kadaluarsa (YYYY-MM-DD): ");
                        String tanggalKadaluarsaInput = scan.nextLine();
                        System.out.print("Masukkan Dosis Obat (misal: 5ml): ");
                        String dosisObat = scan.nextLine();
                        System.out.print("Masukkan bentuk sediaan (misal: tablet, sirup): ");
                        String bentukSediaan = scan.nextLine();
                        System.out.print("Apakah obat ini membutuhkan resep? (ya/tidak): ");
                        String membutuhkanResepInput = scan.nextLine();
                        boolean membutuhkanResep = membutuhkanResepInput.trim().equalsIgnoreCase("ya");
                        System.out.print("Masukkan kategori obat (misal: antibiotik, vitamin): ");
                        String kategoriObat = scan.nextLine();
                        System.out.print("Masukkan Petunjuk Penyimpanan (misal: simpan di tempat sejuk): ");
                        String petunjukPenyimpanan = scan.nextLine();
                        System.out.print("Apakah obat ini merupakan obat racikan? (ya/tidak): ");
                        String obatRacikanInput = scan.nextLine();
                        boolean obatRacikan = obatRacikanInput.trim().equalsIgnoreCase("ya");
                        Medicine obatBaru = new Medicine(idObat, namaObat, deskripsiObat, hargaObat, stokObat, LocalDate.parse(tanggalKadaluarsaInput), dosisObat, bentukSediaan, membutuhkanResep, farmasi.getNama(), kategoriObat, petunjukPenyimpanan, obatRacikan);
                        pharmacy.tambahAtauUpdateItemInventaris(obatBaru, stokObat);
                        break;
                    case 3:
                        System.out.print("Masukkan ID Peralatan: ");
                        String idPeralatan = scan.nextLine();
                        System.out.print("Masukkan Nama Peralatan: ");
                        String namaPeralatan = scan.nextLine();
                        System.out.print("Masukkan Deskripsi Peralatan: ");
                        String deskripsiPeralatan = scan.nextLine();
                        System.out.print("Masukkan Harga Peralatan: ");
                        double hargaPeralatan = -1;
                        try {
                            hargaPeralatan = scan.nextDouble();
                        } catch (InputMismatchException e) {
                            System.out.println("Harga peralatan tidak valid. Silakan coba lagi.");
                            scan.nextLine();
                            break;
                        }
                        scan.nextLine();
                        System.out.print("Masukkan Jumlah Stok Peralatan: ");
                        int stokPeralatan = -1;
                        try {
                            stokPeralatan = scan.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Stok peralatan tidak valid. Silakan coba lagi.");
                            scan.nextLine();
                            break;
                        }
                        scan.nextLine();
                        System.out.print("Masukkan produsen peralatan (misal: PT. Alat Medis): ");
                        String produsenPeralatan = scan.nextLine();
                        System.out.print("Masukkan tipe atau model peralatan (misal: Model X): ");
                        String tipePeralatan = scan.nextLine();
                        System.out.print("Masukkan nomor seri peralatan (misal: 12345): ");
                        String nomorSeriPeralatan = scan.nextLine();
                        System.out.print("Masukkan materi peralatan (misal: stainless steel): ");
                        String materiPeralatan = scan.nextLine();
                        System.out.print("Masukkan peruntukan peralatan (misal: medis, laboratorium): ");
                        String peruntukanPeralatan = scan.nextLine();
                        System.out.print("Masukkan status dan kondisi peralatan (misal: baru, bekas): ");
                        String statusPeralatan = scan.nextLine();
                        Tools peralatanBaru = new Tools(idPeralatan, namaPeralatan, deskripsiPeralatan, hargaPeralatan, stokPeralatan, produsenPeralatan, tipePeralatan, nomorSeriPeralatan, materiPeralatan, peruntukanPeralatan, statusPeralatan);
                        pharmacy.tambahAtauUpdateItemInventaris(peralatanBaru, stokPeralatan);
                        break;
                    case 4:
                        pharmacy.tampilkanInventaris();
                        System.out.print("Masukkan ID Item yang ingin dihapus: ");
                        String idItem = scan.nextLine();
                        pharmacy.hapusItemDariInventaris(idItem);
                        break;
                    case 5:
                        pharmacy.tampilkanInventaris();
                        System.out.print("Masukkan ID Item yang ingin diupdate: ");
                        String idItemUpdate = scan.nextLine();
                        Item itemUpdate = pharmacy.cariItemByID(idItemUpdate);
                        if (itemUpdate != null) {
                            System.out.print("Masukkan Nama Baru (kosongkan jika tidak ingin mengubah): ");
                            String namaBaru = scan.nextLine();
                            if (!namaBaru.isEmpty()) {
                                itemUpdate.setNamaItem(namaBaru);
                            }
                            System.out.print("Masukkan Deskripsi Baru (kosongkan jika tidak ingin mengubah): ");
                            String deskripsiBaru = scan.nextLine();
                            if (!deskripsiBaru.isEmpty()) {
                                itemUpdate.setDeskripsi(deskripsiBaru);
                            }
                            System.out.print("Masukkan Harga Baru (-1 jika tidak ingin mengubah): ");
                            double hargaBaru = -1;
                            try {
                                hargaBaru = scan.nextDouble();
                            } catch (InputMismatchException e) {
                                System.out.println("Harga tidak valid. Silakan coba lagi.");
                                scan.nextLine();
                                break;
                            }
                            scan.nextLine();
                            if (hargaBaru >= 0) {
                                itemUpdate.setHarga(hargaBaru);
                            }
                            System.out.print("Masukkan Stok Baru (-1 jika tidak ingin mengubah): ");
                            int stokBaru = -1;
                            try {
                                stokBaru = scan.nextInt();
                            } catch (InputMismatchException e) {
                                System.out.println(e.getMessage());
                                scan.nextLine();
                                break;
                            }
                            scan.nextLine();
                            pharmacy.tambahAtauUpdateItemInventaris(itemUpdate, stokBaru);
                        } else {
                            System.out.println("Item dengan ID " + idItemUpdate + " tidak ditemukan.");
                        }
                        break;
                    case 6:
                        OUTER:
                        while (true) { 
                            System.out.println("Menu Proses Penjualan:");
                            System.out.println("1. Proses Konsultasi Pasien");
                            System.out.println("2. Proses Resep");
                            System.out.println("3. Proses Penjualan Obat Tanpa Resep");
                            System.out.println("4. Kembali ke Menu Farmasi");
                            System.out.print("Pilih opsi: ");
                            int pilihanPenjualan;
                            try {
                                pilihanPenjualan = scan.nextInt();
                            } catch (InputMismatchException e) {
                                System.out.println("Input harus berupa angka. Silakan coba lagi.");
                                pilihanPenjualan = -1;
                                scan.nextLine();
                                continue;
                            }
                            scan.nextLine();
                            switch (pilihanPenjualan) {
                                case 1:
                                    System.out.print("Masukkan ID Pasien untuk konsultasi: ");
                                    String userIDPasienKonsultasi = scan.nextLine();
                                    Patient pasienKonsultasi = (Patient) accountManager.cariAkunByUserID(userIDPasienKonsultasi);
                                    if (pasienKonsultasi != null) {
                                        pasienKonsultasi.lihatDaftarJanjiTemu();
                                        System.out.print("Masukan ID Appointment yang dipilih: ");
                                        String idAppointment = scan.nextLine();
                                        for (Appointment janji : pasienKonsultasi.getDaftarJanjiTemu()) {
                                            if (janji.getAppointmentID().equals(idAppointment) && janji.getStatus().equals("SELESAI")) {
                                                System.out.println("Janji temu ditemukan: " + janji.getAppointmentID());
                                                System.out.println(janji.toString());
                                                System.out.print("Masukkan biaya konsultasi: ");
                                                double biayaKonsultasi = -1;
                                                try {
                                                    biayaKonsultasi = scan.nextDouble();
                                                } catch (InputMismatchException e) {
                                                    System.out.println("Biaya konsultasi tidak valid. Silakan coba lagi.");
                                                    scan.nextLine();
                                                    break;
                                                }
                                                scan.nextLine();
                                                PaymentInfo paymentInfo = new PaymentInfo(pasienKonsultasi, janji, biayaKonsultasi, farmasi);
                                                System.out.print("Masukkan metode pembayaran (misal: Tunai, Kartu Kredit): ");
                                                String metodePembayaran = scan.nextLine();
                                                paymentInfo.prosesPelunasan(metodePembayaran);
                                                pharmacy.getDaftarTransaksi().add(paymentInfo);
                                                farmasi.processOrder(paymentInfo.getPaymentID());
                                                break;
                                            } else {
                                                System.out.println("Janji temu dengan ID " + idAppointment + " tidak ditemukan atau belum selesai.");
                                            }
                                            
                                        }
                                    } else {
                                        System.out.println("Pasien dengan UserID " + userIDPasienKonsultasi + " tidak ditemukan.");
                                    }
                                    break;
                                case 2:
                                    System.out.print("Masukkan ID Pasien untuk proses resep: ");
                                    String userIDPasienResep = scan.nextLine();
                                    Patient pasienResep = (Patient) accountManager.cariAkunByUserID(userIDPasienResep);
                                    if (pasienResep != null) {
                                        pasienResep.lihatDaftarResep();
                                        System.out.print("Masukkan ID Resep: ");
                                        String idResep = scan.nextLine();
                                        for (Prescription resep : pasienResep.getDaftarResepObat()) {
                                            if (resep.getPrescriptionID().equals(idResep)) {
                                                System.out.println("Resep ditemukan: " + resep.getPrescriptionID());
                                                System.out.println(resep.toString());
                                                System.out.print("Masukkan Masa Berlaku Resep Dalam Hari: ");
                                                int masaBerlaku = -1;
                                                try {
                                                    masaBerlaku = scan.nextInt();
                                                } catch (InputMismatchException e) {
                                                    System.out.println("Masa berlaku tidak valid. Silakan coba lagi.");
                                                    scan.nextLine();
                                                    continue;
                                                }
                                                scan.nextLine(); // Clear the newline character
                                                if (!resep.isKadaluarsa(masaBerlaku) && masaBerlaku >= 0) {
                                                    System.out.println("Resep masih berlaku.");
                                                    pharmacy.prosesResep(resep, farmasi);
                                                    System.out.print("Masukkan harga resep: ");
                                                    double hargaResep = -1;
                                                    try {
                                                        hargaResep = scan.nextDouble();
                                                    } catch (InputMismatchException e) {
                                                        System.out.println("Harga resep tidak valid. Silakan coba lagi.");
                                                        scan.nextLine();
                                                        return;
                                                    }
                                                    scan.nextLine();
                                                    PaymentInfo paymentInfo = new PaymentInfo(pasienResep, resep, hargaResep, farmasi);
                                                    System.out.print("Masukkan metode pembayaran (misal: Tunai, Kartu Kredit): ");
                                                    String metodePembayaran = scan.nextLine();
                                                    paymentInfo.prosesPelunasan(metodePembayaran);
                                                    pharmacy.getDaftarTransaksi().add(paymentInfo);
                                                    pharmacy.prosesResep(resep, farmasi);
                                                    farmasi.processOrder(paymentInfo.getPaymentID());
                                                    HashMap<String, Integer> itemsToPurchaseMap = new HashMap<>();
                                                    for (MedicineUsage obat : resep.getDaftarObatDalamResep()) {
                                                        itemsToPurchaseMap.put(obat.getMedicine().getItemID(), obat.getJumlahDiresepkan());
                                                    }
                                                    pharmacy.prosesPenjualan(pasienResep, itemsToPurchaseMap, farmasi);
                                                } else {
                                                    System.out.println("Resep sudah kadaluarsa.");
                                                    break;
                                                }
                                                break;
                                            } else {
                                                System.out.println("Resep dengan ID " + idResep + " tidak ditemukan.");
                                            }
                                        }
                                    } else {
                                        System.out.println("Pasien dengan UserID " + userIDPasienResep + " tidak ditemukan.");
                                    }
                                    break;
                                case 3:
                                    System.out.print("Masukkan ID Pasien untuk penjualan obat tanpa resep: ");
                                    String userIDPasienPenjualan = scan.nextLine();
                                    Patient pasienPenjualan = (Patient) accountManager.cariAkunByUserID(userIDPasienPenjualan);
                                    if (pasienPenjualan != null) {
                                        System.out.println("Daftar Item yang tersedia:");
                                        pharmacy.tampilkanInventaris();
                                        List<ItemQuantity> itemsToPurchase = new ArrayList<>();
                                        Map<String, Integer> itemsToPurchaseMap = new HashMap<>();
                                        PaymentInfo paymentInfo = new PaymentInfo(pasienPenjualan, itemsToPurchase, farmasi);
                                        while (true) { 
                                            System.out.print("Masukkan ID Item yang ingin dibeli (Ketik 0 jika sudah selesai): ");
                                            String idItemBeli = scan.nextLine();
                                            if (idItemBeli.equals("0")) {
                                                break; // Selesai memilih item
                                            }
                                            Item itemBeli = pharmacy.cariItemByID(idItemBeli);
                                            if (itemBeli instanceof Medicine && ((Medicine) itemBeli).isMembutuhkanResep()) {
                                                System.out.println("Item ini membutuhkan resep. Silakan gunakan opsi proses resep.");
                                                itemBeli = null;
                                                continue;
                                            }
                                            if (itemBeli != null) {
                                                System.out.print("Masukkan jumlah yang ingin dibeli: ");
                                                int jumlahBeli = -1;
                                                try {
                                                    jumlahBeli = scan.nextInt();
                                                } catch (InputMismatchException e) {
                                                    System.out.print("Jumlah beli tidak valid. Silakan coba lagi.");
                                                    scan.nextLine();
                                                    break;
                                                }
                                                scan.nextLine();
                                                if (jumlahBeli > 0 && itemBeli.getStok() >= jumlahBeli) {
                                                    System.out.println("Item " + itemBeli.getNamaItem() + " dengan jumlah " + jumlahBeli + " berhasil ditambahkan ke keranjang.");
                                                    itemsToPurchase.add(new ItemQuantity(itemBeli, jumlahBeli));
                                                    paymentInfo.tambahItemLain(itemBeli, jumlahBeli);
                                                    itemsToPurchaseMap.put(itemBeli.getItemID(), jumlahBeli);
                                                } else {
                                                    System.out.println("Jumlah beli tidak valid atau stok tidak mencukupi.");
                                                }
                                            } else {
                                                System.out.println("Item dengan ID " + idItemBeli + " tidak ditemukan atau tidak dapat dibeli.");
                                            }
                                        }
                                        if (!itemsToPurchaseMap.isEmpty()) {
                                            System.out.print("Masukkan metode pembayaran (misal: Tunai, Kartu Kredit): ");
                                            String metodePembayaran = scan.nextLine();
                                            paymentInfo.prosesPelunasan(metodePembayaran);
                                            pharmacy.getDaftarTransaksi().add(paymentInfo);
                                            pharmacy.prosesPenjualan(pasienPenjualan, itemsToPurchaseMap, farmasi);
                                            farmasi.processOrder(paymentInfo.getPaymentID());
                                        } else {
                                            System.out.println("Tidak ada item yang dibeli.");
                                        }
                                    } else {
                                        System.out.println("Pasien dengan UserID " + userIDPasienPenjualan + " tidak ditemukan.");
                                    }
                                    break;
                                case 4:
                                    System.out.println("Kembali ke menu farmasi.");
                                    break OUTER;
                                default:
                                    System.out.println("Pilihan tidak valid, silakan coba lagi.");
                            }
                            break;
                        }
                        break;
                    case 7:
                        farmasi.logout();
                        System.out.println("Kembali ke menu utama.");
                        return;
                    default:
                        System.out.println("Pilihan tidak valid, silakan coba lagi.");
                        break;
                }
            }
        }
    }
    
    private void patientMenu(Patient pasien) {
        while (true) { 
            System.out.println("Selamat Datang, Pasien " + pasien.getNama());
            System.out.println("Menu Pasien:");
            System.out.println("1. Buat Janji Temu dengan Dokter");
            System.out.println("2. Batalkan Janji Temu");
            System.out.println("3. Lihat Janji Temu");
            System.out.println("4. Lihat Rekam Medis");
            System.out.println("5. Lihat Resep Obat");
            System.out.println("6. Lihat Info Akun");
            System.out.println("7. Edit Info Akun");
            System.out.println("8. Keluar");
            System.out.print("Pilih opsi: ");
            int pilihan;
            try {
                pilihan = scan.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Input harus berupa angka. Silakan coba lagi.");
                pilihan = -1;
                scan.nextLine();
                continue;
            }
            scan.nextLine();
            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan UserID Dokter: ");
                    String userIDDokter = scan.nextLine();
                    Doctor dokter = (Doctor) accountManager.cariAkunByUserID(userIDDokter);
                    if (dokter != null) {
                        System.out.println("Daftar Jadwal Ketersediaan Dokter:");
                        dokter.lihatSemuaJadwalKetersediaan();
                        if (dokter.getDaftarJadwalKetersediaan().isEmpty()) {
                            continue;
                        }
                        System.out.print("Masukkan Waktu Janji (YYYY-MM-DD HH:MM): ");
                        String waktuJanjiInput = scan.nextLine();
                        LocalDateTime waktuJanji;
                        try {
                            waktuJanji = LocalDateTime.parse(waktuJanjiInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                        } catch (DateTimeParseException e) {
                            System.out.println("Format waktu tidak valid, silakan coba lagi.");
                            continue;
                        }
                        System.out.print("Masukkan Keluhan Awal: ");
                        String keluhanAwal = scan.nextLine();
                        boolean tersedia = true;
                        for (Appointment janji : dokter.getDaftarJanjiTemuPasien()) {
                            if (janji.getStatus().equals("DIBATALKAN_PASIEN") || janji.getStatus().equals("DIBATALKAN_DOKTER") || janji.getStatus().equals("DIBATALKAN_ADMIN") || janji.getStatus().equals("SELESAI")) {
                                continue;
                            } else if (janji.getWaktuJanji().equals(waktuJanji)) {
                                tersedia = false;
                                System.out.println("Waktu janji sudah ada, silakan pilih waktu lain.");
                                break;
                            } else if (janji.getPasien().equals(pasien) && janji.getWaktuJanji().isEqual(waktuJanji)) {
                                tersedia = false;
                                System.out.println("Pasien ini sudah memiliki janji pada waktu tersebut.");
                                break;
                            } else if (janji.getDokter().equals(dokter) && janji.getWaktuJanji().isEqual(waktuJanji)) {
                                tersedia = false;
                                System.out.println("Dokter ini sudah memiliki janji pada waktu tersebut.");
                                break;
                            } else if (janji.getWaktuJanji().isAfter(waktuJanji.minusMinutes(30)) && janji.getWaktuJanji().isBefore(waktuJanji.plusMinutes(30))) {
                                tersedia = false;
                                System.out.println("Ada janji temu lain yang bentrok dengan waktu ini, silakan pilih waktu lain.");
                                break;
                            }
                            for (Appointment janjiPasien : dokter.getDaftarJanjiTemuPasien()) {
                                if (janjiPasien.getPasien().equals(pasien) && janjiPasien.getWaktuJanji().isEqual(waktuJanji)) {
                                    tersedia = false;
                                    System.out.println("Pasien ini sudah memiliki janji pada waktu tersebut.");
                                    break;
                                }
                            }
                        }
                        boolean waktuDalamJadwal = false;
                        for (Schedule jadwal : dokter.getDaftarJadwalKetersediaan()) {
                            if (jadwal.isWithinSchedule(waktuJanji)) {
                                waktuDalamJadwal = true;
                                System.out.println("Dokter tersedia pada waktu ini.");
                                break;
                            }
                        }
                        
                        if (!dokter.getDaftarJadwalKetersediaan().isEmpty() && tersedia && waktuDalamJadwal) {
                            Appointment janjiBaru = new Appointment(("APT" + totalJanjiTemu), pasien, dokter, waktuJanji, keluhanAwal);
                            janjiBaru.setStatus("DIAJUKAN");
                            dokter.tambahJanjiTemu(janjiBaru);
                            pasien.tambahJanjiTemu(janjiBaru);
                            totalJanjiTemu++;
                            System.out.println("Janji temu telah dibuat untuk " + pasien.getNama() + " dengan Dr. " + dokter.getNama() + " pada " + waktuJanji);
                        } else {
                            System.out.println("Gagal membuat janji temu: waktu bentrok atau tidak sesuai dengan jadwal ketersediaan.");
                        }
                    } else {
                        System.out.println("Dokter dengan UserID " + userIDDokter + " tidak ditemukan.");
                    }
                    break;
                case 2:
                    pasien.lihatDaftarJanjiTemu();
                    System.out.print("Masukkan ID Janji Temu yang ingin dibatalkan: ");
                    String idJanji = scan.nextLine();
                    Appointment janjiDibatalkan = null;
                    for (Appointment janji : pasien.getDaftarJanjiTemu()) {
                        if (janji.getAppointmentID().equals(idJanji)) {
                            janjiDibatalkan = janji;
                            break;
                        }
                    }
                    if (janjiDibatalkan != null) {
                        if (janjiDibatalkan.getStatus().equals("DIJADWALKAN")) {
                            System.out.println("Janji Temu dengan ID " + idJanji + " tidak dapat dibatalkan karena statusnya bukan DIJADWALKAN.");
                        } else {
                            if (pasien.batalkanJanjiTemu(janjiDibatalkan)) {
                                janjiDibatalkan.batalkanJanjiTemu("PASIEN");
                                System.out.println("Janji Temu " + idJanji + " telah dibatalkan.");
                            } else {
                                System.out.println("Gagal membatalkan janji temu.");
                            }
                        }
                    } else {
                        System.out.println("Janji Temu dengan ID " + idJanji + " tidak ditemukan.");
                    }
                    break;
                case 3:
                    pasien.lihatDaftarJanjiTemu();
                    break;
                case 4:
                    pasien.lihatInformasiRekamMedis();
                    break;
                case 5:
                    pasien.lihatDaftarResep();
                    break;
                case 6:
                    System.out.println(pasien.toString());
                    break;
                case 7:
                    System.out.print("Masukkan Alamat Baru: ");
                    String alamatBaru = scan.nextLine();
                    pasien.setAlamat(alamatBaru);
                    System.out.print("Masukkan Nomor Telepon Baru: ");
                    String nomorTeleponBaru = scan.nextLine();
                    pasien.setNomorTelepon(nomorTeleponBaru);
                    System.out.println("Informasi akun Anda telah diperbarui.");
                    break;
                case 8:
                    pasien.logout();
                    System.out.println("Kembali ke menu utama.");
                    return;
                default:
                    System.out.println("Pilihan tidak valid, silakan coba lagi.");
                    break;
            }
        }
    }

    private void managerMenu() {
        while (true) {
            System.out.println("Menu Manajer:");
            System.out.println("1. Hapus Akun");
            System.out.println("2. Lihat Semua Akun");
            System.out.println("3. Keluar");
            System.out.print("Pilih opsi: ");
            int pilihan;
            try {
                pilihan = scan.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Input harus berupa angka. Silakan coba lagi.");
                pilihan = -1;
                scan.nextLine();
                continue;
            }
            scan.nextLine();
            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan UserID Akun yang ingin dihapus: ");
                    String userID = scan.nextLine();
                    Person akun = accountManager.cariAkunByUserID(userID);
                    if (akun != null) {
                        accountManager.hapusAkun(userID);
                        System.out.println("Akun dengan UserID " + userID + " telah dihapus.");
                    } else {
                        System.out.println("Akun dengan UserID " + userID + " tidak ditemukan.");
                    }
                    break;
                case 2:
                    accountManager.tampilkanDetailSemuaAkun();
                    break;
                case 3:
                    System.out.println("Kembali ke menu utama.");
                    return;
                default:
                    System.out.println("Pilihan tidak valid, silakan coba lagi.");
            }
        }
    }

    private static DayOfWeek konversiHariKeDayOfWeek(String hari) {
    switch (hari.trim().toLowerCase()) {
        case "senin": return DayOfWeek.MONDAY;
        case "selasa": return DayOfWeek.TUESDAY;
        case "rabu": return DayOfWeek.WEDNESDAY;
        case "kamis": return DayOfWeek.THURSDAY;
        case "jumat": return DayOfWeek.FRIDAY;
        case "sabtu": return DayOfWeek.SATURDAY;
        case "minggu": return DayOfWeek.SUNDAY;
        default: throw new IllegalArgumentException("Hari tidak valid: " + hari);
    }
}
}
