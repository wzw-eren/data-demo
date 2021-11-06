package com.wzw.service;

import com.wzw.pojo.dto.EsSearchReq;
import com.wzw.pojo.entity.User;

import java.util.List;

/**
 * es 服务类
 *
 * @author: erenwu
 */

public interface EsService {

    /**
     * es 同步数据
     *
     * @param user
     */
    void esSyncAddData(User user);

    /**
     * es 修改数据
     *
     * @param user
     */
    void esSyncUpdateData(User user);

    /**
     * 查询用户id
     *
     * @param req
     * @return
     */
    List<String> searchUser(EsSearchReq req);
}
