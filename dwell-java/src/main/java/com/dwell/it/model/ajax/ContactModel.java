package com.dwell.it.model.ajax;

import java.io.Serializable;
import java.util.ArrayList;

public class ContactModel implements Serializable {

    private String id;

    private String contact_person_type;

    private String contact_name;

    private String contact_avatar;

    private String tp_number;

    private ArrayList picture_list;


    public ContactModel(String id, String contact_person_type, String contact_name, String contact_avatar, String tp_number, ArrayList picture_list) {
        this.id = id;
        this.contact_person_type = contact_person_type;
        this.contact_name = contact_name;
        this.contact_avatar = contact_avatar;
        this.tp_number = tp_number;
        this.picture_list = picture_list;
    }

    public ContactModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContact_person_type() {
        return contact_person_type;
    }

    public void setContact_person_type(String contact_person_type) {
        this.contact_person_type = contact_person_type;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_avatar() {
        return contact_avatar;
    }

    public void setContact_avatar(String contact_avatar) {
        this.contact_avatar = contact_avatar;
    }

    public String getTp_number() {
        return tp_number;
    }

    public void setTp_number(String tp_number) {
        this.tp_number = tp_number;
    }

    public ArrayList getPicture_list() {
        return picture_list;
    }

    public void setPicture_list(ArrayList picture_list) {
        this.picture_list = picture_list;
    }

    @Override
    public String toString() {
        return "ContactModel{" +
                "id='" + id + '\'' +
                ", contact_person_type='" + contact_person_type + '\'' +
                ", contact_name='" + contact_name + '\'' +
                ", contact_avatar='" + contact_avatar + '\'' +
                ", tp_number='" + tp_number + '\'' +
                ", picture_list=" + picture_list +
                '}';
    }
}