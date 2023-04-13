package com.billmanagemtsystem.services;

import com.billmanagemtsystem.model.BillModel;
import com.billmanagemtsystem.repository.BillRepository;
import com.billmanagemtsystem.repository.RepositoryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public class BillService
{

    RepositoryService repository = BillRepository.getInstance();

    public Object addBill(int id, BillModel billModel)
    {

        return repository.add(id, billModel);
    }

    public List<BillModel> getAll()
    {

        HashMap<Integer, BillModel> map = (HashMap<Integer, BillModel>) repository.get();

        return new ArrayList<>(map.values());
    }

    public List<BillModel> getAll(int meterId)
    {
        HashMap<Integer, BillModel> map = (HashMap<Integer, BillModel>) repository.get();

        return map.values().stream().filter((val)
                -> val.getMeterId() == meterId).collect(Collectors.toList());
    }

    public boolean isPaid(int id)
    {

        BillModel bill = (BillModel) repository.get(id);

        return bill.getStatus().equals("Paid");

    }

    public boolean isExist(int id)
    {

        BillModel bill = (BillModel) repository.get(id);

        return bill != null;

    }

    public Object getById(int id)
    {

        return repository.get(id);
    }

    public Object update(int billId)
    {

        BillModel bill = (BillModel) repository.get(billId);

        if (bill != null)
        {

            System.out.println("inside if update bill service");

            bill.setStatus("Paid");

            return repository.update(billId, bill);
        }

        else
            return null;
    }


}
