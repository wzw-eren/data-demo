package com.wzw.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wzw.pojo.dto.EsUserDTO;
import com.wzw.pojo.entity.User;
import com.wzw.service.EsService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * es 服务类
 *
 * @author: erenwu
 */
@Service
@Slf4j
public class EsServiceImpl implements EsService {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    private static final String INDEX = "users";

    private static final String TYPE = "_doc";

    @Override
    public void esSyncAddData(User user) {
        EsUserDTO esUserDTO = new EsUserDTO();
        BeanUtils.copyProperties(user, esUserDTO);
        String body = JSONObject.toJSONString(esUserDTO);
        IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, user.getId()).source(body, XContentType.JSON);
        try {
            restHighLevelClient.index(indexRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void esSyncUpdateData(User user) {
        EsUserDTO esUserDTO = new EsUserDTO();
        BeanUtils.copyProperties(user, esUserDTO);
        UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, esUserDTO.getId());
        updateRequest.doc(JSONObject.toJSONString(esUserDTO), XContentType.JSON);
        try {
            restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
