package com.wzw.pojo.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户dtp
 *
 * @author: erenwu
 */
@Data
public class UserDTO {

    private String id;

    private String name;

    private Integer age;

    private String gender;

    private String comic;

    private String description;

    private LocalDateTime createTime;
}
