package com.ovo.app.ovo.controllers;

import com.ovo.app.ovo.enums.PlayerTypeEnum;
import com.ovo.app.ovo.models.GameModel;
import com.ovo.app.ovo.models.GameModelDto;
import com.ovo.app.ovo.models.PlayerDto;
import com.ovo.app.ovo.models.PlayerModel;
import com.ovo.app.ovo.repositories.GameRepository;
import com.ovo.app.ovo.services.GameServices;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Random;

@Controller
public class GameManagementController {

    private final GameRepository gameRepository;

    public GameManagementController(GameServices gameServices, GameRepository gameRepository) {
        this.gameServices = gameServices;
        this.gameRepository = gameRepository;
    }

    private final GameServices gameServices;

    @PostMapping("/delete-game")
    public String deleteGame(int gameId){
        gameServices.deleteGame(gameId);
        return "redirect:/games";
    }

    @PostMapping("/update-game")
    public String updateGame(int gameId, String gameName, String gameCategory){
        gameServices.updateGame(gameId, gameName, gameCategory);
        return "redirect:/games";
    }

    @PostMapping("/add-game")
    public String addGame(Model model,
                         @Valid @ModelAttribute("game") GameModelDto gameModelDto,
                         BindingResult bindingResult) {


        try {

            GameModel game = new GameModel();
            game.setGameName(gameModelDto.getGameName());
            game.setGameCategory(gameModelDto.getGameCategory());
            game.setGameDescription(gameModelDto.getGameDescription());
            game.setImageUrl(gameModelDto.getImageUrl());
            gameRepository.save(game);
            model.addAttribute("game", new GameModelDto());
            model.addAttribute("success", true);
            return "gameManagement";
        } catch (Exception e) {
            bindingResult.addError(new FieldError("game", "gamename", e.getMessage()));
            return "redirect:/gameManagement";
        }
    }

    @GetMapping({ "/gameManagement"})
    public String gameManagement(Model model) {
        return "gameManagement";
    }

    @GetMapping("/games")
    public String getAllGames(Model model) {
        List<GameModel> games = gameServices.getAllGames();
        model.addAttribute("games", games);
        return "games";
    }
}
