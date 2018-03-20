package pl.marcinsoja.cms.ocean.rest.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePageRequest {
    private UUID pageId;

    @NotNull
    private UUID layoutId;
    @NotBlank
    private String slug;
    @NotBlank
    private String name;
}
