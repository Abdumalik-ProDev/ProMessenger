package abdumalik.prodev.postservice.mapper;

import abdumalik.prodev.postservice.dto.PostResponse;
import abdumalik.prodev.postservice.model.Hashtag;
import abdumalik.prodev.postservice.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public class PostMapper {

    @Mapping(source = "hashtags", target = "hashtags", qualifiedByName = "hashtagsToStrings")
    public PostResponse toPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .userId(post.getUserId())
                .type(post.getType())
                .caption(post.getCaption())
                .mediaUrl(post.getMediaUrl())
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Named("hashtagsToString")
    public Set<String> hashtagsToString(Set<Hashtag> hashtags) {
        if (hashtags == null) {
            return Set.of();
        }
        return hashtags.stream()
                .map(Hashtag::getName)
                .collect(Collectors.toSet());
    }

}