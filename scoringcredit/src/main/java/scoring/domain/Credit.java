package scoring.domain;

import lombok.Data;

@Data
public class Credit {
    private Long id;
    private Double limitCredit;
    private Double rate;
    private int term;
    private String type_credit;
}
