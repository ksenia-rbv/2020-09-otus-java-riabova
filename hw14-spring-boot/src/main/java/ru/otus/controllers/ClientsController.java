package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.dto.ClientDto;
import ru.otus.mappers.ClientDtoMapper;
import ru.otus.services.ClientService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ClientsController {

    private final ClientService clientService;
    private final ClientDtoMapper clientDtoMapper;

    public ClientsController(ClientService clientService, ClientDtoMapper clientDtoMapper) {
        this.clientService = clientService;
        this.clientDtoMapper = clientDtoMapper;
    }

    @RequestMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/clients")
    public String clientsListView(Model model) {
        List<ClientDto> clients = clientService.findAll().stream()
                .map(clientDtoMapper::createClientDto)
                .collect(Collectors.toList());
        model.addAttribute("clients", clients);
        model.addAttribute("client", new ClientDto());
        return "clients";
    }

}
