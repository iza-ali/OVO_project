package com.ovo.app.ovo.controllers;

import com.ovo.app.ovo.enums.PlayerTypeEnum;
import com.ovo.app.ovo.models.PlayerDto;
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

    public AccountController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }


    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("player", new PlayerDto());
        model.addAttribute("success", false);
        return "signup";
    }
    @PostConstruct
    public void createDefaultAdmin() {
        var bcryptedPassword = new BCryptPasswordEncoder();
        if (playerRepository.findByUsername("admin@ovo.com") == null) {
            PlayerModel admin = new PlayerModel();
            admin.setUsername("admin@ovo.com");
            admin.setEmail("admin@ovo.com");
            admin.setPassword(bcryptedPassword.encode("admin@123"));
            admin.setPlayerId("ADMIN");
            admin.setType(PlayerTypeEnum.ROLE_ADMIN);
            playerRepository.save(admin);
        }
    }
    @PostMapping("/signup")
    public String signup(Model model,
                         @Valid @ModelAttribute("player") PlayerDto playerDto,
                         BindingResult bindingResult) {

        if (!playerDto.getPassword().equals(playerDto.getConfirmPassword())) {
            bindingResult.addError(new FieldError("player", "confirmPassword", "Passwords do not match"));
        }
        if (playerRepository.findByEmail(playerDto.getEmail()) != null) {
            bindingResult.addError(new FieldError("player", "email", "User with given Email already exists"));
        }
        if (playerRepository.findByUsername(playerDto.getUsername()) != null) {
            bindingResult.addError(new FieldError("player", "username", "User with given username already exists"));
        }
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        try {
            var bcryptedPassword = new BCryptPasswordEncoder().encode(playerDto.getPassword());
            PlayerModel player = new PlayerModel();
            player.setEmail(playerDto.getEmail());
            player.setPassword(bcryptedPassword);
            player.setUsername(playerDto.getUsername());
            player.setPlayerId(Math.abs(new Random().nextLong()) + "");
            player.setType(PlayerTypeEnum.ROLE_PLAYER);
            playerRepository.save(player);
            model.addAttribute("player", new PlayerDto());
            model.addAttribute("success", true);
            return "signup";
        } catch (Exception e) {
            bindingResult.addError(new FieldError("player", "username", e.getMessage()));
            return "redirect:/login";
        }
    }

//    @GetMapping("/logout")
//    public String logout() {
//        return "redirect:/logout";
//    }



}