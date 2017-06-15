package com.red.innopolis;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by _red_ on 15.06.17.
 */
public class Client {
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    
    public Client(Socket socket){
        this.socket = socket;
    }
    
    public Client(Socket socket , ObjectOutputStream oos , ObjectInputStream ois ){
        this.socket = socket;
        this.oos = oos;
        this.ois = ois;
    }
    
    public Socket getSocket() {
        return this.socket;
    }
    
    public ObjectOutputStream getThisObjectOutputStream() {
        return this.oos;
    }
    
    public ObjectInputStream getThisObjectInputStream() {
        return this.ois;
    }
    
    public void setThisObjectOutputStream(ObjectOutputStream oos) {
        this.oos = oos;
    }
    
    public void setThisObjectInputStream(ObjectInputStream ois) {
        this.ois = ois;
    }
    
    public static void main (String[] args) throws IOException {
        int port = 1488;
//        try {
//            Client client = new Client(new Socket("127.0.0.1" , port));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        OutputStream
        System.out.println("Welcome to Client side");
        Socket fromserver = new Socket("127.0.0.1",port);
    
//        Message ms = new Message("GoJIoBopeZzZ" + new Random().nextInt(100) , "=)))))");
        try {
            Socket socket = new java.net.Socket("127.0.0.1", port);
    
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            Scanner scan = new Scanner(System.in);
            String message;
            while (!(message = scan.nextLine()).isEmpty()) {
                Message myMessage = new Message("", message);
                outputStream.writeObject(myMessage);
            }
            outputStream.flush();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
