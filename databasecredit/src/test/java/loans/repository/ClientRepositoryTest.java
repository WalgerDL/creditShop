package loans.repository;

import loans.domain.entity.Bank;
import loans.domain.entity.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import scoring.service.Randomizer;


import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(SpringExtension.class)
class ClientRepositoryTest {

    @Autowired
    private Client client;

    @Mock
    private ClientRepository clientRepository;

    @BeforeEach
    public void setUp() {
        client = new Client();
        assertNotNull(client);
        client.setFullName("Тестов Тест Тестович");
        client.setBirthDate(LocalDate.of(1955, 8, 12));
        client.setNumberPhone("914-562-25-45");
        client.setEmail("testoff@gmail.com");
        assertEquals("914-562-25-45", client.getNumberPhone());
    }

    @Test
    void testSave() {
         //clientRepository.save(client);
    }

    @Test
    void findByFullName() {
        //Optional<Client> cl = clientRepository.findByFullName("Тестов Тест Тестович");
        //assertEquals("Тестов Тест Тестович", cl.get().getFullName());
    }
}