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

import java.util.UUID;

@RunWith(JUnit4.class)
public class PageTest {
    private AggregateTestFixture<Page> fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(Page.class);
        fixture.registerCommandDispatchInterceptor(new BeanValidationInterceptor<>());
    }

    @Test
    public void shouldCreatePage() {
        CreatePageCommand cmd = new CreatePageCommand(UUID.randomUUID(), UUID.randomUUID(), "slug", "name");

        fixture.when(cmd)
               .expectSuccessfulHandlerExecution()
               .expectEvents(new PageCreatedEvent(cmd.getPageId(), cmd.getLayoutId(), cmd.getSlug(), cmd.getName()));
    }

    @Test
    public void shouldCreatePageContent() {
        PageCreatedEvent pageCreatedEvent = new PageCreatedEvent(UUID.randomUUID(), UUID.randomUUID(), "slug", "name");

        CreatePageElementCommand cmd = new CreatePageElementCommand(pageCreatedEvent.getId(), UUID.randomUUID(), UUID.randomUUID(), Article.CONTENT_TYPE, 1);

        fixture.given(pageCreatedEvent)
               .when(cmd)
               .expectSuccessfulHandlerExecution()
               .expectEvents(new PageElementCreatedEvent(cmd.getPageId(), cmd.getElementId(), cmd.getContentId(), cmd.getContentType(), cmd.getOrder()));
    }
}
