package com.billmanagemtsystem.repository;

import com.billmanagemtsystem.model.BillModel;

import java.util.HashMap;


public class BillRepository implements RepositoryService
{

    private static BillRepository instance;

    private static final HashMap<Integer, BillModel> BILLS = new HashMap<>();

    private BillRepository()
    {

    }

    public static BillRepository getInstance()
    {

        if (instance == null)

            instance = new BillRepository();

        return instance;
    }

    @Override
    public synchronized Object get(int id)
    {

        return BILLS.get(id);
    }

    @Override
    public synchronized Object get()
    {

        return new HashMap<>(BILLS);

    }

    @Override
    public synchronized Object update(int id, Object obj)
    {

        return BILLS.put(id, (BillModel) obj);
    }

    @Override
    public synchronized Object add(int id, Object obj)
    {

        return BILLS.put(id, (BillModel) obj);
    }


}
