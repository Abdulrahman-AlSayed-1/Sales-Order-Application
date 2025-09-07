package payment;


public class Credit extends ElectronicPayment {
    private final String number;
    private String expireDate;
    private String type;

    public Credit(String number, String exprDate, String type , String accountNumber, String accountHolderName, double balance) {
        super(accountNumber, accountHolderName, balance);
        this.number = number;
        this.type = type;
        this.expireDate = exprDate;
    }

    public String getNumber() {
        return number;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void print() {
        System.out.printf(
                "Credit Card Payment =========\n\n Information:\n Card Number: %s \n Expiration Date: %s \n Type: %s \n Payment Date: %s \n Amount: %.2f $ \n",
                getNumber(), getExpireDate().toString(), getType(), getPaymentDate().toString(), getAmount());
        account.print();
    }
}
