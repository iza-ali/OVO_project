package com.ovo.app.ovo.controllers;

import com.ovo.app.ovo.models.GameModel;
import com.ovo.app.ovo.models.PlayerModel;
import com.ovo.app.ovo.repositories.PlayerRepository;
import com.ovo.app.ovo.services.GameManagementServices;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    private final PlayerRepository playerRepository;
    private final GameManagementServices gameServices;
    public DashboardController(PlayerRepository playerRepository, GameManagementServices gameServices) {
        this.playerRepository = playerRepository;
        this.gameServices = gameServices;
    }

    @GetMapping({ "/dashboard"})
    public String dashboard(Model model) {
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


}