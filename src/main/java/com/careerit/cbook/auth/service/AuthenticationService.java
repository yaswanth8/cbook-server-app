package com.careerit.cbook.auth.service;



import com.careerit.cbook.auth.domain.AppUser;
import com.careerit.cbook.auth.dto.LoginUserDto;
import com.careerit.cbook.auth.dto.RegisterUserDto;
import com.careerit.cbook.auth.repo.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AppUserRepository userRepository;

    public AppUser signup(RegisterUserDto input) {

        userRepository.findByUsername(input.getUsername())
                .ifPresent(user ->{
                    throw  new IllegalArgumentException("User already exists");
                });
        AppUser user = new AppUser();
        user.setUsername(input.getUsername());
        user.setEmail(input.getEmail());
        user.setMobile(input.getMobile());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        return userRepository.save(user);
    }

    public AppUser authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );
        return userRepository.findByUsername(input.getUsername())
                .orElseThrow(()->new UsernameNotFoundException("User or password incorrect"));
    }

}