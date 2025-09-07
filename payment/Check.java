package payment;


public class Check extends ElectronicPayment {
    private String name;
    private String bankID;

    public Check(String name, String bankID, String accountNumber, String accountHolderName, double balance) {
        super(accountNumber, accountHolderName, balance);
        this.name = name;
        this.bankID = bankID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankID() {
        return bankID;
    }

    public void setBankID(String bankID) {
        this.bankID = bankID;
    }

    public void print() {
        System.out.printf(
                "Check Payment =========\n\n Information:\n Name: %s \n Bank ID: %s \n Payment Date: %s \n Amount: %.2f $ \n",
                getName(), getBankID(), getPaymentDate().toString(), getAmount());

        account.print();

    }

}
