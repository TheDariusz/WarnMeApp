package com.thedariusz.warnme.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

public class UserDetailsSecurityService implements UserDetailsService {

    private UserDao dao;

    @Autowired
    public void setUserRepository(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return dao.findByUserName(username)
                .map(this::mapToUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    private UserDetails mapToUserDetails(UserDto userDto) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        userDto.getRoles().forEach(
                role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()))
        );
        return new User(userDto.getUsername(), userDto.getPassword(), grantedAuthorities);
    }
}
