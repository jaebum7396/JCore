package jCore.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

@Component
public class RedisSubscriber {
    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final MessageListenerAdapter messageListenerAdapter;
    private final ChannelTopic channelTopic;

    @Autowired
    public RedisSubscriber(RedisMessageListenerContainer redisMessageListenerContainer,
                           MessageListenerAdapter messageListenerAdapter,
                           ChannelTopic channelTopic) {
        this.redisMessageListenerContainer = redisMessageListenerContainer;
        this.messageListenerAdapter = messageListenerAdapter;
        this.channelTopic = channelTopic;
    }

    public void subscribe() {
        redisMessageListenerContainer.addMessageListener(messageListenerAdapter, channelTopic);
    }
}