package pl.marcinsoja.cms.ocean.api.layout;

import lombok.Value;

import java.util.UUID;

@Value
public class LayoutPlaceholderUpdatedEvent {
    private final UUID layoutId;
    private final UUID placeholderId;
    private final String name;
    private final Integer order;
}
