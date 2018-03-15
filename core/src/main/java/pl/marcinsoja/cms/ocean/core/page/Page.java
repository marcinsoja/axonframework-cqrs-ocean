package pl.marcinsoja.cms.ocean.core.page;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import pl.marcinsoja.cms.ocean.api.page.CreatePageCommand;
import pl.marcinsoja.cms.ocean.api.page.PageCreatedEvent;

import java.util.UUID;

@EqualsAndHashCode(of = "id")
@Aggregate
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Page {

    @AggregateIdentifier
    private UUID id;

    private String slug;
    private String title;

    private UUID layoutId;

    @CommandHandler
    public Page(CreatePageCommand command) {
        AggregateLifecycle.apply(new PageCreatedEvent(command.getPageId(), command.getLayoutId(), command.getSlug(), command.getTitle(), command.getContent()));
    }

    @EventSourcingHandler
    public void on(PageCreatedEvent event) {
        this.id = event.getId();
        this.layoutId = event.getLayoutId();
        this.slug = event.getSlug();
        this.title = event.getTitle();
    }

}
