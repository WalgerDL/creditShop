package scoring.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class Client {
    private Long id;
    private String fullName;

    @JsonFormat(pattern="yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate birthDate;

    private String numberPhone;
    private String email;
    private String serialPassport;
    private String numberPassport;

}
