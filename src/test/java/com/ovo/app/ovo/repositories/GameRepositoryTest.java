package com.ovo.app.ovo.repositories;

import com.ovo.app.ovo.enums.GameStatus;
import com.ovo.app.ovo.models.Game;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.ovo.app.ovo.enums.GameStatus.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class GameRepositoryTest {
    @Autowired
    private GameRepository gameRepository;

    @Test
    public void GameRepository_Save_NewGame() {
        Game game = new Game("Alex");
        game.setStatus(NEW);
        Game savedGame = gameRepository.save(game);

        assertNotNull(savedGame);
        assertNotNull(savedGame.getGameId());
        Assertions.assertEquals(savedGame.getFirstPlayer(), "Alex");
    }

    @Test
    public void GameRepository_findFirstByStatusAndSecondPlayerIsNullAndFirstPlayerIsNot_findsGame() {
        Game game = new Game("Bob");
        game.setStatus(NEW);
        gameRepository.save(game);
        Game gamer2 = new Game("Alex");
        gamer2.setStatus(NEW);
        gameRepository.save(gamer2);

        Optional<Game> foundGame = gameRepository.findFirstByStatusAndSecondPlayerIsNullAndFirstPlayerIsNot(NEW,"Bob");

        assertTrue(foundGame.isPresent());
        assertEquals(foundGame.get().getFirstPlayer(), "Alex");
    }
  
}