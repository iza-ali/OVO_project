package com.ovo.app.ovo.controllers;

import com.ovo.app.ovo.models.PlayerModel;
import com.ovo.app.ovo.repositories.PlayerRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LeaderboardController {

    private final PlayerRepository playerRepository;

    public LeaderboardController(PlayerRepository playerRepository) {

        this.playerRepository = playerRepository;
    }

    @GetMapping({ "/leaderboard"})
    public String account(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        PlayerModel player = playerRepository.findByUsername(username);
        if (player != null) {
            model.addAttribute("player", player);
        } else {
            return "redirect:/login";
        }
        return "leaderboard";
    }
}
