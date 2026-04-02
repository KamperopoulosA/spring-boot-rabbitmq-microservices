package com.example.email_service.publisher;

import com.example.email_service.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);
    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.binding.routing.key}")
    private String orderRoutingkey;

    private RabbitTemplate rabbitTemplate;

    public OrderProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }
    public void sendMessage(OrderEvent orderEvent){
        LOGGER.info(String.format("order event set to rabbit mq ==> %s ", orderEvent.toString()));
        rabbitTemplate.convertAndSend(exchange,orderRoutingkey,orderEvent);
    }

}

