package ru.otus;

public class Cell {
    private final Bill bill;
    private int count;

    public Cell(Bill bill, int count) {
        this.bill = bill;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public Cell addCash(int cash) {
        this.count += cash;
        return this;
    }

    public Cell withdrawCash(int cash) {
        this.count -= cash;
        return this;
    }

    public int getAmount() {
        return bill.getValue() * count;
    }
}
