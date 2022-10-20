package ru.job4j.dreamjob.persistence;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
@Repository
public class CandidateStore {
    private final AtomicInteger id = new AtomicInteger(0);
    private final Map<Integer, Candidate> candidateMap = new ConcurrentHashMap<>();

    public Collection<Candidate> findAll() {
        return candidateMap.values();
    }

    public void add(Candidate candidate) {
        candidate.setId(id.getAndIncrement());
        candidate.setCreated(new Timestamp(System.currentTimeMillis()));
        candidateMap.put(candidate.getId(), candidate);
    }

    public void update(Candidate candidate) {
        candidate.setCreated(new Timestamp(System.currentTimeMillis()));
        candidateMap.replace(candidate.getId(), candidate);
    }

    public Candidate findById(int id) {
        return candidateMap.get(id);
    }
}
