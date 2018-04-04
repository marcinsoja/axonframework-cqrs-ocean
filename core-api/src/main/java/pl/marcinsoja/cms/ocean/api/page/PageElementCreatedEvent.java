package pl.marcinsoja.cms.ocean.api.page;

import lombok.Value;
import org.axonframework.serialization.Revision;

import java.time.Instant;
import java.util.UUID;

@Value
@Revision("1.0")
public class PageElementCreatedEvent {
    private final UUID pageId;
    private final UUID elementId;
    private final UUID contentId;
    private final String contentType;
    private final Integer order;
    private final Instant at;
}
