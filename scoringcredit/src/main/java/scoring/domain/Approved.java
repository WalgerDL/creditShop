package scoring.domain;

import lombok.Data;

@Data
public class Approved {
    private Long id;
    private String message;
    private boolean completed;
    private Long offerId;
}
