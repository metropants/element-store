package xyz.metropants.element.config;

import club.minnced.discord.webhook.WebhookClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebHookConfig {

    @Value("${discord.webhook.url}")
    private String url;

    @Bean
    public WebhookClient client() {
        return WebhookClient.withUrl(url);
    }

}
