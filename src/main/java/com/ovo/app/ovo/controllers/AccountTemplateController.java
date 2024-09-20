package com.ovo.app.ovo.controllers;

import com.ovo.app.ovo.models.PlayerModel;
import com.ovo.app.ovo.repositories.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AccountTemplateController {

    private static final Logger log = LoggerFactory.getLogger(AccountTemplateController.class);
    private final PlayerRepository playerRepository;

    public AccountTemplateController(PlayerRepository playerRepository) {

        this.playerRepository = playerRepository;
    }

    @GetMapping({ "/account"})
    public String account(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        PlayerModel player = playerRepository.findByUsername(username);
        if (player != null) {
            model.addAttribute("player", player);
        } else {
            return "redirect:/login";
        }
        return "account";
    }

}
