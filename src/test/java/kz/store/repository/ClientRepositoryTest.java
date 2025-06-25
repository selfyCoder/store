package kz.store.repository;

import kz.store.Repository.ClientRepository;
import kz.store.entity.ClientEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    private ClientEntity client1;
    private ClientEntity client2;

    @BeforeEach
    void setUp() {
        client1 = new ClientEntity(null, "Alice", "Smith", "Marie", "870212450021");
        client2 = new ClientEntity(null, "Bob", "Johnson", "Edward", "870212450022");

        clientRepository.save(client1);
        clientRepository.save(client2);
    }

    @Test
    @DisplayName("Найти клиента по ID")
    void shouldFindClientById() {
        Optional<ClientEntity> client = clientRepository.findById(client1.getId());
        assertThat(client).isPresent();
        assertThat(client.get().getFirstName()).isEqualTo("Alice");
    }

    @Test
    @DisplayName("Найти клиента по IIN")
    void shouldFindClientByIin() {
        Optional<ClientEntity> client = clientRepository.findByIin("870212450022");
        assertThat(client).isPresent();
        assertThat(client.get().getFirstName()).isEqualTo("Bob");
    }

    @Test
    @DisplayName("Вернуть пусто, если клиента нет")
    void shouldReturnEmpty_WhenClientNotFound() {
        Optional<ClientEntity> client = clientRepository.findByIin("999999999999");
        assertThat(client).isNotPresent();
    }
}
