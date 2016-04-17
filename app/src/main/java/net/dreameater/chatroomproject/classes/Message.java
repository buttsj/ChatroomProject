package net.dreameater.chatroomproject.classes;

// this class is a message that a user has sent in a chatroom
// keeps track of their message and the timestamp

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Message")
public class Message extends ParseObject {
    public static final String USER_ID_KEY = "userId";
    public static final String BODY_KEY = "body";

    public String getUserId() {
        return getString(USER_ID_KEY);
    }

    public String getBody() {
        return getString(BODY_KEY);
    }

    public void setUserId(String userId) {
        put(USER_ID_KEY, userId);
    }

    public void setBody(String body) {
        put(BODY_KEY, body);
    }
}

/*@ParseClassName("Message")
public class Message extends ParseObject {

    public static final String USER_ID_KEY = "userId";
    public static final String BODY_KEY = "body";

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

    @Override
    public String toString() {
        return message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getUserId() { return getString(USER_ID_KEY); }

    public String getBody() { return getString(BODY_KEY); }

    public void setUserId(String userId) { put(USER_ID_KEY, userId); }

    public void setBody(String body) { put(BODY_KEY, body); }
}*/
