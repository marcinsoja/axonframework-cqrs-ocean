package pl.marcinsoja.cms.ocean.api.page;

import lombok.Value;
import org.axonframework.serialization.Revision;

import java.time.Instant;
import java.util.UUID;

@Value
@Revision("2.0")
public class PageCreatedEvent {
    private final UUID id;
    private final UUID layoutId;
    private final String slug;
    private final String name;
    private final Instant at;
}
