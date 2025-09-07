package order;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import stock.*;

public class Order {
    private final int orderId;
    private static int orderCounter = 0;
    private final int number;
    private OrderStatus status;
    private final LocalDate date;
    private double total;
    private final LinkedHashMap<String, OrderItem> orderItems = new LinkedHashMap<>();
    private final Stock stock = Stock.getInstance();

    public Order() {
        orderId = orderCounter++;
        number = (int) (Math.random() * 100000);
        status = OrderStatus.NEW;
        date = LocalDate.now();
    }

    public double getTotal() { return total; }
  
    public int getOrderId() { return orderId; }

    public boolean addOrderItem(String itemName, int quantity) {
    
        OrderItem newItem = new OrderItem(itemName, quantity);
        orderItems.put(itemName, newItem);
        recalculateTotal();

        Product product = stock.searchProduct(itemName);
        product.setQuantity(product.getQuantity() - quantity);
        if(product.getQuantity() == 0) stock.deleteStock(itemName);
        return true;
    }

    public boolean updateOrderItem(String itemName, int quantity) {
       
        OrderItem item = orderItems.get(itemName);
        if (item == null) return false;

        // restore old quantity
        Product product = stock.searchProduct(itemName);
        if (product != null) {
            product.setQuantity(product.getQuantity() + item.getQuantity());
        }

        // set new quantity
        item.setQuantity(quantity);
        recalculateTotal();

        if (product.getQuantity() == 0)
            stock.deleteStock(itemName);
        else
            product.setQuantity(product.getQuantity() - quantity);

        return true;
    }

    public boolean deleteOrderItem(String itemName) {
        if (!orderItems.containsKey(itemName)) return false;

        OrderItem item = orderItems.remove(itemName);

        if (stock.searchProduct(itemName) != null)
            stock.searchProduct(itemName).setQuantity(stock.getItemQuantity(itemName) + item.getQuantity());
        else
            stock.addStock(item.getQuantity(), itemName, item.getPrice() / item.getQuantity());

        recalculateTotal();
        return true;
    }

    private void recalculateTotal() {
        total = orderItems.values().stream().mapToDouble(OrderItem::getPrice).sum();
    }

    public void updateOrderStatus(OrderStatus orderStatus) {
        status = orderStatus;
    }

    public int itemsCount(){
        return orderItems.size();
    }
    public OrderItem findItem (String name){
        return orderItems.get(name);
    }
     public void print() {
        System.out.println("\nOrder Information:\n");
        System.out.println("Order ID: " + orderId);
        System.out.println("Order Creation Date: " + date);
        System.out.println("Order Number: " + number);
        System.out.println("Order Status: " + status.toString() + "\n");
        System.out.println("Ordered Items: ");
        orderItems.values().forEach(OrderItem::print);
        System.out.println("Total Price: " + total + "$\n");
    }
}
