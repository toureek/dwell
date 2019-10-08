package com.dwell.it.dao;

import com.dwell.it.entities.Contact;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IContactDaoTest {

    @Autowired
    private IContactDao iContactDao;


    @Test
    @Ignore
    public void testInsertNewContact() {
        Contact contact = new Contact();
        contact.setName("Facebook");
        contact.setTitle("FB");
        contact.setTelephone("400");
        contact.getAvatar();
        contact.setProviderId(3);
        int effectedNumber = iContactDao.insertNewContact(contact);
        assertTrue(effectedNumber == 1);
    }


    @Test
    @Ignore
    public void testFetchContactById() {
        Contact contact = iContactDao.fetchSingleContactById(2);
        assertTrue(contact.getName().equals("Amazon"));
    }


    @Test
    @Ignore
    public void testUpdateContact() {
        Contact contact = iContactDao.fetchSingleContactById(1);
        contact.setName("amazon");
        contact.setTitle("AWS");
        contact.setTelephone("222");
        contact.setAvatar("www.amazon.com");
        contact.setProviderId(2);
        int effectedNumber = iContactDao.updateContact(contact);
        assertTrue(effectedNumber == 1);
    }


    @Test
    @Ignore
    public void testDeleteContactById() {
        Contact contact = iContactDao.fetchSingleContactById(1);
        if (contact != null) {
            int effectedNumber = iContactDao.deleteContactById(1);
            assertTrue(effectedNumber == 1);
        }
    }

}