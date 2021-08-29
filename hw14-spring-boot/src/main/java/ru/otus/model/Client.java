package ru.otus.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Table("client")
public class Client {

    @Id
    private final Long id;

    @Column("name")
    private final String name;

    @MappedCollection(keyColumn = "client_id", idColumn = "client_id")
    private final AddressDataSet address;

    @MappedCollection(keyColumn = "client_id", idColumn = "client_id")
    private final Set<PhoneDataSet> phones;

    @PersistenceConstructor
    public Client(Long id, String name, AddressDataSet address, Set<PhoneDataSet> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AddressDataSet getAddressDataSet() {
        return address;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public Set<PhoneDataSet> getPhones() {
        return phones;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", phones=" + phones +
                '}';
    }
}
