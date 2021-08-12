package domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentSchedule {

    private Long id;
    private LocalDate paymentDate;
    private int countDay;

    private Double paymentAmount;
    private Double arrayMoney;
    private Double persent;
    private Double principalBalance;

}
