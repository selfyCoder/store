package kz.store.service;

import kz.store.Repository.ClientRepository;
import kz.store.entity.ClientEntity;
import kz.store.exception.ClientException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService {
    private ClientRepository clientRepository;

    public Optional<ClientEntity> findByFields(Long id, String iin) {
        if (id == null && ! StringUtils.hasText(iin)) {
            throw new IllegalArgumentException("Either ID or IIN must be provided.");
        }
         Optional<ClientEntity> client = (id !=null)
                 ? clientRepository.findById(id)
                 : clientRepository.findByIin(iin);
        if (StringUtils.hasText(iin))
            if (client.isPresent() && !iin.equals(client.get().getIin())) {
                throw new ClientException("is id and IIN don't match.");
            }

        return client;
    }
}
