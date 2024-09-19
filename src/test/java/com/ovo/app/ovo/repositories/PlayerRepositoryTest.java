package com.ovo.app.ovo.repositories;

import com.ovo.app.ovo.enums.PlayerTypeEnum;
import com.ovo.app.ovo.models.PlayerModel;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class PlayerRepositoryTest {
    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void PlayerRepository_Save_NewUser() {
        PlayerModel player = new PlayerModel();
        player.setUsername("truePlayEr");
        player.setPassword("password");
        player.setEmail("truePlayEr@ovo.com");
        player.setPlayerId("id123");
        player.setType(PlayerTypeEnum.PLAYER);

        PlayerModel savedPlayer = playerRepository.save(player);

        Assertions.assertNotNull(savedPlayer);
        Assertions.assertEquals(savedPlayer.getUsername(), "truePlayEr");
        Assertions.assertEquals(savedPlayer.getPassword(), "password");
        Assertions.assertEquals(savedPlayer.getEmail(), "truePlayEr@ovo.com");
    }

    @Test
    public void PlayerRepository_FindByUsername_FoundUser() {
        PlayerModel player = new PlayerModel();
        player.setUsername("newPlayEr");
        player.setPassword("password1");
        player.setEmail("NewPlayer@ovo.com");
        player.setPlayerId("id1234");
        player.setType(PlayerTypeEnum.PLAYER);

        playerRepository.save(player);

        PlayerModel foundPlayer = playerRepository.findByUsername("newPlayEr");

        Assertions.assertNotNull(foundPlayer);
        Assertions.assertEquals(foundPlayer.getUsername(), "newPlayEr");
        Assertions.assertEquals(foundPlayer.getPassword(), "password1");
    }

    @Test
    public void PlayerRepository_FindByUsername_NotFoundUser() {
        PlayerModel player = new PlayerModel();
        player.setUsername("newPlayEr");
        player.setPassword("password1");
        player.setEmail("NewPlayer@ovo.com");
        player.setPlayerId("id1235");
        player.setType(PlayerTypeEnum.PLAYER);

        playerRepository.save(player);

        PlayerModel foundPlayer = playerRepository.findByUsername("newPlayEr1");

        Assertions.assertNull(foundPlayer);
    }

    @Test
    public void PlayerRepository_FindByEmail_Found() {
        PlayerModel player = new PlayerModel();
        player.setUsername("EmailName");
        player.setPassword("password1");
        player.setEmail("NewPlayer1234@ovo.com");
        player.setPlayerId("id1236");
        player.setType(PlayerTypeEnum.PLAYER);

        playerRepository.save(player);

        PlayerModel foundPlayer = playerRepository.findByEmail("NewPlayer1234@ovo.com");

        Assertions.assertNotNull(foundPlayer);
        Assertions.assertEquals(foundPlayer.getUsername(), "EmailName");
        Assertions.assertEquals(foundPlayer.getPassword(), "password1");
    }

    @Test
    public void PlayerRepository_FindByEmail_NotFound() {
        PlayerModel player = new PlayerModel();
        player.setUsername("newPlayEr3");
        player.setPassword("password1");
        player.setEmail("NewPlayer555@ovo.com");
        player.setPlayerId("id1237");
        player.setType(PlayerTypeEnum.PLAYER);

        playerRepository.save(player);

        PlayerModel foundPlayer = playerRepository.findByEmail("newPlayEr1");

        Assertions.assertNull(foundPlayer);
    }

}