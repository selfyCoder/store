package kz.store.service;

import kz.store.Repository.ClientRepository;
import kz.store.entity.ClientEntity;
import kz.store.exception.ClientException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class ClientServiceTest {

    private ClientRepository clientRepository = Mockito.mock(ClientRepository.class);
    private ClientService clientService = new ClientService(clientRepository);

    private static final String FIRST_IIN = "991111502399";
    private static final String SECOND_IIN = "881111502399";
    private static final Long FIRST_ID = 1L;
    private static final Long SECOND_ID = 2L;

    @BeforeEach
    void setUp() {
        clientRepository = Mockito.mock(ClientRepository.class);
        clientService = new ClientService(clientRepository);
    }

    @Test
    @DisplayName("Search client by IIN")
    void shouldFindClientByInn_WhenMatch() {
        ClientEntity client = new ClientEntity(null, "John", "Doe", null, FIRST_IIN);
        when(clientRepository.findByIin(FIRST_IIN)).thenReturn(Optional.of(client));
        Optional<ClientEntity> result = clientService.findByFields(null, FIRST_IIN);
        assertThat(result).isEqualTo(Optional.of(client));
    }

    @Test
    @DisplayName("Search client by IIN when null result")
    void shouldFindClientById_WhenMatch() {
        when(clientRepository.findById(FIRST_ID)).thenReturn(Optional.empty());
        assertThat(clientService.findByFields(FIRST_ID, null)).isEmpty();
    }

    @Test
    void shouldBeEmptyWhenIinDoesNotMatch() {
        when(clientRepository.findByIin(FIRST_IIN)).thenReturn(Optional.empty());
        assertThat(clientService.findByFields(null, FIRST_IIN)).isEmpty();
    }

    @Test
    void shouldBeEmptyWhenIdDoesNotMatch() {
        when(clientRepository.findById(FIRST_ID)).thenReturn(Optional.empty());
        assertThat(clientService.findByFields(FIRST_ID, "")).isEmpty();
    }

    @Test
    @DisplayName("Search client by IIN and Id")
    void shouldFindClientByInnAndId_WhenMatch() {
        ClientEntity client = new ClientEntity(FIRST_ID, "John", "Doe", null, FIRST_IIN);
        when(clientRepository.findByIin(FIRST_IIN)).thenReturn(Optional.of(client));
        when(clientRepository.findById(FIRST_ID)).thenReturn(Optional.of(client));
        Optional<ClientEntity> result = clientService.findByFields(FIRST_ID, FIRST_IIN);
        assertThat(result).isEqualTo(Optional.of(client));
    }

    @Test
    void shouldThrowWhenTwoArgsNotMatch() {
        ClientEntity clientFirst = new ClientEntity(FIRST_ID, "John", "Doe", null, FIRST_IIN);
        ClientEntity clientSecond = new ClientEntity(SECOND_ID, "John", "Doe", null, SECOND_IIN);
        when(clientRepository.findByIin(FIRST_IIN)).thenReturn(Optional.of(clientFirst));
        when(clientRepository.findById(SECOND_ID)).thenReturn(Optional.of(clientSecond));
        assertThatThrownBy(()-> clientService.findByFields(SECOND_ID, FIRST_IIN))
                .isInstanceOf(ClientException.class)
                .hasMessage("is id and IIN don't match.");
    }

    @Test
    void shouldThrowWhenTwoArgsIsNull() {
        assertThatThrownBy(()-> clientService.findByFields(null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Either ID or IIN must be provided.");
    }
}
