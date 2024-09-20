package com.ovo.app.ovo.services;

import com.ovo.app.ovo.models.GameModel;
import com.ovo.app.ovo.repositories.GameManagementRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameManagementServices {

    private static final Logger log = LoggerFactory.getLogger(GameManagementServices.class);
    private
final GameManagementRepository gameManagementRepository;

    public GameManagementServices(GameManagementRepository gameManagementRepository) {
        this.gameManagementRepository = gameManagementRepository;
    }

    public List<GameModel> getAllGames() {
        log.info("getAllGames");
        return gameManagementRepository.findAll();
    }



}
