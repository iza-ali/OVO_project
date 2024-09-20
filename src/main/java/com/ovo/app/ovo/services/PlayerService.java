package com.ovo.app.ovo.services;

import com.ovo.app.ovo.models.PlayerModel;
import com.ovo.app.ovo.repositories.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;


@Service
public class PlayerService {

    private static final Logger log = LoggerFactory.getLogger(PlayerService.class);
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public void updatePasswordHelper(String username, String currentPassword, String newPassword) throws Exception {
        PlayerModel player = getUserByUsername(username);
        log.info("Updating password for user ");
        if (player != null) {
            if (!passwordEncoder.matches(currentPassword, player.getPassword())) {
                log.error("Current password does not match stored password");
                throw new Exception("Wrong password");
            }
            player.setPassword(passwordEncoder.encode(newPassword));
            playerRepository.save(player);
        } else {
            log.error("Player not found");
            throw new Exception("Player Not Found");
        }
    }
    public void deleteUser(String username) throws Exception {
        PlayerModel player = getUserByUsername(username);

        if(player==null){
            throw new UsernameNotFoundException("User not found");
        }else {
            playerRepository.delete(player);
        }
    }
    public void updateUsername(String currentUsername, String newUsername) throws Exception {

        PlayerModel player = getUserByUsername(currentUsername);
        if(player==null){
            log.info("updateUsername, Player not found");
            throw new UsernameNotFoundException("User not found");
        }else {
            if (currentUsername.equals(newUsername)) {
                log.info("updateUsername, Same name");
                throw new Exception("You need a new username");
            }
            player.setUsername(newUsername);
            playerRepository.save(player);
        }
    }
    public void updateEmail(String currentUsername, String newEmail) throws Exception {
        PlayerModel player = getUserByUsername(currentUsername);
        if(player==null){
            throw new UsernameNotFoundException("User not found");
        }else {
            if (newEmail.equals(player.getEmail())) {
                throw new Exception("You need a new e-mail");
            }
            player.setEmail(newEmail);
            playerRepository.save(player);
        }
    }

    public PlayerModel getUserByUsername(String username) throws UsernameNotFoundException {

        PlayerModel player= playerRepository.findByUsername(username);
        if(player!=null){
            return player;
        }
        throw new UsernameNotFoundException("User not found");
    }
}

}

