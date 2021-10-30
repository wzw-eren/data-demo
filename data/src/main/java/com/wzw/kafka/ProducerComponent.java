package com.wzw.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * kafka发送消息工具类
 *
 * @author: erenwu
 */
@Component
public class ProducerComponent {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC_INSERT = "insert";

    private static final String TOPIC_UPDATE = "update";

    /**
     * 异步插入发送消息
     *
     * @param msg
     */
    public void sendInsert(String msg) {
        kafkaTemplate.send(TOPIC_INSERT, msg);
    }

    /**
     * 异步更新发送消息
     *
     * @param msg
     */
    public void sendUpdate(String msg) {
        kafkaTemplate.send(TOPIC_UPDATE, msg);
    }
}
