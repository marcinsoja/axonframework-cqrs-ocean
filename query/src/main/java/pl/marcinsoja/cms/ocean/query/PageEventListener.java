package pl.marcinsoja.cms.ocean.query;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import pl.marcinsoja.cms.ocean.api.page.PageCreatedEvent;

@Component
@RequiredArgsConstructor
@Slf4j
@ProcessingGroup("query")
public class PageEventListener {
    private final PageRepository repository;

    @EventHandler
    public void on(PageCreatedEvent event) {
        log.trace("Handling event {event: {}}", event);
        repository.save(PageEntry.builder()
                                 .pageId(event.getId())
                                 .title(event.getTitle())
                                 .slug(event.getSlug())
                                 .content(event.getContent())
                                 .build());
        log.debug("Handled event {event: {}}", event);
    }
}
