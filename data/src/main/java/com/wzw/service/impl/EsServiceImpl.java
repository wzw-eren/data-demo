package com.wzw.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wzw.pojo.dto.EsSearchReq;
import com.wzw.pojo.dto.EsUserDTO;
import com.wzw.pojo.entity.User;
import com.wzw.service.EsService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
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

    @Override
    public List<String> searchUser(EsSearchReq req) {
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        if (!StringUtils.isEmpty(req.getName())) {
            boolBuilder.must(QueryBuilders.termQuery("name", req.getName()));
        }
        if (!StringUtils.isEmpty(req.getComic())) {
            boolBuilder.must(QueryBuilders.termQuery("comic", req.getComic()));
        }
        if (!StringUtils.isEmpty(req.getDescription())) {
            boolBuilder.must(QueryBuilders.termQuery("description", req.getDescription()));
        }

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(boolBuilder);

        SearchRequest searchRequest = new SearchRequest(INDEX);
        searchRequest.source(sourceBuilder);
        List<String> result = new ArrayList<>();
        try {
            SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = search.getHits();
            for (SearchHit hit : hits) {
                result.add(hit.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
