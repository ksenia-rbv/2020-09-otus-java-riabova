package ru.otus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.MessageSystemImpl;
import ru.otus.messagesystem.client.CallbackRegistry;
import ru.otus.messagesystem.client.CallbackRegistryImpl;

@Configuration
public class MessageSystemConfiguration {

    @Bean
    MessageSystem createMessageSystem() {
        return new MessageSystemImpl();
    }

    @Bean
    CallbackRegistry createCallbackRegistry() {
        return new CallbackRegistryImpl();
    }
}
