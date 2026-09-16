package br.com.alura.produto.infra.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfig {

    @Value("${message.market-place.queue-exchange}")
    public String queueExchange;

    @Value("${message.market-place.dlq-exchange}")
    public String dlqExchange;

    @Bean
    TopicExchange queueExchange() {
        return new TopicExchange(queueExchange);
    }

    @Bean
    TopicExchange dlqExchange() {
        return new TopicExchange(dlqExchange);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
