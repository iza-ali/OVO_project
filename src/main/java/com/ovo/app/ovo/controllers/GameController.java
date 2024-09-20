package com.ovo.app.ovo.controllers;

import com.ovo.app.ovo.dtos.*;
import com.ovo.app.ovo.exception.*;
import com.ovo.app.ovo.models.*;
import com.ovo.app.ovo.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/game")
public class GameController {

    private static final Logger log = LoggerFactory.getLogger(GameController.class);
    private final GameService gameService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final PlayerService playerService;
    public GameController(GameService gameService, SimpMessagingTemplate simpMessagingTemplate, PlayerService playerService) {
        this.gameService = gameService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.playerService = playerService;
    }
    @GetMapping
    public String game(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            System.out.println(username);
            model.addAttribute("username", username);
            return "game";
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/start")
    public ResponseEntity<Game> start(@RequestBody StartGameRequest player) {
        log.info("start game request: {}", player);
        System.out.println(player+ "requestbody");
        return ResponseEntity.ok(gameService.createGame(player.getPlayer()));
    }

    @PostMapping("/connect")
    public ResponseEntity<Game> connect(@RequestBody ConnectRequest request) throws InvalidParamException, InvalidGameException {
        log.info("Connect request: {}", request);
        return ResponseEntity.ok(gameService.connectToGame(request.getPlayer(), request.getGameId()));
    }

    @PostMapping("/connect/random")
    public ResponseEntity<Game> connectRandom(@RequestBody String player) throws NotFoundException {
        log.info("Connect to random game{}", player);
        return ResponseEntity.ok(gameService.connectToRandomGame(player));
    }

    @PostMapping("/gameplay")
    public ResponseEntity<Game> gamePlay(@RequestBody GamePlay request) throws NotFoundException, InvalidGameException {
        log.info("Gameplay: {}", request);
        Game game = gameService.gamePlay(request);
        simpMessagingTemplate.convertAndSend("/topic/game-progress/" + game.getGameId(), game);
        return ResponseEntity.ok(game);
    }
}
