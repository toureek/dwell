package com.dwell.it.service.impl;

import com.dwell.it.dao.IContactDao;
import com.dwell.it.entities.Contact;
import com.dwell.it.exception.DBManipulateException;
import com.dwell.it.exception.MessageRuntimeException;
import com.dwell.it.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactServiceImpl implements IContactService {


    @Autowired
    private IContactDao iContactDao;  //TODO: ProviderService Text-String Hardcode-Format


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean addNewContactOne(Contact contact) {
        if ((contact.getName() != null && !"".equals(contact.getName())) &&
                (contact.getTitle() != null && !"".equals(contact.getTitle())) &&
                (contact.getTelephone() != null && !"".equals(contact.getTelephone())) &&
                (contact.getProviderId() != null && contact.getProviderId() > 0)) {
            try {
                int effectedNumber = iContactDao.insertNewContact(contact);
                if (effectedNumber > 0) {
                    return true;
                }
                throw new DBManipulateException("插入这条contact数据失败");
            } catch (Exception ex) {
                throw new DBManipulateException(String.format("插入这条contact数据失败: %s", ex.getMessage()));
            }
        }
        throw new DBManipulateException("插入这条contact数据失败: 信息记录不完整");
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Contact queryForSingleContactById(int id) {
        if (id < 1) {
            throw new MessageRuntimeException("联系人ID不合法 请输入正确的联系人ID");
        }

        try {
            Contact contact = iContactDao.fetchSingleContactById(id);
            if (contact == null) {
                throw new MessageRuntimeException("数据库中暂无这条联系人记录");
            }
            return contact;
        } catch (Exception ex) {
            throw new MessageRuntimeException("数据库中暂无这条联系人记录");
        }
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean modifyContact(Contact contact) {
        if (contact.getId() == null || contact.getId() < 1) {
            throw new DBManipulateException("数据库没有这条记录");
        }

        if ((contact.getName() != null && !"".equals(contact.getName())) &&
                (contact.getTitle() != null && !"".equals(contact.getTitle())) &&
                (contact.getTelephone() != null && !"".equals(contact.getTelephone())) &&
                (contact.getProviderId() != null && contact.getProviderId() > 0)) {
            try {
                int effectedNumber = iContactDao.updateContact(contact);
                if (effectedNumber > 0) {
                    return true;
                }
                throw new DBManipulateException("修改这条contact数据失败");
            } catch (Exception ex) {
                throw new DBManipulateException(String.format("修改这条contact数据失败: %s", ex.getMessage()));
            }
        }
        throw new DBManipulateException("修改这条contact数据失败: 记录信息不完整");
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean removeContactById(int id) {
        if (id < 1) {
            throw new DBManipulateException("删除这条contact数据失败 Id不存在");
        }

        try {
            int effectedNumber = iContactDao.deleteContactById(id);
            if (effectedNumber > 0) {
                return true;
            }
            throw new DBManipulateException("删除这条contact数据失败");
        } catch (Exception ex) {
            throw new DBManipulateException(String.format("删除这条contact数据失败: %s", ex.getMessage()));
        }
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Contact searchingTargetContact(String name, String telephone, Integer providerId) {
        if (name == null || telephone == null || providerId == null) return null;

        if (name.length() == 0 || telephone.length() == 0 || providerId < 1) return null;

        try {
            Contact contact = iContactDao.searchTargetContact(name, telephone, providerId);
            if (contact != null) {
                return contact;
            }
            return null;
        } catch (Exception ex) {
            throw new MessageRuntimeException(ex.getMessage());
        }
    }

}
