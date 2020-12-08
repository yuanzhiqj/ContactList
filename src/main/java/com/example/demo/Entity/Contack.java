package com.example.demo.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Contack {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int curid;
    private String name;
    private String phone;
    private String email;
    private String qq;
    private String address;

    public Contack(String name, String phone, String email, String qq, String address) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.qq = qq;
        this.address = address;
    }

    public Contack(){

    }


    public int getCurid() {
        return curid;
    }

    public void setCurid(int curid) {
        this.curid = curid;
    }



    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getQq() {
        return qq;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
