package stock;

public class Product {
    private final int id;
    private int quantity;
    private final String name;
    private double price;
    private static int productCounter = 0;

    public Product(int quantity, String name, double price) {
        this.id = ++productCounter;
        this.quantity = quantity;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void print(){
        System.out.println("\nProduct ID: " + id);
        System.out.println("Quantity: " + quantity);
        System.out.println("Name: " + name);
        System.out.println("Price: " + price + "\n\n");
    }
}
