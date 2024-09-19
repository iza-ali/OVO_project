package com.ovo.app.ovo.models;

import com.ovo.app.ovo.enums.PlayerTypeEnum;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Optional;

@Entity
@Table(name = "players")
public class PlayerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_id", unique = true, nullable = false)
    private String playerId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PlayerTypeEnum type;

    public PlayerModel() {
        // Constructor
    }

    public PlayerModel(String username, String password, PlayerTypeEnum type, String email) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.email = email;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public PlayerTypeEnum getType() {
        return type;
    }

    public void setType(PlayerTypeEnum type) {
        this.type = type;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

}
