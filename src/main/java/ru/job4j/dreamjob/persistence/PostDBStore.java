package ru.job4j.dreamjob.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;


import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class PostDBStore {

    public static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private final BasicDataSource pool;
    private static final String FIND_ALL = "SELECT * FROM post";
    private static final String ADD = "INSERT INTO post(name, visible, description, city, created) VALUES (?,?,?,?,?)";
    private static final String UPDATE =
            "update post set name = ?, visible = ?, description = ?, city = ?, created = ? where id = ?";
    private static final String FIND_BY_ID = "SELECT * FROM post WHERE id = ?";

    public PostDBStore(CityService cityService, BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(FIND_ALL)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(createPost(it));
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return posts;
    }

    public Post add(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     ADD, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setBoolean(2, post.isVisible());
            ps.setString(3, post.getDescription());
            ps.setInt(4, post.getCity().getId());
            ps.setTimestamp(5, new Timestamp(new Date().getTime()));
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
           LOGGER.error(e.getMessage(), e);
        }
        return post;
    }

    public void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE)) {
            Post currentPost = findById(post.getId());
            ps.setString(1, post.getName());
            ps.setBoolean(2, post.isVisible());
            ps.setString(3, post.getDescription());
            ps.setInt(4, post.getCity().getId());
            ps.setTimestamp(5, new Timestamp(new Date().getTime()));
            ps.setInt(6, currentPost.getId());
            ps.executeUpdate();

        } catch (Exception e) {
           LOGGER.error(e.getMessage(), e);
        }
    }

    public Post findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(FIND_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return createPost(it);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public Post createPost(ResultSet it) throws SQLException {
        return new Post(it.getInt("id"),
                    it.getString("name"),
                    it.getBoolean("visible"),
                    it.getString("description"),
                    new City(it.getInt("city")),
                    it.getDate("created"));
    }
}
