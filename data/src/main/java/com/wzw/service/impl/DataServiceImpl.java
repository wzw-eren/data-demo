package com.wzw.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzw.dao.UserMapper;
import com.wzw.generator.IdGenerator;
import com.wzw.kafka.ProducerComponent;
import com.wzw.pojo.dto.UserDTO;
import com.wzw.pojo.entity.User;
import com.wzw.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * data 服务实现类
 *
 * @author: erenwu
 */
@Service
@Slf4j
public class DataServiceImpl extends ServiceImpl<UserMapper, User> implements DataService {

    @Resource
    private ProducerComponent producerComponent;

    @Resource
    private IdGenerator idGenerator;

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDTO insertData(User user) {
        String id = idGenerator.nextId("");
        user.setCreateTime(LocalDateTime.now());
        user.setId(id);
        String string = JSONObject.toJSONString(user);
        producerComponent.sendInsert(string);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    @Override
    public Integer updateData(User user) {
        userMapper.updateUser(user);
        String string = JSONObject.toJSONString(user);
        producerComponent.sendUpdate(string);
        return 1;
    }

    @Override
    public void saveEntity(User user) {
        this.save(user);
    }


}
