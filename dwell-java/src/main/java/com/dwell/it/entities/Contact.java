package com.dwell.it.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_contacts")
@ToString
public class Contact {

    public static final String avatarUrl = "http://image1.ljcdn.com/rent-front-image/444b415acf9e282f34b8bebe56cabbc2.1512721489026_323fb813-60b3-440a-bb98-b8cf5ffc02eb";

    @Getter
    @Setter
    @Id
    private Integer id;

    private String avatar;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String title;

    @Getter @Setter
    private String telephone;

    @Getter @Setter
    private Integer providerId;


    public Contact(String avatar, String name, String title, String telephone, Integer providerId) {
        this.avatar = avatar;
        this.name = name;
        this.title = title;
        this.telephone = telephone;
        this.providerId = providerId;
    }

    public Contact() {
    }

    // 数据库中 设置一个默认的头像 【placeholder】
    public String getAvatar() {
        return (avatar != null && avatar.length() > 0) ? avatar : avatarUrl;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}