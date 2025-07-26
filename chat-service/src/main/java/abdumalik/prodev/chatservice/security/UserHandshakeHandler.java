package abdumalik.prodev.chatservice.security;

import com.sun.security.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserHandshakeHandler extends DefaultHandshakeHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        return Optional.ofNullable(request.getURI().getQuery())
                .map(query -> query.split("&"))
                .map(queryParams -> {
                    for (String param : queryParams) {
                        if (param.startsWith("token=")) {
                            String token = param.substring(6);
                            if (jwtTokenProvider.isTokenValid(token)) {
                                String username = jwtTokenProvider.getUsernameFromJWT(token);
                                log.info("Authenticated WebSocket user: {}", username);
                                // The name of the principal will be the username (or user ID from JWT subject)
                                return new UsernamePasswordAuthenticationToken(username, null);
                            }
                        }
                    }
                    return null;
                })
                .map(auth -> (Principal) auth.getPrincipal())
                .orElse(null);
    }

}