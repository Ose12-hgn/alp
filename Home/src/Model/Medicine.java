package Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Medicine extends Item {
    private LocalDate tanggalKadaluarsa;
    private String dosis; // Example "Zyloric 300mg"
    private String bentukSediaan; // Example: "Tabs Syrup"
    private boolean membutuhkanResep;
    private String produsen; // Company Make
    private String kategoriObat; // Example: "Antibiotics Vitamin"
    private String petunjukPenyimpanan;
    private boolean isRacikan; // Racik or not

    // Constructor
    public Medicine(String itemID, String namaItem, String deskripsi, double harga, int stok,
                    LocalDate tanggalKadaluarsa, String dosis, String bentukSediaan,
                    boolean membutuhkanResep, String produsen, String kategoriObat,
                    String petunjukPenyimpanan, boolean isRacikan) {
        super (itemID, namaItem, deskripsi, harga, stok);
        this.tanggalKadaluarsa = tanggalKadaluarsa;
        this.dosis = dosis;
        this.bentukSediaan = bentukSediaan;
        this.membutuhkanResep = membutuhkanResep;
        this.produsen = produsen;
        this.kategoriObat = kategoriObat;
        this.petunjukPenyimpanan = petunjukPenyimpanan;
        this.isRacikan = isRacikan;
    } // asking for parameter

    public LocalDate getTanggalKadaluarsa() {
        return tanggalKadaluarsa;
    }

    public String getDosis() {
        return dosis;
    }

    public String getBentukSediaan() {
        return bentukSediaan;
    }

    public boolean isMembutuhkanResep() {
        return membutuhkanResep;
    }

    public String getProdusen() {
        return produsen;
    }

    public String getKategoriObat() {
        return kategoriObat;
    }

    public String getPetunjukPenyimpanan() {
        return petunjukPenyimpanan;
    }

    public boolean isRacikan() {
        return isRacikan;
    }

    public void setTanggalKadaluarsa(LocalDate tanggalKadaluarsa) {
        this.tanggalKadaluarsa = tanggalKadaluarsa;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public void setBentukSediaan(String bentukSediaan) {
        this.bentukSediaan = bentukSediaan;
    }

    public void setMembutuhkanResep(boolean membutuhkanResep) {
        this.membutuhkanResep = membutuhkanResep;
    }

    public void setProdusen(String produsen) {
        this.produsen = produsen;
    }

    public void setKategoriObat(String kategoriObat) {
        this.kategoriObat = kategoriObat;
    }

    public void setPetunjukPenyimpanan(String petunjukPenyimpanan) {
        this.petunjukPenyimpanan = petunjukPenyimpanan;
    }

    public void setRacikan(boolean racikan) {
        isRacikan = racikan;
    }

    public boolean isKadaluarsa() {
        return LocalDate.now().isAfter(this.tanggalKadaluarsa);
    } //getter setter

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyY");
        String tanggalKadaluarsaStr = (tanggalKadaluarsa != null) ? tanggalKadaluarsa.format(formatter) : "N/A";

        return "Medicine{" +
               "itemID='" + getItemID() + '\'' +
               ", namaItem='" + getNamaItem() + '\'' +
               ", harga=Rp" + String.format("%.2f", getHarga()) +
               ", stok=" + getStok() +
               ", tanggalKadaluarsa=" + tanggalKadaluarsaStr +
               ", dosis='" + dosis + '\'' +
               ", bentukSediaan='" + bentukSediaan + '\'' +
               ", membutuhkanResep=" + membutuhkanResep +
               ", produsen='" + produsen + '\'' +
               ", kategoriObat='" + kategoriObat + '\'' +
               ", petunjukPenyimpanan='" + petunjukPenyimpanan + '\'' +
               ", isRacikan=" + isRacikan +
               (isKadaluarsa() ? ", STATUS: KADALUARSA" : "") +
               '}';
    } // printing the medicine
}
