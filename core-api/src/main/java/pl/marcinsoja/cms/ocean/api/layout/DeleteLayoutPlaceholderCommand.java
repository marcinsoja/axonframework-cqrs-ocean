package pl.marcinsoja.cms.ocean.api.layout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
@Builder
@AllArgsConstructor
public class DeleteLayoutPlaceholderCommand {
    @NotNull
    @TargetAggregateIdentifier
    private final UUID layoutId;

    @NotNull
    private final UUID placeholderId;
}
