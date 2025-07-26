package abdumalik.prodev.postservice.repository;

import abdumalik.prodev.postservice.model.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HashtagRepo extends JpaRepository<Hashtag, UUID> {
    Optional<Hashtag> findByName(String name);
}