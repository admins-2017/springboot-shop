package com.zhike.manager.redis;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;

/**
 * @author Administrator
 * 设置redis 订阅模式 接收回调事件
 */
@Configuration
public class MessageListenerConfiguration {

    @Value("${spring.redis.listen-pattern}")
    public String pattern;

    /**
     * 监听redis 回调
     * @param redisConnection
     * @return
     */
    @Bean
    public RedisMessageListenerContainer listenerContainer(RedisConnectionFactory redisConnection) {
//        return new TopicMessageListener()
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnection);
        Topic topic = new PatternTopic(this.pattern);
//      添加监听器
        container.addMessageListener(new TopicMessageListener(), topic);
        return container;
    }
}