package abdumalik.prodev.feedservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

// Feign client to communicate with the User Service
// The name 'user-service' must match the spring.application.name in the User Service's config
@FeignClient(name = "user-service")
public interface UserServiceClient {

    // This endpoint needs to exist in the User Service to return followers
    @GetMapping("/api/users/{userId}/followers")
    List<UUID> getFollowers(@PathVariable("userId") UUID userId);

}