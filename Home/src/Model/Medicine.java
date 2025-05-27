package Model;

public class Medicine {
  // properties in medicine
    private String name;
    private String manufacturer;
    private double price;
    private String expiryDate;
    private int quantity;
  
    // constructors
    public Medicine(String name, String manufacturer, double price, String expiryDate, int quantity) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.price = price;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
    } // asking for parameter

    public String getName() {
      return name;
    }
  
    public void setName(String name) {
      this.name = name;
    }

    public String getManufacturer() {
      return manufacturer;
    }
  
    public void setManufacturer(String manufacturer) {
      this.manufacturer = manufacturer;
    }

    public double getPrice() {
      return price;
    }
  
    public void setPrice(double price) {
      this.price = price;
    }

    public String getExpiryDate() {
      return expiryDate;
    }
  
    public void setExpiryDate(String expiryDate) {
      this.expiryDate = expiryDate;
    }

    public int getQuantity() {
      return quantity;
    }
    public void setQuantity(int quantity) {
      this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name + " (" + manufacturer + "), Expires: " + expiryDate + ", Price: " + price + ", Quantity: " + quantity;
    } // display the information
}
