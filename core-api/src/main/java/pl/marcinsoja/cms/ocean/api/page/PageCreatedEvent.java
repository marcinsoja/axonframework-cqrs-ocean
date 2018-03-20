package pl.marcinsoja.cms.ocean.api.page;

import lombok.Value;

import java.util.UUID;

@Value
public class PageCreatedEvent {
    private final UUID id;
    private final UUID layoutId;
    private final String slug;
    private final String name;
}
