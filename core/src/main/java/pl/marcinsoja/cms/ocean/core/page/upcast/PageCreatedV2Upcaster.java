package pl.marcinsoja.cms.ocean.core.page.upcast;


import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.serialization.SimpleSerializedType;
import org.axonframework.serialization.upcasting.event.IntermediateEventRepresentation;
import org.axonframework.serialization.upcasting.event.SingleEventUpcaster;
import pl.marcinsoja.cms.ocean.api.page.PageCreatedEvent;

import java.time.Instant;

@Slf4j
public class PageCreatedV2Upcaster extends SingleEventUpcaster {

    private static SimpleSerializedType targetType = new SimpleSerializedType(PageCreatedEvent.class.getTypeName(), "1.0");

    @Override
    protected boolean canUpcast(IntermediateEventRepresentation intermediateRepresentation) {
        return intermediateRepresentation.getType().equals(targetType);
    }

    @Override
    protected IntermediateEventRepresentation doUpcast(IntermediateEventRepresentation intermediateRepresentation) {
        return intermediateRepresentation.upcastPayload(
                new SimpleSerializedType(targetType.getName(), "2.0"),
                com.fasterxml.jackson.databind.JsonNode.class,
                document -> {
                    log.debug("Converting event from 1.0 to 2.0 version");
                    ObjectNode node = (ObjectNode) document;
                    node.put("at", Instant.now().toString());
                    return document;
                }
        );
    }
}
