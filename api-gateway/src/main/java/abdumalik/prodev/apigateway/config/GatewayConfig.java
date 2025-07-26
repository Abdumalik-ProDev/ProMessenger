package abdumalik.prodev.apigateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Configuration
public class GatewayConfig {

    /**
     * This bean defines how the rate limiter identifies a client.
     * Here, we are using the client's IP address as the key.
     * This ensures that each unique IP address gets its own token bucket.
     */
    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange ->
                Mono.just(Objects.requireNonNull(exchange.getRequest()
                        .getRemoteAddress()).getAddress().getHostAddress());
    }

}