package com.wzw.service;

import com.wzw.pojo.dto.UserDTO;
import com.wzw.pojo.entity.User;

import java.util.List;

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

    /**
     * 根据id查询用户信息
     *
     * @param id
     * @return
     */
    UserDTO get(String id);

    /**
     * 根据id列表查询用户信息
     *
     * @param ids
     * @return
     */
    List<UserDTO> getByIds(List<String> ids);
}
