package payment;

import java.time.LocalDate;

public class Cash extends Payment {
    private double cashValue;

    public Cash(double cashValue) {
        this.cashValue = cashValue;
    }

    public double getCash() {
        return cashValue;
    }

    public boolean setCash(double cashValue) {
        if (cashValue > 0) {
            this.cashValue = cashValue;
            return true;
        } else return false;
    }

    @Override
    public boolean pay() {
        if (cashValue >= amount) {
            cashValue -= amount;
            setPaymentDate(LocalDate.now().toString());
            return true;
        } else {
            return false;
        }
    }

    public void print() {
        System.out.printf(
                "Cash Payment =========\n\n Information:\n Cash Value: %.2f \n Payment Date: %s \n Amount: %.2f $\n",
                getCash(), getPaymentDate().toString(), getAmount());
    }
}