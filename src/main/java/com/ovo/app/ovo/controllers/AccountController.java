package com.ovo.app.ovo.controllers;

import com.ovo.app.ovo.enums.*;
import com.ovo.app.ovo.dtos.*;
import com.ovo.app.ovo.models.PlayerModel;
import com.ovo.app.ovo.repositories.PlayerRepository;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Random;

@Controller
public class AccountController {

    private final PlayerRepository playerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AccountController(PlayerRepository playerRepository, BCryptPasswordEncoder passwordEncoder) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("player", new PlayerDto());
        model.addAttribute("success", false);
        return "signup";
    }

    @PostConstruct
    public void createDefaultAdmin() {
        if (playerRepository.findByUsername("admin@ovo.com") == null) {
            PlayerModel admin = new PlayerModel();
            admin.setUsername("admin@ovo.com");
            admin.setEmail("admin@ovo.com");
            admin.setPassword(passwordEncoder.encode("admin@123"));
            admin.setPlayerId("ADMIN");
            admin.setType(PlayerTypeEnum.ADMIN);
            playerRepository.save(admin);
        }
    }

    @PostMapping("/signup")
    public String signup(Model model,
                         @Valid @ModelAttribute("player") PlayerDto playerDto,
                         BindingResult bindingResult) {

        if (!playerDto.getPassword().equals(playerDto.getConfirmPassword())) {
            bindingResult.addError(new FieldError("player", "confirmPassword", "Passwords do not match"));
            return "signup";
        }

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        PlayerModel player = new PlayerModel();
        player.setUsername(playerDto.getUsername());
        player.setEmail(playerDto.getEmail());
        player.setPassword(passwordEncoder.encode(playerDto.getPassword()));
        player.setPlayerId("PLAYER_" + new Random().nextInt(1000));
        player.setType(PlayerTypeEnum.PLAYER);

        playerRepository.save(player);
        model.addAttribute("success", true);
        return "redirect:/login";
    }
}