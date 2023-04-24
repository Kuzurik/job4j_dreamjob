package ru.job4j.dreamjob.repository.city;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.dreamjob.model.City;

import java.util.Collection;

@Repository
public class Sql2oCityRepository implements CityRepository {

    private final static String FIND_ALL = "SELECT * FROM cities";

    private final Sql2o sql2o;

    public Sql2oCityRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Collection<City> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery(FIND_ALL);
            return query.executeAndFetch(City.class);
        }
    }
}
