package ru.job4j.dreamjob.model;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PostStore {
    private static final PostStore INST = new PostStore();
    private final AtomicInteger id = new AtomicInteger(0);

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private PostStore() {
    }

    public static PostStore instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public void add(Post post) {
        post.setId(id.getAndIncrement());
        post.setCreated(new Timestamp(System.currentTimeMillis()));
        posts.put(post.getId(), post);
    }
}
