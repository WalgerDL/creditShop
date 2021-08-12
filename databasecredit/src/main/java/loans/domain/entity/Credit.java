package loans.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.util.Objects;

/**
 * Кредиты
 * @author Ivan
 */
@Entity
@Table(name="credit")
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NumberFormat(style=NumberFormat.Style.CURRENCY)
    @Column(name = "limit_credit")
    private Double limitCredit;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "term")
    private int term;

    @Column(name = "type_credit")
    private String type_credit;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_ref")
    @JsonIgnore
    private Bank bank;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLimitCredit() {
        return limitCredit;
    }

    public void setLimitCredit(Double limitCredit) {
        this.limitCredit = limitCredit;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public void setType_credit(String type_credit) {
        this.type_credit = type_credit;
    }

    public String getType_credit() {
        return type_credit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credit credit = (Credit) o;
        return Objects.equals(type_credit, credit.type_credit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type_credit);
    }

    @Override
    public String toString() {
        return String.format("[id=%s, limit=%s, rate=%s, term=%s]", id, limitCredit, rate, term);
    }

}
