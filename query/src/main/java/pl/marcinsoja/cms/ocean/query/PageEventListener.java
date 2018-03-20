package pl.marcinsoja.cms.ocean.query;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import pl.marcinsoja.cms.ocean.api.page.PageCreatedEvent;
import pl.marcinsoja.cms.ocean.api.page.PageElementCreatedEvent;

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
                                 .id(event.getId())
                                 .layoutId(event.getLayoutId())
                                 .slug(event.getSlug())
                                 .name(event.getName())
                                 .build());
        log.debug("Handled event {event: {}}", event);
    }

    @EventHandler
    public void on(PageElementCreatedEvent event) {
        log.trace("Handling event {event: {}}", event);
        PageEntry pageEntry = repository.findOne(event.getPageId());
        pageEntry.getContents().add(PageEntryContent.builder()
                                                    .id(event.getElementId())
                                                    .contentId(event.getContentId())
                                                    .contentType(event.getContentType())
                                                    .build());
        repository.save(pageEntry);
        log.debug("Handled event {event: {}}", event);
    }
}
