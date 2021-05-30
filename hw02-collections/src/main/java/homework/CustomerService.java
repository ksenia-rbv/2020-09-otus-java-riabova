package homework;

import java.util.*;

public class CustomerService {

    private final TreeMap<Customer, String> customerServiceMap = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        return cloneEntry(customerServiceMap.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return cloneEntry(customerServiceMap.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        customerServiceMap.put(customer, data);
    }

    private Map.Entry<Customer, String> cloneEntry(Map.Entry<Customer, String> entry) {
        if (Objects.isNull(entry)) {
            return null;
        }
        return new AbstractMap.SimpleEntry<>(entry.getKey().toClone(), entry.getValue());
    }

}
