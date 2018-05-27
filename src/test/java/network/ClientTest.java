package network;

import org.junit.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {

    @Test
    public void checkResponse() throws IOException {
        Client client = new Client();
        client.startConnection("127.0.0.1", 8000);
        String response = client.sendMessage("Hello Server");
        assertEquals("Hello Client", response);
    }
}