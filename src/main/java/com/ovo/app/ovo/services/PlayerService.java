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


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        PlayerModel player= playerRepository.findByEmail(email);
        if(player!=null){
            return User.withUsername(player.getEmail()).password(player.getPassword()).build();
        }
        throw new UsernameNotFoundException("User not found");
    }

}
