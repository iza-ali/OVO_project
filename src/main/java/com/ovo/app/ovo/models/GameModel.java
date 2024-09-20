package com.ovo.app.ovo.models;


import com.ovo.app.ovo.enums.GameCategoryEnum;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "games")
public class GameModel {


    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column
    private String imageUrl;

    public String getGamePath() {
        return gamePath;
    }

    public void setGamePath(String gamePath) {
        this.gamePath = gamePath;
    }

    @Column (name = "game_path")
    private String gamePath;

    @Column(name = "game_name")
    private String gameName;

    @Enumerated(EnumType.STRING)

    @Column(name = "game_category")
    private GameCategoryEnum gameCategory;



    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public GameCategoryEnum getGameCategory() {
        return gameCategory;
    }

    public void setGameCategory(GameCategoryEnum gameCategory) {
        this.gameCategory = gameCategory;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    public void setGameDescription(String gameDescription) {
        this.gameDescription = gameDescription;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    @Column(name = "game_description")
    private String gameDescription;
    @Id
    @Column(name = "game_id", unique = true)
    private String gameId;




    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
}
