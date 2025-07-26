package abdumalik.prodev.postservice.dto;

import abdumalik.prodev.postservice.model.entity.PostType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostCreateRequest {

    @Column
    private String caption;

    @NotBlank
    private String mediaUrl;

    @NotNull
    private PostType type;

}