package ru.otus.model;

import ru.otus.crm.model.Client;
import ru.otus.crm.model.PhoneDataSet;

import java.util.stream.Collectors;

public class ClientDto {

    private long id;
    private String name;
    private String address;
    private String phones;

    public ClientDto(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.address = client.getAddress().getStreet();
        this.phones = client.getPhones().stream()
                .map(PhoneDataSet::getNumber)
                .collect(Collectors.joining(","));
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhones() {
        return phones;
    }

    @Override
    public String toString() {
        return "ClientDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phones='" + phones + '\'' +
                '}';
    }
}
