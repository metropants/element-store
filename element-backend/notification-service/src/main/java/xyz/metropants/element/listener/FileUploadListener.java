package xyz.metropants.element.listener;

import club.minnced.discord.webhook.WebhookClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import xyz.metropants.element.payload.UploadNotification;

import java.util.function.Consumer;

@Component
public class FileUploadListener {

    private final WebhookClient client;

    public FileUploadListener(WebhookClient client) {
        this.client = client;
    }

    @Bean
    public Consumer<UploadNotification> notificationSupplier() {
        return notification -> client.send(notification.data(), notification.name());
    }

}
