package ru.otus;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class AtmTest {
    private Atm atm;

    @BeforeEach
    void init() {
        atm = new Atm();
    }

    @Test
    void putCash() {
        atm.putCash(Map.of(100, 2, 1000, 4, 200, 2));
        assertThat(atm.getBalance()).isEqualTo(4600);

        atm.putCash(Map.of(100, 0));
        assertThat(atm.getBalance()).isEqualTo(4600);

        atm.putCash(Map.of(100, 2));
        assertThat(atm.getBalance()).isEqualTo(4800);
    }

    @Test
    void getCash() {
        atm.putCash(Map.of(100, 2, 1000, 4, 200, 2));
        assertThat(atm.getBalance()).isEqualTo(4600);

        assertThat(atm.getCash(3700)).isNull();
        assertThat(atm.getBalance()).isEqualTo(4600);

        assertThat(atm.getCash(3600)).containsAllEntriesOf(Map.of(1000, 3, 200, 2, 100, 2));
        assertThat(atm.getBalance()).isEqualTo(1000);
    }

    @Test
    void should_getBalance() {
        assertThat(atm.getBalance()).isEqualTo(0);

        atm.putCash(Map.of(100, 2, 1000, 4, 200, 2));
        assertThat(atm.getBalance()).isEqualTo(4600);
    }
}