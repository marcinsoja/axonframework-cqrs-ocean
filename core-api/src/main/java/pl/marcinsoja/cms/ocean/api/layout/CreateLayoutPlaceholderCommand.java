package pl.marcinsoja.cms.ocean.api.layout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
@Builder
@AllArgsConstructor
public class CreateLayoutPlaceholderCommand {
    @NotNull
    @TargetAggregateIdentifier
    private final UUID layoutId;

    @NotNull
    private final UUID placeholderId;
    @NotBlank
    private final String name;
    @NotNull
    private final Integer order;
}
