class Block {

    private String previousHash;
    private String currentHash;
    private String record;
    private long timestamp;
    private int index;

    void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }
    void setCurrentHash(String currentHash) {
        this.currentHash = currentHash;
    }
    void setRecord(String record) {
        this.record = record;
    }
    void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    void setIndex(int index) {
        this.index = index;
    }

    String getPreviousHash() {
        return previousHash;
    }
    String getCurrentHash() {
        return currentHash;
    }
    String getRecord() {
        return record;
    }
    int getIndex() {
        return index;
    }
    long getTimestamp() {
        return timestamp;
    }


    boolean isValid(Block oldBlock, Block newBlock){
        return oldBlock.getIndex()==newBlock.getIndex() &&
                !oldBlock.getCurrentHash().equals(newBlock.getPreviousHash());
    }

}