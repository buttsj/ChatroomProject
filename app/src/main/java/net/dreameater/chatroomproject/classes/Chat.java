package net.dreameater.chatroomproject.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chat implements Serializable {

    public List<Message> history;

    public Chat() {
        super();
        this.history = new ArrayList<>();
    }

    public void addToHistory(Message msg) {
        history.add(msg);
    }

    public List<Message> retreiveHistory() {
        return history;
    }

}
