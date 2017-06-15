package com.red.innopolis;

import java.util.*;

/**
 * Created by _red_ on 15.06.17.
 */
public class ChatHistory {
    private List<Message> history;
    
    public ChatHistory() {
        this.history = new ArrayList<Message>(1000);
    }
    
    public void addMessage(Message message){
        if (this.history.size() > 1000){
            this.history.remove(0);
        }
        
        this.history.add(message);
    }
    
    public List<Message> getHistory(){
        return this.history;
    }
}
