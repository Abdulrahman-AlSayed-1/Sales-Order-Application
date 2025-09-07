package customer;

import java.util.LinkedHashMap;

public class CustomersRecord {
    private static final CustomersRecord recordInstance = new CustomersRecord();
    private final LinkedHashMap<String, Customer> customers = new LinkedHashMap<>();
    private static int customersCount = 0;

    private CustomersRecord() {}

    public static CustomersRecord getInstance() {
        return recordInstance;
    }

    public boolean customerExists(String key) {
        return customers.containsKey(key);
    }

    public void addPerson(String phoneNumber, String fullName, String billingAddress) {
        Customer newCustomer = new Person(++customersCount, phoneNumber, fullName, billingAddress);
        customers.put(fullName, newCustomer);
    }

    public void addCompany(String phoneNumber, String companyName, String location) {
        Customer newCustomer = new Company(++customersCount, phoneNumber, companyName, location);
        customers.put(companyName, newCustomer);
    }

    public boolean deleteCustomer(String key) {
        if (!customers.containsKey(key)) return false;
        customers.remove(key);
        return true;
    }

    private boolean editPerson(String key, String fieldName, String newData) {
        if (!customers.containsKey(key) || !(customers.get(key) instanceof Person)) return false;
        Person customer = (Person) customers.get(key);
        switch (fieldName) {
            case "phone number": customer.setPhoneNumber(newData); break;
            case "full name": customer.setFullName(newData); break;
            case "billing address": customer.setBillingAddress(newData); break;
            default: return false;
        }
        return true;
    }

    private boolean editCompany(String key, String fieldName, String newData) {
        if (!customers.containsKey(key) || !(customers.get(key) instanceof Company)) return false;
        Company customer = (Company) customers.get(key);
        switch (fieldName) {
            case "phone number": customer.setPhoneNumber(newData); break;
            case "company name": customer.setCompanyName(newData); break;
            case "company location": customer.setLocation(newData); break;
            default: return false;
        }
        return true;
    }
    public boolean editCustomer(String key, String fieldName, String newData){
        if(customers.get(key) instanceof Company) return editCompany(key, fieldName, newData);
        else if (customers.get(key) instanceof Person) return editPerson(key, fieldName, newData);
        else return false;
    }
    public Customer findCustomer(String name) {
        return customers.get(name);
    }

    public void print() {
        System.out.println("\nCustomers Record:\n");
        customers.forEach((key, customer) -> customer.print());
    }
}
