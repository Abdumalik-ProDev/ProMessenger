package abdumalik.prodev.postservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${app.kafka.topics.post-events}")
    private String postEventsTopic;

    @Bean
    public NewTopic postEventsTopic() {
        return TopicBuilder.name(postEventsTopic)
                .partitions(3) // A good starting point
                .replicas(2)   // In a real cluster, this should be >= 2
                .build();
    }

}