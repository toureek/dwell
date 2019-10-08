package com.dwell.it.entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@ToString
@Table(name = "t_providers")
public class Provider {

    @Id
    private Integer id;

    private String name;

    private String descriptions;


    public Provider() {
        this.descriptions = "";
    }

    public Provider(String name, String descriptions) {
        this.name = name;
        this.descriptions = descriptions.length() > 0 ? descriptions : "";
    }
}