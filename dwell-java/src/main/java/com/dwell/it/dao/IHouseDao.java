package com.dwell.it.dao;

import com.dwell.it.entities.House;
import com.dwell.it.entities.HouseDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IHouseDao {

    //  Basic CRUD Operations
    int insertNewHouseObject(House house);
    int updateResidenceDetailObject(HouseDetail house);
    House fetchSingleHouseFromListPageById(int id);
    int deleteHouseObjectById(int id);
}
