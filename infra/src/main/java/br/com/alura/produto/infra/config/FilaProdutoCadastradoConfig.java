package br.com.alura.produto.infra.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.amqp.core.BindingBuilder.bind;

@Configuration
public class FilaProdutoCadastradoConfig {

    @Value("${message.cadastro-produto.queue-name}")
    public String queueName;

    @Value("${message.cadastro-produto.dlq-name}")
    public String dlqName;

    @Value("${message.cadastro-produto.routing-key}")
    public String routingKeyName;

    @Bean
    Queue queue() {
        return QueueBuilder
                .durable(queueName)
                .deadLetterExchange(dlqName)
                .deadLetterRoutingKey(routingKeyName)
                .build();
    }

    @Bean
    Binding binding(TopicExchange queueExchange) {
        return bind(queue())
                .to(queueExchange)
                .with(routingKeyName);
    }

    @Bean
    Queue dlq() {
        return new Queue(dlqName);
    }
}
