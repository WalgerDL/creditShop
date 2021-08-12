package loans.domain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class PaymentSchedule {

    @Id
    private Long id;
    private LocalDate paymentDate;
    private int countDay;

    private Double paymentAmount;
    private Double arrayMoney;
    private Double persent;
    private Double principalBalance;

}
