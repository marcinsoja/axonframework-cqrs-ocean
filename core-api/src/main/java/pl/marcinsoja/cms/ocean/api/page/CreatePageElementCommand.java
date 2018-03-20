package pl.marcinsoja.cms.ocean.api.page;

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
public class CreatePageElementCommand {
    @NotNull
    @TargetAggregateIdentifier
    private final UUID pageId;

    @NotNull
    private final UUID elementId;
    @NotNull
    private final UUID contentId;
    @NotBlank
    private final String contentType;
    @NotNull
    private final Integer order;
}
