package scoring.domain;

import lombok.Data;

@Data
public class CreditOffer {
    private Long id;
    private String fullName;
    private Client client;
    private Credit credit;
    private Double amount;
    private String message;
    private boolean completed;
    private Long clientId;
}
