package net.dreameater.chatroomproject.classes;

import java.io.Serializable;

public class Room implements Serializable {

    private String roomName;
    private String imgName;
    private boolean favorite;
    private Chat chatTool;

    public Room(String name, String imgName) {
        super();
        this.roomName = name;
        this.imgName = imgName;
        this.favorite = false;
        this.chatTool = new Chat();
    }

    @Override
    public String toString() {
        return this.roomName;
    }

    public String getImg() { return this.imgName; }

    public void setImg(String img) {
        imgName = img;
    }

    public boolean isWithinRange() {
        return imgName.equals("green");
    }

    public boolean isFavorited() { return favorite; }

    public void sendMessage(Message msg) {
        chatTool.addToHistory(msg);
    }

    public Chat currentChat() {
        return chatTool;
    }
}
