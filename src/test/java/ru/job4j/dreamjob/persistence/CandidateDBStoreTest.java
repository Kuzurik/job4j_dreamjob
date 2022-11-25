package ru.job4j.dreamjob.persistence;

import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class CandidateDBStoreTest {

    @Test
    public void whenCreateCandidate() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        Candidate candidate = new Candidate(0, "Java Junior", true, "experience one year",
                new City(1, "Moscow"), null, new Date());
        store.add(candidate);
        Candidate result = store.findById(candidate.getId());
        assertThat(result.getName(), is(candidate.getName()));
        store.wipeTable();
    }

    @Test
    public void whenFindAllCandidate() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        Candidate candidate = new Candidate(0, "Java Junior", true, "experience one year",
                new City(1, "Moscow"), null, new Date());
        Candidate candidate1 = new Candidate(1, "Java Middle", false, "experience two years",
                new City(1, "Moscow"), null, new Date());
        store.add(candidate);
        store.add(candidate1);
        List<Candidate> expected = List.of(candidate, candidate1);
        List<Candidate> result = store.findAll();
        assertThat(result, is(expected));
        store.wipeTable();
    }

    @Test
    public void whenUpdateCandidate() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        Candidate candidate = new Candidate(0, "Java Junior", true, "experience one year",
                new City(1, "Moscow"), null, new Date());
        store.add(candidate);
        candidate.setName("java middle");
        store.update(candidate);
        Candidate result = store.findById(candidate.getId());
        assertThat(result.getName(), is(candidate.getName()));
        store.wipeTable();
    }

    @Test
    public void whenFindByIdCandidate() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        Candidate candidate = new Candidate(0, "Java Junior", true, "experience one year",
                new City(1, "Moscow"), null, new Date());
        store.add(candidate);
        Candidate result = store.findById(candidate.getId());
        assertThat(result.getName(), is(candidate.getName()));
        store.wipeTable();
    }

}