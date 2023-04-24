package ru.job4j.dreamjob.repository.vacancy;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.dreamjob.model.Vacancy;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oVacancyRepository implements VacancyRepository {

    private final static String SAVE =
                      """
                      INSERT INTO vacancies(title, description, creation_date, visible, city_id, file_id)
                      VALUES (:title, :description, :creationDate, :visible, :cityId, :fileId)
                      """;

    private final static String DELETE_BY_ID = "DELETE FROM vacancies WHERE id = :id";

    private final static String UPDATE =
                    """
                    UPDATE vacancies
                    SET title = :title, description = :description, creation_date = :creationDate,
                        visible = :visible, city_id = :cityId, file_id = :fileId
                    WHERE id = :id
                    """;

    private final static String FIND_BY_ID = "SELECT * FROM vacancies WHERE id = :id";

    private final static String FIND_ALL = "SELECT * FROM vacancies";

    private final Sql2o sql2o;

    public Sql2oVacancyRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        try (var connection = sql2o.open()) {
           var query = connection.createQuery(SAVE, true)
                    .addParameter("title", vacancy.getTitle())
                    .addParameter("description", vacancy.getDescription())
                    .addParameter("creationDate", vacancy.getCreationDate())
                    .addParameter("visible", vacancy.getVisible())
                    .addParameter("cityId", vacancy.getCityId())
                    .addParameter("fileId", vacancy.getFileId());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            vacancy.setId(generatedId);
            return vacancy;
        }
    }

    @Override
    public boolean deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(DELETE_BY_ID);
            query.addParameter("id", id);
            var affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public boolean update(Vacancy vacancy) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(UPDATE)
                    .addParameter("title", vacancy.getTitle())
                    .addParameter("description", vacancy.getDescription())
                    .addParameter("creationDate", vacancy.getCreationDate())
                    .addParameter("visible", vacancy.getVisible())
                    .addParameter("cityId", vacancy.getCityId())
                    .addParameter("fileId", vacancy.getFileId())
                    .addParameter("id", vacancy.getId());
            var affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(FIND_BY_ID);
            query.addParameter("id", id);
            var vacancy = query.setColumnMappings(Vacancy.COLUMN_MAPPING).executeAndFetchFirst(Vacancy.class);
            return Optional.ofNullable(vacancy);
        }
    }

    @Override
    public Collection<Vacancy> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(FIND_ALL);
            return query.setColumnMappings(Vacancy.COLUMN_MAPPING).executeAndFetch(Vacancy.class);
        }
    }
}
