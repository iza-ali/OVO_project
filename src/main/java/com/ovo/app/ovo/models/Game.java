package com.ovo.app.ovo.models;

import com.ovo.app.ovo.enums.GameStatus;
import jakarta.persistence.*;

@Entity
@Table
public class Game {
    @Id
    @GeneratedValue
    private Integer gameId;
    private String firstPlayer;
    private String secondPlayer;
    private TicToe winner;

    private GameStatus status;

    private int[][] board;

    public Game(String firstPlayer) {
        this.firstPlayer = firstPlayer;
    }
    public Game() {}
    public Integer getGameId() {
        return gameId;
    }
    public void setGameId(Integer id) {
        gameId = id;
    }
    public String getFirstPlayer() {
        return firstPlayer;
    }
    public void setFirstPlayer(String firstPlayer) {
        this.firstPlayer = firstPlayer;
    }
    public String getSecondPlayer() {
        return secondPlayer;
    }
    public void setSecondPlayer(String secondPlayer) {
        this.secondPlayer = secondPlayer;
    }
    public TicToe getWinner() {
        return winner;
    }
    public void setWinner(TicToe winner) {
        this.winner = winner;
    }
    public GameStatus getStatus() {
        return status;
    }
    public void setStatus(GameStatus status) {
        this.status = status;
    }
    public int[][] getBoard() {
        return board;
    }
    public void setBoard(int[][] board) {
        this.board = board;
    }
}
