package com.ovo.app.ovo.repositories;

import com.ovo.app.ovo.models.GameModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameModel, Integer>{

}
