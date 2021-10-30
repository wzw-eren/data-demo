package com.wzw.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体
 *
 * @author: erenwu
 */
@Data
@TableName("t_user")
public class User {

    @TableId(value = "id")
    private String id;

    @TableId(value = "name")
    private String name;

    @TableId(value = "age")
    private Integer age;

    @TableId(value = "skill")
    private String skill;

    @TableId(value = "gender")
    private String gender;

    @TableId(value = "comic")
    private String comic;

    @TableId(value = "description")
    private String description;

    @TableId(value = "create_time")
    private LocalDateTime createTime;
}
