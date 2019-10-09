package com.dwell.it.dao;

import com.dwell.it.entities.House;
import com.dwell.it.entities.HouseDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IHouseDao {

    //  Basic CRUD Operations
    int insertNewHouseObject(House house);

    int updateResidenceDetailObject(HouseDetail house);

    House fetchSingleHouseFromListPageById(int id);

    int deleteHouseObjectById(int id);


    // Batch Operations
    boolean batchInsertNewHouseList(@Param("list") List<House> list);


    // ForeignKey Handler
    House searchTargetHouseByTitleAndURL(@Param("houseTitle") String houseTitle,
                                         @Param("detailPageUrl") String detailPageUrl);

    List<House> searchHousesByPageURL(@Param("url") String url);  // 由url反向查询house 返回的是列表页面house对象 不是houseDetail


    // Amap-SDK Requesting condition
    List<House> fetchExistedAddressQualifiedHouseListWithoutGeo();

    List<House> fetchExistedAddressQualifiedHouseListWithinGeo();

    boolean batchUpdateHouseGeoInfo(@Param("list") List<House> list);  // Batch Update after fetch coordinates in Amap-SDK

}
