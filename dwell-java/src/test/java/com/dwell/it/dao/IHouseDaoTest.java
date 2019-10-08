package com.dwell.it.dao;

import com.dwell.it.entities.House;
import com.dwell.it.entities.HouseDetail;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IHouseDaoTest {

    @Autowired
    private IHouseDao iHouseDao;


    @Test
    @Ignore
    public void testFetchSingleHouseFromListPageById() {
        House house = iHouseDao.fetchSingleHouseFromListPageById(2);
        assertTrue("rrtt".equals(house.getTitle()));
    }


    @Test
    public void testInsertNewHouseObject() {
        House house = new House();
        house.setProviderId(1);
        house.setTitle("rrtt");
        house.setDetailPageUrl("http://www.baidu.com");
        house.setArea("BBB");
        house.setAspect("ccc");
        house.setLivingDiningKitchenInfo("3LDK");
        house.setStockCount("");
        house.setMainImageUrl("google");
        house.setCityZone("Nice");
        house.setInfoTags("1;3;5;7;9");
        house.setTradePrice("2000");
        house.setTradePriceUnit("元/月");
        house.setLastUpdateTime("2018-09-01");
        int effectedNumber = iHouseDao.insertNewHouseObject(house);
        assertTrue(effectedNumber > 0);
    }


    @Test
    public void testUpdateResidenceDetailObject() {
        House fetchedHouse = iHouseDao.fetchSingleHouseFromListPageById(2);
        HouseDetail house = new HouseDetail(fetchedHouse);
        house.setIdentifier("macbook air 2015");
        house.setContactId(2);
        house.setBannerImageUrls("e;b;c;d;");
        house.setAddress("addressZZZ");
        house.setConfirmApartmentType("0");
        house.setHouseDescription("WWW.FACEBOOK.COM");

        house.setPublishDateTime("2011-11-11");
        house.setRentHouseType("Unkonw");

        house.setPublishedDateTime("2018-09-11");
        house.setRentLease("3months");
        house.setFloorHigh("28");
        house.setParkingSpace("exists");
        house.setElectronicConsume("life");
        house.setMoveInCondition("anytime");
        house.setVisitingCondition("anytime");
        house.setLiftCondition("good");
        house.setWaterConsume("industry");
        house.setGasConsume("exists");
        house.setHeatingConsume("winter");

        house.setTvSupported("1");
        house.setRefrigeratorSupported("1");
        house.setWashingMachineSupported("0");
        house.setAirConditionSupported("0");
        house.setWaterHeatingSupported("0");
        house.setBedSupported("1");
        house.setHeatingSupported("1");
        house.setWifiSupported("0");
        house.setWardrobeSupported("0");
        house.setGasSupported("1");

        house.setInfoTags("1;2;3;4;5;6;7;8;9");

        int effectedNumber = iHouseDao.updateResidenceDetailObject(house);
        assertTrue(effectedNumber > 0);
    }


    @Test
    @Ignore
    public void testDeleteHouseObjectById() {
        int effectedNumber = iHouseDao.deleteHouseObjectById(2);
        assertTrue(effectedNumber > 0);
    }
}