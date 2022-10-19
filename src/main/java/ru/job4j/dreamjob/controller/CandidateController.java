package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.CandidateStore;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CandidateService;

import java.util.Date;

@Controller
public class CandidateController {

    private final CandidateService service = CandidateService.instOf();

    @GetMapping("/candidates")
    public String postCandidates(Model model) {
        model.addAttribute("candidates", service.findAll());
        return "candidates";
    }

    @GetMapping("/formAddCandidate")
    public String addPost(Model model) {
        model.addAttribute("addCandidate",
                new Candidate(0, "Новый кондидат", "Программист", new Date()));
        return "addCandidate";
    }

    @PostMapping("/createCandidate")
    public String createPost(@ModelAttribute Candidate candidate) {
        service.add(candidate);
        return "redirect:/candidates";
    }

    @PostMapping("/updateCandidate")
    public String updatePost(@ModelAttribute Candidate candidate) {
        service.update(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/formUpdateCandidate/{candidateId}")
    public String formUpdateCandidate(Model model, @PathVariable("candidateId") int id) {
        model.addAttribute("candidate", service.findById(id));
        return "updateCandidate";
    }
}
