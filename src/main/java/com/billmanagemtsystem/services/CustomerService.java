package com.billmanagemtsystem.services;

import com.billmanagemtsystem.model.CustomerModel;
import com.billmanagemtsystem.repository.CustomerRepository;
import com.billmanagemtsystem.repository.RepositoryService;


public class CustomerService
{

    RepositoryService repository = CustomerRepository.getInstance();

    public Object addCustomer(int id, Object bean)
    {

        return repository.add(id, bean);
    }

    public boolean isExist(int id)
    {

        return repository.get(id) != null;
    }

    public boolean isCreated(int id)
    {

        CustomerModel model = (CustomerModel) repository.get(id);

        return model.getId() != 0;

    }

    public boolean login(int id, String password)
    {

        CustomerModel model = (CustomerModel) repository.get(id);

        return model != null && password.equals(model.getPassword());

    }

}
