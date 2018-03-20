package pl.marcinsoja.cms.ocean.rest.page;

import com.google.common.base.MoreObjects;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.marcinsoja.cms.ocean.api.ObjectId;
import pl.marcinsoja.cms.ocean.api.page.CreatePageCommand;
import pl.marcinsoja.cms.ocean.api.page.CreatePageElementCommand;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/pages")
@AllArgsConstructor
public class PageController {
    private final CommandGateway commandGateway;

    @PostMapping(consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(CREATED)
    public ObjectId create(@RequestBody @NotNull @Valid CreatePageRequest request) {
        UUID id = MoreObjects.firstNonNull(request.getPageId(), UUID.randomUUID());
        commandGateway.sendAndWait(new CreatePageCommand(id, request.getLayoutId(), request.getSlug(), request.getName()));
        return new ObjectId(id);
    }

    @PostMapping(path = "/{pageId}/elements", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(CREATED)
    public ObjectId createPageElement(
            @PathVariable("pageId") @NotNull UUID pageId,
            @RequestBody @NotNull @Valid CreatePageElementRequest request) {
        UUID id = MoreObjects.firstNonNull(request.getElementId(), UUID.randomUUID());
        commandGateway.sendAndWait(new CreatePageElementCommand(pageId, id, request.getContentId(), request.getContentType(), request.getOrder()));
        return new ObjectId(id);
    }
}
