package ru.otus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Atm implements IAtm {
    private final Map<Integer, Integer> cashStorage;
    private final static List<Integer> BILLS = List.of(5000, 2000, 1000, 500, 200, 100);

    public Atm() {
        this.cashStorage = new HashMap<>();
        BILLS.forEach(b -> cashStorage.put(b, 0));
    }

    @Override
    public void putCash(Map<Integer, Integer> cash) {
        for (Map.Entry<Integer, Integer> c : cash.entrySet()) {
            cashStorage.compute(c.getKey(), (k, v) -> v + c.getValue());
        }
    }

    @Override
    public Map<Integer, Integer> getCash(int sum) {
        Map<Integer, Integer> cash = new HashMap<>();

        for (Integer bill : BILLS) {
            if (bill > sum) {
                continue;
            }
            int available = cashStorage.get(bill);
            int needed = sum / bill;
            if (available >= needed) {
                cash.put(bill, needed);
                sum -= bill * needed;
            } else if (available > 0) {
                cash.put(bill, available);
                sum -= bill * available;
            }
        }

        if (sum > 0) {
            System.out.println("ATM cannot issue the required cash");
            return null;
        }
        withdrawFromStorage(cash);
        return cash;
    }

    @Override
    public int getBalance() {
        int cash = 0;
        for (Map.Entry<Integer, Integer> cell : cashStorage.entrySet()) {
            cash += cell.getKey() * cell.getValue();
        }
        return cash;
    }

    void withdrawFromStorage( Map<Integer, Integer> cash){
        for (Map.Entry<Integer, Integer> c : cash.entrySet()) {
            cashStorage.compute(c.getKey(), (k, v) -> v - c.getValue());
        }
    }
}
