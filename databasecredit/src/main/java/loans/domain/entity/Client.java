package loans.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.sun.istack.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Класс Client
 * @author Ivan
 */
@Entity
@Table(name="client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "full_name")
    private String fullName;

    //@DateTimeFormat(pattern="dd.MM.yyyy")
    @JsonFormat(pattern="yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "number_phone")
    private String numberPhone;

    @Column(name = "email_address")
    private String email;

    @Column(name = "serial_passport")
    private String serialPassport;

    @Column(name = "number_passport")
    private String numberPassport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_ref")
    @JsonIgnore
    private Bank bank;

    //гетеры и сетеры
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumberPassport() {
        return numberPassport;
    }

    public void setNumberPassport(String numberPassport) {
        this.numberPassport = numberPassport;
    }

    public String getSerialPassport() {
        return serialPassport;
    }

    public void setSerialPassport(String serialPassport) {
        this.serialPassport = serialPassport;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Bank getBank() {
        return bank;
    }

    @Override
    public String toString() {
        return "ClientEntity{" +
                "id=" + id +
                ", fullName=" + fullName +
                ", numberPhone=" + numberPhone + '\'' +
                '}';
    }

}
