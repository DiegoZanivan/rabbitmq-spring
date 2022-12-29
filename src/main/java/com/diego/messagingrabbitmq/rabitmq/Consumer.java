package com.diego.messagingrabbitmq.rabitmq;

import com.diego.messagingrabbitmq.global.CustomException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @RabbitListener(queues = {"test.queue"})
    public void receiveMessage(Message message) throws CustomException {
        String type = message.getMessageProperties().getHeader("type");

        if ("error".equals(type)) {
            System.out.println("Fail to consume the following message:");
            System.out.println(message);
            throw new CustomException();
        }

        System.out.println("----------------------------------");
        System.out.println(message);
        System.out.println(new String(message.getBody()));
        System.out.println("Message consumed successfully");
        System.out.println("----------------------------------");
    }
}
