package com.ovo.app.ovo.models;

import com.ovo.app.ovo.enums.GameCategoryEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class GameModelDto {

    @NotEmpty
    private String gameName;



    @NotEmpty
    private String gameDescription;

    @NotNull
    private GameCategoryEnum gameCategory;

    public @NotNull MultipartFile getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@NotNull MultipartFile imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NotNull
    private MultipartFile imageUrl;


    public @NotNull GameCategoryEnum getGameCategory() {
        return gameCategory;
    }

    public void setGameCategory(@NotNull GameCategoryEnum gameCategory) {
        this.gameCategory = gameCategory;
    }



    public @NotEmpty String getGameName() {
        return gameName;
    }

    public void setGameName(@NotEmpty String gameName) {
        this.gameName = gameName;
    }


    public @NotEmpty String getGameDescription() {
        return gameDescription;
    }

    public void setGameDescription(@NotEmpty String gameDescription) {
        this.gameDescription = gameDescription;
    }


}