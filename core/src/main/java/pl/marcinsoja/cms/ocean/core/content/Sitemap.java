package pl.marcinsoja.cms.ocean.core.content;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Getter
@EqualsAndHashCode(of = "id")
@Aggregate
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Sitemap {

    public static final String CONTENT_TYPE = "sitemap.v1";

    @AggregateIdentifier
    private UUID id;
    private String name;
    private UUID rootPageId;
}
