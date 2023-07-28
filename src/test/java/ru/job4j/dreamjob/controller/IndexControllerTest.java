package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class IndexControllerTest {

    @Test
    public void whenRequestIndexThenGetPageIndex() {
        assertThat(new IndexController().getIndex()).isEqualTo("index");
    }
}