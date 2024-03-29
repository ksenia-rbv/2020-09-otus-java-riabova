package ru.otus.core.model;

import ru.otus.jdbc.annotation.Id;

public class Account {
    @Id
    private final String no;
    private final String type;
    private final double rest;

    public Account(String no, String type, double rest) {
        this.no = no;
        this.type = type;
        this.rest = rest;
    }

    public String getNo() {
        return no;
    }

    public String getType() {
        return type;
    }

    public double getRest() {
        return rest;
    }

    @Override
    public String toString() {
        return "Account{" +
                "no='" + no + '\'' +
                ", type='" + type + '\'' +
                ", rest=" + rest +
                '}';
    }
}
