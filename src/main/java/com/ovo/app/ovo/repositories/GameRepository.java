package com.ovo.app.ovo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ovo.app.ovo.enums.GameStatus;
import com.ovo.app.ovo.models.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
    Optional<Game> findFirstByStatusAndSecondPlayerIsNull(GameStatus status);

}
