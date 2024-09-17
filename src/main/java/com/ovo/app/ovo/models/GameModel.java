package com.ovo.app.ovo.models;


import com.ovo.app.ovo.enums.GameCategoryEnum;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "game")
public class GameModel {
    @Id
    private Long id;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column
    private String imageUrl;

    @Column(name = "game_name")
    private String gameName;
    @Enumerated(EnumType.STRING)
    @Column(name = "game_category")
    private GameCategoryEnum gameCategory;

    @Column(name = "game_description")
    private String gameDescription;

    @Column(name="game_id" , unique = true)
    private String gameId;



    public String getGameName() {
        return gameName;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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



    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
}
