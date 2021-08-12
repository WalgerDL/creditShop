package domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Payments {
    private final List<PaymentSchedule> payments = new ArrayList<>();

    public Payments() {
    }

    public void addPayment(PaymentSchedule p) {
        payments.add(p);
    }

    public List<PaymentSchedule> getPaymentSchedules() {
        return payments;
    }

    public int getCount() {
        return payments.size();
    }

}
