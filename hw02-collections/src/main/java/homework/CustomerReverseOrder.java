package homework;


import java.util.ArrayDeque;
import java.util.Deque;

public class CustomerReverseOrder {

    private final Deque<Customer> arrayDeque = new ArrayDeque<>();

    public void add(Customer customer) {
        arrayDeque.push(customer);
    }

    public Customer take() {
        return arrayDeque.pop();
    }
}
