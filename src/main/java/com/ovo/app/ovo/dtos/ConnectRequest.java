package com.ovo.app.ovo.dtos;

public class ConnectRequest {
    private String player;
    private Integer gameId;

    private ConnectRequest() {}
    public ConnectRequest(String player, Integer gameId) {
        this.player = player;
        this.gameId = gameId;
    }
    
    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }
}
