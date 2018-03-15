package pl.marcinsoja.cms.ocean.core.layout;


import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import pl.marcinsoja.cms.ocean.api.layout.AddLayoutPlaceholderCommand;
import pl.marcinsoja.cms.ocean.api.layout.CreateLayoutCommand;
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
    public void testShouldCreateLayoutTest() {
        UUID layoutId = UUID.randomUUID();
        String layoutName = "Layout";
        fixture.when(new CreateLayoutCommand(layoutId, layoutName))
               .expectSuccessfulHandlerExecution()
               .expectEvents(new LayoutCreatedEvent(layoutId, layoutName));
    }

    @Test
    public void shouldCreateLayoutPlaceholder() {
        UUID layoutId = UUID.randomUUID();
        UUID placeholderId = UUID.randomUUID();
        String layoutName = "Layout";
        String placeholderName = "place1";
        fixture.given(new LayoutCreatedEvent(layoutId, layoutName))
               .when(new AddLayoutPlaceholderCommand(layoutId, placeholderId, placeholderName, 1))
               .expectSuccessfulHandlerExecution()
               .expectEvents(new LayoutPlaceholderCreatedEvent(layoutId, placeholderId, placeholderName, 1))
        ;
    }

    @Test
    public void shouldDeleteLayoutPlaceholder() {
        UUID layoutId = UUID.randomUUID();
        UUID placeholderId = UUID.randomUUID();
        String layoutName = "Layout";
        String placeholderName = "place1";
        fixture.given(new LayoutCreatedEvent(layoutId, layoutName), new LayoutPlaceholderCreatedEvent(layoutId, placeholderId, placeholderName, 1))
               .when(new DeleteLayoutPlaceholderCommand(layoutId, placeholderId))
               .expectSuccessfulHandlerExecution()
               .expectEvents(new LayoutPlaceholderDeletedEvent(layoutId, placeholderId))
        ;
    }

    @Test
    public void shouldUpdateLayoutPlaceholder() {
        UUID layoutId = UUID.randomUUID();
        UUID placeholderId1 = UUID.randomUUID();
        UUID placeholderId2 = UUID.randomUUID();
        String layoutName = "Layout";
        String placeholderName = "place1";
        fixture.given(
                new LayoutCreatedEvent(layoutId, layoutName),
                new LayoutPlaceholderCreatedEvent(layoutId, placeholderId1, placeholderName, 1),
                new LayoutPlaceholderCreatedEvent(layoutId, placeholderId2, placeholderName, 2))
               .when(new UpdateLayoutPlaceholderCommand(layoutId, placeholderId1, "change", 3))
               .expectSuccessfulHandlerExecution()
               .expectEvents(new LayoutPlaceholderUpdatedEvent(layoutId, placeholderId1, "change", 3))
        ;
    }
}
