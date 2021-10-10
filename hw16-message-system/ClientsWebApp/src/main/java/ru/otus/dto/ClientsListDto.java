package ru.otus.dto;


import ru.otus.messagesystem.client.ResultDataType;

import java.util.List;

public class ClientsListDto extends ResultDataType {

    private List<ClientDto> list;

    public ClientsListDto() {
    }

    public ClientsListDto(List<ClientDto> list) {
        this.list = list;
    }

    public List<ClientDto> getAll() {
        return list;
    }

    @Override
    public String toString() {
        return "ClientsListDto{" +
                "list=" + list +
                '}';
    }
}
