package ru.otus.mappers;

import ru.otus.dto.ClientDto;
import ru.otus.model.Client;

public interface ClientDtoMapper {

    Client createClient(ClientDto clientDto);

    ClientDto createClientDto(Client client);
}
