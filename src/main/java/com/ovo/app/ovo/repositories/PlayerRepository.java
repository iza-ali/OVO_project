package com.ovo.app.ovo.repositories;


import com.ovo.app.ovo.models.PlayerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerModel, Integer> {



    PlayerModel findByEmail(String email);

}
