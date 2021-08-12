package loans.domain.entity;

import lombok.Data;

import java.io.Serializable;


@Data
public class Approved implements Serializable {
    private static final long serialVersionUID = -1L;

    private Long id;
    private String message;
    private boolean completed;
    private Long offerId;
}
