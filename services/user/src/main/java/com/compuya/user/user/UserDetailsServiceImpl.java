package com.compuya.user.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        UserEntity user = userEntityRepository.findUserEntityByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username is not available"));
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        user.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        return new User(user.getUsername(), user.getPassword(), authorityList);
    }
}
