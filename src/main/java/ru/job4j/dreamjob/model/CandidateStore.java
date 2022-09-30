package ru.job4j.dreamjob.model;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CandidateStore {

    private static final CandidateStore INST = new CandidateStore();

    private final Map<Integer, Candidate> candidateMap = new ConcurrentHashMap<>();

    private CandidateStore() {
        candidateMap.put(1, new Candidate(1, "Max",
                "Junior Java developer, experience one year", new Date()));
        candidateMap.put(2, new Candidate(2, "Piter",
                "Middle Java developer, experience two years", new Date()));
        candidateMap.put(3, new Candidate(3, "Alex",
                "Senior Java developer, experience five years", new Date()));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidateMap.values();
    }
}
