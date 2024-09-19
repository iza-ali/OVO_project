package com.ovo.app.ovo.controllers;

import com.ovo.app.ovo.enums.GameCategoryEnum;
import com.ovo.app.ovo.models.GameModel;
import com.ovo.app.ovo.models.GameModelDto;
import com.ovo.app.ovo.repositories.GameManagementRepository;
import com.ovo.app.ovo.services.GameManagementServices;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.Random;

@Controller
public class GameManagementController {

    private static final Logger logger = LoggerFactory.getLogger(GameManagementController.class);

    private final GameManagementRepository gameManagementRepository;
    private final GameManagementServices gameServices;

    public GameManagementController(GameManagementServices gameServices, GameManagementRepository gameManagementRepository) {
        this.gameServices = gameServices;
        this.gameManagementRepository = gameManagementRepository;
    }

    @GetMapping("/api/games")
    @ResponseBody
    public List<GameModel> getGames() {
        return gameServices.getAllGames();
    }

    @PostMapping("/addGame")
    public String addGame(Model model,
                          @Valid @ModelAttribute("gameObj") GameModelDto gameModelDto,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "gameManagement";
        }
        try {
            GameModel game = new GameModel();
            game.setGameName(gameModelDto.getGameName());

            game.setGameCategory(gameModelDto.getGameCategory());
            game.setGameDescription(gameModelDto.getGameDescription());
            if (!gameModelDto.getImageUrl().isEmpty()) {
                byte[] imageBytes = gameModelDto.getImageUrl().getBytes();
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                game.setImageUrl(base64Image);
            }
            game.setGameId(String.valueOf(new Random().nextInt(1000)));
            gameManagementRepository.save(game);
            model.addAttribute("gameObj", new GameModelDto());
            model.addAttribute("success", true);
            return "gameManagement";
        } catch (Exception e) {
            logger.error("Error saving game: ", e);
            bindingResult.addError(new FieldError("gameObj", "gameName", e.getMessage()));
            return "redirect:/gameManagement";
        }
    }

    @GetMapping({"/gameManagement"})
    public String gameManagement(Model model) {
        model.addAttribute("gameObj", new GameModelDto());
        model.addAttribute("success", false);
        return "gameManagement";
    }
    @GetMapping("/api/games/{gameId}")
    @ResponseBody
    public ResponseEntity<?> getGameById(@PathVariable Integer gameId) {
        try {
            GameModel game = gameManagementRepository.findById(gameId)
                    .orElseThrow(() -> new RuntimeException("Game not found"));
            return ResponseEntity.ok(game);
        } catch (Exception e) {
            logger.error("Error fetching game details: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PutMapping("/api/games/{gameId}")
    @ResponseBody
    public ResponseEntity<?> updateGame(@PathVariable String gameId,
                                        @RequestParam String gameName,
                                        @RequestParam String gameDescription,
                                        @RequestParam GameCategoryEnum gameCategory,
                                        @RequestParam(required = false) MultipartFile imageUrl) {
        try {
            GameModel game = gameManagementRepository.findById(Integer.valueOf(gameId)).orElseThrow(() -> new RuntimeException("Game not found"));
            game.setGameName(gameName);
            game.setGameDescription(gameDescription);
            game.setGameCategory(gameCategory);
            if (imageUrl != null && !imageUrl.isEmpty()) {
                byte[] imageBytes = imageUrl.getBytes();
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                game.setImageUrl(base64Image);
            }
            gameManagementRepository.save(game);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error updating game: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}