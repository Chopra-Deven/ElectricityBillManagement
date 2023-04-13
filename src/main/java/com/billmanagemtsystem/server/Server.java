package com.billmanagemtsystem.server;

import com.billmanagemtsystem.util.Constants;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server
{

    ExecutorService pool;

    Socket client;

    int port;

    Server(int port)
    {

        this.port = port;

        pool = Executors.newFixedThreadPool(20);

    }

    public void startServer()
    {

        try (ServerSocket serverSocket = new ServerSocket(port))
        {

            while (true)
            {
                client = serverSocket.accept();

                ClientHandler newClient = new ClientHandler(client);

                pool.execute(newClient);

            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public static void main(String[] args)
    {

        Server server = new Server(Constants.PORT);

        server.startServer();

    }


}
