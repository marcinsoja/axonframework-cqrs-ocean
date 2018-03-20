package pl.marcinsoja.cms.ocean.core.layout;


import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import pl.marcinsoja.cms.ocean.api.layout.CreateLayoutCommand;
import pl.marcinsoja.cms.ocean.api.layout.CreateLayoutPlaceholderCommand;
import pl.marcinsoja.cms.ocean.api.layout.DeleteLayoutPlaceholderCommand;
import pl.marcinsoja.cms.ocean.api.layout.LayoutCreatedEvent;
import pl.marcinsoja.cms.ocean.api.layout.LayoutPlaceholderCreatedEvent;
import pl.marcinsoja.cms.ocean.api.layout.LayoutPlaceholderDeletedEvent;
import pl.marcinsoja.cms.ocean.api.layout.LayoutPlaceholderUpdatedEvent;
import pl.marcinsoja.cms.ocean.api.layout.UpdateLayoutPlaceholderCommand;

import java.util.UUID;

@RunWith(JUnit4.class)
public class LayoutTest {

    private AggregateTestFixture<Layout> fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(Layout.class);
        fixture.registerCommandDispatchInterceptor(new BeanValidationInterceptor<>());
    }

    @Test
    public void shouldCreateLayout() {
        CreateLayoutCommand cmd = new CreateLayoutCommand(UUID.randomUUID(), "name");

        fixture.when(cmd)
               .expectSuccessfulHandlerExecution()
               .expectEvents(new LayoutCreatedEvent(cmd.getLayoutId(), cmd.getName()));
    }

    @Test
    public void shouldCreateLayoutPlaceholder() {
        LayoutCreatedEvent layoutCreatedEvent = new LayoutCreatedEvent(UUID.randomUUID(), "name");

        CreateLayoutPlaceholderCommand cmd = new CreateLayoutPlaceholderCommand(layoutCreatedEvent.getId(), UUID.randomUUID(), "name", 1);

        fixture.given(layoutCreatedEvent)
               .when(cmd)
               .expectSuccessfulHandlerExecution()
               .expectEvents(new LayoutPlaceholderCreatedEvent(cmd.getLayoutId(), cmd.getPlaceholderId(), cmd.getName(), cmd.getOrder()));
    }

    @Test
    public void shouldDeleteLayoutPlaceholder() {
        LayoutCreatedEvent createdEvent = new LayoutCreatedEvent(UUID.randomUUID(), "name");
        LayoutPlaceholderCreatedEvent placeholderCreatedEvent = new LayoutPlaceholderCreatedEvent(createdEvent.getId(), UUID.randomUUID(), "name", 1);

        DeleteLayoutPlaceholderCommand cmd = new DeleteLayoutPlaceholderCommand(placeholderCreatedEvent.getLayoutId(), placeholderCreatedEvent.getPlaceholderId());

        fixture.given(createdEvent, placeholderCreatedEvent)
               .when(cmd)
               .expectSuccessfulHandlerExecution()
               .expectEvents(new LayoutPlaceholderDeletedEvent(cmd.getLayoutId(), cmd.getPlaceholderId()));
    }

    @Test
    public void shouldUpdateLayoutPlaceholder() {
        LayoutCreatedEvent createdEvent = new LayoutCreatedEvent(UUID.randomUUID(), "name");
        LayoutPlaceholderCreatedEvent placeholder1CreatedEvent = new LayoutPlaceholderCreatedEvent(createdEvent.getId(), UUID.randomUUID(), "name1", 1);
        LayoutPlaceholderCreatedEvent placeholder2CreatedEvent = new LayoutPlaceholderCreatedEvent(createdEvent.getId(), UUID.randomUUID(), "name2", 2);

        UpdateLayoutPlaceholderCommand cmd = new UpdateLayoutPlaceholderCommand(createdEvent.getId(), placeholder1CreatedEvent.getPlaceholderId(), "name_", 3);

        fixture.given(createdEvent, placeholder1CreatedEvent, placeholder2CreatedEvent)
               .when(cmd)
               .expectSuccessfulHandlerExecution()
               .expectEvents(new LayoutPlaceholderUpdatedEvent(cmd.getLayoutId(), cmd.getPlaceholderId(), cmd.getName(), cmd.getOrder()));
    }
}
