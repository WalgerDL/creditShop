package scoring.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Range;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import scoring.customException.WrongAgeException;
import scoring.domain.Client;
import scoring.domain.Credit;
import scoring.domain.CreditOffer;
import scoring.service.Randomizer;

import java.time.LocalDate;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@Slf4j
public class ScoringImplTest {

    private static final int AGE_BETWEEN = 20;

    private Client client = new Client();
    private Randomizer randomizer = new Randomizer();
    private CreditOffer creditOffer;

    @Mock
    ScoringServiceImpl mock = Mockito.mock(ScoringServiceImpl.class);


    @BeforeEach
    void setUp() {
        System.out.println("Start test!");
        MockitoAnnotations.initMocks(this);

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

    }

    @AfterEach
    void tearDown() {
        System.out.println("End test!");
    }

    @Test
    void age() {
        assertEquals(36,36);
        client.setBirthDate(LocalDate.of(2001, 02, 25));
        LocalDate birthDate = client.getBirthDate();
        LocalDate currentDate = LocalDate.now();
        if ((birthDate != null) && (currentDate != null)) {
            System.out.println(Period.between(birthDate, currentDate).getYears());
        } else {
        }

    }

    @Test
    void ageCoefficient1() {
        Range<Integer> range1 = Range.between(25, 55);
        for (int i=25; i<=55; i++) {
            assertTrue(range1.contains(i));
        }
    }

    @Test
    void ageCoefficient2() {
        Range<Integer> range2 = Range.between(18, 24);
        for (int i=18; i<=24; i++) {
            assertTrue(range2.contains(i));
        }
    }

    @Test
    void ageCoefficient3() {
        Range<Integer> range3 = Range.between(56,75);
        for (int i=56; i<=75; i++) {
            assertTrue(range3.contains(i));
        }
    }


    @Test
    void ageCoefficientException() {

        Range<Integer> range1 = Range.between(25, 55);
        Range<Integer> range2 = Range.between(18, 24);
        Range<Integer> range3 = Range.between(56, 75);

        if (range1.contains(20)) {
            System.out.println(1);
        } else if (range2.contains(36)) {
            System.out.println(2);
        } else if (range3.contains(20)) {
            System.out.println(3);
        } else try {
            throw new WrongAgeException("Возраст кредитования должен быть от 18 до 75 лет включительно");
        } catch (WrongAgeException e) {
            e.printStackTrace();
        }
    }

    @Test
    void creditApproval() {

        when(mock.creditApproval(creditOffer)).thenReturn(true);

        assertTrue(mock.creditApproval(creditOffer));

        randomizer = new Randomizer();
        int age = mock.ageCoefficient(creditOffer);
        if(age == -1)
            System.out.println("FALSE");;
        if(age == 1) {
            System.out.println(randomizer.randomize());
        } else if(age == 2) {
            System.out.println(randomizer.randomize());
        }
        System.out.println(randomizer.randomize());
    }

}
