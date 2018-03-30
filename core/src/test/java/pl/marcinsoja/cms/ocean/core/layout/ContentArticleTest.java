package pl.marcinsoja.cms.ocean.core.layout;

import com.neovisionaries.i18n.LanguageCode;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.matchers.IgnoreField;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import pl.marcinsoja.cms.ocean.api.content.article.ArticleCreatedEvent;
import pl.marcinsoja.cms.ocean.api.content.article.ArticleUpdatedEvent;
import pl.marcinsoja.cms.ocean.api.content.article.CreateArticleCommand;
import pl.marcinsoja.cms.ocean.api.content.article.UpdateArticleCommand;
import pl.marcinsoja.cms.ocean.core.content.Article;
import pl.marcinsoja.cms.ocean.core.content.ArticleHandler;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.axonframework.test.matchers.Matchers.equalTo;
import static org.axonframework.test.matchers.Matchers.listWithAllOf;
import static org.axonframework.test.matchers.Matchers.messageWithPayload;

@RunWith(JUnit4.class)
public class ContentArticleTest {
    private AggregateTestFixture<Article> fixture;

    private Instant SOME_INSTANT = Instant.parse("2007-12-03T10:15:30.00Z");

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(Article.class);
        fixture.registerCommandDispatchInterceptor(new BeanValidationInterceptor<>());
        fixture.registerAnnotatedCommandHandler(new ArticleHandler(fixture.getRepository(), Clock.fixed(SOME_INSTANT, ZoneOffset.UTC)));
    }

    @Test
    public void shouldCreateArticle() {
        CreateArticleCommand cmd = new CreateArticleCommand(UUID.randomUUID(), "name", "content", LanguageCode.en);

        fixture.when(cmd)
               .expectSuccessfulHandlerExecution()
               .expectEventsMatching(listWithAllOf(
                       messageWithPayload(equalTo(new ArticleCreatedEvent(cmd.getArticleId(), cmd.getName(), cmd.getContent(), cmd.getLanguage(), Instant.now()), new IgnoreField(ArticleCreatedEvent.class, "at")))
               ));
    }

    @Test
    public void shouldUpdateArticle() {
        ArticleCreatedEvent createdEvent = new ArticleCreatedEvent(UUID.randomUUID(), "name", "content", LanguageCode.en, SOME_INSTANT.minusSeconds(60));

        UpdateArticleCommand cmd = new UpdateArticleCommand(createdEvent.getArticleId(), "name_", "content_", LanguageCode.it);

        fixture.given(createdEvent)
               .when(cmd)
               .expectSuccessfulHandlerExecution()
               .expectEventsMatching(listWithAllOf(
                       messageWithPayload(equalTo(new ArticleUpdatedEvent(createdEvent.getArticleId(), cmd.getName(), cmd.getContent(), cmd.getLanguage(), SOME_INSTANT)))
               ));
    }
}
