package abdumalik.prodev.searchservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.util.Set;
import java.util.UUID;

@Document(indexName = "posts")
@Data
public class PostDocument {

    @Id
    private UUID postId;

    @Field(type = FieldType.Text, name = "caption")
    private String caption;

    @Field(type = FieldType.Keyword, name = "hashtags")
    private Set<String> hashtags;

    @Field(name = "userId")
    private UUID userId;

}