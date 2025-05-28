package Model;

public class Tools extends Item {
    private String produsen;
    private String modelAtauTipe;
    private String nomorSeri;
    private String material;
    private String peruntukan;
    private boolean membutuhkanPerawatanKhusus;
    private String statusKondisi;

    public Tool(String itemID, String namaItem, String deskripsi, double harga, int stok,
                String produsen, String modelAtauTipe, String nomorSeri, String material,
                String peruntukan, boolean membutuhkanPerawatanKhusus, String statusKondisi) {
        super(itemID, namaItem, deskripsi, harga, stok);

        if (produsen == null || produsen.trim().isEmpty()) {
            throw new IllegalArgumentException("Produsen tidak boleh kosong untuk Tool.");
        }
        this.produsen = produsen;
        this.modelAtauTipe = (modelAtauTipe == null || modelAtauTipe.trim().isEmpty()) ? "N/A" : modelAtauTipe;
        this.nomorSeri = nomorSeri;
        this.material = (material == null || material.trim().isEmpty()) ? "N/A" : material;
        this.peruntukan = (peruntukan == null || peruntukan.trim().isEmpty()) ? "Umum" : peruntukan;
        this.membutuhkanPerawatanKhusus = membutuhkanPerawatanKhusus;
        this.statusKondisi = (statusKondisi == null || statusKondisi.trim().isEmpty()) ? "Baik" : statusKondisi;
    }

    public String getProdusen() {
        return produsen;
    }

    public String getModelAtauTipe() {
        return modelAtauTipe;
    }

    public String getNomorSeri() {
        return nomorSeri;
    }

    public String getMaterial() {
        return material;
    }

    public String getPeruntukan() {
        return peruntukan;
    }

    public boolean isMembutuhkanPerawatanKhusus() {
        return membutuhkanPerawatanKhusus;
    }

    public String getStatusKondisi() {
        return statusKondisi;
    }

    public void setProdusen(String produsen) {
        if (produsen == null || produsen.trim().isEmpty()) {
            throw new IllegalArgumentException("Produsen tidak boleh kosong.");
        }
        this.produsen = produsen;
    }

    public void setModelAtauTipe(String modelAtauTipe) {
        this.modelAtauTipe = (modelAtauTipe == null || modelAtauTipe.trim().isEmpty()) ? "N/A" : modelAtauTipe;
    }

    public void setNomorSeri(String nomorSeri) {
        this.nomorSeri = nomorSeri;
    }

    public void setMaterial(String material) {
        this.material = (material == null || material.trim().isEmpty()) ? "N/A" : material;
    }

    public void setPeruntukan(String peruntukan) {
        this.peruntukan = (peruntukan == null || peruntukan.trim().isEmpty()) ? "Umum" : peruntukan;
    }

    public void setMembutuhkanPerawatanKhusus(boolean membutuhkanPerawatanKhusus) {
        this.membutuhkanPerawatanKhusus = membutuhkanPerawatanKhusus;
    }

    public void setStatusKondisi(String statusKondisi) {
        this.statusKondisi = (statusKondisi == null || statusKondisi.trim().isEmpty()) ? "Baik" : statusKondisi;
    }

    @Override
    public String toString() {
        String itemInfo = "itemID='" + getItemID() + '\'' +
                          ", namaItem='" + getNamaItem() + '\'' +
                        ", harga=Rp" + String.format("%.2f", getHarga()) +
                          ", stok=" + getStok();

        return "Tool{" +
               itemInfo + // Informasi dari kelas Item
               ", produsen='" + produsen + '\'' +
               ", modelAtauTipe='" + modelAtauTipe + '\'' +
               (nomorSeri != null && !nomorSeri.isEmpty() ? ", nomorSeri='" + nomorSeri + '\'' : "") +
               ", material='" + material + '\'' +
               ", peruntukan='" + peruntukan + '\'' +
               ", membutuhkanPerawatanKhusus=" + membutuhkanPerawatanKhusus +
               ", statusKondisi='" + statusKondisi + '\'' +
               '}';
    }
}
