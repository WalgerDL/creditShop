package loans.repository;

import loans.domain.entity.Bank;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BankRepository extends CrudRepository<Bank, Long> {

    List<Bank> findByNameBank(String nameBank);
}
