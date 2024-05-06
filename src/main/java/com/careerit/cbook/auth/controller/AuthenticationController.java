package com.careerit.cbook.auth.controller;



import com.careerit.cbook.auth.domain.AppUser;
import com.careerit.cbook.auth.dto.JwtTokenResponse;
import com.careerit.cbook.auth.dto.LoginUserDto;
import com.careerit.cbook.auth.dto.RegisterUserDto;
import com.careerit.cbook.auth.service.AuthenticationService;
import com.careerit.cbook.auth.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    @PostMapping("/signup")
    public ResponseEntity<AppUser> signup(@RequestBody RegisterUserDto registerUserDto) {
        AppUser response = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenResponse> loginUser(@RequestBody LoginUserDto loginUserDto) {
        AppUser authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        JwtTokenResponse loginResponse = new JwtTokenResponse();
        loginResponse.setToken(jwtToken);
        return ResponseEntity.ok(loginResponse);
    }
}