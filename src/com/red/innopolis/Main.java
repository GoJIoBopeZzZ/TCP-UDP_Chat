package com.red.innopolis;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int port = 1488;
        try {
            ServerSocket socket = new ServerSocket(port);
            System.out.println("Ждем коннекта ...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    Socket client = new Socket();
}
