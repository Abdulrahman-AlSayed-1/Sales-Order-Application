
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Predicate;

import order.Order;
import payment.Transaction;
import stock.Stock;

class Validator {
    private static Stock stock = Stock.getInstance();

    // standard validation
    static String standardValidation(Predicate<String> rule, String message, String errorMessage,
            Scanner sc) {
        while (true) {
            System.out.println(message);
           String input = sc.nextLine().trim();
            if (!rule.test(input))
                System.out.println(errorMessage);
            else
                return input;
        }
    }

    // overloading
    static String standardValidation(Predicate<String> rule, String message, String errorMessage,
            Scanner sc,
            String textCase) {
        while (true) {
            System.out.println(message);
            String input = textCase.equals("lower") ? sc.nextLine().trim().toLowerCase() : sc.nextLine().trim();
            if (!rule.test(input))
                System.out.println(errorMessage);
            else
                return input;
        }
    }

    // custom validation

    // customer
    static String customerType(Scanner sc) {
        while (true) {
            System.out.println("Enter Customer Type (Person / Company)");
            String name = sc.nextLine().trim().toLowerCase();
            if (!name.equals("company") && !name.equals("person"))
                System.out.println("Invalid customer type. Only company and person are accepted");
            else
                return name;
        }
    }

    static String personName(Scanner sc) {
        while (true) {
            System.out.println("Enter Customer full name");
            String name = sc.nextLine().trim();
            if (!name.matches("^[^0-9]+$"))
                System.out.println("Customer Name Cannot Contain Digits");
            else if (name.length() < 6)
                System.out.println("Customer Name Must Be At Least 6 Characters Long!!");
            else
               return name;
        }
    }

    static String phoneNumber(Scanner sc) {
        while (true) {
            System.out.println("Enter Customer phone number");
            String number = sc.nextLine().trim();
            if (!number.matches("^\\d+$")) {
                System.out.println("Phone Number Must Contain Digits Only");
            } else if (number.length() < 10)
                System.out.println("Phone number must be at least 10 digits long");
            else
                return number;
        }
    }

    static String address(Scanner sc) {
        while (true) {
            System.out.println("Enter Customer Billing Address");
            String billingAddress = sc.nextLine().trim();
            if (billingAddress.length() < 10)
                System.out.println("Billing Address Must Be At Least 10 Characters Long");
            else
                return billingAddress;
        }
    }

    static String companyName( Scanner sc) {
        while (true) {
            System.out.println("Enter Company Name");
            String name = sc.nextLine().trim();
            if(name.length() < 3)
                System.out.println("Company Name Must Be At Least 3 Characters Long");
            else if (!name.matches("^[^0-9]+$"))
                System.out.println("Company Name Cannot Have Digits");
            else
                return name ;
        }
    }

    static String location(Scanner sc) {
        while (true) {
            System.out.println("Enter Location of The Company:");
            String location = sc.nextLine().trim();
            if (location.length() < 10)
                System.out.println("Location Must Be At Least 10 Characters Long");
            else
                return location;
        }
    }

    // Product
    static String productName( Scanner sc) {
        while (true) {
            System.out.println("Enter Product Name:");
            String name = sc.nextLine().trim();
            if (name.length() < 4)
                System.out.println("Product Name Must Be At Least 4 Characters Long");
            else if (stock.searchProduct(name) != null)
                System.out.println("Product With This Name Already Exists");
            else
                return name;
        }
    }

    static int productQuantity(Scanner sc) {
        while (true) {
            System.out.println("Enter Product Quantity:");
            int quantity = 0;
            try {
                quantity = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input Value , Quantity Must Be an Integer");
                sc.nextLine();
                continue;
            }
            if (quantity < 0)
                System.out.println("Quantity Cannot Be Negative");
            else
                return quantity;
        }
    }

    static double financialValue(String message, Scanner sc) { // like product price | account balance
        while (true) {
            System.out.println(message);
            double value = 0;
            try {
                value = sc.nextDouble();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input Value , Value Must Be a Number");
                sc.nextLine();
                continue;
            }
            if (value < 0)
                System.out.println("Value Cannot Be Negative");
            else
                return value;
        }
    }

    // order item
    static String itemNameInStock(Scanner sc) {
        while (true) {
            System.out.println("Enter Item Name to Add to Order:");
            String name = sc.nextLine().trim();
            if(name.length() == 0)
                System.out.println("Item Name Cannot Be Empty");
            else if (stock.searchProduct(name) == null)
                System.out.println("This Item Does not Exist in Stock");
            else
                return name;
        }
    }

    static String itemNameInOrder(String message, Order order , Scanner sc) {
        while (true) {
            System.out.println(message);
            String name = sc.nextLine().trim();
            if(name.length() == 0)
                System.out.println("Item Name Cannot Be Empty");
            else if (order.findItem(name) == null)
                System.out.println("This Item Does not Exist in Order List");
            else
                return name;
        }
    }

    static int itemQuantity(String name, Scanner sc) {
        while (true) {
            System.out.println("Enter Item Quantity:");
            int quantity = 0;
            try {
                quantity = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input Value , Quantity Must Be an Integer");
                sc.nextLine();
                continue;
            }
            if (quantity < 0)
                System.out.println("Quantity Cannot Be Negative");
            else if (stock.getItemQuantity(name) < quantity)
                System.out.println(stock.getItemQuantity(name) + " is The Max Quantity of This Item in The Stock");
            else
                return quantity;
        }
    }

    static Transaction orderID(int id, Scanner sc) {
        while (true) {
            System.out.println("Enter Order ID:");
            try {
                id = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Order ID Must Be an Integer");
                sc.nextLine();
                continue;
            }
            Transaction transaction = Transaction.find(id);
            if (transaction == null) // may throw erro of negative outofboundindex
                System.out.println("There is No Transaction Recorded for this Order in The System.");
            else
                return transaction;
        }
    }

    // payment
    static String accountHolderNameOrCheckName(String message, Scanner sc) {
        while (true) {
            System.out.println(message);
            String name = sc.nextLine().trim();
            if (name.length() < 10)
                System.out.println("The Name Must Be At Least 10 Characters Long!!");
            else if (name.matches(".*\\d.*"))
                System.out.println("The Name Cannot Contain Digits");
            else
                return name;
        }
    }
}
