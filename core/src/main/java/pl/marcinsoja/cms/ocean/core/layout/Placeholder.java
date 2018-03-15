package pl.marcinsoja.cms.ocean.core.layout;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.commandhandling.model.EntityId;
import org.axonframework.eventsourcing.EventSourcingHandler;
import pl.marcinsoja.cms.ocean.api.layout.LayoutPlaceholderUpdatedEvent;
import pl.marcinsoja.cms.ocean.api.layout.UpdateLayoutPlaceholderCommand;

import java.util.UUID;

@Data
@Builder
@EqualsAndHashCode(of = "placeholderId")
public class Placeholder {
    @EntityId
    private UUID placeholderId;
    private String name;
    private Integer order;

    @CommandHandler
    public void command(UpdateLayoutPlaceholderCommand command) {
        AggregateLifecycle.apply(new LayoutPlaceholderUpdatedEvent(command.getLayoutId(), command.getPlaceholderId(), command.getName(), command.getOrder()));
    }

    @EventSourcingHandler
    public void on(LayoutPlaceholderUpdatedEvent event) {
        this.name = event.getName();
        this.order = event.getOrder();
    }
}
