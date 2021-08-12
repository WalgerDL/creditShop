package loans;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import scoring.ScoringApplication;

@SpringBootApplication
@ComponentScan("loans")
public class LoansApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(LoansApplication.class);
    }

    public static void main(String[] args) {
        //SpringApplication.run(LoansApplication.class, args);
        new SpringApplicationBuilder()
        //        .sources(LoansApplication.class, ScoringApplication.class)
                .sources(ScoringApplication.class, LoansApplication.class)
                .run(args);
    }

}
