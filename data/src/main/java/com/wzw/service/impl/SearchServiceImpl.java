package com.wzw.service.impl;

import com.wzw.pojo.dto.EsSearchReq;
import com.wzw.pojo.dto.UserDTO;
import com.wzw.service.DataService;
import com.wzw.service.EsService;
import com.wzw.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 搜索查询 服务类
 *
 * @author: erenwu
 */
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Resource
    private DataService dataService;

    @Resource
    private EsService esService;

    @Override
    public List<UserDTO> search(EsSearchReq req) {
        List<String> ids = esService.searchUser(req);
        return dataService.getByIds(ids);
    }
}
