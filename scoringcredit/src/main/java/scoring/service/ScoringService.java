package scoring.service;

import scoring.domain.CreditOffer;

public interface ScoringService {
    public int age(CreditOffer creditOffer);
    public int ageCoefficient(CreditOffer creditOffer);
    public boolean creditApproval(CreditOffer creditOffer);
}
