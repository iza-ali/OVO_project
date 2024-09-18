package com.ovo.app.ovo.controllers;

import com.ovo.app.ovo.models.GameModel;
import com.ovo.app.ovo.models.GameModelDto;
import com.ovo.app.ovo.models.PlayerDto;
import com.ovo.app.ovo.repositories.GameManagementRepository;
import com.ovo.app.ovo.repositories.GameRepository;
import com.ovo.app.ovo.services.GameManagementServices;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class GameManagementController {


    private final GameManagementRepository gameManagementRepository;

    public GameManagementController(GameManagementServices gameServices, GameManagementRepository gameManagementRepository) {
        this.gameServices = gameServices;

        this.gameManagementRepository = gameManagementRepository;
    }

    private final GameManagementServices gameServices;



//    @PostMapping("/update-game")
//    public String updateGame(int gameId, String gameName, String gameCategory){
//        gameServices.updateGame(gameId, gameName, gameCategory);
//        return "redirect:/games";
//    }

    @PostMapping("/add-game")
    public String addGame(Model model,
                         @Valid @ModelAttribute("gameObj") GameModelDto gameModelDto,
                         BindingResult bindingResult) {
        try {

            GameModel game = new GameModel();
            game.setGameName(gameModelDto.getGameName());
            game.setGameCategory(gameModelDto.getGameCategory());
            game.setGameDescription(gameModelDto.getGameDescription());
            game.setImageUrl(gameModelDto.getImageUrl());
        gameManagementRepository.save(game);
            model.addAttribute("gameObj", new GameModelDto());
            model.addAttribute("success", true);
            return "gameManagement";
        } catch (Exception e) {
            bindingResult.addError(new FieldError("gameObj", "gameName", e.getMessage()));
            return "redirect:/gameManagement";
        }
    }

    @GetMapping({"/gameManagement"})
    public String gameManagement(Model model) {
        model.addAttribute("gameObj", new GameModelDto());
        return "gameManagement";

    }

}
