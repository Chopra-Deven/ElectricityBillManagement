package com.billmanagemtsystem.client;

import com.billmanagemtsystem.util.Constants;
import com.billmanagemtsystem.util.Message;
import com.billmanagemtsystem.util.Utility;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.SocketException;


public class AdminClient
{


    public static void main(String[] args)
    {

        try(BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in)))
        {

            boolean shouldBreak = true;

            while (shouldBreak)
            {

                System.out.println(Constants.LINE_SEPARATOR + "1 - Create User");

                System.out.println("2 - Create Bill");

                System.out.println("3 - View Bills");

                System.out.println("4 - Exit");

                System.out.print(Constants.LINE_SEPARATOR + "Enter your Choice : ");

                String choice = userInput.readLine();

                switch (choice)
                {

                    case "1":

                        System.out.print("\nEnter Meter Number : ");

                        String meterId = userInput.readLine();

                        System.out.print("\nEnter Phone Number : ");

                        String phone = userInput.readLine();

                        JSONObject request = new JSONObject();

                        request.put("reqCode", "1");

                        request.put("meterID", meterId);

                        request.put("phone", phone);

                        JSONObject response = Utility.sendRequest(request);

                        String message = (String) response.get("msg");

                        if (message.equals(Message.RESPONSE_STATUS_OK.getConstant()))
                            System.out.println(Constants.LINE_SEPARATOR + "User Created");
                        else
                            System.out.println(message);

                        break;

                    case "2":

                        System.out.print(Constants.LINE_SEPARATOR + "Enter Meter Number : ");

                        meterId = userInput.readLine();

                        System.out.print(Constants.LINE_SEPARATOR + "Enter Consumed Unit : ");

                        String consumedUnit = userInput.readLine();

                        request = new JSONObject();

                        request.put("reqCode", "2");

                        request.put("meterId", meterId);

                        request.put("consumedUnit", consumedUnit);

                        response = Utility.sendRequest(request);

                        message = (String) response.get("msg");

                        if (message.equals(Message.RESPONSE_STATUS_OK.getConstant()))

                            System.out.println(Constants.LINE_SEPARATOR + "Bill Created");

                        if (message.equals(Message.INVALID_METER_ID.getConstant()))

                            System.err.println(Constants.LINE_SEPARATOR + "Invalid Meter ID!");

                        else if (message.equals(Message.RESPONSE_STATUS_ERROR.getConstant()))
                            System.out.println(Constants.LINE_SEPARATOR + "Bill Not Created!");

                        else
                            System.out.println(Constants.LINE_SEPARATOR + message);

                        break;

                    case "3":

                        request = new JSONObject();

                        request.put("reqCode", "3");

                        response = Utility.sendRequest(request);

                        message = (String) response.get("msg");

                        if (message.equals(Message.RESPONSE_STATUS_OK.getConstant()))
                        {
                            JSONArray billArray = (JSONArray) response.get("bills");

                            Utility.display(billArray);
                        }

                        else
                        {
                            System.out.println(Constants.LINE_SEPARATOR + message + "Bill Not Found!");
                        }

                        break;

                    case "4":

                        shouldBreak = false;

                        break;

                    default:
                        System.err.println("\nInvalid Choice");

                }

            }
        }
        catch (SocketException e){
            System.err.println(Constants.LINE_SEPARATOR + Message.SERVER_UNREACHABLE.getConstant() + Constants.LINE_SEPARATOR);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}
