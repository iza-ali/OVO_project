package com.ovo.app.ovo.services;

import com.ovo.app.ovo.models.PlayerModel;
import com.ovo.app.ovo.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

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
