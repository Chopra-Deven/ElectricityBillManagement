package com.billmanagemtsystem.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.Formatter;


public class Utility
{

    static int billNumber = 0;

    static int customerId = 0;

    public static int getBillNumber()
    {

        return ++billNumber;
    }

    public static int getCustomerId()
    {

        return ++customerId;
    }

    public static LocalDate getGeneratedDate()
    {

        System.out.println("Generated Date : " + LocalDate.now());

        return LocalDate.now();
    }

    public static LocalDate getDueDate()
    {

        System.out.println("DueDate : " + LocalDate.now().plusDays(Constants.DUE_DAYS));

        return LocalDate.now().plusDays(Constants.DUE_DAYS);

    }

    public static double calculateBill(int consumedUnit)
    {

        return consumedUnit * Constants.PER_UNIT_RATE;

    }

    public static void display(JSONArray array)
    {

        Formatter fmt = new Formatter();

        fmt.format("\n%-15s %-15s %-15s %-15s %-15s %-15s %-15s\n", "Bill ID", "Meter ID", "Consumed Unit", "Amount", "Generated On", "Created On", "Status");

        for (Object obj : array)
        {
            JSONObject bill = new JSONObject(obj.toString());

            fmt.format("%-15s %-15s %-15s %-15s %-15s %-15s %-15s\n", bill.get("id"), bill.get("meterId"), bill.get("consumedUnit"), bill.get("amount"), bill.get("createdOn"), bill.get("dueDate"), bill.get("status"));
        }
        System.out.println(fmt);

    }

    public static JSONObject sendRequest(JSONObject request)
    {

        JSONObject response = null;

        try(Socket client = new Socket(Constants.SERVER_ADDRESS, Constants.PORT); PrintWriter outputStream = new PrintWriter(client.getOutputStream(), true); BufferedReader inputStream = new BufferedReader(new InputStreamReader(client.getInputStream()));)
        {
            outputStream.println(request);

            response = new JSONObject(inputStream.readLine());
        }
        catch (Exception e)
        {
            if (response != null)

                response.put("msg",e.getMessage());
        }

        return response;
    }

}
