
package com.leysoft.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMqConfiguration {

    @Value(
            value = "${spring.rabbitmq.host}")
    private String rabbitHost;

    @Value(
            value = "${spring.rabbitmq.port}")
    private int rabbitPort;

    @Value(
            value = "${spring.rabbitmq.username}")
    private String rabbitUsername;

    @Value(
            value = "${spring.rabbitmq.password}")
    private String rabbitPassword;

    @Value(
            value = "${rabbitmq.queue.name}")
    private String queueName;

    @Value(
            value = "${rabbitmq.queue.durable:true}")
    private boolean queueDurable;

    @Value(
            value = "${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value(
            value = "${rabbitmq.exchange.durable:true}")
    private boolean exchangeDurable;

    @Value(
            value = "${rabbitmq.routing-key}")
    private String routingKey;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitHost);
        connectionFactory.setPort(rabbitPort);
        connectionFactory.setUsername(rabbitUsername);
        connectionFactory.setPassword(rabbitPassword);
        return connectionFactory;
    }

    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }

    @Bean
    public Binding binding(Exchange exchange, Queue queue) {
        // La queue esta interesada en los mensajes del exchange con toutingKey
        return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean(
            name = {
                "rabbitListenerContainerFactory"
            })
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory, Queue queue, MessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        return factory;
    }
}
