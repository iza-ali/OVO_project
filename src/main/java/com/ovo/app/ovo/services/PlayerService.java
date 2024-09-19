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

        if(player==null){
            throw new UsernameNotFoundException("User not found");
        }else {

            if (!passwordEncoder.matches(currentPassword, player.getPassword())) {
                throw new Exception("You need a new password");
            }

            player.setPassword(passwordEncoder.encode(newPassword));
            playerRepository.save(player);
        }
    }

    public void updateUsername(String currentUsername, String newUsername) throws Exception {

        PlayerModel player = playerRepository.findByUsername(currentUsername);

        if(player==null){
            throw new UsernameNotFoundException("User not found");
        }else {

            if (currentUsername.equals(newUsername)) {
                throw new Exception("You need a new username");
            }

            player.setUsername(newUsername);
            playerRepository.save(player);
        }
    }

    public void updateEmail(String currentUsername, String newEmail) throws Exception {

        PlayerModel player = playerRepository.findByUsername(currentUsername);

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

    public void deleteUser(String username) throws Exception {

        PlayerModel player = playerRepository.findByUsername(username);

        if(player==null){
            throw new UsernameNotFoundException("User not found");
        }else {
            playerRepository.delete(player);
        }
    }
}
