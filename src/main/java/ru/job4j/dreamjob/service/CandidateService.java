package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.CandidateStore;

import java.util.Collection;

public class CandidateService {

    public static final CandidateService SERVICE = new CandidateService();

    public final CandidateStore store = CandidateStore.instOf();


    public static CandidateService instOf() {
        return SERVICE;
    }

    public Collection<Candidate> findAll() {
        return store.findAll();
    }

    public void add(Candidate candidate) {
        store.add(candidate);
    }

    public Candidate findById(int id) {
        return store.findById(id);
    }

    public void update(Candidate candidate) {
        store.update(candidate);
    }
}
