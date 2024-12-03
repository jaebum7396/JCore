package jCore.api.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisMessageSubscriber implements MessageListener {
    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("FriendInfoSynchronizeService.onMessage()" + message);
        try {
            // redis에서 발행된 데이터를 받아 deserialize
            //String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
            //FriendInfo friendInfo = objectMapper.readValue(publishMessage, FriendInfo.class);
            //chatService.updateFriendInfo(friendInfo);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}