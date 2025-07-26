package abdumalik.prodev.searchservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.UUID;

@Document(indexName = "users")
@Data
public class UserDocument {

    @Id
    private UUID userId;

    @Field(type = FieldType.Text, name = "username")
    private String username;

    @Field(type = FieldType.Text, name = "fullName")
    private String fullName;

}