package abdumalik.prodev.searchservice.repository;

import abdumalik.prodev.searchservice.model.PostDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostSearchRepo extends ElasticsearchRepository<PostDocument, UUID> {
    List<PostDocument> findByCaptionContainingOrHashtagsContaining(String caption, String hashtag);
}