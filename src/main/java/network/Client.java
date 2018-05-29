package network;

import blockchain.Block;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(),true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    private String sendMessage(String msg) throws IOException{
        out.println(msg);
        return in.readLine();
    }

    public Block requestLastBlock() throws IOException, ClassNotFoundException {
        String response = sendMessage("01189998819991197253");
        return new Block().deserialise(response);
    }

    public void sendBlock(Block block) throws IOException {
        System.out.println(new Block().serialise(block));
        sendMessage(new Block().serialise(block));
        in.readLine();
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
