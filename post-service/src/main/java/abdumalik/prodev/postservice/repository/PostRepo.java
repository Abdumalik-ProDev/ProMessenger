package abdumalik.prodev.postservice.repository;

import abdumalik.prodev.postservice.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepo extends JpaRepository<Post, UUID> {
    List<Post> findByUserId(UUID userId);
}