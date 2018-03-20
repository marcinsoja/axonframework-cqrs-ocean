package pl.marcinsoja.cms.ocean.core.page;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.axonframework.commandhandling.model.EntityId;

import java.util.UUID;

@Data
@Builder
@EqualsAndHashCode(of = "contentId")
public class Element {
    @EntityId
    private UUID elementId;
    private UUID contentId;
    private String contentType;
    private Integer order;
}
