// ReportController.java
package com.ovo.app.ovo.controllers;

import com.ovo.app.ovo.models.PlayerModel;
import com.ovo.app.ovo.models.ReportDto;
import com.ovo.app.ovo.models.ReportModel;
import com.ovo.app.ovo.repositories.PlayerRepository;
import com.ovo.app.ovo.repositories.ReportRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
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
    public String createReport(@Valid @ModelAttribute("report") ReportDto reportDto, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "dashboard";
        }

        ReportModel report = new ReportModel();
        report.setTitle(reportDto.getTitle());
        report.setDescription(reportDto.getDescription());
        report.setCreatedBy(principal.getName());

        reportRepository.save(report);
        return "redirect:/dashboard";
    }
}