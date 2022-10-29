package ru.job4j.dreamjob.model;

import org.springframework.format.annotation.DateTimeFormat;
import ru.job4j.dreamjob.service.CityService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public class Post {
    private int id;
    private String name;
    private boolean visible;
    private String description;
    private City city;
    private Date created;

    public Post() { }

    public Post(int id, String name, String description, City city, Date created) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.city = city;
        this.created = created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
