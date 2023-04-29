package ru.job4j.dreamjob.repository.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2oException;
import ru.job4j.dreamjob.configuration.DatasourceConfiguration;
import ru.job4j.dreamjob.model.User;

import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertThrows;

class Sql2oUserRepositoryTest {
    private static Sql2oUserRepository sql2oUserRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oUserRepositoryTest.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oUserRepository = new Sql2oUserRepository(sql2o);
    }

    @AfterEach
    public void clearUsers() {
        var users = sql2oUserRepository.findAll();
        for (var user : users) {
            sql2oUserRepository.deleteById(user.getId());
        }
    }

    @Test
    public void whenSaveThanGetSame() {
        var user = sql2oUserRepository.save(
                new User(0, "vorota-24@bk.ru", "alex", "1111")).get();
        var savedUser = sql2oUserRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()).get();
        assertThat(savedUser).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    public void whenFindUserByEmailAndPass() {
        var user = sql2oUserRepository.save(new User(0, "vorota-24@bk.ru", "alex", "1111")).get();
        assertThat(user).usingRecursiveComparison().isEqualTo(
                sql2oUserRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()).get());
    }

    @Test
    public void whenFindAllUsers() {
        var user = sql2oUserRepository.save(
                new User(0, "vorota-24@bk.ru", "alex", "1111")).get();
        var user1 = sql2oUserRepository.save(
                new User(1, "alex.beltexno@gmail.com", "fedor", "1111")).get();
        var user2 = sql2oUserRepository.save(
                new User(2, "desing125@bk.ru", "irina", "1111")).get();
        var result = sql2oUserRepository.findAll();
        assertThat(result).isEqualTo(List.of(user, user1, user2));
    }

    @Test
    public void whenUserIsAlreadyExistsThenException() {
        var user = sql2oUserRepository.save(
                new User(0, "vorota-24@bk.ru", "alex", "1111")).get();
        assertThrows(Sql2oException.class, () -> sql2oUserRepository.save(user));
    }

    @Test
    public void whenDeleteById() {
        var user = sql2oUserRepository.save(
                new User(0, "vorota-24@bk.ru", "alex", "1111")).get();
        var user1 = sql2oUserRepository.save(
                new User(0, "alex.beltexno@gmail.com", "alex", "2222")).get();
        sql2oUserRepository.deleteById(user.getId());
        assertThat(sql2oUserRepository.findAll()).isEqualTo(List.of(user1));
    }
}