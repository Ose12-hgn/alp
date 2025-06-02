package Model;

public class Tools extends Item {
    private String produsen;
    private String modelAtauTipe;
    private String nomorSeri;
    private String material;
    private String peruntukan;
    private String statusKondisi;

    public Tools (String itemID, String namaItem, String deskripsi, double harga, int stok) {
        super(itemID, namaItem, deskripsi, harga, stok);

        if (produsen == null || produsen.trim().isEmpty()) {
            throw new IllegalArgumentException("Produsen tidak boleh kosong untuk Tool.");
        }
    }

    public String getProdusen() {
        return produsen;
    }

    public void setProdusen(String produsen) {
        if (produsen == null || produsen.trim().isEmpty()) {
            throw new IllegalArgumentException("Produsen tidak boleh kosong.");
        }
        this.produsen = produsen;
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
