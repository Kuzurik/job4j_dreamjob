package ru.job4j.dreamjob.repository.file;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.dreamjob.model.File;

import java.util.Optional;

@Repository
public class Sql2oFileRepository implements FileRepository {

    private final static String SAVE = "INSERT INTO files (name, path) VALUES (:name, :path)";

    private final static String FIND_BY_ID = "SELECT * FROM files WHERE id = :id";

    private final static String DELETE_BY_ID = "DELETE FROM files WHERE id = :id";

    private final Sql2o sql2o;

    public Sql2oFileRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public File save(File file) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(SAVE, true)
                    .addParameter("name", file.getName())
                    .addParameter("path", file.getPath());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            file.setId(generatedId);
            return file;
        }
    }

    @Override
    public Optional<File> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(FIND_BY_ID);
            var file = query.addParameter("id", id).executeAndFetchFirst(File.class);
            return Optional.ofNullable(file);
        }
    }

    @Override
    public boolean deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(DELETE_BY_ID);
            var affectedRows = query.addParameter("id", id).executeUpdate().getResult();
            return affectedRows > 0;
        }
    }
}
