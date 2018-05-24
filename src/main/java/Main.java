import org.apache.commons.codec.digest.DigestUtils;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args){
        // Hard coded genesis block
        Block genesis = new Block();
        genesis.setIndex(0);
        genesis.setTimestamp(System.currentTimeMillis());
        genesis.setCurrentHash(DigestUtils.sha256Hex("Genesis"));
        genesis.setPreviousHash(DigestUtils.sha256Hex("Genesis"));
        genesis.setRecord("genesis");

        LinkedList<Block> blockchain = new LinkedList<>();

        blockchain.add(genesis);
        blockchain.add(generateBlock(genesis,"appended"));
        blockchain.add(generateBlock(blockchain.getLast(),"record"));

        //print blockchain
        blockchain.forEach(Main::report);
    }

    private static void report(Block block){
        System.out.println("\nprevious: " + block.getPreviousHash());
        System.out.println("current: " + block.getCurrentHash());
        System.out.println("index: " + block.getIndex());
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
                        newBlock.getIndex()
                )
        );
        if(newBlock.isValid(oldBlock,newBlock)) {
            return newBlock;
        } else {
            return null;
        }
    }
}
