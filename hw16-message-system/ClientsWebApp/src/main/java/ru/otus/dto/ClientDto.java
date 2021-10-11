package ru.otus.dto;


import ru.otus.messagesystem.client.ResultDataType;

public class ClientDto extends ResultDataType {

    private long id;
    private String name;
    private String address;
    private String phones;

    public ClientDto() {
    }

    public ClientDto(long id, String name, String address, String phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
