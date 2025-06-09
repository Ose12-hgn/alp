package Model;

public class MedicineUsage {
    // Attribute
    private Medicine medicine; 
    private String dosis;
    private String frekuensi; 
    private String durasi;
    private String aturanPakai; 
    private int jumlahDiresepkan;

    // Constructor
    public MedicineUsage(Medicine medicine, String dosis, String frekuensi, String durasi, String aturanPakai, int jumlahDiresepkan) {
        if (medicine == null) {
            System.out.println("Obat tidak boleh null untuk MedicineUsage.");
        }
        if (jumlahDiresepkan <= 0) {
            System.out.println("Jumlah yang diresepkan harus lebih dari 0.");
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
            System.out.println("Jumlah yang diresepkan harus lebih dari 0.");
        }
        this.jumlahDiresepkan = jumlahDiresepkan;
    }

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
