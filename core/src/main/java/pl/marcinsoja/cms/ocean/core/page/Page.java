package pl.marcinsoja.cms.ocean.core.page;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.commandhandling.model.AggregateMember;
import org.axonframework.commandhandling.model.ForwardMatchingInstances;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import pl.marcinsoja.cms.ocean.api.page.CreatePageCommand;
import pl.marcinsoja.cms.ocean.api.page.CreatePageElementCommand;
import pl.marcinsoja.cms.ocean.api.page.PageCreatedEvent;
import pl.marcinsoja.cms.ocean.api.page.PageElementCreatedEvent;

import java.time.Instant;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

@Getter
@EqualsAndHashCode(of = "id")
@ToString
@Aggregate
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class Page {

    @AggregateIdentifier
    private UUID id;
    private String slug;
    private String name;
    private UUID layoutId;
    private Instant createdAt;

    @AggregateMember(eventForwardingMode = ForwardMatchingInstances.class)
    private SortedSet<Element> elements = new TreeSet<>(Comparator.comparing(Element::getOrder));

    public Page(CreatePageCommand command, Instant at) {
        AggregateLifecycle.apply(new PageCreatedEvent(command.getPageId(), command.getLayoutId(), command.getSlug(), command.getName(), at));
    }

    public void command(CreatePageElementCommand command, Instant at) {
        AggregateLifecycle.apply(new PageElementCreatedEvent(command.getPageId(), command.getElementId(), command.getContentId(), command.getContentType(), command.getOrder(), at));
    }

    @EventSourcingHandler
    public void on(PageCreatedEvent event) {
        log.debug("EventSourcingHandler {self: {}, event: {}}", this, event);
        this.id = event.getId();
        this.layoutId = event.getLayoutId();
        this.name = event.getName();
        this.slug = event.getSlug();
        this.createdAt = event.getAt();
    }

    @EventSourcingHandler
    public void on(PageElementCreatedEvent event) {
        log.debug("EventSourcingHandler {self: {}, event: {}}", this, event);
        this.elements.add(Element.builder()
                                 .elementId(event.getElementId())
                                 .contentId(event.getContentId())
                                 .contentType(event.getContentType())
                                 .order(event.getOrder())
                                 .build());
    }

}
