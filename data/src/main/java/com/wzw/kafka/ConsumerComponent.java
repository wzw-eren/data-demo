package com.wzw.kafka;

import com.alibaba.fastjson.JSONObject;
import com.wzw.pojo.entity.User;
import com.wzw.service.DataService;
import com.wzw.service.EsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * kafka 消费者配置类
 *
 * @author: erenwu
 */
@Component
@Slf4j
public class ConsumerComponent {

    @Resource
    private EsService esService;

    @Resource
    private DataService dataService;

    @KafkaListener(topics = "insert")
    public void insertListen (ConsumerRecord<?, String> record) {
        String msg = record.value();
        //四个参数依次为topic名字，偏移量，传输的数据，分区号
        log.info("这一号消费者topic = {}, offset = {}, value = {} ,index={}",
                record.topic(), record.offset(), msg, record.partition());
        User user = JSONObject.parseObject(msg, User.class);
        esService.esSyncAddData(user);
        dataService.saveEntity(user);
    }

    @KafkaListener(topics = "update")
    public void updateListen (ConsumerRecord<?, String> record) {
        //四个参数依次为topic名字，偏移量，传输的数据，分区号
        log.info("这二号消费者topic = {}, offset = {}, value = {} ,index={}",
                record.topic(), record.offset(), record.value(), record.partition());
        User user = JSONObject.parseObject(record.value(), User.class);
        esService.esSyncUpdateData(user);
    }
}
