package net.dreameater.chatroomproject;

public class Room {

    private String roomName;
    private boolean withinRange;

    public Room() {
        super();
    }

    public Room(String name) {
        super();
        this.roomName = name;
    }

    @Override
    public String toString() {
        return this.roomName;
    }

    public boolean isWithinRange() {
        return withinRange;
    }
}
