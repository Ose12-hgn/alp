package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PaymentInfo {
    // Attribute
    private String paymentID;
    private Patient patient;
    private Appointment appointment;
    private Prescription prescription; 
    private List<ItemQuantity> otherItemsPurchased;
    private double totalBiaya;
    private String metodePembayaran;
    private String statusPembayaran;
    private LocalDateTime tanggalPembayaranDibuat;
    private LocalDateTime tanggalPelunasan;
    private Person diprosesOleh;

    private static final DateTimeFormatter ID_TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    public record ItemQuantity(Item item, int quantity) {
        public double getTotalPrice() {
            if (item != null) {
                return item.getHarga() * quantity;
            }
            return 0;
        }

        @Override
        public String toString() {
            if (item != null) {
                return String.format("%s (Qty: %d) @ Rp%.2f = Rp%.2f",
                        item.getNamaItem(), quantity, item.getHarga(), getTotalPrice());
            }
            return "Item tidak valid";
        }
    }
    
    // Constructor
    public PaymentInfo(Patient patient, double totalBiaya, Person diprosesOleh) {
        if (patient == null || patient.getNama() == null || patient.getNama().trim().isEmpty()) {
            System.out.println("Pasien dan nama pasien tidak boleh null atau kosong untuk membuat PaymentID.");
        } else {

        String nameForIdGeneration = patient.getNama();
        String baseNameForId = nameForIdGeneration.replaceAll("[AIUEOaiueoBbCc ]", "").toUpperCase();
        
        if (baseNameForId.length() > 10) {
            baseNameForId = baseNameForId.substring(0, 10);
        }
        if (baseNameForId.isEmpty()){
            baseNameForId = "GENERIC"; 
        }
        this.paymentID = "PAY-" + baseNameForId + "-" + LocalDateTime.now().format(ID_TIMESTAMP_FORMATTER);
        
        this.patient = patient;
        this.totalBiaya = totalBiaya;
        this.statusPembayaran = "BELUM_LUNAS";
        this.tanggalPembayaranDibuat = LocalDateTime.now();
        this.diprosesOleh = diprosesOleh;
        this.otherItemsPurchased = new ArrayList<>();
        }
    }

    // Constructor khusus untuk pembayaran Janji Temu
    public PaymentInfo(Patient patient, Appointment appointment, double totalBiaya, Person diprosesOleh) {
        this(patient, totalBiaya, diprosesOleh);
        this.appointment = appointment;
    }

    // Constructor khusus untuk pembayaran Resep
    public PaymentInfo(Patient patient, Prescription prescription, double totalBiaya, Person diprosesOleh) {
        this(patient, totalBiaya, diprosesOleh);
        this.prescription = prescription;
    }

    // Constructor khusus untuk pembelian item non-resep di farmasi
    public PaymentInfo(Patient patient, List<ItemQuantity> items, Person diprosesOleh) {
        this(patient, 0, diprosesOleh); // Total biaya akan dihitung
        this.otherItemsPurchased = (items != null) ? new ArrayList<>(items) : new ArrayList<>();
        this.totalBiaya = calculateTotalFromItems();
    }

    public String getPaymentID() { 
        return paymentID;
    }

    public Patient getPatient() { 
        return patient;
    }
    
    public Appointment getAppointment() { 
        return appointment; 
    }

    public Prescription getPrescription() { 
        return prescription; 
    }

    public List<ItemQuantity> getOtherItemsPurchased() { 
        return otherItemsPurchased; 
    }

    public double getTotalBiaya() {
        if (this.prescription == null && this.appointment == null && this.otherItemsPurchased != null && !this.otherItemsPurchased.isEmpty()) {
            return calculateTotalFromItems();
        }
        return totalBiaya;
    }
    
    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    public String getStatusPembayaran() { 
        return statusPembayaran; 
    }

    public LocalDateTime getTanggalPembayaranDibuat() { 
        return tanggalPembayaranDibuat;
    }

    public LocalDateTime getTanggalPelunasan() { 
        return tanggalPelunasan; 
    }

    public Person getDiprosesOleh() { 
        return diprosesOleh; 
    }

    public void setPatient(Patient patient) { 
        this.patient = patient; 
    }

    public void setAppointment(Appointment appointment) { 
        this.appointment = appointment; 
    }

    public void setPrescription(Prescription prescription) { 
        this.prescription = prescription; 
    }

    public void setOtherItemsPurchased(List<ItemQuantity> otherItemsPurchased) {
        this.otherItemsPurchased = otherItemsPurchased;
        this.totalBiaya = calculateTotalFromItems();
    }
    
    public void setTotalBiaya(double totalBiaya) {
        if (totalBiaya < 0) { System.out.println("Total biaya tidak boleh negatif."); return; }
        this.totalBiaya = totalBiaya;
    }

    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran; 
    }

    public void setStatusPembayaran(String statusPembayaran) {
        this.statusPembayaran = statusPembayaran;
        if ("LUNAS".equalsIgnoreCase(statusPembayaran) && this.tanggalPelunasan == null) {
            this.tanggalPelunasan = LocalDateTime.now();
        } else if (!"LUNAS".equalsIgnoreCase(statusPembayaran)) {
            this.tanggalPelunasan = null;
        }
    }
    
    public void setDiprosesOleh(Person diprosesOleh) {
        this.diprosesOleh = diprosesOleh; 
    }

    // Function untuk menghitung total biaya dari item yang dibeli
    private double calculateTotalFromItems() {
        if (this.otherItemsPurchased == null || this.otherItemsPurchased.isEmpty()) { 
            return 0; 
        }
        double sum = 0;
        for (ItemQuantity iq : this.otherItemsPurchased) { 
            sum += iq.getTotalPrice(); 
        }
        return sum;
    }

    // Function untuk menambahkan item lain ke dalam pembayaran
    public void tambahItemLain(Item item, int quantity) {
        if (item == null || quantity <= 0) { System.out.println("Item atau kuantitas tidak valid."); return; }
        if (this.otherItemsPurchased == null) { this.otherItemsPurchased = new ArrayList<>(); }
        this.otherItemsPurchased.add(new ItemQuantity(item, quantity));
        this.totalBiaya = calculateTotalFromItems();
    }

    // Function untuk memproses pelunasan pembayaran
    public boolean prosesPelunasan(String metode) {
        if ("LUNAS".equalsIgnoreCase(this.statusPembayaran)) { 
            System.out.println("Pembayaran ID: " + this.paymentID + " sudah lunas."); 
            return false;
        }
        setMetodePembayaran(metode);
        setStatusPembayaran("LUNAS");
        System.out.println("Pembayaran ID: " + this.paymentID + " sejumlah Rp" + String.format("%.2f", this.totalBiaya) + " berhasil dilunasi dengan metode " + metode + ".");
        return true;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss");
        String paymentDetails = "PaymentInfo{\n";
        paymentDetails += "  paymentID='" + paymentID + "',\n";
        paymentDetails += "  patient=" + (patient != null ? patient.getNama() : "N/A") + ",\n";

        if (appointment != null) {
            paymentDetails += "  appointmentID='" + appointment.getAppointmentID() + "',\n";
        }
        if (prescription != null) {
            paymentDetails += "  prescriptionID='" + (prescription.getPrescriptionID() != null ? prescription.getPrescriptionID() : "N/A") + "',\n";
        }
        if (otherItemsPurchased != null && !otherItemsPurchased.isEmpty()) {
            paymentDetails += "  otherItemsPurchased=[\n";
            for (int i = 0; i < otherItemsPurchased.size(); i++) {
                ItemQuantity iq = otherItemsPurchased.get(i);
                paymentDetails += "    " + iq.toString();
                if (i < otherItemsPurchased.size() - 1) {
                    paymentDetails += ",\n";
                } else {
                    paymentDetails += "\n";
                }
            }
            paymentDetails += "  ],\n";
        }

        paymentDetails += "  totalBiaya=Rp" + String.format("%.2f", getTotalBiaya()) + ",\n";
        paymentDetails += "  metodePembayaran='" + (metodePembayaran != null ? metodePembayaran : "Belum ditentukan") + "',\n";
        paymentDetails += "  statusPembayaran='" + statusPembayaran + "',\n";
        paymentDetails += "  tanggalPembayaranDibuat=" + (tanggalPembayaranDibuat != null ? tanggalPembayaranDibuat.format(formatter) : "N/A") + ",\n";

        if (tanggalPelunasan != null) {
            paymentDetails += "  tanggalPelunasan=" + tanggalPelunasan.format(formatter) + ",\n";
        }
        paymentDetails += "  diprosesOleh=" + (diprosesOleh != null ? diprosesOleh.getNama() : "N/A") + "\n";
        paymentDetails += "}";
        return paymentDetails;
    }
}
