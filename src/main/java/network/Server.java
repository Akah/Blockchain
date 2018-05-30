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
        int i = 0;
        serverSocket = new ServerSocket(port);
        while (true) {
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String message = in.readLine();
            String code = message.substring(0,3);
            String body = message.substring(3,message.length());

            System.out.println("client said ("+i+"): "+message);
            switch (code) {
                case "100":
                    out.println(new Block().serialise(blockchain.getLast()));
                    System.out.println("code: 100");
                    System.out.println("response :"+new Block().serialise(blockchain.getLast()));
                    break;

                case "101":
                    System.out.println("code: 101");
                    out.println(new Block().serialise(blockchain.get(Integer.valueOf(body))));
                    break;

                case "200":
                    System.out.println("code: 200");
                    Block block = new Block().deserialise(body);
                    blockchain.add(block);
                    out.println("block added");
                    System.out.println("response :block added");
                    break;
            }
            i++;
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
