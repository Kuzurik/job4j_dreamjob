package ru.job4j.dreamjob.model;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PostStore {
    private static final PostStore INST = new PostStore();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private PostStore() {
        posts.put(1, new Post(1, "Junior Java developer", "Junior Java developer", new Date()));
        posts.put(2, new Post(2, "Middle Java developer)", "Middle Java developer", new Date()));
        posts.put(3, new Post(3, "Senior Java developer", "Senior Java developer", new Date()));
    }

    public static PostStore instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public void add(Post post) {
        posts.put(post.getId(), post);
    }
}
