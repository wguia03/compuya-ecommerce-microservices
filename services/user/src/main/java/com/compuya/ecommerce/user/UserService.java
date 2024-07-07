package com.compuya.ecommerce.user;

import com.compuya.ecommerce.exception.UserNotFoundException;
import com.compuya.ecommerce.security.JwtUtils;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;

    public String register(UserRequest request) {

        if (request == null) {
            return null;
        }

        List<RoleEnum> roleEnumList = request.roleList().stream()
                .map(item -> RoleEnum.valueOf(item.toUpperCase()))
                .toList();

        Set<Role> roleEntityList = new HashSet<>(roleRepository.findRolesByRoleEnumIn(roleEnumList));

        if (roleEntityList.isEmpty()) {
            throw new IllegalArgumentException("The roles specified does not exist.");
        }

        var user = UserEntity.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .roles(roleEntityList)
                .build();

        this.userRepository.save(user);

        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        SecurityContext securityContextHolder = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null, authorities);

        return jwtUtils.createToken(authentication);
    }

    public String login(LoginRequest authLoginRequest) {

        String username = authLoginRequest.username();
        UserEntity userEntity = userRepository.findUserEntityByUsername(username).orElseThrow(() ->
                new RuntimeException("Username is not available"));

        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtUtils.createToken(authentication);
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }

        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }

    public void updateCustomer(UserUpdateRequest request) {
        var user = this.userRepository.findById(request.id())
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("Cannot update user:: No user found with the provided ID: %d", request.id())
                ));
        mergeCustomer(user, request);
        this.userRepository.save(user);
    }

    private void mergeCustomer(UserEntity user, UserUpdateRequest request) {
        if (StringUtils.isNotBlank(request.username())) {
            user.setUsername(request.username());
        }
        if (StringUtils.isNotBlank(request.password())) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }
    }

    public List<UserResponse> findAllCustomers() {
        return this.userRepository.findAll()
                .stream()
                .map(this.mapper::fromCustomer)
                .collect(Collectors.toList());
    }

    public UserResponse findById(Integer id) {
        return this.userRepository.findById(id)
                .map(mapper::fromCustomer)
                .orElseThrow(() -> new UserNotFoundException(String.format("No user found with the provided ID: %d", id)));
    }

    public boolean existsById(Integer id) {
        return this.userRepository.findById(id)
                .isPresent();
    }

    public void deleteCustomer(Integer id) {
        UserEntity user = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("No user found with the provided ID: %d", id)));

        user.getRoles().clear();
        userRepository.delete(user);
    }
}
