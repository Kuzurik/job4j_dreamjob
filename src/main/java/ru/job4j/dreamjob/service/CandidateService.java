package ru.job4j.dreamjob.service;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.persistence.CandidateDBStore;
import ru.job4j.dreamjob.persistence.CandidateStore;

import java.util.Collection;
import java.util.List;

@ThreadSafe
@Service
public class CandidateService {

    private final CandidateDBStore store;
    private final CityService cityService;

    public CandidateService(CandidateDBStore store, CityService cityService) {
        this.store = store;
        this.cityService = cityService;
    }

    public Collection<Candidate> findAll() {
        List<Candidate> candidates = store.findAll();
        candidates.forEach(candidate -> candidate.setCity(
                cityService.findById(candidate.getCity().getId())
        ));
        return candidates;
    }

    public void add(Candidate candidate) {
        candidate.setCity(cityService.findById(candidate.getCity().getId()));
        store.add(candidate);
    }

    public Candidate findById(int id) {
        return store.findById(id);
    }

    public void update(Candidate candidate) {
        candidate.setCity(cityService.findById(candidate.getCity().getId()));
        store.update(candidate);
    }
}
