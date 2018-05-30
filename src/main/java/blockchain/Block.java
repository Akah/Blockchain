package blockchain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

public class Block implements Serializable {
    private String previousHash;
    private String currentHash;
    private String record;
    private long timestamp;
    private int index;

    public void setPreviousHash(String previousHash) { this.previousHash = previousHash; }
    public void setCurrentHash(String currentHash) { this.currentHash = currentHash; }
    public void setRecord(String record) { this.record = record; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    public void setIndex(int index) { this.index = index; }

    public String getPreviousHash() { return previousHash; }
    public String getCurrentHash() { return currentHash; }
    public String getRecord() { return record; }
    public int    getIndex() { return index; }
    public long   getTimestamp() { return timestamp; }


    public boolean isValid(Block oldBlock, Block newBlock){
        return oldBlock.getIndex()!=newBlock.getIndex() &&
                oldBlock.getCurrentHash().equals(newBlock.getPreviousHash());
    }

    public String serialise(Block block) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(block);
        objectOutputStream.flush();
        return new String(Base64.getEncoder().encode(byteArrayOutputStream.toByteArray()));
    }

    public Block deserialise(String str) throws IOException, ClassNotFoundException {
        byte b[] = Base64.getDecoder().decode(str);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(b);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return (Block)objectInputStream.readObject();
    }

}