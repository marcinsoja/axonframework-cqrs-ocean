package pl.marcinsoja.cms.ocean.core.type;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.List;
import java.util.UUID;

@Aggregate
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContentType {

    @AggregateIdentifier
    private UUID id;

    private String name;

    private List<AttributeHolder> attributes;
}
