package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Vacancy;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ThreadSafe
public class MemoryVacancyRepository implements VacancyRepository {

    private final AtomicInteger total = new AtomicInteger(0);

    private final Map<Integer, Vacancy> vacancies = new HashMap<>();

    private MemoryVacancyRepository() {
        save(new Vacancy(0, "Intern Java Developer", "Intern Java Developer", true, 2, 0));
        save(new Vacancy(0, "Junior Java Developer", "Junior Java Developer", false, 1,  0));
        save(new Vacancy(0, "Junior+ Java Developer", "Junior+ Java Developer", true, 3,  0));
        save(new Vacancy(0, "Middle Java Developer", "Middle Java Developer", true, 2, 0));
        save(new Vacancy(0, "Middle+ Java Developer", "Middle+ Java Developer", true, 1,  0));
        save(new Vacancy(0, "Senior Java Developer", "Senior Java Developer", true, 2,  0));
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(total.incrementAndGet());
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public void deleteById(int id) {
        vacancies.remove(id);
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(vacancy.getId(), (id, oldVacancy) ->
                new Vacancy(oldVacancy.getId(), vacancy.getTitle(), vacancy.getDescription(),
                vacancy.getVisible(), vacancy.getCityId(), vacancy.getFileId())) != null;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancies.values();
    }
}
