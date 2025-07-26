package abdumalik.prodev.feedservice.repository;

import abdumalik.prodev.feedservice.model.UserFeed;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserFeedRepo extends MongoRepository<UserFeed, UUID> {
}