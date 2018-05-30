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

    private String sendMessage(int code, String msg) throws IOException{
        out.println(code+msg);
        return in.readLine();
    }
    private String sendMessage(int code) throws IOException {
        out.println(code);
        return in.readLine();
    }

    public Block getLastBlock() throws IOException, ClassNotFoundException {
        String response = sendMessage(100);
        return new Block().deserialise(response);
    }

    public Block getNthBlock(int n) throws IOException, ClassNotFoundException{
        String response = sendMessage(101, Integer.toString(n));
        return new Block().deserialise(response);
    }

    public void requestAddBlock(Block block) throws IOException {
        System.out.println(sendMessage(200, new Block().serialise(block)));
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public void restart(String ip, int port) throws IOException {
        stopConnection();
        startConnection(ip,port);
    }
}
