package customer;

public abstract class Customer {
    private final int id;
    private String phoneNumber;

    Customer(int id, String phoneNumber) {
        this.id = id;
        this.phoneNumber = phoneNumber;
    }

    final public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    final public int getId() {
        return id;
    }

    final public String getPhoneNumber() {
        return phoneNumber;
    }

    public abstract void print();
}

class Person extends Customer {
    private String billingAddress;
    private String fullName;

    Person(int id, String phoneNumber, String fullName, String billingAddress) {
        super(id, phoneNumber);
        this.fullName = fullName;
        this.billingAddress = billingAddress;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getFullName() {
        return fullName;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    @Override
    public void print() {
        System.out.printf(
                "\nPerson Customer =========\n\n id: %d \n Full Name: %s \n Phone Number: %s \n Billing Address: %s\n\n",
                getId(), getFullName(), getPhoneNumber(), getBillingAddress());
    }
}

class Company extends Customer {
    private String location;
    private String companyName;

    Company(int id, String phoneNumber, String companyName, String location) {
        super(id, phoneNumber);
        this.companyName = companyName;
        this.location = location;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public void print() {
        System.out.printf(
                "\nCompany Customer =========\n\n id: %d \n Company Name: %s \n Phone Number: %s \n Location: %s\n",
                getId(), getCompanyName(), getPhoneNumber(), getLocation());
    }
}