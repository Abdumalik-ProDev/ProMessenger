package abdumalik.prodev.userservice.controller;

import abdumalik.prodev.userservice.dto.UserDto;
import abdumalik.prodev.userservice.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "APIs for fetching user profiles")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public HttpEntity<UserDto> getUserByUsername(@PathVariable String username) {
        return new ResponseEntity<>(userService.findUserByUsername(username), HttpStatus.OK);
    }

    // This endpoint is added to satisfy the Feign client in the Feed Service.
    // In a real system, this would either be removed (and the Feed Service would call the Graph Service)
    // or it would make its own call to the Graph Service.
    @GetMapping("/{userId}/followers")
    public HttpEntity<List<Long>> getFollowers(@PathVariable UUID userId) {
        // Returning empty list as Graph Service is the source of truth for this data.
        return ResponseEntity.ok(Collections.emptyList());
    }

}