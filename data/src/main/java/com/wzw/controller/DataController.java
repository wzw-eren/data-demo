package com.wzw.controller;

import com.wzw.pojo.dto.UserDTO;
import com.wzw.pojo.entity.User;
import com.wzw.service.DataService;
import com.wzw.service.EsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * data 控制类
 *
 * @author: erenwu
 */
@RestController
@RequestMapping("/data")
public class DataController {

    @Resource
    private DataService dataService;

    @PostMapping("/insert")
    public UserDTO insertData(@RequestBody User user) {
        return dataService.insertData(user);
    }

    @PostMapping("/update")
    public Integer updateData(@RequestBody User user) {
        return dataService.updateData(user);
    }
}
