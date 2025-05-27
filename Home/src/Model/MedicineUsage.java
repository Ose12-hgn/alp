package Model;

public class MedicineUsage {
    private Medicine medicine; // Meds that is racik
    private String dosis;      // Example: "5mg, 10ml"
    private String frekuensi;  // Example: "4x a day"
    private String durasi;     // Example: "1 Week, 1 Month"
    private String aturanPakai; // Example: "Before eating, after eating"
    private int jumlahDiresepkan; // Example: "1 bottle, 10 pills"

    // Constructor
    public MedicineUsage(Medicine medicine, String dosis, String frekuensi, String durasi, String aturanPakai, int jumlahDiresepkan) {
        if (medicine == null) {
            throw new IllegalArgumentException("Obat tidak boleh null untuk MedicineUsage.");
        }
        if (jumlahDiresepkan <= 0) {
            throw new IllegalArgumentException("Jumlah yang diresepkan harus lebih dari 0.");
        }
        this.medicine = medicine;
        this.dosis = (dosis == null || dosis.trim().isEmpty()) ? "Sesuai anjuran" : dosis;
        this.frekuensi = (frekuensi == null || frekuensi.trim().isEmpty()) ? "Sesuai anjuran" : frekuensi;
        this.durasi = (durasi == null || durasi.trim().isEmpty()) ? "Sesuai anjuran" : durasi;
        this.aturanPakai = (aturanPakai == null || aturanPakai.trim().isEmpty()) ? "Sesuai anjuran" : aturanPakai;
        this.jumlahDiresepkan = jumlahDiresepkan;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public String getDosis() {
        return dosis;
    }

    public String getFrekuensi() {
        return frekuensi;
    }

    public String getDurasi() {
        return durasi;
    }

    public String getAturanPakai() {
        return aturanPakai;
    }

    public int getJumlahDiresepkan() {
        return jumlahDiresepkan;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public void setFrekuensi(String frekuensi) {
        this.frekuensi = frekuensi;
    }

    public void setDurasi(String durasi) {
        this.durasi = durasi;
    }

    public void setAturanPakai(String aturanPakai) {
        this.aturanPakai = aturanPakai;
    }

    public void setJumlahDiresepkan(int jumlahDiresepkan) {
        if (jumlahDiresepkan <= 0) {
            throw new IllegalArgumentException("Jumlah yang diresepkan harus lebih dari 0.");
        }
        this.jumlahDiresepkan = jumlahDiresepkan;
    } // Getter Setter

    @Override
    public String toString() {
        return medicine.getNamaItem() +
               " (" + medicine.getBentukSediaan() + ")" +
               " - Jumlah: " + jumlahDiresepkan +
               ", Dosis: " + dosis +
               ", Frekuensi: " + frekuensi +
               ", Durasi: " + durasi +
               ", Aturan: " + aturanPakai;
    }
}
