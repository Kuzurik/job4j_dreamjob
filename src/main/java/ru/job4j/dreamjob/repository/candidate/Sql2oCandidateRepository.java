package ru.job4j.dreamjob.repository.candidate;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oCandidateRepository implements CandidateRepository {

    private final static String SAVE = """
            INSERT INTO candidates(name, description, creation_date, visible, city_id, file_id)
            VALUES (:name, :description, :creationDate, :visible, :cityId, :fileId)
            """;

    private final static String DELETE_BY_ID = "DELETE FROM candidates WHERE id = :id";

    private final static String UPDATE = """
             UPDATE candidates
            SET name = :name, description = :description, creation_date = :creationDate,
                visible = :visible, city_id = :cityId, file_id = :fileId
            WHERE id = :id
            """;

    private final static String FIND_BY_ID = "SELECT * FROM candidates WHERE id = :id";

    private final static String FIND_ALL = "SELECT * FROM candidates";

    private final Sql2o sql2o;

    public Sql2oCandidateRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Candidate save(Candidate candidate) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(SAVE, true)
                    .addParameter("name", candidate.getName())
                    .addParameter("description", candidate.getDescription())
                    .addParameter("creationDate", candidate.getCreationDate())
                    .addParameter("visible", candidate.getVisible())
                    .addParameter("cityId", candidate.getCityId())
                    .addParameter("fileId", candidate.getFileId());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            candidate.setId(generatedId);
            return candidate;
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
    public boolean update(Candidate candidate) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(UPDATE)
                    .addParameter("name", candidate.getName())
                    .addParameter("description", candidate.getDescription())
                    .addParameter("creationDate", candidate.getCreationDate())
                    .addParameter("visible", candidate.getVisible())
                    .addParameter("cityId", candidate.getCityId())
                    .addParameter("fileId", candidate.getFileId())
                    .addParameter("id", candidate.getId());
            var affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    @Override
    public Optional<Candidate> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(FIND_BY_ID);
            query.addParameter("id", id);
            var candidate = query
                    .setColumnMappings(Candidate.COLUMN_MAPPING)
                    .executeAndFetchFirst(Candidate.class);
            return Optional.ofNullable(candidate);
        }
    }

    @Override
    public Collection<Candidate> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(FIND_ALL);
            return query
                    .setColumnMappings(Candidate.COLUMN_MAPPING)
                    .executeAndFetch(Candidate.class);
        }
    }
}
