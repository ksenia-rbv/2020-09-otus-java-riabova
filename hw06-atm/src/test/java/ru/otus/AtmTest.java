package ru.otus;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.otus.Bill.*;

class AtmTest {
    private Atm atm;

    @BeforeEach
    void init() {
        atm = new Atm();
    }

    @Test
    void putCash() {
        atm.putCash(Map.of(ONE_HUNDRED, 2, ONE_THOUSAND, 4, TWO_HUNDRED, 2));
        assertThat(atm.getBalance()).isEqualTo(4600);

        atm.putCash(Map.of(ONE_HUNDRED, 0));
        assertThat(atm.getBalance()).isEqualTo(4600);

        atm.putCash(Map.of(ONE_HUNDRED, 2));
        assertThat(atm.getBalance()).isEqualTo(4800);
    }

    @Test
    void getCash() throws AtmException {

        atm.putCash(Map.of(ONE_HUNDRED, 2, ONE_THOUSAND, 4, TWO_HUNDRED, 2));
        assertThat(atm.getBalance()).isEqualTo(4600);

        assertThatThrownBy(() -> atm.getCash(3700))
                .isInstanceOf(AtmException.class)
                .hasMessage("ATM cannot issue the required cash: 3700");
        assertThat(atm.getBalance()).isEqualTo(4600);

        assertThat(atm.getCash(3600)).containsAllEntriesOf(Map.of(ONE_THOUSAND, 3, TWO_HUNDRED, 2, ONE_HUNDRED, 2));
        assertThat(atm.getBalance()).isEqualTo(1000);
    }

    @Test
    void should_getBalance() {
        assertThat(atm.getBalance()).isEqualTo(0);

        atm.putCash(Map.of(ONE_HUNDRED, 2, ONE_THOUSAND, 4, TWO_HUNDRED, 2));
        assertThat(atm.getBalance()).isEqualTo(4600);
    }
}