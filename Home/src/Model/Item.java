package Model;

public class Item {
    // Atributte
    private String itemID;
    private String namaItem;
    private String deskripsi;
    private double harga;
    private int stok;

    // Constructor
    public Item(String itemID, String namaItem, String deskripsi, double harga, int stok) {
        this.itemID = itemID;
        this.namaItem = namaItem;
        this.deskripsi = deskripsi;
        this.harga = harga;
        this.stok = stok;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getNamaItem() {
        return namaItem;
    }

    public void setNamaItem(String namaItem) {
        this.namaItem = namaItem;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        if (harga < 0) {
            System.out.println("Harga tidak boleh negatif.");
            return;
        }
        this.harga = harga;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        if (stok < 0) {
            System.out.println("Stok tidak boleh negatif.");
            return;
        }
        this.stok = stok;
    }

    public String getInfoHarga() {
        return "Harga " + namaItem + ": Rp" + harga;
    }

    // Function untuk menambah stok 
    public void tambahStok(int jumlah) {
        if (jumlah < 0) {
            System.out.println("Jumlah penambahan stok tidak boleh negatif.");
            return;
        }
        this.stok += jumlah;
        System.out.println(jumlah + " " + namaItem + " telah ditambahkan ke stok. Stok saat ini: " + this.stok);
    }

    // Function untuk mengurangi stok
    public boolean kurangiStok(int jumlah) {
        if (jumlah < 0) {
            System.out.println("Jumlah pengurangan stok tidak boleh negatif.");
            return false;
        }
        if (this.stok >= jumlah) {
            this.stok -= jumlah;
            System.out.println(jumlah + " " + namaItem + " telah dikurangi dari stok. Stok saat ini: " + this.stok);
            return true;
        } else {
            System.out.println("Stok " + namaItem + " tidak mencukupi. Stok saat ini: " + this.stok + ", dibutuhkan: " + jumlah);
            return false;
        }
    }

    @Override
    public String toString() {
        return "Item{" +
               "itemID='" + itemID + '\'' +
               ", namaItem='" + namaItem + '\'' +
               ", harga=" + harga +
               ", stok=" + stok +
               '}';
    }
}
