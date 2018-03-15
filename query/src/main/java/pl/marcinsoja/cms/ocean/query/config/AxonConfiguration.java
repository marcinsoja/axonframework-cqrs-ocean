package pl.marcinsoja.cms.ocean.query.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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

    @Bean
    public SpringAMQPMessageSource querySource(Serializer serializer) {
        return new SpringAMQPMessageSource(serializer) {
            @RabbitListener(queues = "page-queue")
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                super.onMessage(message, channel);
            }
        };
    }

}
