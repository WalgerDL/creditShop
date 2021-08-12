package loans.repository;

import loans.domain.entity.Client;
import loans.domain.entity.CreditOffer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OfferRepository extends CrudRepository<CreditOffer, Long> {

    @Query("select c from CreditOffer c where c.client.id = :idd")
    Iterable<CreditOffer> findByOfferClientId(@Param("idd") Long id);

    @Query("select cl from Client cl where cl.id = :idd")
    Optional<Client> findByClientIdForOffer(@Param("idd") Long id);

}
