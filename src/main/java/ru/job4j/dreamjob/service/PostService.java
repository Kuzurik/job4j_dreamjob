package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.persistence.PostDBStore;


import java.util.Collection;
@ThreadSafe
@Service
public class PostService {

   private final PostDBStore store;
   private final CityService cityService;

   public PostService(PostDBStore store, CityService cityService) {
       this.store = store;
       this.cityService = cityService;
   }

    public Collection<Post> findAll() {
        return store.findAll();
    }

    public void add(Post post) {
        post.setCity(cityService.findById(post.getCity().getId()));
        store.add(post);
    }

    public Post findById(int id) {
        return store.findById(id);
    }

    public void update(Post post) {
        post.setCity(cityService.findById(post.getCity().getId()));
        store.update(post);
    }
}
