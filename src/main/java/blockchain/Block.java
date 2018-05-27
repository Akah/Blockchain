package blockchain;

public class Block {
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

}