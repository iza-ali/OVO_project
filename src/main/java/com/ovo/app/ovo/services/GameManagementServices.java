package com.ovo.app.ovo.services;

import com.ovo.app.ovo.models.GameModel;
import com.ovo.app.ovo.repositories.GameManagementRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameManagementServices {
final GameManagementRepository gameManagementRepository;

    public GameManagementServices(GameManagementRepository gameManagementRepository) {
        this.gameManagementRepository = gameManagementRepository;
    }

    public List<GameModel> getAllGames() {
        return gameManagementRepository.findAll();
    }

}
