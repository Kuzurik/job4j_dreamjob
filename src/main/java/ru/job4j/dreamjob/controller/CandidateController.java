package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.service.CandidateService;
import ru.job4j.dreamjob.service.CityService;

import java.util.Date;

@ThreadSafe
@Controller
public class CandidateController {

    private final CandidateService service;
    private final CityService cityService;

    public CandidateController(CandidateService service, CityService cityService) {
        this.service = service;
        this.cityService = cityService;
    }

    @GetMapping("/candidates")
    public String postCandidates(Model model) {
        model.addAttribute("candidates", service.findAll());
        return "candidates";
    }

    @GetMapping("/formAddCandidate")
    public String addPost(Model model) {
        model.addAttribute("cities", cityService.getAllCities());
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
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("candidate", service.findById(id));
        return "updateCandidate";
    }
}
