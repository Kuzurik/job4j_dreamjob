package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import javax.annotation.concurrent.ThreadSafe;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ThreadSafe
public class MemoryCandidateRepository implements CandidateRepository {

    private final AtomicInteger total = new AtomicInteger(0);

    private final Map<Integer, Candidate> candidates = new HashMap<>();

    private MemoryCandidateRepository() {
        save(new Candidate(0, "Alexander Kuzura", "Intern Java Developer"));
        save(new Candidate(0, "Petr Arsentev", "Junior Java Developer"));
        save(new Candidate(0, "Vladimir Danilovich", "Junior+ Java Developer"));
        save(new Candidate(0, "Alexei Zolotov", "Middle Java Developer"));
        save(new Candidate(0, "Andrey Rasolko", "Middle+ Java Developer"));
        save(new Candidate(0, "Irina Kazanouskaya", "Senior Java Developer"));
    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(total.incrementAndGet());
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public void deleteById(int id) {
       candidates.remove(id);
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(candidate.getId(),
                (id, oldVacancy) -> new Candidate(oldVacancy.getId(),
                        candidate.getName(), candidate.getDescription())) != null;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
