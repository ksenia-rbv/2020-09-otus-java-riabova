package ru.otus.mappers;

import org.springframework.stereotype.Component;
import ru.otus.dto.ClientDto;
import ru.otus.model.AddressDataSet;
import ru.otus.model.Client;
import ru.otus.model.PhoneDataSet;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ClientDtoMapperImpl implements ClientDtoMapper {

    @Override
    public Client createClient(ClientDto clientDto) {
        return new Client(null, clientDto.getName(), new AddressDataSet(clientDto.getAddress()), getPhoneDataSet(clientDto.getPhones()));
    }

    @Override
    public ClientDto createClientDto(Client client) {
        return new ClientDto(client.getId(), client.getName(), client.getAddress().getStreet(), getPhonesString(client.getPhones()));
    }

    private Set<PhoneDataSet> getPhoneDataSet(String phones) {
        return Stream.of(phones.split(","))
                .map(String::trim)
                .map(PhoneDataSet::new)
                .collect(Collectors.toSet());
    }

    private String getPhonesString(Set<PhoneDataSet> phoneDataSets) {
        return phoneDataSets.stream()
                .map(PhoneDataSet::getNumber)
                .collect(Collectors.joining(","));
    }
}
