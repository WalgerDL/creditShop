package payment;

import domain.PaymentSchedule;
import domain.Payments;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CalculationPayment {
    private Double arrayAllMP;
    private Double arrayPersent;
    private Payments payments;

    //период (количество месяцецев)
    private int yearMouth;

    //Входящий остаток(или лимит)
    private Double arrayAppendix;
    //%
    private Double persent;
    //Платеж
    private Double arrayMoney;

    public CalculationPayment(int term, double amount, double rate) {
        this.yearMouth = term;
        this.arrayAppendix = amount;
        this.persent = rate;
        payments = new Payments();

        //ежемесячный платеж равные доли
        this.arrayMoney = arrayAppendix/yearMouth;
        //ежемесячный платеж
        Double resultDOUBLE = Math.pow((1 + persent / 1200), yearMouth);
        this.arrayAllMP = (arrayAppendix * (persent / 1200))/(1-(1/resultDOUBLE));
    }

    public void payAnnuity() {

        for (int i = 1; i < yearMouth + 1; i++)
        {
            //"Месяц" = i;
            //% всего
            arrayPersent = (arrayAppendix * persent / 1200);
            //сумма
            arrayMoney = arrayAllMP - arrayPersent;
            //Остаток
            arrayAppendix = arrayAppendix - arrayMoney;

            PaymentSchedule paymentSchedule = new PaymentSchedule();
            paymentSchedule.setArrayMoney(arrayMoney);
            paymentSchedule.setPersent(arrayPersent);
            paymentSchedule.setPaymentAmount(arrayAllMP);
            paymentSchedule.setPrincipalBalance(arrayAppendix);
            payments.addPayment(paymentSchedule);
            System.out.println("" + i + " сумма:=" + arrayMoney + " %=" + arrayPersent + " платеж" + arrayAllMP + " остаток:=" + arrayAppendix);
        }
    }

    public void payDifferential() {

        for (int i = 1; i < yearMouth+1; i++)
        {
            //"Месяц" = i;
            //% всего
            arrayPersent = (arrayAppendix * persent / 1200);
            arrayAppendix = arrayAppendix - arrayMoney;
            //
            PaymentSchedule paymentSchedule = new PaymentSchedule();
            paymentSchedule.setArrayMoney(arrayMoney);
            paymentSchedule.setPersent(arrayPersent);
            paymentSchedule.setPaymentAmount(arrayAllMP);
            paymentSchedule.setPrincipalBalance(arrayAppendix);
            payments.addPayment(paymentSchedule);
            //display
            System.out.println("" + i + " сумма:=" + arrayMoney + " %=" + arrayPersent + " платеж" + arrayAllMP + " остаток:=" + arrayAppendix);
        }
    }

    public Payments getPayments() {
        return payments;
    }

}
