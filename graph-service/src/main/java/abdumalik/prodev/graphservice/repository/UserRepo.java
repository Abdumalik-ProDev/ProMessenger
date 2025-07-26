package abdumalik.prodev.graphservice.repository;

import abdumalik.prodev.graphservice.model.UserNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends Neo4jRepository<UserNode, UUID> {

    Optional<UserNode> findByUserId(UUID userId);

    // Custom Cypher query to create the FOLLOWS relationship
    @Query("MATCH (u:User {userId: $userId}), (f:User {userId: $followId}) " +
            "MERGE (u)-[:FOLLOWS]->(f)")
    void follow(@Param("userId") UUID userId, @Param("followId") UUID followId);

    // Custom Cypher query to remove the FOLLOWS relationship
    @Query("MATCH (u:User {userId: $userId})-[r:FOLLOWS]->(f:User {userId: $followId}) " +
            "DELETE r")
    void unfollow(@Param("userId") UUID userId, @Param("followId") UUID followId);

    // Get the list of users that a specific user is following
    @Query("MATCH (:User {userId: $userId})-[:FOLLOWS]->(following:User) RETURN following")
    List<UserNode> findFollowing(@Param("userId") UUID userId);

    // Get the list of users who follow a specific user
    @Query("MATCH (follower:User)-[:FOLLOWS]->(:User {userId: $userId}) RETURN follower")
    List<UserNode> findFollowers(@Param("userId") UUID userId);

}