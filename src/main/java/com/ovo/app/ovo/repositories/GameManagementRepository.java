package com.ovo.app.ovo.repositories;

import com.ovo.app.ovo.models.GameModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameManagementRepository extends JpaRepository<GameModel, Integer> {

    @NonNull
    List<GameModel> findAll();



}
