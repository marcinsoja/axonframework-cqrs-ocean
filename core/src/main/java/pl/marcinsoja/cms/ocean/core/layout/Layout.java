package pl.marcinsoja.cms.ocean.core.layout;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.commandhandling.model.AggregateMember;
import org.axonframework.commandhandling.model.ForwardMatchingInstances;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;
import pl.marcinsoja.cms.ocean.api.layout.AddLayoutPlaceholderCommand;
import pl.marcinsoja.cms.ocean.api.layout.CreateLayoutCommand;
import pl.marcinsoja.cms.ocean.api.layout.DeleteLayoutPlaceholderCommand;
import pl.marcinsoja.cms.ocean.api.layout.LayoutCreatedEvent;
import pl.marcinsoja.cms.ocean.api.layout.LayoutPlaceholderCreatedEvent;
import pl.marcinsoja.cms.ocean.api.layout.LayoutPlaceholderDeletedEvent;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

@EqualsAndHashCode(of = "id")
@Aggregate
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Layout {
    @AggregateIdentifier
    private UUID id;
    private String name;

    @AggregateMember(eventForwardingMode = ForwardMatchingInstances.class)
    private SortedSet<Placeholder> placeholders = new TreeSet<>(Comparator.comparing(Placeholder::getOrder));

    @CommandHandler
    public Layout(CreateLayoutCommand command) {
        AggregateLifecycle.apply(new LayoutCreatedEvent(command.getLayoutId(), command.getName()));
    }

    @CommandHandler
    public void command(AddLayoutPlaceholderCommand command) {
        AggregateLifecycle.apply(new LayoutPlaceholderCreatedEvent(command.getLayoutId(), command.getPlaceholderId(), command.getName(), command.getOrder()));
    }

    @CommandHandler
    public void command(DeleteLayoutPlaceholderCommand command) {
        Assert.isTrue(this.placeholders.stream().anyMatch(placeholder -> placeholder.getPlaceholderId().equals(command.getPlaceholderId())), "");
        AggregateLifecycle.apply(new LayoutPlaceholderDeletedEvent(command.getLayoutId(), command.getPlaceholderId()));
    }

    @EventSourcingHandler
    public void on(LayoutCreatedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
    }

    @EventSourcingHandler
    public void on(LayoutPlaceholderCreatedEvent event) {
        this.placeholders.add(Placeholder.builder()
                                         .name(event.getName())
                                         .order(event.getOrder())
                                         .placeholderId(event.getPlaceholderId())
                                         .build());
    }

    @EventSourcingHandler
    public void on(LayoutPlaceholderDeletedEvent event) {
        this.placeholders.forEach(placeholder -> {
            if(placeholder.getPlaceholderId().equals(event.getPlaceholderId())) {
                this.placeholders.remove(placeholder);
            }
        });
    }
}
