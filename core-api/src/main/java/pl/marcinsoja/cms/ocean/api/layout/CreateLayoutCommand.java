package pl.marcinsoja.cms.ocean.api.layout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
@Builder
@AllArgsConstructor
public class CreateLayoutCommand {
    @NotNull
    private final UUID layoutId;

    @NotBlank
    private final String name;
}
