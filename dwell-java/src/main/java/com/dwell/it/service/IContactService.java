package com.dwell.it.service;

import com.dwell.it.entities.Contact;

public interface IContactService {

    // Basic
    boolean addNewContactOne(Contact contact);

    Contact queryForSingleContactById(int id);

    boolean modifyContact(Contact contact);

    boolean removeContactById(int id);


    // 反向查询连信任信息
    Contact searchingTargetContact(String name, String telephone, Integer providerId);
}
