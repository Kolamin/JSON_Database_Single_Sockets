package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.net.*;
import java.io.*;

public class Main {

    @Parameter(names = {"-t"})
    String request_type;

    @Parameter(names = {"-i"})
    int index;

    @Parameter(names = {"-m"})
    String record;


    private static final String address = "127.0.0.1";
    private static final int port = 23456;

    public static void main(String[] args) {

        Socket socket = null;

        try {
            socket = new Socket(address, port);

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream output = new PrintStream(socket.getOutputStream());

            Main main = new Main();

            JCommander.newBuilder().addObject(main).build().parse(args);

            String msg = main.run();

            output.println(msg); //отправить сообщение на сервер

            String receiveMsg = input.readLine(); //принять сообщение
            System.out.println("Client started!");
            System.out.println("Sent: " + msg);
            System.out.println("Received: " + receiveMsg);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String run() {
        return String.format("%s %d %s", request_type, index, record);
    }
}
