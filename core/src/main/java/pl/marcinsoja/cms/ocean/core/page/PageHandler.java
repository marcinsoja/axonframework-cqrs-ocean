package pl.marcinsoja.cms.ocean.core.page;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Aggregate;
import org.axonframework.commandhandling.model.AggregateNotFoundException;
import org.axonframework.commandhandling.model.Repository;
import pl.marcinsoja.cms.ocean.api.page.CreatePageCommand;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class PageHandler {

    private final Repository<Page> pageRepository;

    @CommandHandler
    public void handle(CreatePageCommand command) throws Exception {
        tryFindAggregate(command.getPageId()).ifPresent(pageAggregate -> {
            throw new PageAlreadyExists(command.getPageId());
        });
        pageRepository.newInstance(() -> new Page(command));
    }

    private Optional<Aggregate<Page>> tryFindAggregate(UUID pageId) {
        try {
            return Optional.of(pageRepository.load(pageId.toString()));
        } catch (AggregateNotFoundException ex) {
            return Optional.empty();
        }
    }
}
