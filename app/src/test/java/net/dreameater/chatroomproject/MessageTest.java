package net.dreameater.chatroomproject;

import net.dreameater.chatroomproject.classes.Chat;
import net.dreameater.chatroomproject.classes.Message;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class MessageTest {

    @Test
    public void checkMessageMethods() throws Exception{

        Long timestamp = new Long(10);
        String message = "Hello, is it me you're looking for?";

        Message msg = new Message(message, timestamp);

        Long msgTimeStamp = msg.getTimeStamp();
        String msgMessage = msg.toString();

        assertEquals(msgTimeStamp, timestamp);
        assertEquals(msgMessage, message);
    }
}