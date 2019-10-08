package com.dwell.it.service.impl;

import com.dwell.it.dao.IProviderDao;
import com.dwell.it.entities.Provider;
import com.dwell.it.exception.DBManipulateException;
import com.dwell.it.exception.MessageRuntimeException;
import com.dwell.it.service.IProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProviderServiceImpl implements IProviderService {

    @Autowired
    private IProviderDao iProviderDao;


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean addNewProviderOne(Provider provider) {
        if (provider != null &&
                (provider.getName() != null && !"".equals(provider.getName())) &&
                provider.getDescriptions() != null) {
            try {
                int effectedNumber = iProviderDao.insertNewProvider(provider);
                if (effectedNumber > 0) {
                    return true;
                }
                throw new DBManipulateException("插入这条provider数据失败: addNewProviderOne()");
            } catch (Exception ex) {
                throw new DBManipulateException(String.format("插入这条provider数据失败: %s", ex.getMessage()));
            }
        }
        throw new DBManipulateException("插入这条provider数据失败: 信息记录不完整");
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Provider queryForSingleProvidertById(int id) {
        if (id < 1) {
            throw new MessageRuntimeException("中介供应商ID记录不正确");
        }

        try {
            Provider provider = iProviderDao.fetchSingleProviderById(id);
            if (provider != null)    return provider;

            throw new MessageRuntimeException("没有查询到该供应商ID对应的记录");
        } catch (Exception ex) {
            throw new MessageRuntimeException("没有查询到该供应商ID对应的记录");
        }
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean modifyProvider(Provider provider) {
        if (provider == null || provider.getId() < 1) {
            throw new DBManipulateException("修改这条provider数据失败：providerId不正确");
        }

        if ((provider.getName() != null && !"".equals(provider.getName())) && provider.getDescriptions() != null) {
            try {
                int effectedNumber = iProviderDao.updateProvider(provider);
                if (effectedNumber > 0) {
                    return true;
                }
                throw new DBManipulateException("修改这条provider数据失败");
            } catch (Exception ex) {
                throw new DBManipulateException(String.format("插入这条provider数据失败: %s", ex.getMessage()));
            }
        }
        throw new DBManipulateException("修改这条provider数据失败: 信息记录不完整");
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean removeProviderById(int id) {
        if (id < 1) {
            throw new DBManipulateException("删除这条provider数据失败：Id不存在");
        }

        try {
            int effectedNumber = iProviderDao.deleteProviderById(id);
            if (effectedNumber > 0) {
                return true;
            }
            throw new DBManipulateException("删除这条provider数据失败：removeProviderById()");
        } catch (Exception ex) {
            throw new DBManipulateException(String.format("删除这条provider数据失败: %s", ex.getMessage()));
        }
    }
}
