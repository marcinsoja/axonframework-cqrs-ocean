package pl.marcinsoja.cms.ocean.core.page;

import lombok.Getter;

import java.util.UUID;

@Getter
public class PageAlreadyExists extends RuntimeException {
    private final UUID pageId;
    public PageAlreadyExists(UUID pageId) {
        super(String.format("Page already exists {pageId: %s}", pageId));
        this.pageId = pageId;
    }
}
