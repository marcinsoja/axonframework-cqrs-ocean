package pl.marcinsoja.cms.ocean.core.content;

import com.neovisionaries.i18n.LanguageCode;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import pl.marcinsoja.cms.ocean.api.content.article.ArticleCreatedEvent;
import pl.marcinsoja.cms.ocean.api.content.article.ArticleUpdatedEvent;
import pl.marcinsoja.cms.ocean.api.content.article.CreateArticleCommand;
import pl.marcinsoja.cms.ocean.api.content.article.UpdateArticleCommand;

import java.time.Instant;
import java.util.UUID;

@Getter
@EqualsAndHashCode(of = "id")
@Aggregate
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Article {

    public static final String CONTENT_TYPE = "article.v1";

    @AggregateIdentifier
    private UUID id;
    private String name;
    private String content;
    private LanguageCode language;
    private Instant created;
    private Instant lastUpdated;

    @CommandHandler
    public Article(CreateArticleCommand command) {
        AggregateLifecycle.apply(new ArticleCreatedEvent(command.getArticleId(), command.getName(), command.getContent(), command.getLanguage(), Instant.now()));
    }

    @CommandHandler
    public void command(UpdateArticleCommand command) {
        AggregateLifecycle.apply(new ArticleUpdatedEvent(command.getArticleId(), command.getName(), command.getContent(), command.getLanguage(), Instant.now()));
    }

    @EventSourcingHandler
    public void on(ArticleCreatedEvent event) {
        this.id = event.getArticleId();
        this.name = event.getName();
        this.content = event.getContent();
        this.language = event.getLanguage();
        this.created = event.getAt();
    }
    @EventSourcingHandler
    public void on(ArticleUpdatedEvent event) {
        this.name = event.getName();
        this.content = event.getContent();
        this.language = event.getLanguage();
        this.lastUpdated = event.getAt();
    }

}
