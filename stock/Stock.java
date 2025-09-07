package stock;

import java.util.LinkedHashMap;

public class Stock {
    private static int count = 0;
    private static Stock stockInstance = new Stock();
    private final LinkedHashMap<String, Product> stock = new LinkedHashMap<>();

    private Stock() {}

    public static Stock getInstance() {
        return stockInstance;
    }

    public boolean addStock(int quantity, String name, double price) {

        if (stock.containsKey(name)) return false;
        stock.put(name, new Product(quantity, name, price));
        count++;
        return true;
    }

    public void updateStock(String name, int newQuantity) {
        Product product = stock.get(name);
        product.setQuantity(newQuantity);
        if (product.getQuantity() == 0)
            stock.remove(name);
    }

    public boolean deleteStock(String name) {
        if (!stock.containsKey(name))
            return false;
        else {
            stock.remove(name);
            count--;
            return true;
        }
    }

    public int getItemQuantity(String name) {
        return stock.get(name).getQuantity();
    }

    public void setItemQuantity(String name, int quantity) {
        stock.get(name).setQuantity(quantity);
    }

    public Product searchProduct(String name) {
        return stock.get(name);
    }

    public double getPrice(String name) {
        return stock.get(name).getPrice();
    }

    public void print() {
        System.out.println("Stock Products:\n");
        System.out.println("No. of Available Products: " + count + ".\n");
        stock.forEach((name, product) -> product.print());
    }
}
