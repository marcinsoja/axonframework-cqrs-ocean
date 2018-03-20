package pl.marcinsoja.cms.ocean.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.config.EventHandlingConfiguration;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
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
