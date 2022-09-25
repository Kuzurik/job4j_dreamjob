package ru.job4j.test;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MainTest {

    @Test
    void add() {
        Main main = new Main();
        int result = main.add(1, 1);
        assertThat(result).isEqualTo(2);
    }
}