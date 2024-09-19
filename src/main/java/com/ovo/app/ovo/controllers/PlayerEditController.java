package com.ovo.app.ovo.controllers;


import com.ovo.app.ovo.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class PlayerEditController {


    public PlayerEditController(PlayerService playerService) {
        this.playerService = playerService;
    }

    private final PlayerService playerService;

    @PostMapping("/update-password")
    public String updatePassword(@RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmNewPassword,
                                 Principal principal, Model model) {
        try{
            if (!newPassword.equals(confirmNewPassword)){
                model.addAttribute("error", "Passwords do not match");
                return "account";
            }

            playerService.updatePasswordHelper(principal.getName(), currentPassword, newPassword);
            model.addAttribute("message", "Password updated successfully");
        }   catch (Exception e){
            model.addAttribute("error", e.getMessage());
        }
        return "index";
    }

    @PostMapping("/update-username")
    public String updatePassword(@RequestParam String newUsername,
                                 Principal principal, Model model) {
        try{
            playerService.updateUsername(principal.getName(), newUsername);
            model.addAttribute("message", "Username updated successfully");
        }   catch (Exception e){
            model.addAttribute("error", e.getMessage());
        }
        return "index";
    }

    @PostMapping("/update-email")
    public String updateEmail(@RequestParam String newEmail,
                              Principal principal, Model model) {
        try{
            playerService.updateEmail(principal.getName(), newEmail);
            model.addAttribute("message", "Username updated successfully");
        }   catch (Exception e){
            model.addAttribute("error", e.getMessage());
        }
        return "index";
    }

    @PostMapping("/delete-user")
    public String deleteUser(Principal principal, Model model) {
        try{
            playerService.deleteUser(principal.getName());
        }   catch (Exception e){
            model.addAttribute("error", e.getMessage());
        }
        return "index";
    }

}
