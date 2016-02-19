package net.dreameater.chatroomproject.classes;

public class Room {

    private String roomName;
    private boolean withinRange;

    public Room() {
        super();
    }

    public Room(String name) {
        super();
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
