package net.dreameater.chatroomproject.classes;


import java.util.ArrayList;
import java.io.Serializable;

public class Chat implements Serializable {

    private ArrayList<Message> history;

    public Chat() {
        super();
        this.history = new ArrayList<>();
    }

    public void addToHistory(Message msg) {
        history.add(msg);
    }

    public ArrayList<Message> retreiveHistory() {
        return history;
    }

}
