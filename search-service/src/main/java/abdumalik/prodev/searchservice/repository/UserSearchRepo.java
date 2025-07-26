package abdumalik.prodev.searchservice.repository;

import abdumalik.prodev.searchservice.model.UserDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.UUID;

public interface UserSearchRepo extends ElasticsearchRepository<UserDocument, UUID> {
    List<UserDocument> findByUsernameContainingOrFullNameContaining(String username, String fullName);
}