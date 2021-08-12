package loans.repository;

import loans.domain.entity.Bank;
import loans.domain.entity.Client;
import loans.domain.entity.Credit;
import loans.domain.entity.CreditOffer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CreditRepository extends CrudRepository<Credit, Long> {

    @Query("select c from Credit c where c.bank.id = :idd")
    Iterable<Credit> findByBankRef(@Param("idd") Long id);

    @Query("select c from Bank c ")
    Iterable<Bank> findByBankAll();

    @Query("select cl from Client cl where cl.id = :idd")
    Optional<Client> findByClientId(@Param("idd") Long id);
}
