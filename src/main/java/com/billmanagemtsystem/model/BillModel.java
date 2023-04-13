package com.billmanagemtsystem.model;

import com.billmanagemtsystem.util.Utility;

import java.time.LocalDate;


public class BillModel
{

    private final int id;

    private int meterId;

    private int consumedUnit;

    private double amount;

    private String status;

    private final LocalDate createdOn;

    private final LocalDate dueDate;

    public BillModel(int id, int meterId, int consumedUnit, double amount, String status, LocalDate createdOn, LocalDate dueDate)
    {

        this.id = id;
        this.meterId = meterId;
        this.consumedUnit = consumedUnit;
        this.amount = amount;
        this.status = status;
        this.createdOn = createdOn;
        this.dueDate = dueDate;
    }

    public BillModel(int meterId, int consumedUnit)
    {

        this.id = Utility.getBillNumber();

        this.meterId = meterId;

        this.consumedUnit = consumedUnit;

        this.amount = Utility.calculateBill(consumedUnit);

        this.status = "Not Paid!";

        this.createdOn = Utility.getGeneratedDate();

        this.dueDate = Utility.getDueDate();

    }

    public int getId()
    {

        return id;
    }

    public int getMeterId()
    {

        return meterId;
    }

    public int getConsumedUnit()
    {

        return consumedUnit;
    }


    public double getAmount()
    {

        return amount;
    }

    public String getStatus()
    {

        return status;
    }

    public void setStatus(String status)
    {

        this.status = status;
    }

    public LocalDate getCreatedOn()
    {

        return createdOn;
    }

    public LocalDate getDueDate()
    {

        return dueDate;
    }


}
