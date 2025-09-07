package order;

import stock.Stock;

public class OrderItem {
    private String itemName;
    private int quantity;
    private double price;

    OrderItem(String itemName, int quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
        setPrice();
    }

    public void setQuantity(int newQuantity) {
        this.quantity = newQuantity;
        setPrice();
    }

    public int getQuantity() {
        return quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }
    
    private void setPrice() {
        price = quantity * Stock.getInstance().getPrice(itemName);
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
        setPrice();
    }

    public void print() {
        System.out.println("Item Name: " + itemName);
        System.out.println("Item Quantity Ordered: " + quantity);
        System.out.println("Item Price: " + price + "$\n");
    }
}
