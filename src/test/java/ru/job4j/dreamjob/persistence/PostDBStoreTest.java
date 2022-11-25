package ru.job4j.dreamjob.persistence;

import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.util.Date;
import java.util.List;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class PostDBStoreTest {

    @Test
    public void whenCreatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(0, "Java Job", true, "experience two years",
                new City(1, "Москва"), new Date());
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
        store.wipeTable();
    }

    @Test
    public void whenUpdatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(0, "Java Job", true, "experience two years",
                new City(1, "Москва"), new Date());
        store.add(post);
        post.setName("java junior job");
        store.update(post);
        Post result = store.findById(post.getId());
        assertThat(result, is(post));
        store.wipeTable();

    }

    @Test
    public void whenFindAllPosts() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(0, "Java Job", true, "experience two years",
                new City(1, "Москва"), new Date());
        Post post1 = new Post(1, "Java Job1", true, "experience two years",
                new City(1, "Москва"), new Date());
        store.add(post);
        store.add(post1);
        List<Post> expected = List.of(post, post1);
        List<Post> result = store.findAll();
        assertThat(result, is(expected));
        store.wipeTable();
    }

    @Test
    public void whenFindById() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(0, "Java Job", true, "experience two years",
                new City(1, "Москва"), new Date());
        store.add(post);
        Post result = store.findById(post.getId());
        assertThat(result, is(post));
        store.wipeTable();
    }
}