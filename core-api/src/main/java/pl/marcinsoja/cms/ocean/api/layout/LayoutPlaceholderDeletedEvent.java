package pl.marcinsoja.cms.ocean.api.layout;

import lombok.Value;

import java.util.UUID;

@Value
public class LayoutPlaceholderDeletedEvent {
    private final UUID layoutId;
    private final UUID placeholderId;
}
