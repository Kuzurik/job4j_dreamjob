package ru.job4j.dreamjob.model;

import java.util.Objects;

public class User {
    private int id;
    private String eMail;
    private String password;

    public User() {
    }

    public User(String eMail, String password) {
        this.eMail = eMail;
        this.password = password;
    }

    public User(int id, String eMail, String password) {
        this.id = id;
        this.eMail = eMail;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id && Objects.equals(eMail, user.eMail) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eMail, password);
    }
}
