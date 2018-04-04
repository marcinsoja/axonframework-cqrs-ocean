package pl.marcinsoja.cms.ocean.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.common.jdbc.PersistenceExceptionResolver;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.Configurer;
import org.axonframework.config.EventHandlingConfiguration;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.jpa.JpaEventStorageEngine;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.axonframework.serialization.upcasting.event.EventUpcasterChain;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import pl.marcinsoja.cms.ocean.core.content.Article;
import pl.marcinsoja.cms.ocean.core.content.ArticleHandler;
import pl.marcinsoja.cms.ocean.core.page.Page;
import pl.marcinsoja.cms.ocean.core.page.PageHandler;
import pl.marcinsoja.cms.ocean.core.page.upcast.PageCreatedV2Upcaster;

import java.time.Clock;

@Configuration
public class AxonConfiguration {

    @Primary
    @Bean
    public Serializer serializer(ObjectMapper objectMapper) {
        return new JacksonSerializer(objectMapper);
    }

    @Autowired
    public void configure(EventHandlingConfiguration config) {
        config.usingTrackingProcessors();
    }

    @Autowired
    public void configureBeanValidation(@Qualifier("localSegment") SimpleCommandBus localSegment) {
        localSegment.registerDispatchInterceptor(new BeanValidationInterceptor<>());
    }

    @Autowired
    public void configure(Configurer configurer) {
        configurer.registerCommandHandler(configuration -> new PageHandler(configuration.repository(Page.class), Clock.systemUTC()));
        configurer.registerCommandHandler(configuration -> new ArticleHandler(configuration.repository(Article.class), Clock.systemUTC()));
    }

    @Bean
    public EventStorageEngine eventStorageEngine(Serializer serializer,
                                                 PersistenceExceptionResolver persistenceExceptionResolver,
                                                 org.axonframework.spring.config.AxonConfiguration configuration,
                                                 EntityManagerProvider entityManagerProvider,
                                                 TransactionManager transactionManager) {
        return new JpaEventStorageEngine(serializer, new EventUpcasterChain(new PageCreatedV2Upcaster()),
                persistenceExceptionResolver, configuration.eventSerializer(), null,
                entityManagerProvider, transactionManager, null, null, true);
    }

    @Bean
    RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    Queue queue() {
        return new Queue("page-queue", true);
    }

    @Bean
    Exchange exchange() {
        return ExchangeBuilder.fanoutExchange("page-exchange").durable(true).build();
    }

    @Bean
    Binding binding(Queue queue, Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("*").noargs();
    }
}
