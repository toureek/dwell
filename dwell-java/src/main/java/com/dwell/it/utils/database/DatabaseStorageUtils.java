package com.dwell.it.utils.database;

import com.dwell.it.entities.House;
import com.dwell.it.entities.HouseDetail;
import com.dwell.it.entities.Provider;
import com.dwell.it.exception.DBManipulateException;
import com.dwell.it.exception.InternalMethodInvokeException;
import com.dwell.it.service.IContactService;
import com.dwell.it.service.IHouseService;
import com.dwell.it.service.IProviderService;
import com.dwell.it.service.impl.ProviderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class DatabaseStorageUtils {

    public static DatabaseStorageUtils databaseStorageUtils;

    @Autowired
    private IHouseService iHouseService;

    @Autowired
    private IProviderService iProviderService;

    @Autowired
    private IContactService iContactService;

    @PostConstruct
    public void init() {
        databaseStorageUtils = this;
    }


    /**
     * 向数据库批量插入house
     *
     * @param list houseList
     */
    public static void batchInsertHouseItemsInDatabase(List<House> list) throws InternalMethodInvokeException {
        try {
            databaseStorageUtils.iHouseService.batchAddNewHouseList(list);
        } catch (DBManipulateException ex) {
            String runningMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
            throw new InternalMethodInvokeException(InternalMethodInvokeException.INTERNAL_METHOD_INVOKE_PREFIX + "" + runningMethodName + "()");
        }
    }


    /**
     * 一级页面数据存入数据库的操作
     *
     * @param house 一级页面的houseItem
     * @return success or failure
     */
    public static boolean isHouseTableForeignKeyProviderIdExisted(House house) {
        if (house == null) return false;

        Integer providerId = insertNewProviderRecord(new Provider(house.getProviderName(), ""));
        if (providerId > 0) {
            house.setProviderId(providerId);
        }
        return databaseStorageUtils.iHouseService.isAllowedToInsertNewHouseRecord(house.getTitle(), house.getDetailPageUrl());
    }


    /**
     * 解决t_houses表中providerid的依赖
     *
     * @param provider 中介品牌供应商
     * @return providerId
     * 解决t_houses表中providerId的外键依赖： 考虑到house的数据量大，而中介商的数据量必然会少很多，有可能会少一个量级，这里没采用批处理操作
     */
    private static Integer insertNewProviderRecord(Provider provider) {
        if (provider == null) return -1;

        // 如果没有这条provider的记录 就先插入这条provider记录 然后再返回这条记录的主键； 如果有这条provider记录 直接返回主键
        if (databaseStorageUtils.iProviderService.isAllowedInsertNewOne(provider.getName()) == 0) {  // DB中没有这条记录
            if (provider.getName().length() == 0) {  // 如果列表页面没有显示中介运营商的信息， 那么先给name个默认值
                provider.setName(ProviderServiceImpl.providerUnknownName);
            }
            databaseStorageUtils.iProviderService.addNewProviderOne(provider);
        }
        return databaseStorageUtils.iProviderService.isAllowedInsertNewOne(provider.getName());
    }


    /**
     * 只处理数据库中已存在的数据(由列表页面获取并存入数据库的)， 这里是反向查询(由url，反向查询house)
     *
     * @param url 详情页面url
     * @return 返回一级页面的数据类型 house对象
     */
    public static House searchingTargetHouseByPageURL(String url) {
        if (url == null || url.length() == 0) return null;

        return databaseStorageUtils.iHouseService.findTargetHouseByPageUrl(url);
    }


    /**
     * 扩展单条house记录的详情信息 （之前在访问列表页面的时候，数据已经插入数据库了，当请求完详情页后，更新之前house不完整的数据为houseDetail）
     *
     * @param houseDetail 详情页面的数据信息
     */
    public static void updateHouseInfoForDetails(HouseDetail houseDetail) {
        databaseStorageUtils.iHouseService.modifyHouseDetailOne(houseDetail);
    }
}
