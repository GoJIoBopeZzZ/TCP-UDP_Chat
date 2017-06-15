package com.red.innopolis;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by _red_ on 15.06.17.
 */
public class Server {
    private static UsersList list = new UsersList();
    private static ChatHistory chatHistory = new ChatHistory();
    
    public static void main(String[] args) {
        try {
            //Создаем слушатель
            int port = 1488;
            ServerSocket socketListener = new ServerSocket(port);
            
            while (true) {
                Socket client = null;
                while (client == null) {
                    client = socketListener.accept();
                }
                new ClientThread(client); //Создаем новый поток, которому передаем сокет
            }
        } catch (SocketException e) {
            System.err.println("Socket exception");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("I/O exception");
            e.printStackTrace();
        }
    }
    
    public synchronized static UsersList getUserList() {
        return list;
    }
    
    public synchronized static ChatHistory getChatHistory() {
        return chatHistory;
    }
}
