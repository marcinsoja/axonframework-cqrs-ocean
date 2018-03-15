package pl.marcinsoja.cms.ocean.api.layout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotBlank;

import java.util.UUID;

@Value
@Builder
@AllArgsConstructor
public class CreateLayoutCommand {
    @TargetAggregateIdentifier
    private final UUID layoutId;
    @NotBlank
    private final String name;
}
