package ALCOHOLIC_BOOT.server.user;

import ALCOHOLIC_BOOT.server.constant.Authority;
import ALCOHOLIC_BOOT.server.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;


public class MashilangUserDetails implements UserDetails {
    private final ALCOHOLIC_BOOT.server.entity.User user;

    public MashilangUserDetails(User user) { this.user = user; }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        switch(user.getAuthority()) {
            case ROLE_USER : GrantedAuthority userAuthority = new SimpleGrantedAuthority(Authority.ROLE_USER.name());
                return Collections.singleton(userAuthority);
            case ROLE_ADMIN : GrantedAuthority adminAuthority = new SimpleGrantedAuthority(Authority.ROLE_ADMIN.name());
                return Collections.singleton(adminAuthority);
        }

        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public String getNickname() {
        return user.getNickname();
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
        return user.isEnabled();
    }
}
