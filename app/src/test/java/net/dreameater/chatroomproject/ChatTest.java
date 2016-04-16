package net.dreameater.chatroomproject;

import net.dreameater.chatroomproject.classes.Chat;
import net.dreameater.chatroomproject.classes.Message;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ChatTest {

    @Test
    public void chatHistoryRetrieval() throws Exception{
        Chat chat = new Chat();
        List<Message> history = chat.history;
        List<Message> retHistory = chat.retreiveHistory();

        assertEquals(history, retHistory);
    }
    @Test
    public void addMessageToHistory() throws Exception{

        Long timestamp = new Long(10);
        String message = "Hello, is it me you're looking for?";

        Message msg = new Message(message, timestamp);
        Chat chat = new Chat();

        Long msgTimeStamp = msg.getTimeStamp();
        String msgMessage = msg.toString();

        chat.addToHistory(msg);

        List<Message> history = chat.history;
        List<Message> retHistory = chat.retreiveHistory();

        assertEquals(history, retHistory);
    }
}