package ru.job4j.dreamjob.model;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CandidateStore {

    private static final CandidateStore INST = new CandidateStore();

    private final AtomicInteger id = new AtomicInteger(0);
    private final Map<Integer, Candidate> candidateMap = new ConcurrentHashMap<>();

    private CandidateStore() {
    }

    public static CandidateStore instOf() {
        return INST;
    }

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
