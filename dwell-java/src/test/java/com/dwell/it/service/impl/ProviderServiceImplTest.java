package com.dwell.it.service.impl;

import com.dwell.it.entities.Provider;
import com.dwell.it.exception.DBManipulateException;
import com.dwell.it.exception.MessageRuntimeException;
import com.dwell.it.service.IProviderService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProviderServiceImplTest {

    @Autowired
    private IProviderService iProviderService;



    @Test(expected = MessageRuntimeException.class)
    public void testQueryForSingleEmptyProvidertById() {
        iProviderService.queryForSingleProvidertById(-11);
    }


    @Test
    @Ignore
    public void testQueryForSingleProvidertById() {
        Provider provider = iProviderService.queryForSingleProvidertById(3);
        assertTrue("壹人壹家白领公寓".equals(provider.getName()));  // 数据库动态更新的 不一定每次ID为3的都是这个中介商
    }


    @Test(expected = DBManipulateException.class)
    @Ignore
    public void testAddNewProviderEmptyOne() {
        Provider provider = new Provider("name", null);
        iProviderService.addNewProviderOne(provider);
    }


    @Test
    @Ignore
    public void testAddNewProviderOne() {
        Provider provider = new Provider("name", "desc");
        boolean result = iProviderService.addNewProviderOne(provider);
        assertTrue(result);
    }


    @Test
    @Ignore
    public void testModifyProvider() {
        Provider provider = iProviderService.queryForSingleProvidertById(1);
        provider.setDescriptions("description");
        boolean result = iProviderService.modifyProvider(provider);
        assertTrue(result);
    }


    @Test(expected = DBManipulateException.class)
    public void testModifyEmptyProvider() {
        Provider provider = iProviderService.queryForSingleProvidertById(2);
        provider.setId(-11);
        iProviderService.modifyProvider(provider);
    }


    @Test(expected = DBManipulateException.class)
    public void testRemoveEmptyProviderById() {
        iProviderService.removeProviderById(-1);
    }


    @Test
    @Ignore
    public void testRemoveProviderById() {
        boolean result = iProviderService.removeProviderById(1);
        assertTrue(result);
    }

}