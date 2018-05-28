package network;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetAddress;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {

    @Test
    public void checkResponse() throws IOException {
        InetAddress IP = InetAddress.getLocalHost();
        Client client = new Client();
        client.startConnection("192.168.0.13", 8000);
        String response = client.sendMessage("I am "+IP);
        client.stopConnection();
        //assertEquals("Hello Client", response);
    }
}