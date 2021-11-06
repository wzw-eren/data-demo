package com.wzw.service;

import com.wzw.pojo.dto.EsSearchReq;
import com.wzw.pojo.dto.UserDTO;

import java.util.List;

/**
 * 搜索查询 服务类
 *
 * @author: erenwu
 */
public interface SearchService {


    /**
     * 搜索用户
     *
     * @param req
     * @return
     */
    List<UserDTO> search(EsSearchReq req);
}
