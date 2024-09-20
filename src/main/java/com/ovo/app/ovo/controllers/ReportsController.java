// ReportController.java
package com.ovo.app.ovo.controllers;

import com.ovo.app.ovo.models.ReportDto;
import com.ovo.app.ovo.models.ReportModel;
import com.ovo.app.ovo.repositories.ReportRepository;
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

    public ReportsController(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @GetMapping("/reports")
    public String reports(Model model) {
        model.addAttribute("reports", reportRepository.findAll());
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