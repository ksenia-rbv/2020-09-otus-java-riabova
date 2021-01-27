package ru.otus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.otus.Bill.*;

public class Atm implements IAtm {
    private final Map<Bill, Cell> cashStorage;
    private final static List<Bill> BILLS = List.of(FIVE_THOUSAND, TWO_THOUSAND, ONE_THOUSAND, FIVE_HUNDRED, TWO_HUNDRED, ONE_HUNDRED);

    public Atm() {
        this.cashStorage = new HashMap<>();
        BILLS.forEach(b -> cashStorage.put(b, new Cell(b, 0)));
    }

    @Override
    public void putCash(Map<Bill, Integer> cash) {
        for (Map.Entry<Bill, Integer> c : cash.entrySet()) {
            cashStorage.compute(c.getKey(), (k, v) -> v.addCash(c.getValue()));
        }
    }

    @Override
    public Map<Bill, Integer> getCash(int sum) throws AtmException {
        Map<Bill, Integer> cash = new HashMap<>();
        int remainingSum = sum;

        for (Bill bill : BILLS) {
            if (bill.getValue() > remainingSum) {
                continue;
            }
            int available = cashStorage.get(bill).getCount();
            int needed = remainingSum / bill.getValue();
            if (available >= needed) {
                cash.put(bill, needed);
                remainingSum -= bill.getValue() * needed;
            } else if (available > 0) {
                cash.put(bill, available);
                remainingSum -= bill.getValue() * available;
            }
        }

        if (remainingSum > 0) {
            throw new AtmException("ATM cannot issue the required cash: " + sum);
        }
        withdrawFromStorage(cash);
        return cash;
    }

    @Override
    public int getBalance() {
        return cashStorage.values().stream().mapToInt(Cell::getAmount).sum();
    }

    void withdrawFromStorage(Map<Bill, Integer> cash) {
        for (Map.Entry<Bill, Integer> c : cash.entrySet()) {
            cashStorage.compute(c.getKey(), (k, v) -> v.withdrawCash(c.getValue()));
        }
    }
}
