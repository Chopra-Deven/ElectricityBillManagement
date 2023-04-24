package com.billmanagemtsystem.server;

import com.billmanagemtsystem.model.BillModel;
import com.billmanagemtsystem.model.CustomerModel;
import com.billmanagemtsystem.services.BillService;
import com.billmanagemtsystem.services.CustomerService;
import com.billmanagemtsystem.util.Message;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;


public class ClientHandler extends Thread
{

    Socket client;

    public ClientHandler(Socket client)
    {

        this.client = client;

    }

    @Override
    public void run()
    {

        JSONObject response = new JSONObject();

        PrintWriter outputStream = null;

        BufferedReader inputStream = null;

        try
        {

            inputStream = new BufferedReader(new InputStreamReader(client.getInputStream()));

            outputStream = new PrintWriter(client.getOutputStream(), true);

            CustomerService customerService = new CustomerService();

            BillService billService = new BillService();

            JSONObject request = new JSONObject(inputStream.readLine());

            String requestCode = (String) request.get("reqCode");

            switch (requestCode)
            {

                case "1":

                    int meterId = Integer.parseInt((String) request.get("meterID"));

                    long phone = Long.parseLong((String) request.get("phone"));

                    CustomerModel customerModel = new CustomerModel(meterId, phone);

                    String message;

                    if (customerService.addCustomer(meterId, customerModel) == null)

                        message = (String) Message.RESPONSE_STATUS_OK.getConstant();

                    else

                        message = (String) Message.RESPONSE_STATUS_ERROR.getConstant();

                    response = new JSONObject();

                    response.put("resCode", "200");

                    response.put("msg", message);

                    outputStream.println(response);

                    break;

                case "2":

                    meterId = Integer.parseInt((String) request.get("meterId"));

                    int consumedUnit = Integer.parseInt((String) request.get("consumedUnit"));

                    response = new JSONObject();

                    if (customerService.isExist(meterId))
                    {

                        BillModel billModel = new BillModel(meterId, consumedUnit);

                        if (billService.addBill(billModel.getId(), billModel) == null)
                        {
                            message = (String) Message.RESPONSE_STATUS_OK.getConstant();

                            BillModel bean = (BillModel) billService.getById(billModel.getId());

                            response.put("id", bean.getId());

                            response.put("meterId", bean.getMeterId());

                            response.put("amount", bean.getAmount());

                            response.put("createdOn", bean.getCreatedOn());

                            response.put("dueDate", bean.getDueDate());

                            response.put("status", bean.getStatus());

                            response.put("msg", message);

                        }
                        else

                            message = (String) Message.RESPONSE_STATUS_ERROR.getConstant();
                    }
                    else
                    {
                        message = (String) Message.INVALID_METER_ID.getConstant();
                    }

                    response.put("msg", message);

                    outputStream.println(response);

                    break;

                case "3":

                    List<BillModel> bills = billService.getAll();

                    response = new JSONObject();

                    response.put("msg", Message.RESPONSE_STATUS_OK.getConstant());

                    response.put("bills", bills);

                    outputStream.println(response);

                    break;

                case "4":

                    meterId = Integer.parseInt((String) request.get("meterId"));

                    response = new JSONObject();

                    if (customerService.isExist(meterId) && !customerService.isCreated(meterId))
                    {

                        String name = (String) request.get("name");

                        String city = (String) request.get("city");

                        phone = Long.parseLong((String) request.get("phone"));

                        String password = (String) request.get("password");

                        customerModel = new CustomerModel(meterId, name, phone, city, password);

                        if (customerService.addCustomer(meterId, customerModel) != null)

                            message = (String) Message.RESPONSE_STATUS_OK.getConstant();

                        else

                            message = (String) Message.RESPONSE_STATUS_ERROR.getConstant();

                    }
                    else
                    {

                        if (!customerService.isExist(meterId))
                            message = (String) Message.INVALID_METER_ID.getConstant();

                        else
                            message = (String) Message.USER_ALREADY_CREATED.getConstant();

                    }

                    response.put("msg", message);

                    outputStream.println(response);

                    break;


                case "5":

                    meterId = Integer.parseInt((String) request.get("meterId"));

                    String password = (String) request.get("password");

                    response = new JSONObject();

                    if (customerService.login(meterId, password))
                        response.put("msg", Message.RESPONSE_STATUS_OK.getConstant());

                    else
                        response.put("msg", Message.RESPONSE_STATUS_ERROR.getConstant());

                    outputStream.println(response);

                    break;

                case "6":

                    meterId = request.getInt("meterId");

                    bills = billService.getAll(meterId);

                    response = new JSONObject();

                    if (bills != null)
                    {
                        message = (String) Message.RESPONSE_STATUS_OK.getConstant();

                        response.put("bills", bills);
                    }

                    else
                        message = (String) Message.RESPONSE_STATUS_ERROR.getConstant();

                    response.put("msg", message);

                    outputStream.println(response);

                    break;

                case "8":

                    int billId = Integer.parseInt((String) request.get("billId"));

                    boolean isPaid = billService.isPaid(billId);

                    boolean isExist = billService.isExist(billId);

                    response = new JSONObject();

                    System.out.println("Bill Id : " + billId);

                    System.out.println("is Paid : " + isPaid);

                    System.out.println("is Exist : " + isExist);


                    if (isExist && !isPaid)
                    {
                        if (billService.update(billId) != null)
                        {
                            System.out.println("All Condition satisfied for pay!!!!!!!!!");

                            message = (String) Message.RESPONSE_STATUS_OK.getConstant();
                        }

                        else
                            message = (String) Message.RESPONSE_STATUS_ERROR.getConstant();
                    }
                    else
                    {
                        if (isPaid)
                            message = (String) Message.ALREADY_PAID.getConstant();

                        else
                            message = (String) Message.INVALID_METER_ID.getConstant();

                    }

                    response.put("msg", message);

                    outputStream.println(response);

                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + requestCode);
            }

        }
        catch (Exception e)
        {

            response.put("msg", e.getMessage());

            if (outputStream != null)

                outputStream.println(response);

        }
        finally
        {
            try
            {
                client.close();

                if (inputStream != null)

                    inputStream.close();

                if (outputStream != null)
                    outputStream.close();

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }


    }

}
