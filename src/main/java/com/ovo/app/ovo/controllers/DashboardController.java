package com.ovo.app.ovo.controllers;

import com.ovo.app.ovo.models.GameModel;
import com.ovo.app.ovo.models.PlayerModel;
import com.ovo.app.ovo.repositories.PlayerRepository;
import com.ovo.app.ovo.services.GameManagementServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    private static final Logger log = LoggerFactory.getLogger(DashboardController.class);
    private final PlayerRepository playerRepository;
    private final GameManagementServices gameServices;
    public DashboardController(PlayerRepository playerRepository, GameManagementServices gameServices) {
        this.playerRepository = playerRepository;
        this.gameServices = gameServices;
    }

    @GetMapping({ "/dashboard"})
    public String dashboard(Model model) {
        log.info("dashboard");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        PlayerModel player = playerRepository.findByUsername(username);
        List<GameModel> games = gameServices.getAllGames();
        model.addAttribute("games", games);
        if (player != null) {
            model.addAttribute("player", player);
        } else {
            return "redirect:/login";
        }
        return "dashboard";
    }

    @GetMapping({ "/adminDashboard"})
    public String adminDashboard(Model model) {
        log.info("adminDashboard");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        PlayerModel player = playerRepository.findByUsername(username);
        if (player != null) {
            model.addAttribute("player", player);
        } else {
            return "redirect:/login";
        }
        return "adminDashboard";
    }


}