package com.codecrafters.notification.service;

import com.codecrafters.notification.model.Notification;
import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public void sendNotification(Notification notification) {
        try {
            logger.debug("Received request to send message");

            Payload payload = Payload.builder().text(notification.getMessage()).build();
            WebhookResponse webhookResponse = Slack.getInstance().send(notification.getWebHookUrl(), payload);
            if (webhookResponse.getBody() == "ok") {
                logger.debug("Message sent");
            } else {
                logger.error("Failed to send notification to Slack. Check logs for details!");
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Failed to send notification to Slack. Check logs for details!");
        }
    }
}

