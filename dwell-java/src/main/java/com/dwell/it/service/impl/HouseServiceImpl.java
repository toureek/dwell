package com.dwell.it.service.impl;

import com.dwell.it.dao.IHouseDao;
import com.dwell.it.entities.House;
import com.dwell.it.entities.HouseDetail;
import com.dwell.it.exception.DBManipulateException;
import com.dwell.it.exception.MessageRuntimeException;
import com.dwell.it.service.IHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HouseServiceImpl implements IHouseService {

    @Autowired
    private IHouseDao iHouseDao;


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean addNewHouseOne(House house) {  // providerId有个外键依赖
        if (house != null && house.getProviderId() != null && house.getTitle() != null && house.getDetailPageUrl() != null
                && house.getTitle() != null && house.getMainImageUrl() != null && house.getLastUpdateTime() != null
                && house.getTradePrice() != null && house.getTradePriceUnit() != null) {
            try {
                int effectedNumber = iHouseDao.insertNewHouseObject(house);
                if (effectedNumber > 0) {
                    return true;
                }
                throw new DBManipulateException("插入这条house数据失败" + "URL:" + house.getDetailPageUrl());
            } catch (Exception ex) {
                throw new DBManipulateException(String.format("插入这条house数据失败: URL-%s %s", house.getDetailPageUrl(), ex.getMessage()));
            }
        }
        throw new DBManipulateException("插入这条house数据失败: 信息记录不完整");
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean modifyHouseDetailOne(HouseDetail houseDetail) {
        if (houseDetail == null || (houseDetail.getId() == null || houseDetail.getId() < 1)) {
            throw new DBManipulateException("这条house记录为空 或 ID信息不正确");
        }

        String type = houseDetail.getConfirmApartmentType();
        if (type == null || type.length() == 0) {
            throw new MessageRuntimeException("缺少ConfirmApartmentType参数");
        }

        if (type.equals("2")) {  // 住宅类型的数据更新
            return updateResidenceTypeHouse(houseDetail);
        }

        throw new DBManipulateException("修改这条house数据失败: type记录不完整");
    }


    @Transactional(propagation = Propagation.REQUIRED)
    private boolean updateResidenceTypeHouse(HouseDetail houseDetail) {
        try {
            int effectedNumber = iHouseDao.updateResidenceDetailObject(houseDetail);
            if (effectedNumber > 0) {
                return true;
            }
            throw new DBManipulateException("修改这条住宅类型的house数据失败");
        } catch (Exception ex) {
            throw new DBManipulateException(String.format("修改这条住宅类型的house数据失败: %s", ex.getMessage()));
        }
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean removeHouseById(int id) {
        if (id < 1) {
            throw new DBManipulateException("删除这条house数据失败：Id不存在");
        }

        try {
            int effectedNumber = iHouseDao.deleteHouseObjectById(id);
            if (effectedNumber > 0) {
                return true;
            }
            throw new DBManipulateException("删除这条公寓类型的house数据失败");
        } catch (Exception ex) {
            throw new DBManipulateException(String.format("删除这条住宅类型的house数据失败: %s", ex.getMessage()));
        }
    }




    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean batchAddNewHouseList(List<House> list) {
        if (list == null || list.size() == 0)    throw new DBManipulateException("没有要插入的数据");

        try {
            if (iHouseDao.batchInsertNewHouseList(list)) {
                return true;
            }
            throw new DBManipulateException(String.format("本次批处理插入数据失败"));
        } catch (Exception ex) {
            throw new DBManipulateException(String.format("本次批处理插入数据失败: %s", ex.getMessage()));
        }
    }



    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean isAllowedToInsertNewHouseRecord(String houseTitle, String detailPageURL) {
        if (detailPageURL == null || detailPageURL.trim().length() == 0) {
            throw new DBManipulateException("这条houseItem detailPageURL条件为空");
        }

        try {
            return iHouseDao.searchTargetHouseByTitleAndURL(houseTitle, detailPageURL) == null;
        } catch (Exception ex) {
            throw new DBManipulateException("这条houseItem detailPageURL 查询为空");
        }
    }



    // 二级页面的数据以来逻辑
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public House findTargetHouseByPageUrl(String pageURL) {
        if (pageURL == null || pageURL.length() == 0) {
            throw new DBManipulateException("这条house记录的 detailPageURL条件为空");
        }

        try {
            List<House> list = iHouseDao.searchHousesByPageURL(pageURL);
            if (list.size() > 0) {
                House house = list.get(0);
                return house;
            } else {
                throw new DBManipulateException(String.format("没有找到该url对应的记录"));
            }
        } catch (Exception ex) {
            throw new DBManipulateException(String.format("没有找到该url对应的记录: %s", ex.getMessage()));
        }
    }

}