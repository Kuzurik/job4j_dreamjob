package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.ValidateUser;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.CandidateService;
import ru.job4j.dreamjob.service.CityService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;
import java.io.IOException;

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
    public String postCandidates(Model model, HttpSession session) {
        User user = ValidateUser.validateUser(session);
        model.addAttribute("user", user);
        model.addAttribute("candidates", service.findAll());
        return "candidates";
    }

    @GetMapping("/formAddCandidate")
    public String addCandidate(Model model, HttpSession session) {
        User user = ValidateUser.validateUser(session);
        model.addAttribute("user", user);
        model.addAttribute("cities", cityService.getAllCities());
        return "addCandidate";
    }

    @PostMapping("/createCandidate")
    public String createCandidate(@ModelAttribute Candidate candidate,
                             @RequestParam("file") MultipartFile file) throws IOException {
        candidate.setPhoto(file.getBytes());
        service.add(candidate);
        return "redirect:/candidates";
    }

    @PostMapping("/updateCandidate")
    public String updateCandidate(@ModelAttribute Candidate candidate,
                             @RequestParam("file") MultipartFile file) throws IOException {
        candidate.setPhoto(file.getBytes());
        service.update(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/formUpdateCandidate/{candidateId}")
    public String formUpdateCandidate(Model model, @PathVariable("candidateId") int id, HttpSession session) {
        User user = ValidateUser.validateUser(session);
        model.addAttribute("user", user);
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("candidate", service.findById(id));
        return "updateCandidate";
    }

    @GetMapping("/photoCandidate/{candidateId}")
    public ResponseEntity<Resource> download(@PathVariable("candidateId") Integer candidateId) {
        Candidate candidate = service.findById(candidateId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(candidate.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(candidate.getPhoto()));
    }
}
