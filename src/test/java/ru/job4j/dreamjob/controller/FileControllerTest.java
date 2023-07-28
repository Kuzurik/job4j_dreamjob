package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.service.file.FileService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileControllerTest {

    private FileService fileService;
    private FileController fileController;

    @BeforeEach
    public void initService() {
        fileService = mock(FileService.class);
        fileController = new FileController(fileService);
    }

    @Test
    public void whenRequestFilesIdThenReturnOk() {
        byte[] body = new byte[]{1, 2, 3};
        var fileDto = Optional.of(new FileDto("name", body));

        when(fileService.getFileById(anyInt())).thenReturn(fileDto);
        var responseEntity = fileController.getById(anyInt());

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(body, responseEntity.getBody());
    }

    @Test
    public void whenRequestFilesIdThenReturnError() {
        when(fileService.getFileById(anyInt())).thenReturn(Optional.empty());
        var responseEntity = fileController.getById(anyInt());

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertThat(responseEntity.getBody()).isNull();
    }

}