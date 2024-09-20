package com.ovo.app.ovo.services;

import com.ovo.app.ovo.dtos.*;
import com.ovo.app.ovo.enums.*;
import com.ovo.app.ovo.exception.*;
import com.ovo.app.ovo.models.*;
import com.ovo.app.ovo.repositories.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameService {
    private static final Logger log = LoggerFactory.getLogger(GameService.class);
    public final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game createGame(String player) {

        Game game = new Game();
        game.setFirstPlayer(player);
        game.setBoard(new int[3][3]);
        game.setStatus(GameStatus.NEW);
        log.info("Creating new game {}", game);
        gameRepository.save(game);
        return game;
    }

    public Game connectToGame(String player2, Integer gameId) throws InvalidParamException, InvalidGameException {
        Optional<Game> game = gameRepository.findById(gameId);

        if (game.isPresent()) {
            if ((game.get().getSecondPlayer() != null && !(game.get().getSecondPlayer().equals(player2) || game.get().getFirstPlayer().equals(player2)))) {
                log.info("Connecting to a game that is not valid anymore");
                throw new InvalidGameException("Game is not valid anymore");
            }
            if(game.get().getStatus().equals(GameStatus.FINISHED)){
                log.info("Connecting to a game that is Finished");
                throw new InvalidGameException("Game is not Finished");
            }
        }
        else {
            log.info("Game doesn't exist");
            throw new InvalidParamException("Game with provided id doesn't exist");
        }

        game.get().setSecondPlayer(player2);
        game.get().setStatus(GameStatus.IN_PROGRESS);
        gameRepository.save(game.get());
        log.info("Connecting to a game {}", game);
        return game.get();
    }

    public Game connectToRandomGame(String player2) throws NotFoundException {
        log.info("Connecting to a random game {}", player2);
        Optional<Game> game = gameRepository.findFirstByStatusAndSecondPlayerIsNullAndFirstPlayerIsNot(GameStatus.NEW, player2);
        if (game.isPresent()) {
            game.get().setSecondPlayer(player2);
            game.get().setStatus(GameStatus.IN_PROGRESS);
            gameRepository.save(game.get());
            return game.orElse(null);
        }
        else {
            return createGame(player2);
        }
    }

    public Game gamePlay(GamePlay gamePlay) throws NotFoundException, InvalidGameException {
        log.info("Playing game {}", gamePlay);
        Optional<Game> gameOptional = gameRepository.findById(gamePlay.getGameId());
        if (gameOptional.isEmpty()) {
            throw new NotFoundException("Game not found");
        }

        Game game = gameOptional.get();
        if (!game.getStatus().equals(GameStatus.IN_PROGRESS)) {
            log.info("Game already finished");
            throw new InvalidGameException("Game is already finished or doesnt have second player");
        }

        int[][] board = game.getBoard();
        board[gamePlay.getCoordinateX()][gamePlay.getCoordinateY()] = gamePlay.getType().getValue();

        Boolean xWinner = checkWinner(game.getBoard(), TicToe.X);
        Boolean oWinner = checkWinner(game.getBoard(), TicToe.O);

        if (xWinner) {
            game.setWinner(TicToe.X);
            game.setStatus(GameStatus.FINISHED);
        } else if (oWinner) {
            game.setWinner(TicToe.O);
            game.setStatus(GameStatus.FINISHED);
        }
        gameRepository.save(game);
        return game;
    }

    private Boolean checkWinner(int[][] board, TicToe ticToe) {
        log.info("Checking winner for");
        int[] boardArray = new int[9];
        int counterIndex = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                boardArray[counterIndex] = board[i][j];
                counterIndex++;
            }
        }

        int[][] winCombinations = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
        for (int i = 0; i < winCombinations.length; i++) {
            int counter = 0;
            for (int j = 0; j < winCombinations[i].length; j++) {
                if (boardArray[winCombinations[i][j]] == ticToe.getValue()) {
                    counter++;
                    if (counter == 3) {
                        log.info("Winner found");
                        return true;
                    }
                }
            }
        }
        return false;
    }
}