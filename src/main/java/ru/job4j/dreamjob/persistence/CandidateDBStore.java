package ru.job4j.dreamjob.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;


import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class CandidateDBStore {

    public static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private final BasicDataSource pool;
    private static final String FIND_ALL = "SELECT * FROM candidate";
    private static final String ADD =
            "INSERT INTO candidate(name, visible, description, city_id, photo, created) VALUES (?,?,?,?,?,?)";
    private static final String UPDATE =
            "update candidate set name = ?, visible = ?, description = ?, city_id = ?, photo = ?, created = ? where id = ?";
    private static final String FIND_BY_ID = "SELECT * FROM candidate WHERE id = ?";
    private static final String WIPE_TABLE = "delete from candidate";

    public CandidateDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_ALL)) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(createCandidates(it));
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return candidates;
    }

    public Candidate add(Candidate candidate) {
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement(
                ADD, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getName());
            ps.setBoolean(2, candidate.isVisible());
            ps.setString(3, candidate.getDescription());
            ps.setInt(4, candidate.getCity().getId());
            ps.setBytes(5, candidate.getPhoto());
            ps.setTimestamp(6, new Timestamp(new Date().getTime()));
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return candidate;
    }

    public boolean update(Candidate candidate) {
        boolean result = false;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE)) {
            Candidate currentCandidate = findById(candidate.getId());
            ps.setString(1, candidate.getName());
            ps.setBoolean(2, candidate.isVisible());
            ps.setString(3, candidate.getDescription());
            ps.setInt(4, candidate.getCity().getId());
            ps.setBytes(5, candidate.getPhoto());
            ps.setTimestamp(6, new Timestamp(new Date().getTime()));
            ps.setInt(7, currentCandidate.getId());
            result  = ps.executeUpdate() > 0;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }

    public Candidate findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(FIND_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return createCandidates(it);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public Candidate createCandidates(ResultSet it) throws SQLException {
        return new Candidate(it.getInt("id"),
                it.getString("name"),
                it.getBoolean("visible"),
                it.getString("description"),
                new City(it.getInt("city_id")),
                it.getBytes("photo"),
                it.getDate("created"));
    }

    public void wipeTable() {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(WIPE_TABLE)) {
            ps.execute();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
