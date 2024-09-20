package com.ovo.app.ovo.dtos;

import com.ovo.app.ovo.enums.*;

public class GamePlay {

    private TicToe type;
    private Integer coordinateX;
    private Integer coordinateY;
    private Integer gameId;

    public Integer getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(Integer coordinateX) {
        this.coordinateX = coordinateX;
    }

    public Integer getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(Integer coordinateY) {
        this.coordinateY = coordinateY;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public TicToe getType() {
        return type;
    }

    public void setType(TicToe type) {
        this.type = type;
    }
}
