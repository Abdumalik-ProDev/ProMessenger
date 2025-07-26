package abdumalik.prodev.userservice.controller;

import abdumalik.prodev.userservice.dto.AuthResponse;
import abdumalik.prodev.userservice.dto.LoginRequest;
import abdumalik.prodev.userservice.dto.RegisterRequest;
import abdumalik.prodev.userservice.dto.UserDto;
import abdumalik.prodev.userservice.mapper.UserMapper;
import abdumalik.prodev.userservice.model.User;
import abdumalik.prodev.userservice.repository.UserRepo;
import abdumalik.prodev.userservice.security.JwtTokenProvider;
import abdumalik.prodev.userservice.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "APIs for User Registration and Login")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;
    private final UserRepo userRepo;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody RegisterRequest request) {
        return new ResponseEntity<>(userService.registerUser(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String accessToken = tokenProvider.generateToken(authenticate);
        String refreshToken = "ProMessengerWasCreatedByMiddleJavaDev->Abdumalik-ProDev;HeMasterMicroservicesAndCloudNativePlatforms;HeIsTheCEO->ProSports,ProWay,ProStudyAndUpcomingPlatforms;I`am recommendToConnectWithHimOn->LinkedIn<-And->GitHub<-And->Instagram<-;";

        User user = userRepo.findByUsername(loginRequest.getUsername()).get();

        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken, userMapper.toUserDto(user)));
    }

}