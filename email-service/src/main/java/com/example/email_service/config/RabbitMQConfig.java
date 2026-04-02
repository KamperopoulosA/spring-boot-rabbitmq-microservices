package com.example.email_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class RabbitMQConfig {

    @Value("${rabbitmq.queue.order.name}")
   private String orderQueue;

   @Value("${rabbit.mq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.binding.routing.key}")
    private String orderRoutingkey;
    // spring bean for queue - order queue


    @Bean
    public Queue orderQueue(){
        return new Queue(orderQueue);
    }

    // spring bean for exchange
    @Bean
    public TopicExchange exchange(){
       return new TopicExchange(exchange);
    }
    // spring bean for binding between exchange and queue suing routing key
    public Binding binding(){
        return BindingBuilder
                .bind(orderQueue())
                .to(exchange())
                .with(orderRoutingkey);
    }
    // messsage converter
    @Bean
    public MessageConverter converter(){
        return new JacksonJsonMessageConverter();
    }
    //configure RabbitTemplate
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory ){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
