package net.dreameater.chatroomproject;

public class Room {

    private String roomName;
    private boolean withinRange;

    public Room(String name)
    {
        this.roomName = name;
        this.withinRange = false;
    }

    @Override
    public String toString() {
        return this.roomName;
    }

    public boolean isWithinRange() {
        return withinRange;
    }
}
