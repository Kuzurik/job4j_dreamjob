package ru.job4j.dreamjob.repository.candidate;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
public class MemoryCandidateRepository implements CandidateRepository {

    private int nextId = 1;

    private final Map<Integer, Candidate> candidates = new HashMap<>();

    private MemoryCandidateRepository() {
        save(new Candidate(0, "Intern Java Developer", "Стажер Java разработчик",
                LocalDateTime.now(), true, 1, 0));
        save(new Candidate(0, "Junior Java Developer", "Младший Java разработчик",
                LocalDateTime.now(), true, 1, 0));
        save(new Candidate(0, "Junior+ Java Developer", "Java разработчик",
                LocalDateTime.now(), true, 2, 0));
        save(new Candidate(0, "Middle Java Developer", "Старший Java разработчик",
                LocalDateTime.now(), true, 2, 0));
        save(new Candidate(0, "Middle+ Java Developer", "Ведущий Java разработчик",
                LocalDateTime.now(), true, 2, 0));
        save(new Candidate(0, "Senior Java Developer", "Главный Java разработчик",
                LocalDateTime.now(), true, 3, 0));
    }


    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId++);
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public boolean deleteById(int id) {
        return candidates.remove(id) != null;
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(candidate.getId(), (id, oldCandidate)
                -> new Candidate(oldCandidate.getId(), candidate.getName(),
                candidate.getDescription(), candidate.getCreationDate(),
                candidate.getVisible(), candidate.getCityId(), candidate.getFileId())) != null;
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
