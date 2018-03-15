package pl.marcinsoja.cms.ocean.api.layout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.UUID;

@Value
@Builder
@AllArgsConstructor
public class DeleteLayoutPlaceholderCommand {
    @TargetAggregateIdentifier
    private final UUID layoutId;
    private final UUID placeholderId;
}
