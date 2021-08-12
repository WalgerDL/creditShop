package loans.repository;

import loans.domain.entity.Bank;
import loans.domain.entity.Client;
import loans.domain.entity.Credit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long> {

    Optional<Client> findByFullName(String fullName);

    Optional<Client> findByFullNameAndNumberPhone(String fullName, String numberPhone);

    @Query("select c from Client c where c.bank.id = :idd")
    Iterable<Client> findByBankRef(@Param("idd") Long id);

    @Query("select c from Bank c ")
    Iterable<Bank> findByBankAll();

    @Query("select crd.id from Credit crd ")
    List<Long> findByCreditIdAll();

    @Query("select crd from Credit crd where crd.id = :idd")
    Optional<Credit> findByCreditId(@Param("idd") Long id);

}
