package net.dreameater.chatroomproject;

import net.dreameater.chatroomproject.classes.Chat;
import net.dreameater.chatroomproject.classes.Message;
import net.dreameater.chatroomproject.classes.Room;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class RoomTest {

    @Test
    public void checkLocation() throws Exception{
        Room room = new Room("Caldwell",  40.0022382, -83.01503539999999, 1);
        double latitude = 40.0022382;
        double longitude = -83.01503539999999;

        assertTrue(room.gpsCheck(latitude, longitude));
    }
    @Test
    public void checkName() throws Exception{
        Room room = new Room("Caldwell",  40.0022382, -83.01503539999999, 1);
        String testName = "Caldwell";

        assertEquals(testName, room.toString());
    }
    @Test
    public void checkActive() throws Exception{
        Room room = new Room("Caldwell",  40.0022382, -83.01503539999999, 1);
        room.setImg("green");

        assertTrue(room.isWithinRange());
    }
    @Test
    public void checkInactive() throws Exception{
        Room room = new Room("Caldwell",  40.0022382, -83.01503539999999, 1);
        room.setImg("red");

        assertFalse(room.isWithinRange());
    }
    @Test
    public void sendMessage() throws Exception{
        Room room = new Room("Caldwell",  40.0022382, -83.01503539999999, 1);

        Long timestamp = new Long(10);
        String message = "Hello, is it me you're looking for?";

        Message msg = new Message(message, timestamp);

        room.sendMessage(msg);
        List<Message> history = room.chatTool.retreiveHistory();

        assertEquals(msg.toString(), history.get(0).toString());
    }
    @Test
    public void receiveMessage() throws Exception{
        Room room = new Room("Caldwell",  40.0022382, -83.01503539999999, 1);

        Long timestamp = new Long(10);
        String message = "Hello, is it me you're looking for?";

        Message msg = new Message(message, timestamp);

        room.sendMessage(msg);
        List<Message> history = room.currentChat().retreiveHistory();

        assertEquals(msg.toString(), history.get(0).toString());
    }
}