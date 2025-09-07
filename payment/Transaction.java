package payment;

import java.time.LocalDate;
import java.util.LinkedHashMap;

import customer.Customer;
import order.Order;
import order.OrderStatus;

public class Transaction {
    private final LocalDate date;
    private Customer customer;
    private Order order;
    private Payment payment;
    private static final LinkedHashMap<Integer , Transaction> transactions = new LinkedHashMap<>();

    public Transaction(Customer customer, Order order, Payment payment) {
        this.customer = customer;
        this.date = LocalDate.now();
        this.order = order;
        order.updateOrderStatus(OrderStatus.HOLD);
        this.payment = payment;
        this.payment.amount = order.getTotal();
        transactions.put(order.getOrderId(), this);    
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Order getOrder() {
        return order;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public boolean pay() {
        if (payment.pay()){
            order.updateOrderStatus(OrderStatus.PAID);
            return true;
        }
        order.updateOrderStatus(OrderStatus.CANCELLED);
        return false;                 
    }
    public static void deleteTransaction(int order_id){
        transactions.remove(order_id);
    }
    public static Transaction find(int order_id){
       return transactions.get(order_id);
    }
    public void print() {
        System.out.println("Transaction information:\n\n");
        System.out.println("Transaction Date: " + date.toString());
        customer.print();
        order.print();
        payment.print();
        System.out.println("\n====================================\n");
    }

    public static void printTransactions(){
        System.out.println("All System Transactions:\n\n");
        transactions.forEach((key,transaction) -> transaction.print());
    }
}
