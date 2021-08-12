package scoring.service.impl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import scoring.domain.Client;
import scoring.domain.Credit;
import scoring.domain.CreditOffer;
import scoring.service.Randomizer;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ScoringServiceImplTest.ScoringConfig.class)
public class ScoringServiceImplTest {
    private static final int AGE_BETWEEN = 20;

    @Autowired
    private ScoringServiceImpl scoringImpl;
    @Autowired
    private Randomizer randomizer;

    private CreditOffer creditOffer;

    @BeforeEach
    public void setUp() throws Exception {

        creditOffer = new CreditOffer() {{
            setId(1L);
            setFullName("Check full name");
            setClient(new Client() {{
                setBirthDate(LocalDate.now().minusYears(AGE_BETWEEN));
            }});
            setCredit(new Credit());
            setAmount(30000d);
            setMessage("Check message");
            setCompleted(Boolean.TRUE);
            setClientId(1L);
        }};

        when(randomizer.randomize()).thenReturn(Boolean.TRUE);
    }

    @Test
    public void age() {

        int age = scoringImpl.age(creditOffer);

        assertEquals(AGE_BETWEEN, age);
    }

    @Test
    public void ageCoefficient() {

        int ageCoefficient = scoringImpl.ageCoefficient(creditOffer);

        assertEquals(2, ageCoefficient);
    }

    @Test
    public void creditApproval() {

        boolean approval = scoringImpl.creditApproval(creditOffer);

        assertTrue(approval);
    }

    @Configuration
    static class ScoringConfig {

        @Bean
        public ScoringServiceImpl scoringImpl() {
            return new ScoringServiceImpl(
                    randomizer()
            );
        }

        @Bean
        public Randomizer randomizer() {
            return mock(Randomizer.class);
        }
    }

}
