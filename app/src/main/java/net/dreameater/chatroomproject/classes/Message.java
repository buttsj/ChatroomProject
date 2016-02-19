package net.dreameater.chatroomproject.classes;

// this class is a message that a user has sent in a chatroom
// keeps track of their message and the timestamp

public class Message {

    private String message;
    private long timeStamp;

    public Message(){
        super();
    }

    public Message(String message, long timeStamp){
        super();
        this.message   = message;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
