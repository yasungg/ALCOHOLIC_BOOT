package startline.server.user;

import startline.server.constant.Authority;
import startline.server.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class MashilangUserDetails implements UserDetails {
    private final User user;

    public MashilangUserDetails(User user) { this.user = user; }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        if(user.getAuthorities().contains(Authority.ROLE_USER)) authorities.add(new SimpleGrantedAuthority(Authority.ROLE_USER.name()));

        if(user.getAuthorities().contains(Authority.ROLE_PRE)) authorities.add(new SimpleGrantedAuthority(Authority.ROLE_PRE.name()));

        if(user.getAuthorities().contains(Authority.ROLE_PRIVILEGED)) authorities.add(new SimpleGrantedAuthority(Authority.ROLE_PRIVILEGED.name()));

        if(user.getAuthorities().contains(Authority.ROLE_ADMIN)) authorities.add(new SimpleGrantedAuthority(Authority.ROLE_ADMIN.name()));

        return authorities;
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
        return user.isAccountNonLocked();
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
