package ru.otus.servlet;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.crm.model.AddressDataSet;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.PhoneDataSet;
import ru.otus.crm.service.DBServiceClient;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ClientApiServlet extends HttpServlet {

    private static final String PARAM_NAME = "name";
    private static final String PARAM_ADDRESS = "address";
    private static final String PARAM_PHONES = "phones";

    private final DBServiceClient dbServiceClient;

    public ClientApiServlet(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter(PARAM_NAME);
        String address = request.getParameter(PARAM_ADDRESS);
        String phones = request.getParameter(PARAM_PHONES);

        Client client = dbServiceClient.saveClient(new Client(name, new AddressDataSet(address), getPhoneDataSet(phones)));

        ServletOutputStream out = response.getOutputStream();
        out.print(client.getId());
    }

    private List<PhoneDataSet> getPhoneDataSet(String phones) {
        return Stream.of(phones.split(","))
                .map(String::trim)
                .map(PhoneDataSet::new)
                .collect(Collectors.toList());
    }

}
