package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.PostStore;

import java.util.Collection;

public class PostService {

    private static final PostService SERVICE = new PostService();
    private final PostStore store = PostStore.instOf();

    public static PostService instOf() {
        return SERVICE;
    }

    public Collection<Post> findAll() {
        return store.findAll();
    }

    public void add(Post post) {
        store.add(post);
    }

    public Post findById(int id) {
        return store.findById(id);
    }

    public void update(Post post) {
        store.update(post);
    }
}