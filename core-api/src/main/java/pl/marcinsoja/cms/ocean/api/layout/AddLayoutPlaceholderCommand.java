package pl.marcinsoja.cms.ocean.api.layout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.axonframework.commandhandling.model.EntityId;

import java.util.UUID;

@Value
@Builder
@AllArgsConstructor
public class AddLayoutPlaceholderCommand {
    @TargetAggregateIdentifier
    private final UUID layoutId;
    @EntityId
    private final UUID placeholderId;
    private final String name;
    private final Integer order;
}
