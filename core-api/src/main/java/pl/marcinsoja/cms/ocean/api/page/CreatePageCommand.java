package pl.marcinsoja.cms.ocean.api.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
@Builder
@AllArgsConstructor
public class CreatePageCommand {
    @NotNull
    private final UUID pageId;

    @NotNull
    private final UUID layoutId;
    @NotBlank
    private final String slug;
    @NotBlank
    private final String name;
}
