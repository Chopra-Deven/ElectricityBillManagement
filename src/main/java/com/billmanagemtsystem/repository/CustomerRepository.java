package com.billmanagemtsystem.repository;

import com.billmanagemtsystem.model.CustomerModel;

import java.util.HashMap;


public class CustomerRepository implements RepositoryService
{

    private final HashMap<Integer, CustomerModel> CUSTOMERS = new HashMap<>();

    private static CustomerRepository instance;

    private CustomerRepository()
    {

    }

    public static CustomerRepository getInstance()
    {

        if (instance == null)
            instance = new CustomerRepository();

        return instance;
    }

    @Override
    public synchronized Object get(int id)
    {

        return CUSTOMERS.get(id);
    }

    @Override
    public synchronized Object get()
    {

        return null;
    }

    @Override
    public synchronized Object update(int id, Object obj)
    {

        return null;
    }

    @Override
    public synchronized Object add(int id, Object obj)
    {

        return CUSTOMERS.put(id, (CustomerModel) obj);

    }

}
