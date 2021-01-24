package ru.otus;

import java.util.Map;

public interface IAtm {
    void putCash(Map<Integer, Integer> cash);

    Map<Integer, Integer> getCash(int sum);

    int getBalance();
}
