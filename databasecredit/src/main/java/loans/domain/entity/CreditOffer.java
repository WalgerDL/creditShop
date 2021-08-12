package loans.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Кредитное предложение
 * @author Ivan
 */
@Entity
@Table(name = "creditoffer")
public class CreditOffer implements Serializable {

    private static final long serialVersionUID = 123L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_id")
    @JsonManagedReference
    private Credit credit;

    @NumberFormat(style=NumberFormat.Style.CURRENCY)
    @Column(name = "amount")
    private Double amount;

    @Column(name = "dt_created")
    @JsonIgnore
    private LocalDateTime created;

    @Column(name="message_app")
    private String message;

    @Column(name="offer_app")
    private boolean completed;

    public CreditOffer() {}

    public CreditOffer(Client client) {
        this.client = client;
        this.fullName = client.getFullName();
    }

    public CreditOffer(String fullName) {
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public Long getClientId() {
        return client.getId();
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @PrePersist
    void onCreate() {
        this.setCreated(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "CreditOffer{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                '}';
    }

}
