package com.wzw.service;

import com.wzw.pojo.dto.UserDTO;
import com.wzw.pojo.entity.User;

/**
 * data 服务类
 *
 * @author: erenwu
 */
public interface DataService {

    /**
     * 新增数据
     *
     * @param user
     * @return
     */
    UserDTO insertData(User user);

    /**
     * 修改数据
     *
     * @param user
     * @return
     */
    Integer updateData(User user);

    /**
     * 保存用户
     *
     * @param user
     */
    void saveEntity(User user);
}
