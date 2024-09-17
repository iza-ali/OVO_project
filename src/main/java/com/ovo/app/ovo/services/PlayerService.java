package com.ovo.app.ovo.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ovo.app.ovo.models.PlayerModel;
import com.ovo.app.ovo.repositories.PlayerRepository;


@Service
public class PlayerService {


    private BCryptPasswordEncoder passwordEncoder;
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }



    public PlayerModel getUserByUsername(String username) throws UsernameNotFoundException {

        PlayerModel player= playerRepository.findByUsername(username);
        if(player!=null){
            return player;
        }
        throw new UsernameNotFoundException("User not found");
    }

    public void updatePassword(String username, String currentPassword, String newPassword) throws Exception {
        PlayerModel player = playerRepository.findByUsername(username);

        if (!passwordEncoder.matches(currentPassword, player.getPassword())){
            throw new Exception("Wrong password");
        }

        player.setPassword(passwordEncoder.encode(newPassword));
        playerRepository.save(player);
    }
}
