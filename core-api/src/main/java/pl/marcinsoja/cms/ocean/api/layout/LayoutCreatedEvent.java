package pl.marcinsoja.cms.ocean.api.layout;

import lombok.Value;

import java.util.UUID;

@Value
public class LayoutCreatedEvent {
    private final UUID id;
    private final String name;
}
