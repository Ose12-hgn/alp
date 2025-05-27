package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Pharmacy {
    // Attribute
    private String pharmacyID;
    private String namaFarmasi;
    private String alamatFarmasi;
    private Map<String, Item> inventarisItems;
    private List<PaymentInfo> daftarTransaksi;

    // Constructor
    public Pharmacy(String pharmacyID, String namaFarmasi, String alamatFarmasi) {
        this.pharmacyID = pharmacyID;
        this.namaFarmasi = namaFarmasi;
        this.alamatFarmasi = alamatFarmasi;
        this.inventarisItems = new HashMap<>();
        this.daftarTransaksi = new ArrayList<>();
    }

    public String getPharmacyID() {
        return pharmacyID;
    }

    public String getNamaFarmasi() {
        return namaFarmasi;
    }

    public String getAlamatFarmasi() {
        return alamatFarmasi;
    }

    public Map<String, Item> getInventarisItems() {
        return inventarisItems;
    }

    // Function untuk menambah atau meng-update item ke gudang
    public void tambahAtauUpdateItemInventaris(Item item, int quantity) {
        if (item == null || quantity <= 0) {
            System.out.println("Gagal menambahkan item: Data item atau kuantitas tidak valid.");
            return;
        }

        if (inventarisItems.containsKey(item.getItemID())) {
            Item existingItem = inventarisItems.get(item.getItemID());
            existingItem.tambahStok(quantity);
            System.out.println("Stok untuk item '" + item.getNamaItem() + "' berhasil diperbarui. Stok saat ini: " + existingItem.getStok());
        } else {
            item.setStok(quantity);
            inventarisItems.put(item.getItemID(), item);
            System.out.println("Item baru '" + item.getNamaItem() + "' berhasil ditambahkan ke inventaris dengan stok: " + quantity);
        }
    }

    // Function untuk menghapus item dari gudang
    public void hapusItemDariInventaris(String itemID) {
        if (inventarisItems.containsKey(itemID)) {
            Item removedItem = inventarisItems.remove(itemID);
            System.out.println("Item '" + removedItem.getNamaItem() + "' berhasil dihapus dari inventaris.");
        } else {
            System.out.println("Gagal menghapus item: Item dengan ID '" + itemID + "' tidak ditemukan.");
        }
    }

    // Function untuk mencari item berdasarkan ID 
    public Item cariItemByID(String itemID) {
        return inventarisItems.get(itemID);
    }

    // Function untuk mencari itemn berdasarkan nama
    public List<Item> cariItemByName(String namaItem) {
        return inventarisItems.values().stream()
                .filter(item -> item.getNamaItem().equalsIgnoreCase(namaItem))
                .collect(Collectors.toList());
    }

    // Function untuk menampikan semua inventaris
    public void tampilkanInventaris() {
        if (inventarisItems.isEmpty()) {
            System.out.println("Inventaris farmasi '" + namaFarmasi + "' kosong.");
            return;
        }
        System.out.println("Inventaris Farmasi: " + namaFarmasi);
        System.out.println("--------------------------------------------------");
        System.out.printf("%-10s | %-30s | %-10s | %-10s%n", "ID Item", "Nama Item", "Harga (Rp)", "Stok");
        System.out.println("--------------------------------------------------");
        for (Item item : inventarisItems.values()) {
            System.out.printf("%-10s | %-30s | %-10.2f | %-10d%n",
                    item.getItemID(), item.getNamaItem(), item.getHarga(), item.getStok());
        }
        System.out.println("--------------------------------------------------");
    }

    // Function untuk memproses dari penjual obat
    public PaymentInfo prosesPenjualan(Patient patient, Map<String, Integer> itemsToPurchase, PharmacyUser pharmacyUser) {
        if (patient == null || itemsToPurchase == null || itemsToPurchase.isEmpty() || pharmacyUser == null) {
            System.out.println("Proses penjualan gagal: Data pasien, item, atau pengguna farmasi tidak lengkap.");
            return null;
        }

        System.out.println("Memproses penjualan untuk pasien: " + patient.getNama() + " oleh " + pharmacyUser.getNama());
        List<Item> purchasedItemsList = new ArrayList<>();
        double totalBiaya = 0;

        for (Map.Entry<String, Integer> entry : itemsToPurchase.entrySet()) {
            String itemID = entry.getKey();
            int kuantitasDiminta = entry.getValue();
            Item itemDiInventaris = cariItemByID(itemID);

            if (itemDiInventaris == null) {
                System.out.println("Item dengan ID '" + itemID + "' tidak ditemukan.");
                return null;
            }
            if (itemDiInventaris.getStok() < kuantitasDiminta) {
                System.out.println("Stok untuk item '" + itemDiInventaris.getNamaItem() + "' tidak mencukupi. Diminta: " + kuantitasDiminta + ", Tersedia: " + itemDiInventaris.getStok());
                return null;
            }

            if (itemDiInventaris instanceof Medicine) {
                Medicine medicine = (Medicine) itemDiInventaris;
                if (medicine.membutuhkanResep()) {
                    System.out.println("Obat '" + medicine.getNamaItem() + "' membutuhkan resep. Proses melalui `prosesResep`.");
                }
            }

            totalBiaya += itemDiInventaris.getHarga() * kuantitasDiminta;
            purchasedItemsList.add(itemDiInventaris);
        }

        System.out.println("Total biaya: Rp" + totalBiaya);
        for (Map.Entry<String, Integer> entry : itemsToPurchase.entrySet()) {
            Item itemDiInventaris = cariItemByID(entry.getKey());
            itemDiInventaris.kurangiStok(entry.getValue());
        }
        System.out.println("Penjualan berhasil diproses.");
        System.out.println("Fungsi prosesPenjualan perlu implementasi objek PaymentInfo/Transaction yang lebih detail.");
        return null;
    }
    // Function untuk konfirmasi proses resep
    public boolean prosesResep(Prescription prescription, PharmacyUser pharmacyUser) {
        if (prescription == null || pharmacyUser == null) {
            System.out.println("Proses resep gagal: Data resep atau pengguna farmasi tidak valid.");
            return false;
        }

        System.out.println("Memproses resep ID: " + prescription.getPrescriptionID() + " untuk pasien: " + prescription.getPasien().getNama() + " oleh " + pharmacyUser.getNama());
        System.out.println("Resep berhasil diproses.");
        System.out.println("Fungsi prosesResep perlu implementasi detail interaksi dengan objek Prescription dan Medicine.");
        return true;
    }

    // Function untuk membuat obat racikan atau obat baru
    public Medicine buatObatRacikan(List<Map<Medicine, Double>> components, Prescription prescription, PharmacyUser pharmacyUser) {
        System.out.println(pharmacyUser.getNama() + " meracik obat untuk resep ID: " + prescription.getPrescriptionID());
        System.out.println("Fungsi buatObatRacikan perlu implementasi detail.");
        return null;
    }

    @Override
    public String toString() {
        return "Pharmacy{" +
               "pharmacyID='" + pharmacyID + '\'' +
               ", namaFarmasi='" + namaFarmasi + '\'' +
               ", alamatFarmasi='" + alamatFarmasi + '\'' +
               ", jumlahItemDiInventaris=" + inventarisItems.size() +
               '}';
    }
}
