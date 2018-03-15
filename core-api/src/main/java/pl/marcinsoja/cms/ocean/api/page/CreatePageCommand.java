package pl.marcinsoja.cms.ocean.api.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.UUID;

@Value
@Builder
@AllArgsConstructor
public class CreatePageCommand {
    @TargetAggregateIdentifier
    private final UUID pageId;
    private final UUID layoutId;
    private final String slug;
    private final String title;
    private final String content;
}
