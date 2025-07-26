package abdumalik.prodev.graphservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Node("User")
@Data
@NoArgsConstructor
public class UserNode {

    @Id
    private UUID userId;

    @Property("username")
    private String username;

    @Relationship(type = "FOLLOWS", direction = Relationship.Direction.OUTGOING)
    private Set<UserNode> following = new HashSet<>();

    @Relationship(type = "FOLLOWS", direction = Relationship.Direction.INCOMING)
    private Set<UserNode> followers = new HashSet<>();

    public UserNode(UUID userId, String username) {
        this.userId = userId;
        this.username = username;
    }

}