package com.practice.notification_service.listener;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
@Component
public class NotificationListener {

    @RabbitListener(queues = "order-queue")
    public void consume(String message) {
        System.out.println("🔥 Notification received: " + message);
    }
}
