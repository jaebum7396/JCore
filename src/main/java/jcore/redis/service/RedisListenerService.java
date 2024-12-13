package jcore.redis.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jcore.redis.model.dto.Envelope;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisListenerService implements MessageListener {
    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String messageString = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
        try {
            if (messageString == null) {
                log.error("this message is null");
                return;
            }
            Envelope envelope = objectMapper.readValue(messageString, Envelope.class);
            String topic = envelope.getTopic();
            ObjectNode payload = envelope.getPayload();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}