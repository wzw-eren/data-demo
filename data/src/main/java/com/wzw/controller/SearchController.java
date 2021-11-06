package com.wzw.controller;

import com.wzw.pojo.dto.EsSearchReq;
import com.wzw.pojo.dto.UserDTO;
import com.wzw.service.SearchService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 搜索查询 控制类
 *
 * @author: erenwu
 */
@RestController
@RequestMapping("/search")
public class SearchController {
    
    @Resource
    private SearchService searchService;

    @PostMapping("/user")
    public List<UserDTO> insertData(@RequestBody EsSearchReq req) {
        return searchService.search(req);
    }
}
