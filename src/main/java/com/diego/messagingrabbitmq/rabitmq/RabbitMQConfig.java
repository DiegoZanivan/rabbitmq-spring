package com.diego.messagingrabbitmq.rabitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    DirectExchange deadLetterExchange() {
        return new DirectExchange("test-dead-letter-exchange");
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange("test-exchange");
    }

    @Bean
    Queue dlq() {
        return QueueBuilder.durable("test.dead.letter.queue").build();
    }

    @Bean
    Queue queue() {
        return QueueBuilder.durable("test.queue").withArgument("x-dead-letter-exchange", "test-dead-letter-exchange")
                .withArgument("x-dead-letter-routing-key", "dead-letter-rk").build();
    }

    @Bean
    Binding DLQbinding() {
        return BindingBuilder.bind(dlq()).to(deadLetterExchange()).with("dead-letter-rk");
    }

    @Bean
    Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with("test-rk");
    }

    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }
}