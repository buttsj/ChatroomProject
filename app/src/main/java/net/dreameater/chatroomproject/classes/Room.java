package net.dreameater.chatroomproject.classes;

import java.io.Serializable;

public class Room implements Serializable {

    private String roomName;
    private boolean withinRange;
    private boolean favorite;
    private Chat chatTool;

    public Room() {
        super();
    }

    public Room(String name) {
        super();
        this.roomName = name;
        this.withinRange = false;
        this.favorite = false;
        this.chatTool = new Chat();
    }

    @Override
    public String toString() {
        return this.roomName;
    }

    public boolean isWithinRange() {
        return withinRange;
    }

    public boolean isFavorited() { return favorite; }

    public void sendMessage(Message msg) {
        chatTool.addToHistory(msg);
    }

    public Chat currentChat() {
        return chatTool;
    }
}
