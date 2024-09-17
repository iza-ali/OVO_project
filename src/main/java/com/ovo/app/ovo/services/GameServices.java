package com.ovo.app.ovo.services;

import com.ovo.app.ovo.enums.GameCategoryEnum;
import com.ovo.app.ovo.models.GameModel;
import com.ovo.app.ovo.repositories.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServices {
final GameRepository gameRepository;

    public GameServices(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public void deleteGame(int gameId){
        gameRepository.deleteById(gameId);
    }
    public void updateGame(int gameId, String gameName, String gameCategory){
        gameRepository.findById(gameId).ifPresent(game -> {
            game.setGameName(gameName);
            game.setGameCategory(GameCategoryEnum.valueOf(gameCategory));
            gameRepository.save(game);
        });
    }
    public List<GameModel> getAllGames() {
        return gameRepository.findAll();
    }
}
