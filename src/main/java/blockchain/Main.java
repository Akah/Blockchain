package blockchain;

import network.Client;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Client client = new Client();
        client.startConnection("127.0.0.1",8000);

        Block last = client.getLastBlock();
        System.out.println("first:");
        report(last);

        client.restart("127.0.0.1",8000);

        System.out.println("inbetween");

        client.requestAddBlock(generateBlock(last,"this is a new block"));
        report(client.getLastBlock());

        client.restart("127.0.0.1",8000);

        report(client.getNthBlock(3));

        System.out.println("\nsecond:");
        report(client.getLastBlock());
    }

    private static void report(Block block){
        System.out.println( "previous: " + block.getPreviousHash() +
                "\ncurrent: " + block.getCurrentHash() +
                "\nindex: " + block.getIndex() +
                "\nrecord: " + block.getRecord()
        );
    }

    private static Block generateBlock(Block oldBlock, String record){
        Block newBlock = new Block();
        newBlock.setIndex(oldBlock.getIndex()+1);
        newBlock.setTimestamp(System.currentTimeMillis());
        newBlock.setPreviousHash(oldBlock.getCurrentHash());
        newBlock.setRecord(record);
        newBlock.setCurrentHash(
            DigestUtils.sha256Hex(newBlock.getPreviousHash()+
                newBlock.getTimestamp()+
                newBlock.getRecord()+
                newBlock.getIndex())
        );

        return newBlock.isValid(oldBlock, newBlock) ? newBlock : null; // causes the null pointer error
    }
}
