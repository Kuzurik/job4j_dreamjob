package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.services.CandidateService;
import ru.job4j.dreamjob.services.SimpleCandidateService;

@Controller
@RequestMapping("/candidates")
public class CandidateController {

    private final CandidateService candidateService = SimpleCandidateService.getInstance();

    @GetMapping
    public String getAllCandidates(Model model) {
        model.addAttribute("candidates", candidateService.findAll());
        return "candidates/candidatesList";
    }

    @GetMapping("/candidateCreate")
    public String getCreationPage() {
        return "candidates/candidateCreate";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var candidateOptional = candidateService.findById(id);
        if (candidateOptional.isEmpty()) {
            model.addAttribute("message", "Вакансия с указанным идентификатором не найдена");
            return "errors/404";
        }
        model.addAttribute("candidate", candidateOptional.get());
        return "candidates/editCandidate";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Candidate candidate, Model model) {
        var isUpdated = candidateService.update(candidate);
        if (!isUpdated) {
            model.addAttribute("message", "Вакансия с указанным идентификатором не найдена");
            return "errors/404";
        }
        return "redirect:/candidates";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        var isDeleted = candidateService.findById(id).isEmpty();
        if (isDeleted) {
            model.addAttribute("message", "Вакансия с указанным идентификатором не найдена");
            return "errors/404";
        }
        candidateService.deleteById(id);
        return "redirect:/candidates";
    }
}
