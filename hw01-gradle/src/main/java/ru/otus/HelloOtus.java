package ru.otus;

import com.google.common.collect.Lists;

import java.util.List;

public class HelloOtus {
    public static void main(String[] args) {

        List<String> students = Lists.newArrayList("ivanov", "sidorov", "egorov", "baranov");

        for (String student: students) {
            System.out.println(student);
        }
    }
}
