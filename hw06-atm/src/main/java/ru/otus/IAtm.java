package ru.otus;

import java.util.Map;

public interface IAtm {
    void putCash(Map<Bill, Integer> cash);

    Map<Bill, Integer> getCash(int sum) throws AtmException;

    int getBalance();
}
