package com.capstone.onlineBookstore.security;

import com.capstone.onlineBookstore.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * The type App user details.
 */
public class AppUserDetails implements UserDetails {
    private final User user;

    /**
     * Instantiates a new App user details.
     *
     * @param user the user
     */
    public AppUserDetails(User user) { this.user = user; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of();
    }

    @Override
    public String getPassword() {
        return user.getHashedPassword();
    }

    @Override
    public String getUsername() {
        // Use email as username
        return user.getEmail();
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() { return user.getId(); }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() { return user.getFirstName(); }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() { return user.getLastName(); }
}
