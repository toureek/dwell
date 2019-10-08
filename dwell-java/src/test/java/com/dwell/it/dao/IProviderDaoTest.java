package com.dwell.it.dao;

import com.dwell.it.entities.Provider;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IProviderDaoTest {

    @Autowired
    private IProviderDao iProviderDao;


    @Test
    @Ignore
    public void testFetchSingleProviderById() {
        Provider provider = iProviderDao.fetchSingleProviderById(1);
        assertTrue(provider == null);
    }


    //    Fix数据库表的一个默认值： 把provider表的desc用SQL设置默认''
    //    因为，在这个UT中，desc如果为空，则throw a DataIntegrityViolationException
    @Test
    public void testInsertNewErrorProvider() {
        Provider provider = new Provider();
        provider.setName("Amazon service");
//        provider.safeProviderDescription();
        iProviderDao.insertNewProvider(provider);
    }


    @Test
    public void testInsertNewProvider() {
        Provider provider = new Provider("hp", "book_T400_Core2_4G_SSD_HDD");
        int effectedNumber = iProviderDao.insertNewProvider(provider);
        assertTrue(effectedNumber == 1);
    }


    @Test
    @Ignore
    public void testUpdateProvider() {
        Provider provider = iProviderDao.fetchSingleProviderById(2);
        provider.setName("new_IBM");
        provider.setDescriptions("IBM-Lenovo ThinkPad T60");
        int effectedNumber = iProviderDao.updateProvider(provider);
        assertTrue(effectedNumber > 0);
    }


    @Test
    @Ignore  // 找不到数据会抛出NullPointerException
    public void testUpdateErrorProvider() {
        Provider provider = iProviderDao.fetchSingleProviderById(2);
        provider.setId(-1);
        provider.setName("GOOGLE");
        provider.setDescriptions("HTC-GOOGLE G1");
        int effectedNumber = iProviderDao.updateProvider(provider);
        assertTrue(effectedNumber == 0);
    }


    @Test
    @Ignore
    public void testDeleteProviderById() {
        int effectedNumber = iProviderDao.deleteProviderById(2);
        assertTrue(effectedNumber == 1);
    }

}