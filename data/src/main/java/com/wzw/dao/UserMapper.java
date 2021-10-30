package com.wzw.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzw.pojo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * user Mapper 接口
 *
 * @author erenwu
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 更新用户信息
     *
     */
    void updateUser(@Param("user") User user);
}
