package com.dwell.it.controller;

import com.dwell.it.entities.House;
import com.dwell.it.model.HttpJSONResponse;
import com.dwell.it.service.IHouseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/fetch")
public class HouseController {

    @Autowired
    private IHouseService iHouseService;


    private final String qualifiedConditionSQL = "where confirm_apartment_type = 2 and LENGTH(city_zone) > 5 and geo_info != '0,0'";


    /**
     * 获取在默认排序的房源列表api  TODO: 这里有个性能问题，每次请求都要去查询DB, 计划使用redis给缓存数据
     *
     * @param pageNumber 目前是第几页
     * @param pageSize   分页大小
     * @return API-Response
     */
    @RequestMapping(value = "/houses", method = RequestMethod.GET)
    public HttpJSONResponse fetchHouseListBaseOnPageNumber(@RequestParam(value = "pageNumber") Integer pageNumber,
                                                           @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize)
            throws JsonProcessingException {
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
        }

        String whereConditionSql = qualifiedConditionSQL;
        Integer totalCount = iHouseService.queryTotalQualifiedHousesCountByCondition(whereConditionSql);
        if (totalCount > pageNumber * pageSize) {
            Page<House> data = iHouseService.queryHousesByPaging(pageNumber, pageSize, whereConditionSql);
            if (data == null || data.size() == 0) {
                return HttpJSONResponse.errorMessage("暂无数据");
            }

            Map<String, Object> map = new HashMap<>();
            map.put("pageNumber", pageNumber + "");
            map.put("pageSize", pageSize + "");
            map.put("pageTotal", totalCount + "");
            map.put("arrays", data);
            HttpJSONResponse response = HttpJSONResponse.ok(map);
            return response;
        } else {
            return HttpJSONResponse.successMessage("没有更多数据了");
        }
    }
}
