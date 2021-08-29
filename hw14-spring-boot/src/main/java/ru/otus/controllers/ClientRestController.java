package ru.otus.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.dto.ClientDto;
import ru.otus.mappers.ClientDtoMapper;
import ru.otus.model.Client;
import ru.otus.services.ClientService;

@RestController
public class ClientRestController {

    private final ClientService clientService;
    private final ClientDtoMapper clientDtoMapper;

    public ClientRestController(ClientService clientService, ClientDtoMapper clientDtoMapper) {
        this.clientService = clientService;
        this.clientDtoMapper = clientDtoMapper;
    }

    @PostMapping("/api/client")
    public ClientDto saveClient(@RequestBody ClientDto clientDto) {
        Client client = clientService.save(clientDtoMapper.createClient(clientDto));
        clientDto.setId(client.getId());
        return clientDto;
    }

}
