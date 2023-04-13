package com.billmanagemtsystem.model;

import com.billmanagemtsystem.util.Utility;

public class CustomerModel
{

    private int id;

    private int meterId;

    private String name;

    private long phone;

    private String city;

    private String password;

    public CustomerModel(int meterId, String name, long phone, String city, String password)
    {

        this.id = Utility.getCustomerId();
        this.meterId = meterId;
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.password = password;
    }

    public String getPassword()
    {

        return password;
    }

    public CustomerModel(int meterId, long phone)
    {

        this.id = 0;
        this.meterId = meterId;
        this.phone = phone;
    }

    public int getId()
    {

        return id;
    }

    public void setId(int id)
    {

        this.id = id;
    }

}
