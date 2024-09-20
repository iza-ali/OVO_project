// ReportController.java
package com.ovo.app.ovo.controllers;

import com.ovo.app.ovo.models.PlayerModel;
import com.ovo.app.ovo.models.ReportDto;
import com.ovo.app.ovo.models.ReportModel;
import com.ovo.app.ovo.repositories.PlayerRepository;
import com.ovo.app.ovo.repositories.ReportRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

@Controller
public class ReportsController {

    private final ReportRepository reportRepository;
    private final PlayerRepository playerRepository;

    public ReportsController(ReportRepository reportRepository, PlayerRepository playerRepository) {
        this.reportRepository = reportRepository;
        this.playerRepository = playerRepository;
    }

    @GetMapping("/reports")
    public String reports(Model model) {
        model.addAttribute("reports", reportRepository.findAll());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        PlayerModel player = playerRepository.findByUsername(username);
        if (player != null) {
            model.addAttribute("player", player);
        }

        return "reports";
    }

    @PostMapping("/reports")
    public ResponseEntity<?> createReport(@RequestBody ReportDto reportDto) {
        if (reportDto.getTitle() == null || reportDto.getDescription() == null) {
            return ResponseEntity.badRequest().body("{\"success\": false, \"message\": \"Title and description are required.\"}");
        }

        ReportModel report = new ReportModel();
        report.setTitle(reportDto.getTitle());
        report.setDescription(reportDto.getDescription());
        report.setCreatedBy(reportDto.getCreatedBy());
        report.setGameId(reportDto.getGameId());
        reportRepository.save(report);

        return ResponseEntity.ok("{\"success\": true, \"message\": \"Report submitted successfully.\"}");
    }
}