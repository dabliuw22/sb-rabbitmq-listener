
package com.leysoft.service.imple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leysoft.model.SimpleMessage;
import com.leysoft.service.inter.ReceiverService;

@Service
public class ReceiverServiceImp implements ReceiverService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReceiverServiceImp.class);

    @Value(
            value = "${rabbitmq.routing-key}")
    private String routingKey;

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(
            queues = {
                "${rabbitmq.queue.name}"
            },
            containerFactory = "rabbitListenerContainerFactory")
    @Override
    public void receive(SimpleMessage message) {
        String info = "Error";
        try {
            info = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            LOGGER.error("{}", e.getMessage());
        }
        LOGGER.info("receive message -> {}", info);
    }
}
