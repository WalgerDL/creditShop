package scoring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
@ComponentScan("scoring")
public class ScoringApplication {

    public static void main(String[] args) {
        log.info("===============Модуль со скорингом===========");
        SpringApplication.run(ScoringApplication.class, args);
    }
}
