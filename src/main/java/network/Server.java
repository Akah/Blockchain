package network;

import blockchain.Block;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private LinkedList<Block> blockchain = new LinkedList<>();

    public void start(int port) throws IOException, ClassNotFoundException {

        // Hard coded genesis block this should be in the blockchain file... not in the start up code
        Block genesis = new Block();
        genesis.setIndex(0);
        genesis.setTimestamp(System.currentTimeMillis());
        genesis.setCurrentHash(DigestUtils.sha256Hex("Digest"));
        genesis.setRecord("genesis");
        blockchain.add(genesis);

        serverSocket = new ServerSocket(port);
        while (true) {
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String message = in.readLine();

            switch (message) {
                case "01189998819991197253":
                    out.println(new Block().serialise(blockchain.getLast()));
                    break;

                default:
                    Block block = new Block().deserialise(message);
                    blockchain.add(block);
                    out.println("block added");
                    break;
            }

            out.println(message);
            System.out.println("1 "+message);
        }
    }

    public void stop() throws IOException{
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

    public static void main (String[] args) throws IOException, ClassNotFoundException {
        Server server = new Server();
        server.start(8000);
    }
}
