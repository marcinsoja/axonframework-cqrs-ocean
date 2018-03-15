package pl.marcinsoja.cms.ocean.rest;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.marcinsoja.cms.ocean.api.page.CreatePageCommand;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/pages")
@AllArgsConstructor
public class PageController {
    private final CommandGateway commandGateway;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ObjectId create(@RequestBody @NotNull @Valid CreatePageRequest request) {
        UUID id = Optional.ofNullable(request.getPageId()).map(UUID::fromString).orElse(UUID.randomUUID());
        commandGateway.sendAndWait(new CreatePageCommand(id, request.getSlug(), request.getTitle(), request.getContent()));
        return new ObjectId(id);
    }

    @Value
    @AllArgsConstructor
    public static class ObjectId {
        private final UUID id;
    }
}
