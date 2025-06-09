package Model;

public class Tools extends Item {
    // Attribute
    private String produsen;
    private String modelAtauTipe;
    private String nomorSeri;
    private String material;
    private String peruntukan;
    private String statusKondisi;

    // Constructor
    public Tools (String itemID, String namaItem, String deskripsi, double harga, int stok, String produsen, String modelAtauTipe, String nomorSeri, String material, String peruntukan, String statusKondisi) {
        super(itemID, namaItem, deskripsi, harga, stok);
        this.produsen = produsen;
        this.modelAtauTipe = modelAtauTipe;
        this.nomorSeri = nomorSeri;
        this.material = material;
        this.peruntukan = peruntukan;
        this.statusKondisi = statusKondisi;
    }

    public String getProdusen() {
        return produsen;
    }

    public void setProdusen(String produsen) {
        this.produsen = produsen;
    }

    public String getModelAtauTipe() {
        return modelAtauTipe;
    }

    public void setModelAtauTipe(String modelAtauTipe) {
        this.modelAtauTipe = modelAtauTipe;
    }

    public String getNomorSeri() {
        return nomorSeri;
    }

    public void setNomorSeri(String nomorSeri) {
        this.nomorSeri = nomorSeri;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getPeruntukan() {
        return peruntukan;
    }

    public void setPeruntukan(String peruntukan) {
        this.peruntukan = peruntukan;
    }

    public String getStatusKondisi() {
        return statusKondisi;
    }

    public void setStatusKondisi(String statusKondisi) {
        this.statusKondisi = statusKondisi;
    }

    @Override
    public String toString() {
        return "Tool{" +
                "itemID='" + getItemID() + '\'' +
                ", namaItem='" + getNamaItem() + '\'' +
                ", deskripsi='" + getDeskripsi() + '\'' +
                ", harga=Rp" + String.format("%.2f", getHarga()) +
                ", stok=" + getStok() +
                '}';
    }
}
