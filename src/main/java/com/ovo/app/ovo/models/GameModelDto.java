package com.ovo.app.ovo.models;

import com.ovo.app.ovo.enums.GameCategoryEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class GameModelDto {

    @NotEmpty
    private String gameName;

    @NotEmpty
    private String gameId;

    @NotEmpty
    private String imageUrl;

    @NotEmpty
    private String gameDescription;

    @NotEmpty
    private GameCategoryEnum gameCategory;

    public @NotEmpty String getGameName() {
        return gameName;
    }

    public void setGameName(@NotEmpty String gameName) {
        this.gameName = gameName;
    }

    public @NotEmpty String getGameId() {
        return gameId;
    }

    public void setGameId(@NotEmpty String gameId) {
        this.gameId = gameId;
    }

    public @NotEmpty String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@NotEmpty String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public @NotEmpty String getGameDescription() {
        return gameDescription;
    }

    public void setGameDescription(@NotEmpty String gameDescription) {
        this.gameDescription = gameDescription;
    }

    public @NotEmpty GameCategoryEnum getGameCategory() {
        return gameCategory;
    }

    public void setGameCategory(@NotEmpty GameCategoryEnum gameCategory) {
        this.gameCategory = gameCategory;
    }
}