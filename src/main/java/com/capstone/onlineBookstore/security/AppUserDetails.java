package com.capstone.onlineBookstore.security;

import com.capstone.onlineBookstore.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AppUserDetails implements UserDetails {
    private final User user;

    public AppUserDetails(User user) { this.user = user; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user.getRole() == null || user.getRole().getRoleName() == null) {
            return List.of();
        }
        String roleName = user.getRole().getRoleName().trim();
        String authority = roleName.startsWith("ROLE_") ? roleName : "ROLE_" + roleName;

        return List.of(new SimpleGrantedAuthority(authority));
    }

    @Override public String getPassword() { return user.getHashedPassword(); }
    @Override public String getUsername() { return user.getEmail(); }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    public Long getId() { return user.getId(); }
    public String getFirstName() { return user.getFirstName(); }
    public String getLastName() { return user.getLastName(); }
}
