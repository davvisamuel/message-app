package com.davvi.message_app.domain;

import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.equals(UserRole.ROLE_ADMIN) ?
                List.of(new SimpleGrantedAuthority(role.name()), new SimpleGrantedAuthority(UserRole.ROLE_USER.name()))
                :
                List.of(new SimpleGrantedAuthority(UserRole.ROLE_USER.name()));
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }
}
