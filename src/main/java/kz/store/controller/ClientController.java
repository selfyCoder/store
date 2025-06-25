package kz.store.controller;

import io.swagger.v3.oas.annotations.Operation;
import kz.store.dto.response.ClientResponse;
import kz.store.entity.ClientEntity;
import kz.store.mapper.ClientMapper;
import kz.store.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @Operation(summary = "получение информации о клиенте по ИИН или ID")
    @GetMapping("/search")
    public ResponseEntity<ClientResponse> search(@RequestParam(required = false) Long id, @RequestParam(required = false) String iin) {
        Optional<ClientEntity> result = clientService.findByFields(id, iin);

        return result
                .map(userEntity -> ResponseEntity.ok(clientMapper.toResponse(userEntity)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
