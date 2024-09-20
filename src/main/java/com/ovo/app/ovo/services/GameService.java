package com.ovo.app.ovo.services;

import com.ovo.app.ovo.enums.GameStatus;
import com.ovo.app.ovo.exception.*;
import com.ovo.app.ovo.models.*;
import com.ovo.app.ovo.repositories.GameRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameService {
    public final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game createGame(String player) {

        Game game = new Game();
        game.setFirstPlayer(player);
        game.setBoard(new int[3][3]);
        game.setStatus(GameStatus.NEW);
        System.out.println("Creating game1 " + player);
        gameRepository.save(game);
        System.out.println("Creating game2 " + player);
        return game;
    }

    public Game connectToGame(String player2, Integer gameId) throws InvalidParamException, InvalidGameException {
        Optional<Game> game = gameRepository.findById(gameId);
        if (game.isPresent()) {
            if ((game.get().getSecondPlayer() != null && !(game.get().getSecondPlayer().equals(player2) || game.get().getFirstPlayer().equals(player2)))) {
                throw new InvalidGameException("Game is not valid anymore");
            }
            if(game.get().getStatus().equals(GameStatus.FINISHED)){
                throw new InvalidGameException("Game is not valid anymore");
            }
        }
        else {
            throw new InvalidParamException("Game with provided id doesn't exist");
        }

        game.get().setSecondPlayer(player2);
        game.get().setStatus(GameStatus.IN_PROGRESS);
        gameRepository.save(game.get());
        return game.get();
    }

    public Game connectToRandomGame(String player2) throws NotFoundException {
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
        System.out.println(gamePlay.getGameId() + " gameid");
        System.out.println(gameRepository.findById(gamePlay.getGameId()).get().getFirstPlayer()+ " gameid");
        Optional<Game> gameOptional = gameRepository.findById(gamePlay.getGameId());
        if (gameOptional.isEmpty()) {
            throw new NotFoundException("Game not found");
        }

        Game game = gameOptional.get();
        if (!game.getStatus().equals(GameStatus.IN_PROGRESS)) {
            throw new InvalidGameException("Game is already finished or doesnt have second player");
        }

        int[][] board = game.getBoard();
        board[gamePlay.getCoordinateX()][gamePlay.getCoordinateY()] = gamePlay.getType().getValue();

        Boolean xWinner = checkWinner(game.getBoard(), TicToe.X);
        Boolean oWinner = checkWinner(game.getBoard(), TicToe.O);

        if (xWinner) {
            game.setWinner(TicToe.X);
        } else if (oWinner) {
            game.setWinner(TicToe.O);
        }
        gameRepository.save(game);
        return game;
    }

    private Boolean checkWinner(int[][] board, TicToe ticToe) {
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
                        return true;
                    }
                }
            }
        }
        return false;
    }
}