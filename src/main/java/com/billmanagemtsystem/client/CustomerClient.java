package com.billmanagemtsystem.client;

import com.billmanagemtsystem.util.Constants;
import com.billmanagemtsystem.util.Message;
import com.billmanagemtsystem.util.Utility;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class CustomerClient
{

    private static void mainMenu(int meterId, BufferedReader userInput) throws IOException
    {

        PrintWriter outputStream = null;

        BufferedReader inputStream = null;

        boolean shouldBreak = true;

        while (shouldBreak)
        {

            System.out.println(Constants.LINE_SEPARATOR + "1 - View All Bills");
            System.out.println("2 - Pay Bills");
            System.out.println("3 - Exit");

            System.out.print(Constants.LINE_SEPARATOR + "Enter your Choice : ");

            String choice = userInput.readLine();

            switch (choice)
            {
                case "1":

                    try (Socket client = new Socket(Constants.SERVER_ADDRESS, Constants.PORT))
                    {

                        outputStream = new PrintWriter(client.getOutputStream(), true);

                        inputStream = new BufferedReader(new InputStreamReader(client.getInputStream()));

                        JSONObject request = new JSONObject();

                        request.put("reqCode", "6");

                        request.put("meterId", meterId);

                        outputStream.println(request);

                        JSONObject response = new JSONObject(inputStream.readLine());

                        if (response.get("msg").equals(Message.RESPONSE_STATUS_OK.getConstant()))
                        {
                            JSONArray billArray = (JSONArray) response.get("bills");

                            Utility.display(billArray);
                        }

                        else
                            System.out.println(Constants.LINE_SEPARATOR + "Unable to fetch Bill");

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        if (outputStream != null)
                            outputStream.close();

                        if (inputStream != null)
                            inputStream.close();
                    }

                    break;


                case "2":

                    System.out.println(Constants.LINE_SEPARATOR + "Enter Invoice Number : ");

                    String billId = userInput.readLine();

                    try (Socket client = new Socket(Constants.SERVER_ADDRESS, Constants.PORT))
                    {

                        outputStream = new PrintWriter(client.getOutputStream(), true);

                        inputStream = new BufferedReader(new InputStreamReader(client.getInputStream()));

                        JSONObject request = new JSONObject();

                        request.put("reqCode", "8");

                        request.put("billId", billId);

                        request.put("meterId", meterId);

                        outputStream.println(request);

                        JSONObject response = new JSONObject(inputStream.readLine());

                        String message = (String) response.get("msg");

                        if (message.equals(Message.RESPONSE_STATUS_OK.getConstant()))
                        {
                            System.out.println(Constants.LINE_SEPARATOR + "Bill Paid Successful");
                        }
                        else if (message.equals(Message.ALREADY_PAID.getConstant()))

                            System.out.println(Constants.LINE_SEPARATOR + "Bill is already Paid");

                        else if (message.equals(Message.INVALID_METER_ID.getConstant()))

                            System.out.println(Constants.LINE_SEPARATOR + "Invalid Meter ID!");

                        else
                            System.err.println(Constants.LINE_SEPARATOR + "Something went wrong, Bill not Paid!!");

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        if (outputStream != null)
                            outputStream.close();

                        if (inputStream != null)
                            inputStream.close();
                    }


                    break;

                case "3":
                    shouldBreak = false;

                    break;

                default:
                    System.err.println(Constants.LINE_SEPARATOR + Message.INVALID_CHOICE.getConstant() + Constants.LINE_SEPARATOR);


            }
        }

    }

    public static void main(String[] args)
    {

        try (BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in)))
        {

            boolean shouldBreak = true;

            while (shouldBreak)
            {

                System.out.println(Constants.LINE_SEPARATOR + "1 - Create Account");

                System.out.println("2 - Login");

                System.out.println("3 - Exit");

                System.out.print(Constants.LINE_SEPARATOR + "Enter Your Choice : ");

                String choice = userInput.readLine();

                switch (choice)
                {

                    case "1":

                        System.out.print(Constants.LINE_SEPARATOR + "Enter Meter ID : ");

                        String meterId = userInput.readLine();

                        System.out.print(Constants.LINE_SEPARATOR + "Enter Your Name : ");

                        String name = userInput.readLine();

                        System.out.print(Constants.LINE_SEPARATOR + "Enter Phone : ");

                        String phone = userInput.readLine();

                        System.out.print(Constants.LINE_SEPARATOR + "Enter City : ");

                        String city = userInput.readLine();

                        System.out.print(Constants.LINE_SEPARATOR + "Create Password : ");

                        String password = userInput.readLine();

                        JSONObject request = new JSONObject();

                        request.put("reqCode", "4");

                        request.put("meterId", meterId);

                        request.put("name", name);

                        request.put("city", city);

                        request.put("phone", phone);

                        request.put("password", password);

                        JSONObject response = Utility.sendRequest(request);

                        String message = (String) response.get("msg");

                        if (message.equals(Message.RESPONSE_STATUS_OK.getConstant()))

                            System.out.println(Constants.LINE_SEPARATOR + "User Created. Please Login to Continue." + Constants.LINE_SEPARATOR);

                        else if (message.equals(Message.INVALID_METER_ID.getConstant()))

                            System.err.println(Constants.LINE_SEPARATOR + "Invalid Meter Number!!" + Constants.LINE_SEPARATOR);

                        else if (message.equals(Message.USER_ALREADY_CREATED.getConstant()))

                            System.err.println(Constants.LINE_SEPARATOR + "User is already created with Meter Number : " + meterId + Constants.LINE_SEPARATOR);

                        else

                            System.err.println(Constants.LINE_SEPARATOR + "User Not Created! Please Try Again." + Constants.LINE_SEPARATOR);


                        break;

                    case "2":

                        System.out.print(Constants.LINE_SEPARATOR + "Enter Meter Number : ");

                        meterId = userInput.readLine();

                        System.out.print(Constants.LINE_SEPARATOR + "Enter Password : ");

                        password = userInput.readLine();

                        request = new JSONObject();

                        request.put("reqCode", "5");

                        request.put("meterId", meterId);

                        request.put("password", password);

                        response = Utility.sendRequest(request);

                        message = (String) response.get("msg");

                        if (message.equals(Message.RESPONSE_STATUS_OK.getConstant()))
                        {

                            System.out.println(Constants.LINE_SEPARATOR + "Login Successful." + Constants.LINE_SEPARATOR);

                            mainMenu(Integer.parseInt(meterId), userInput);
                        }
                        else

                            System.err.println(Constants.LINE_SEPARATOR + "Invalid Credential" + Constants.LINE_SEPARATOR);


                        break;

                    case "3":

                        shouldBreak = false;

                        break;

                    default:

                        System.err.println(Constants.LINE_SEPARATOR + "Invalid Choice!!" + Constants.LINE_SEPARATOR);

                }

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}
