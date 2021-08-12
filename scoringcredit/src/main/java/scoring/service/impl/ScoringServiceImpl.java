package scoring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Range;
import org.springframework.stereotype.Service;
import scoring.customException.WrongAgeException;
import scoring.domain.CreditOffer;
import scoring.service.Randomizer;
import scoring.service.ScoringService;

import java.time.LocalDate;
import java.time.Period;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScoringServiceImpl implements ScoringService {

    private final Randomizer randomizer;

    @Override
    public int age(CreditOffer creditOffer) {
        LocalDate birthDate = creditOffer.getClient().getBirthDate();
        LocalDate currentDate=LocalDate.now();
        if((birthDate!=null) && (currentDate!=null)){
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
    }

    @Override
    public int ageCoefficient(CreditOffer creditOffer) {
        Range<Integer> range1 = Range.between(25, 55);
        Range<Integer> range2 = Range.between(18, 24);
        Range<Integer> range3 = Range.between(56, 75);

        if (range1.contains(age(creditOffer))) {
            return 1;
        } else if (range2.contains(age(creditOffer))) {
            return 2;
        } else if (range3.contains(age(creditOffer))) {
            return 3;
        } else try {
            throw new WrongAgeException("Возраст кредитования должен быть от 18 до 75 лет включительно");
        } catch (WrongAgeException e) {
            e.printStackTrace();
            log.error("err:=" + e);
            return -1;
        }
    }

    @Override
    public boolean creditApproval(CreditOffer creditOffer) {
        int age = ageCoefficient(creditOffer);
        if(age == -1) return false;
        if(age == 1) {
            if(age(creditOffer) < 45) return true;
            return randomizer.randomize();
        } else if(age == 2) {
            if(age(creditOffer) > 21) return true;
            return randomizer.randomize();
        }
        int ageInts = randomizer.randomGroupAge(1, 3);
        if(ageInts >= 2) return true;
        return randomizer.randomize();
    }

}
