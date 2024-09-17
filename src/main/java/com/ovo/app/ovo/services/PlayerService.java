package com.ovo.app.ovo.services;


import com.ovo.app.ovo.models.PlayerModel;
import com.ovo.app.ovo.repositories.PlayerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PlayerService implements UserDetailsService {


    private final PlayerRepository playerRepository;

    public PlayerService( PlayerRepository playerRepository) {

        this.playerRepository = playerRepository;
    }

    public PlayerModel getUserByUsername(String username) throws UsernameNotFoundException {

        PlayerModel player= playerRepository.findByUsername(username);
        if(player!=null){
            return player;
        }
        throw new UsernameNotFoundException("User not found");
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        PlayerModel player= playerRepository.findByUsername(username);
        if(player!=null){
            return User.withUsername(player.getUsername()).password(player.getPassword()).build();
        }
        throw new UsernameNotFoundException("User not found");
    }

}
