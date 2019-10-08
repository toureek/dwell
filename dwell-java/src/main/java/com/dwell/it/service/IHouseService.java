package com.dwell.it.service;

import com.dwell.it.entities.House;
import com.dwell.it.entities.HouseDetail;

import java.util.List;

public interface IHouseService {

    // Basic single-ops
    boolean addNewHouseOne(House house);
    boolean modifyHouseDetailOne(HouseDetail houseDetail);
    boolean removeHouseById(int id);


    // Batch multi-ops
    boolean batchAddNewHouseList(List<House> list);


    boolean isAllowedToInsertNewHouseRecord(String houseTitle, String detailPageUrl);  // 反向查询 记录是否在数据库内
}
