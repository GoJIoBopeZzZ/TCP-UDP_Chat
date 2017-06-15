package com.red.innopolis;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Timer;

import static com.red.innopolis.Server.getChatHistory;
import static com.red.innopolis.Server.getUserList;

/**
 * Created by _red_ on 15.06.17.
 */
public class ClientThread extends Thread{
    private final static int DELAY = 30000;
    
    private Socket socket;
    private Message c;
    private String login;
    private int inPacks = 0;
    private int outPacks = 0;
    private boolean flag = false;
    private Timer timer;
    
    public ClientThread(Socket socket) {
        this.socket = socket;
        this.start();
        timer = new Timer();
    }
    
    
    public void run() {
        try {
            final ObjectInputStream inputStream   = new ObjectInputStream(this.socket.getInputStream());
            final ObjectOutputStream outputStream = new ObjectOutputStream(this.socket.getOutputStream());
    
            try {
                this.c = (Message) inputStream.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.login = this.c.getLogin();
            
            
            if (! this.c.getMessage().equals("HELLOW MESSAGE")) {
                System.out.println("[" + this.c.getLogin() + "]: " + this.c.getMessage());
                getChatHistory().addMessage(this.c);
            } else {
                outputStream.writeObject(getChatHistory());
                this.broadcast(getUserList().getClientsList(), new Message("Server-Bot", "The user " + login + " has been connect"));
            }
            getUserList().addUser(login, socket, outputStream, inputStream);
            
            this.c.setOnlineUsers(getUserList().getUsers());
            this.broadcast(getUserList().getClientsList(), this.c);
            
            outputStream.writeObject(new Ping());
            this.outPacks++;
            System.out.println(outPacks + " out");
            
            while (true) {
                if(this.flag) {
                    this.flag = false;
                    break;
                }
                this.c = (Message) inputStream.readObject();
                System.out.println(c);
                if (this.c instanceof Ping) {
                    this.inPacks++;
                    System.out.println(this.inPacks + " in");
                    
                } else if (! c.getMessage().equals("HELLOW MESSAGE")) {
                    System.out.println("[" + login + "]: " + c.getMessage());
                    getChatHistory().addMessage(this.c);
                    
                } else {
                    outputStream.writeObject(getChatHistory());
                    this.broadcast(getUserList().getClientsList(), new Message("Server-Bot", "The user " + login + " has been connect"));
                }
                
                this.c.setOnlineUsers(getUserList().getUsers());
                
                if (! (c instanceof Ping) && ! c.getMessage().equals("HELLOW MESSAGE")) {
                    System.out.println("Send broadcast Message:" + " " + c.getMessage() + " ");
                    this.broadcast(getUserList().getClientsList(), this.c);
                }
            }
            
        } catch (SocketException e) {
            System.out.println(login + " disconnected!");
            getUserList().deleteUser(login);
            broadcast(getUserList().getClientsList(), new Message("Server-Bot", "The user " + login + " has been disconnect", getUserList().getUsers()));
        } catch (IOException e) {
            System.out.println("IO капать тут");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    
    private void broadcast(ArrayList<Client> clientsArrayList, Message message) {
        try {
            for (Client client : clientsArrayList) {
                client.getThisObjectOutputStream().writeObject(message);
            }
        } catch (SocketException e) {
            System.out.println("in broadcast: " + login + " disconnected!");
            getUserList().deleteUser(login);
            this.broadcast(getUserList().getClientsList(), new Message("Server-Bot", "The user " + login + " has been disconnected", getUserList().getUsers()));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
