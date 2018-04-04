package pl.marcinsoja.cms.ocean.core.layout;

import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import pl.marcinsoja.cms.ocean.api.page.CreatePageCommand;
import pl.marcinsoja.cms.ocean.api.page.CreatePageElementCommand;
import pl.marcinsoja.cms.ocean.api.page.PageCreatedEvent;
import pl.marcinsoja.cms.ocean.api.page.PageElementCreatedEvent;
import pl.marcinsoja.cms.ocean.core.content.Article;
import pl.marcinsoja.cms.ocean.core.page.Page;
import pl.marcinsoja.cms.ocean.core.page.PageHandler;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.UUID;

@RunWith(JUnit4.class)
public class PageTest {
    private Instant SOME_INSTANT = Instant.parse("2007-12-03T10:15:30.00Z");

    private AggregateTestFixture<Page> fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(Page.class);
        fixture.registerCommandDispatchInterceptor(new BeanValidationInterceptor<>());
        fixture.registerAnnotatedCommandHandler(new PageHandler(fixture.getRepository(), Clock.fixed(SOME_INSTANT, ZoneOffset.UTC)));
    }

    @Test
    public void shouldCreatePage() {
        CreatePageCommand cmd = new CreatePageCommand(UUID.randomUUID(), UUID.randomUUID(), "slug", "name");

        fixture.when(cmd)
               .expectSuccessfulHandlerExecution()
               .expectEvents(new PageCreatedEvent(cmd.getPageId(), cmd.getLayoutId(), cmd.getSlug(), cmd.getName(), SOME_INSTANT));
    }

    @Test
    public void shouldCreatePageContent() {
        PageCreatedEvent pageCreatedEvent = new PageCreatedEvent(UUID.randomUUID(), UUID.randomUUID(), "slug", "name", Instant.now());

        CreatePageElementCommand cmd = new CreatePageElementCommand(pageCreatedEvent.getId(), UUID.randomUUID(), UUID.randomUUID(), Article.CONTENT_TYPE, 1);

        fixture.given(pageCreatedEvent)
               .when(cmd)
               .expectSuccessfulHandlerExecution()
               .expectEvents(new PageElementCreatedEvent(cmd.getPageId(), cmd.getElementId(), cmd.getContentId(), cmd.getContentType(), cmd.getOrder(), SOME_INSTANT));
    }
}
