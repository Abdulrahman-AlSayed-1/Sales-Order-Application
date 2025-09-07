package payment;

import java.time.LocalDate;

public abstract class Payment {
    protected String paymentDate;
    protected double amount;
  
    Payment(){
        paymentDate = "Not Yet Paid";
    }
    public String getPaymentDate() {
        return paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    protected void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public abstract boolean pay();

    public abstract void print();
}

abstract class ElectronicPayment extends Payment {

    protected BankAccount account;

    ElectronicPayment(String accountNumber, String accountHolderName, double balance) {
        account = new BankAccount(accountNumber, accountHolderName, balance);
    }
    
    @Override
    public boolean pay() {
        if (account.withdraw(amount)) {
            setPaymentDate(LocalDate.now().toString());
            return true;
        }
        return false;
    }

    public BankAccount getAccount() {
        return account;
    }

}


