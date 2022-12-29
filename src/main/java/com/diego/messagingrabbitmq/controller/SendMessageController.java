package com.diego.messagingrabbitmq.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("send-message")
public class SendMessageController {

    @Autowired
    private AmqpTemplate queueSender;

    @GetMapping("success")
    public ResponseEntity<String> sendMessageSuccess(@RequestParam("message") String message) {
        sendMessage(message, "success");
        return ResponseEntity.ok("Success message sent");
    }

    @GetMapping("error")
    public ResponseEntity<String> sendMessageError(@RequestParam("message") String message) {
        sendMessage(message, "error");
        return ResponseEntity.ok("Error message sent");
    }

    private void sendMessage(String message, String typeHeader) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("type", typeHeader);
        Message rabbitmqMessage = new Message(message.getBytes(), messageProperties);

        queueSender.convertAndSend("test-exchange", "test-rk", rabbitmqMessage);
    }
}
