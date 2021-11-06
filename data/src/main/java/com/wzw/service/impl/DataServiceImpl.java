package com.wzw.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzw.constant.RedisKeys;
import com.wzw.dao.UserMapper;
import com.wzw.generator.IdGenerator;
import com.wzw.kafka.ProducerComponent;
import com.wzw.pojo.dto.UserDTO;
import com.wzw.pojo.entity.User;
import com.wzw.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public UserDTO insertData(User user) {
        String id = idGenerator.nextId("");
        user.setCreateTime(LocalDateTime.now());
        user.setId(id);
        String userString = JSONObject.toJSONString(user);
        redisTemplate.opsForValue().set(RedisKeys.USER_INFO_KEY + user.getId(), userString, 1, TimeUnit.HOURS);
        producerComponent.sendInsert(userString);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    @Override
    public Integer updateData(User user) {
        User byId = this.getById(user.getId());
        if (byId == null) {
            return 0;
        }
        userMapper.updateUser(user);
        String string = JSONObject.toJSONString(user);
        redisTemplate.delete(RedisKeys.USER_INFO_KEY + user.getId());
        producerComponent.sendUpdate(string);
        return 1;
    }

    @Override
    public void saveEntity(User user) {
        this.save(user);
    }

    @Override
    public UserDTO get(String id) {
        String userString = redisTemplate.opsForValue().get(RedisKeys.USER_INFO_KEY + id);
        UserDTO userDTO = new UserDTO();
        if (!StringUtils.isEmpty(userString)) {
            User user = JSONObject.parseObject(userString, User.class);
            BeanUtils.copyProperties(user, userDTO);
            return userDTO;
        }
        User user = this.getById(id);
        if (user == null) {
            return userDTO;
        }
        redisTemplate.opsForValue().set(
                RedisKeys.USER_INFO_KEY + user.getId(), JSONObject.toJSONString(user), 1, TimeUnit.HOURS);
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    @Override
    public List<UserDTO> getByIds(List<String> ids) {
        List<User> users = userMapper.selectBatchIds(ids);
        List<UserDTO> dtos = new ArrayList<>();
        if (CollectionUtils.isEmpty(users)) {
            return dtos;
        }
        users.forEach(user -> {
            UserDTO dto = new UserDTO();
            BeanUtils.copyProperties(user, dto);
            dtos.add(dto);
        });
        return dtos;
    }
}
