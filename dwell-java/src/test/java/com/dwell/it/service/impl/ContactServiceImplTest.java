package com.dwell.it.service.impl;

import com.dwell.it.entities.Contact;
import com.dwell.it.exception.DBManipulateException;
import com.dwell.it.exception.MessageRuntimeException;
import com.dwell.it.service.IContactService;
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
public class ContactServiceImplTest {

    @Autowired
    private IContactService iContactService;


    @Test
    @Ignore
    public void testQueryForSingleContactById() {
        Contact contact = iContactService.queryForSingleContactById(2);
        assertTrue(contact.getName().equals("amazon"));
    }


    private Contact buildTestContactModel() {
        Contact contact = new Contact();
        contact.setName("Apple");
        contact.setTitle("apple");
        contact.setTelephone("666");
        contact.getAvatar();
        return contact;
    }


    @Test
    @Ignore
    public void testAddNewContactOne() {
        Contact contact = buildTestContactModel();
        contact.setProviderId(4);
        assertTrue(iContactService.addNewContactOne(contact));
    }


    @Test(expected = DBManipulateException.class)
    public void testAddNewErrorContact() {
        Contact contact = buildTestContactModel();
        contact.setProviderId(-1);
        iContactService.addNewContactOne(contact);
    }


    @Test
    @Ignore
    public void testModifyContact() {
        Contact contact = iContactService.queryForSingleContactById(8);
        contact.setName("IBM");
        assertTrue(iContactService.modifyContact(contact));
        Contact checkContact = iContactService.queryForSingleContactById(8);
        assertTrue(checkContact.getName().equals("IBM"));
    }


    @Test(expected = DBManipulateException.class)
    @Ignore
    public void testModifyContactNotInDataBase() {
        Contact contact = new Contact();
        contact.setId(2);
        contact.setName("");
        assertTrue(iContactService.modifyContact(contact));
    }


    @Test(expected = DBManipulateException.class)
    public void testRemoveEmptyContactById() {
        iContactService.removeContactById(-1);
    }


    @Test
    @Ignore
    public void testRemoveContactById() {
        assertTrue(iContactService.removeContactById(11));
    }


    @Test(expected = DBManipulateException.class)
    @Ignore
    public void testRemoveDeletedContactById() {
        assertTrue(iContactService.removeContactById(11));
    }


    @Test(expected = MessageRuntimeException.class)
    public void testFetchContactNotInDatabase() {
        iContactService.queryForSingleContactById(-1);
    }

}