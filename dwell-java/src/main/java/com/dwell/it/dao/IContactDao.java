package com.dwell.it.dao;

import com.dwell.it.entities.Contact;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IContactDao {

    //  Basic CRUD Operations
    int insertNewContact(Contact contact);

    int updateContact(Contact contact);

    int deleteContactById(int id);

    Contact fetchSingleContactById(int id);


    // 反向查询联系人信息
    Contact searchTargetContact(@Param("name") String name,
                                @Param("telephone") String telephone,
                                @Param("providerId") Integer providerId);

}