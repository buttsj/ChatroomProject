package net.dreameater.chatroomproject.classes;

public class Room {

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
}
