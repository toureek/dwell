package com.dwell.it.service;

import com.dwell.it.entities.House;
import com.dwell.it.entities.HouseDetail;

public interface IHouseService {

    // Basic single-ops
    boolean addNewHouseOne(House house);
    boolean modifyHouseDetailOne(HouseDetail houseDetail);
    boolean removeHouseById(int id);

}
