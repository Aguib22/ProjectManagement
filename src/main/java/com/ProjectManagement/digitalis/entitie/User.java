package com.ProjectManagement.digitalis.entitie;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Column(unique = true, length = 32, nullable = false)
    private Long matriculeUser;
    private String prenomUser;
    private String nomUser;
    private int numeroUser;

    @Column(unique = true, length = 100, nullable = false)
    private String mailUser;

    @Column(nullable = false)
    private String password;



    private String passwordResetToken;

    private LocalDateTime expirePasswordResetToken;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "dev")
    @JsonIgnore
    private List<SousTache> listSt_dev;

    @OneToMany(mappedBy = "testeur")
    @JsonIgnore
    private List<SousTache> listSt_testeur;

    @ManyToOne
    @JoinColumn(name = "reunion",nullable = true)
    private Reunion reunion;

    @ManyToOne
    @JoinColumn(name = "idUserService", nullable = false)
    @JsonIgnore
    private UserService userService;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return getMailUser();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
