package com.ovo.app.ovo.controllers;

import com.ovo.app.ovo.models.PlayerModel;
import com.ovo.app.ovo.repositories.PlayerRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final PlayerRepository playerRepository;

    public DashboardController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @GetMapping({ "/dashboard"})
    public String dashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        PlayerModel player = playerRepository.findByEmail(email);
        model.addAttribute("player", player);
        return "dashboard";
    }
}