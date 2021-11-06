package com.wzw.pojo.dto;

import lombok.Data;

/**
 * 用户es 请求类
 *
 * @author: erenwu
 */
@Data
public class EsSearchReq {

    private String name;

    private String description;

    private String comic;

}
