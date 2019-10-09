package com.dwell.it.dao;

import com.dwell.it.entities.Contact;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IContactDao {

    //  Basic CRUD Operations
    int insertNewContact(Contact contact);

    int updateContact(Contact contact);

    int deleteContactById(int id);

    Contact fetchSingleContactById(int id);

}