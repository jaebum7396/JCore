package jcore.redis.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class RedisSubscriber {
    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final MessageListenerAdapter messageListenerAdapter;

    @PostConstruct
    public void init() {
        log.info("Initializing Redis Subscriber");
    }

    @Autowired
    public RedisSubscriber(RedisMessageListenerContainer redisMessageListenerContainer,
                           MessageListenerAdapter messageListenerAdapter) {
        this.redisMessageListenerContainer = redisMessageListenerContainer;
        this.messageListenerAdapter = messageListenerAdapter;
    }

    public void subscribe(ChannelTopic channelTopic) {
        redisMessageListenerContainer.addMessageListener(messageListenerAdapter, channelTopic);
    }
}