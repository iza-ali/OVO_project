package com.ovo.app.ovo.services;

import com.ovo.app.ovo.enums.PlayerTypeEnum;
import com.ovo.app.ovo.models.PlayerModel;
import com.ovo.app.ovo.repositories.PlayerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class PlayerServiceTest {
    @MockBean
    private PlayerRepository playerRepository;
    @InjectMocks
    private PlayerService playerService;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @Mock
    private Model model;

    @Test
    public void PlayerService_updatePasswordHelper_CorrectOldPassword() throws Exception {
        String Username = "Username";
        String OldPassword = "OldPassword";
        String NewPassword = "NewPassword";
        PlayerModel player = new PlayerModel();

        player.setUsername(Username);
        player.setPassword(OldPassword);
        player.setEmail("Player@ovo.com");
        player.setPlayerId("id1237");
        player.setType(PlayerTypeEnum.PLAYER);

        Mockito.when(playerRepository.findByUsername(Username)).thenReturn(player);
        Mockito.when(passwordEncoder.matches(player.getPassword(), OldPassword)).thenReturn(true);
        Mockito.when(passwordEncoder.encode(NewPassword)).thenReturn(NewPassword);

        playerService.updatePasswordHelper(Username,OldPassword,NewPassword);

        Assertions.assertEquals(NewPassword, player.getPassword());
    }

    @Test
    public void PlayerService_updatePasswordHelper_IncorrectOldPassword() throws Exception {
        String Username = "Username";
        String OldPassword = "Password";
        String NewPassword = "NewPassword";
        PlayerModel player = new PlayerModel();

        player.setUsername(Username);
        player.setPassword("OldPassword");
        player.setEmail("Player@ovo.com");
        player.setPlayerId("id1237");
        player.setType(PlayerTypeEnum.PLAYER);

        Mockito.when(playerRepository.findByUsername(Username)).thenReturn(player);
        Mockito.when(passwordEncoder.matches(OldPassword, player.getPassword())).thenReturn(false);
        Mockito.when(passwordEncoder.encode(NewPassword)).thenReturn(NewPassword);

        assertThrows(Exception.class, () -> playerService.updatePasswordHelper(Username,OldPassword,NewPassword));
    }

    @Test
    public void PlayerService_updatePasswordHelper_NoUser(){
        String Username = "Username";
        String OldPassword = "OldPassword";
        String NewPassword = "NewPassword";

        Mockito.when(playerRepository.findByUsername(Username)).thenReturn(null);
        assertThrows(Exception.class, () -> playerService.updatePasswordHelper(Username,OldPassword,NewPassword));


    }

//    @Test
//    @WithMockUser(username = "my_principal")
//    public void PlayerService_updatePasswordPasswordsDontMatch() throws Exception {
//        String CurrentPassword = "OldPassword";
//        String NewPassword = "NewPassword";
//        String NewConfirmPassword = "NewConfirmPassword";
//        Principal principal = () -> "my_principal";
//
//        assertDoesNotThrow(() -> playerService.updatePasswordHelper(CurrentPassword,NewPassword,NewConfirmPassword,principal,model));
//        verify(model).addAttribute("error", "Passwords do not match");
//    }
//
//    @Test
//    @WithMockUser(username = "my_principal")
//    public void PlayerService_updatePasswordPasswordsMatch() throws Exception {
//        String CurrentPassword = "OldPassword";
//        String NewPassword = "NewPassword";
//        String NewConfirmPassword = "NewPassword";
//        Principal principal = () -> "my_principal";
//
//        assertDoesNotThrow(() -> playerService.updatePassword(CurrentPassword,NewPassword,NewConfirmPassword,principal,model));
//        verify(model,never()).addAttribute("error", "Passwords do not match");
//        verify(model).addAttribute("message", "Password updated successfully");
//
//    }
//
//    @Test
//    @WithMockUser(username = "Username")
//    public void PlayerService_updatePasswordOldPasswordsNotMatch() throws Exception {
//        String currentPassword = "OldOtherPassword";
//        String NewPassword = "NewPassword";
//        String NewConfirmPassword = "NewPassword";
//        String Username = "Username";
//        Principal principal = () -> "Username";
//
//        PlayerModel player = new PlayerModel();
//        player.setUsername("Username");
//        player.setPassword("OldPassword");
//        player.setEmail("Player@ovo.com");
//        player.setPlayerId("id1237");
//        player.setType(PlayerTypeEnum.PLAYER);
//
//        Mockito.when(playerRepository.findByUsername(Username)).thenReturn(player);
//        Mockito.when(passwordEncoder.matches(currentPassword, player.getPassword())).thenReturn(false);
//
//        assertThrows(Exception.class, () -> playerService.updatePassword(currentPassword,NewPassword,NewConfirmPassword,principal,model));
//
//    }
}