package ru.otus.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.otus.dto.ClientDto;
import ru.otus.dto.ClientsListDto;
import ru.otus.services.ClientService;

@Controller
public class ClientsController {

    private final SimpMessagingTemplate template;
    private final ClientService clientService;

    public ClientsController(SimpMessagingTemplate template, ClientService clientService) {
        this.template = template;
        this.clientService = clientService;
    }

    @MessageMapping("client")
    public void saveClient(ClientDto clientDto) {
        clientService.save(clientDto, this::postSavedClient);
    }

    public void postSavedClient(ClientDto clientDto) {
        template.convertAndSend("/topic/client", clientDto);
    }

    @MessageMapping("clients")
    public void getAllClients() {
        clientService.findAll(this::postAllClients);
    }

    public void postAllClients(ClientsListDto clientsListDto) {
        template.convertAndSend("/topic/clients", clientsListDto.getAll());
    }
}
