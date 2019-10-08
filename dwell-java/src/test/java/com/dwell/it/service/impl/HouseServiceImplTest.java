package com.dwell.it.service.impl;

import com.dwell.it.entities.House;
import com.dwell.it.entities.HouseDetail;
import com.dwell.it.exception.DBManipulateException;
import com.dwell.it.exception.MessageRuntimeException;
import com.dwell.it.service.IHouseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HouseServiceImplTest {

    @Autowired
    private IHouseService iHouseService;


    @Test(expected = DBManipulateException.class)
    public void testAddNewHouseEmptyOne() {
        House house = null;
        iHouseService.addNewHouseOne(house);
    }


    @Test
    public void testAddNewHouseOne() {
        House house = new House(1, "A", "B", "C", "D",
                "E", "F", "G", "H",
                "I", "J", "K", "L");
        boolean result = iHouseService.addNewHouseOne(house);
        assertTrue(result);
    }


    @Test(expected = DBManipulateException.class)
    public void testRemoveHouseById() {
        iHouseService.removeHouseById(-1);
    }


    @Test(expected = DBManipulateException.class)
    public void testModifyNullHouseDetailOne() {
        iHouseService.modifyHouseDetailOne(null);
    }

    @Test(expected = MessageRuntimeException.class)
    public void testModifyNonTypeHouseDetailOne() {
        HouseDetail house = buildHouseDetailModel();
        house.setId(9);
        iHouseService.modifyHouseDetailOne(house);
    }


    @Test(expected = DBManipulateException.class)
    public void testModifyNonIdentifierTypeHouseDetailOne() {
        HouseDetail house = buildHouseDetailModel();
        iHouseService.modifyHouseDetailOne(house);
    }

    @Test(expected = MessageRuntimeException.class)
    public void testModifyUncheckedTypeHouseDetailOne() {
        HouseDetail house = buildHouseDetailModel();
        house.setConfirmApartmentType("");
        house.setId(9);
        iHouseService.modifyHouseDetailOne(house);
    }


    @Test(expected = DBManipulateException.class)
    public void testModifyTypeHouseDetailOne() {
        HouseDetail house = buildHouseDetailModel();
        house.setConfirmApartmentType("1");
        iHouseService.modifyHouseDetailOne(house);
    }

    private HouseDetail buildHouseDetailModel() {
        HouseDetail house = new HouseDetail();
        house.setIdentifier("macbook air 2015");
        house.setContactId(2);
        house.setBannerImageUrls("e;b;c;d;");
        house.setAddress("addressZZZ");
        house.setHouseDescription("WWW.FACEBOOK.COM");
        return house;
    }
}