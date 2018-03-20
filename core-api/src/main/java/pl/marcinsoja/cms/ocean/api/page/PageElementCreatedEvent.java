package pl.marcinsoja.cms.ocean.api.page;

import lombok.Value;

import java.util.UUID;

@Value
public class PageElementCreatedEvent {
    private final UUID pageId;
    private final UUID elementId;
    private final UUID contentId;
    private final String contentType;
    private final Integer order;
}
