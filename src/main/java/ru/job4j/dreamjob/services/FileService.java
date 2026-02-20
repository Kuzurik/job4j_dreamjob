package ru.job4j.dreamjob.services;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.model.File;

import java.util.Optional;

@Service
public interface FileService {

    File save(FileDto fileDto);

    Optional<FileDto> getFileById(int id);

    void deleteById(int id);
}
