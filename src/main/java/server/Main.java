package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Main {

    private static final int PORT = 23456;

    public static void main(String[] args) throws IOException {
        //установить сокет на стороне сервера

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started!");

            //ожидать подключение клиента
            Socket socket = serverSocket.accept();

            PrintStream outStream = new PrintStream(socket.getOutputStream());
            BufferedReader intStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            String[] dataBase = new String[1000];

            //Передать данные полученные от клиента

            String commands = intStream.readLine();
            System.out.println("Client send: " + commands);
            //set 1 HelloWorld!
            //get 1
            String[] parsStr = commands.split(" ");
            Collection<String> collection = Arrays.asList(parsStr);


            if (parsStr[1].equals("set")) {
                int index = Integer.parseInt(parsStr[1]) - 1;
                if (index >= 0 && index <= 999) {

                    dataBase[index] =
                            collection.stream().skip(2).reduce((s1, s2) -> s1 + " " + s2).get();
                    outStream.println("OK");
                } else {
                    outStream.println("ERROR");

                }
                outStream.flush();
            }

            if (parsStr[1].equals("get")) {
                int index = Integer.parseInt(parsStr[1]) - 1;
                if (index >= 0
                        && index <= 999
                        && dataBase[index] != null) {
                    outStream.println(dataBase[index]);

                } else {
                    outStream.println("ERROR");
                }
            }


            if (parsStr[0].equals("delete")) {
                int index = Integer.parseInt(parsStr[1]) - 1;
                if (index >= 0 && index <= 999) {
                    dataBase[index] = null;
                    outStream.println("OK");
                } else {
                    outStream.println("ERROR");
                }
                outStream.flush();
            }

            if (parsStr[0].equals("exit")) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

