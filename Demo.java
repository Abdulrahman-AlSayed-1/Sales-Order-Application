import java.util.InputMismatchException;
import java.util.Scanner;

import customer.*;
import order.Order;
import payment.*;
import stock.Stock;

public class Demo {
    public static void main(String[] arg) {
        Scanner sc = new Scanner(System.in);
        byte choice = 0;
        System.out.println("\n\n");
        System.out.println("\t\t Welcome to Sales Order Application \n\n");
        do {
            System.out.println("\n- Customer Options\n");
            System.out.println("\t1. Add a New Customer");
            System.out.println("\t2. Update an Existing Customer");
            System.out.println("\t3. Delete an Existing Customer\n");
            System.out.println("- Product Options\n");
            System.out.println("\t4. Add a New Product");
            System.out.println("\t5. Update an Existing Product Quantity");
            System.out.println("\t6. Delete an Existing Product\n");
            System.out.println("- Sales Process Options\n");
            System.out.println("\t7. Add Transaction");
            System.out.println("\t8. Update Order");
            System.out.println("\t9. Pay Order \n");
            System.out.println("- Printing Options\n");
            System.out.println("\t10. Print Customers Details");
            System.out.println("\t11. Print Stock Products");
            System.out.println("\t12. Print System Transactions\n");
            System.out.println("13. Exit\n\n");
            try {
                choice = sc.nextByte();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input Value");
                sc.nextLine();
                continue;
            }
            CustomersRecord record = CustomersRecord.getInstance();
            Stock stock = Stock.getInstance();

            String phoneNumber = null;
            switch (choice) {
                case 1:
                    String type = Validator.customerType(sc);
                    if (type.equals("person")) {
                        String fullName = Validator.personName(sc);
                        String billingAddress = Validator.address(sc);
                        phoneNumber = Validator.phoneNumber(sc);
                        record.addPerson(phoneNumber, fullName, billingAddress);
                    } else {
                        String companyName = Validator.companyName(sc);
                        String location = Validator.location(sc);
                        phoneNumber = Validator.phoneNumber(sc);
                        record.addCompany(phoneNumber, companyName, location);
                    }
                    System.out.println("Customer Added Successfully.");
                    break;
                case 2:
                    String typeToUpdate = Validator.customerType(sc), nameToUpdate = null, field = null,
                            newValue = null;
                    if (typeToUpdate.equals("person")) {

                        nameToUpdate = Validator.standardValidation(name -> record.findCustomer(name) != null,
                                "Enter Customer Name:", "Customer not found.", sc);
                        field = Validator.standardValidation(
                                value -> value.equals("full name") || value.equals("billing address")
                                        || value.equals("phone number"),
                                "What do you want to change (full name, billing address, phone number)?",
                                "Invalid field. No field with this name:", sc, "lower");

                        if (field.equals("phone number"))
                            newValue = Validator.phoneNumber(sc);
                        else if (field.equals("full name"))
                            newValue = Validator.personName(sc);
                        else
                            newValue = Validator.address(sc);

                    } else {
                        nameToUpdate = Validator.standardValidation(name -> record.findCustomer(name) != null,
                                "Enter Company Name:", "Company not found.", sc);
                        field = Validator.standardValidation(
                                value -> value.equals("company name") || value.equals("location")
                                        || value.equals("phone number"),
                                "What do you want to change (company name, company location , phone number)?",
                                "Invalid field. There is No field With This Name:", sc, "lower");

                        if (field.equals("phone number"))
                            newValue = Validator.phoneNumber(sc);
                        else if (field.equals("company name"))
                            newValue = Validator.companyName(sc);
                        else
                            newValue = Validator.location(sc);
                    }
                    if (record.editCustomer(nameToUpdate, field, newValue))
                        System.out.println("Customer Updated Successfully");
                    else
                        System.out.println("Failed to Update Customer");

                    break;
                case 3:
                    String customerNameToDelete = Validator.standardValidation(
                            value -> record.findCustomer(value) != null,
                            "Enter Customer Name You Want to Delete: ", "Customer With This Name Does Not Exist", sc);
                    record.deleteCustomer(customerNameToDelete);
                    System.out.println("Customer Deleted Successfully");
                    break;
                case 4:
                    String productName = Validator.productName(sc);
                    double price = Validator.financialValue("Enter Product Price:", sc);
                    int quantity = Validator.productQuantity(sc);
                    stock.addStock(quantity, productName, price);
                    System.out.println("Product Added Successfully");
                    break;
                case 5:
                    String productNameToUpdate = Validator.standardValidation(
                            value -> stock.searchProduct(value) != null,
                            "Enter Product Name:", "This Product does not exist!!", sc);
                    int newQuantity = Validator.productQuantity(sc);
                    stock.updateStock(productNameToUpdate, newQuantity);
                    System.out.println("Product Updated Successfully");
                    break;
                case 6:
                    String productNameToDelete = Validator.standardValidation(
                            value -> stock.searchProduct(value) != null,
                            "Enter Product Name:", "This Product does not exist!!", sc);
                    stock.deleteStock(productNameToDelete);
                    System.out.println("Product Deleted Successfully");
                    break;
                case 7:
                    String customerName = Validator.standardValidation(value -> record.findCustomer(value) != null,
                            "Enter Customer Name:", "Customer with this name does not exist", sc);

                    Customer customer = record.findCustomer(customerName);

                    Order order = new Order(); // create new order
                    while (true) {
                        System.out.println("1. Add Order item");
                        System.out.println("2. Exit");
                        byte input;
                        try {
                            input = sc.nextByte();
                            sc.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid Input Value");
                            sc.nextLine();
                            continue;
                        }

                        if (input == 1) {

                            String itemName = Validator.itemNameInStock(sc);
                            int itemQuantity = Validator.itemQuantity(itemName, sc);
                            order.addOrderItem(itemName, itemQuantity);

                        } else if (input == 2 && order.itemsCount() == 0) {
                            System.out.println("You Must Add At Least One Item to Your Order");
                        } else if (input == 2)
                            break;
                        else
                            System.out.println("Invalid Input , Choose Either 1 or 2");
                    }

                    String paymentMethod = Validator.standardValidation(
                            value -> value.equals("credit") || value.equals("cash") || value.equals("check"),
                            "Enter Payment Method (Credit/Cash/Check Only):", "Invalid Payment Method!!", sc, "lower");

                    String holderName = null, accountNumber = null;
                    double balance = 0;
                    if (!paymentMethod.equals("cash")) {
                        System.out.println("\nFirstly We Need to Get Bank Account Information\n");

                        holderName = Validator.accountHolderNameOrCheckName("Enter Account Holder Name:", sc);
                        accountNumber = Validator.standardValidation(value -> value.length() >= 10,
                                "Enter Account Number:", "Account Number Must Be At Least 10 Characters Long!!",
                                sc);
                        balance = Validator.financialValue("Enter Account Intial Balance", sc);
                    }
                    Payment payment = null;
                    switch (paymentMethod) {

                        case "credit":

                            String cardNumber = Validator.standardValidation(
                                    value -> value.length() >= 10 && !value.matches(".*[a-zA-Z].*"),
                                    "Enter Credit Card Number (minimum length is 10 digits)", "Invalid Card Number!",
                                    sc);
                            String creditType = Validator.standardValidation(
                                    value -> value.equals("visa") || value.equals("mastercard"),
                                    "Enter Credit Card Type (Visa/MasterCard) Only",
                                    "Unsupported Type!! ,Only Visa and MasterCard are Accepted", sc, "lower");
                            String expirationDate = Validator.standardValidation(
                                    value -> value.matches("\\d{2}/\\d{2}"),
                                    "Enter Expiration Date (MM/YY):", "Invalid Expiration Date Format!!", sc);

                            payment = new Credit(cardNumber, expirationDate, creditType, accountNumber, holderName,
                                    balance);
                            break;

                        case "check":
                            String checkName = Validator.accountHolderNameOrCheckName(
                                    "Please Enter The Payee's Name as Written on The Check:", sc);
                            String bankID = Validator.standardValidation(value -> value.length() >= 10,
                                    "Enter Payer Bank ID:", "Bank ID Must Be At Least 10 Characters Long!!",
                                    sc);
                            payment = new Check(checkName, bankID, accountNumber, holderName, balance);
                            break;
                        default:
                            double cashValue = Validator.financialValue("Enter Cash Value:", sc);
                            payment = new Cash(cashValue);
                            break;
                    }
                    new Transaction(customer, order, payment);
                    System.out.println("Transaction Added Successfully.");
                    break;
                case 8:
                    int orderID = 0;
                    Transaction transaction = Validator.orderID(orderID, sc);
                    Order orderToUpdate = transaction.getOrder();
                    byte ch = -1;
                    do {
                        System.out.println("\n");
                        System.out.println("Order Updation Options\n");
                        System.out.println("\t1. Add New Item");
                        System.out.println("\t2. Delete Item");
                        System.out.println("\t3. Replace Item");
                        System.out.println("\t4. Update item Quantity");
                        System.out.println("\n5. Exit");
                        try {
                            ch = sc.nextByte();
                            sc.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid Input Value");
                            sc.nextLine();
                            continue;
                        }
                        switch (ch) {
                            case 1:
                                String itemName = Validator.itemNameInStock(sc);
                                int orderedQuantity = Validator.itemQuantity(itemName, sc);
                                orderToUpdate.addOrderItem(itemName, orderedQuantity);
                                System.out.println("New Item Added Successfully");
                                break;
                            case 2:
                                String itemNameToDelete = Validator.itemNameInOrder("Enter Item Name to Delete:",
                                        orderToUpdate, sc);
                                orderToUpdate.deleteOrderItem(itemNameToDelete);
                                if (orderToUpdate.itemsCount() == 0)
                                    System.out.println(
                                            "Item Deleted Successfully and Transaction Deleted Because The Order Has Become Empty");
                                else
                                    System.out.println("Item Deleted Successfully");
                                break;
                            case 3:
                                String itemNameToReplace = Validator.itemNameInOrder("Enter Item Name to Replace:",
                                        orderToUpdate, sc);
                                orderToUpdate.deleteOrderItem(itemNameToReplace);
                                String newItemName = Validator.itemNameInStock(sc);
                                int newItemQuantity = Validator.itemQuantity(newItemName, sc);
                                orderToUpdate.addOrderItem(newItemName, newItemQuantity);
                                System.out.println("Item Replaced Successfully");
                                break;
                            case 4:
                                String itemNameToUpdateQuantity = Validator
                                        .itemNameInOrder("Enter Item Name to Update its Quantity:", orderToUpdate, sc);
                                int updatedQuantity = Validator.itemQuantity(itemNameToUpdateQuantity, sc);
                                orderToUpdate.updateOrderItem(itemNameToUpdateQuantity, updatedQuantity);
                                System.out.println("Item Quantity Updated Successfully");
                                break;
                            case 5:
                                // Exist
                                transaction.getPayment().setAmount(orderToUpdate.getTotal());
                                break;
                            default:
                                System.out.println("Invalid Choice Please Choose (1-5) Only");
                        }

                    } while (ch != 5);
                    break;
                case 9:
                    int orderIDToPay = -1;
                    Transaction ts = Validator.orderID(orderIDToPay, sc);
                    if (ts.pay())
                        System.out.println("Order Paid Successfully");
                    else {
                        System.out.println("Order Payment Cancelled , There is No Enough Balance/Cash.");
                        System.out.println("Do you want to deposit some money? Answer with (yes,no)");
                        String answer = sc.next().toLowerCase();
                        if (answer.equals("yes")) {
                            if (ts.getPayment() instanceof Check) {
                                Check check = (Check) ts.getPayment();
                                double deposit = Validator.financialValue("How Much Do You Want to Add?", sc);
                                check.getAccount().deposit(deposit);
                            } else if (ts.getPayment() instanceof Credit) {
                                Credit credit = (Credit) ts.getPayment();
                                double deposit = Validator.financialValue("How Much Do You Want to Add?", sc);
                                credit.getAccount().deposit(deposit);
                            } else {
                                Cash cash = (Cash) ts.getPayment();
                                double deposit = Validator.financialValue("How Much Do You Want to Add?", sc);
                                cash.setCash(deposit);
                            }
                            System.out.println("Deposit Successful");
                        }
                    }
                    break;
                case 10:
                    record.print();
                    break;
                case 11:
                    stock.print();
                    break;
                case 12:
                    Transaction.printTransactions();
                    break;
                case 13:
                    sc.close();
                    System.out.println("\n\n\t\tThank you for using Sales Order Application!\n\n");
                    break;
                default:
                    System.out.println("Invalid Choice , Choose between (1-13) only");
            }

        } while (choice != 13);
    }
}
