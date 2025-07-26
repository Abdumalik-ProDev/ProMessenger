package abdumalik.prodev.searchservice.service;

import abdumalik.prodev.searchservice.model.PostDocument;
import abdumalik.prodev.searchservice.model.UserDocument;
import abdumalik.prodev.searchservice.repository.PostSearchRepo;
import abdumalik.prodev.searchservice.repository.UserSearchRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final UserSearchRepo userSearchRepository;
    private final PostSearchRepo postSearchRepository;

    public Map<String, Object> searchAll(String query) {
        List<UserDocument> users = userSearchRepository.findByUsernameContainingOrFullNameContaining(query, query);
        List<PostDocument> posts = postSearchRepository.findByCaptionContainingOrHashtagsContaining(query, query);

        Map<String, Object> results = new HashMap<>();
        results.put("users", users);
        results.put("posts", posts);
        return results;
    }

}