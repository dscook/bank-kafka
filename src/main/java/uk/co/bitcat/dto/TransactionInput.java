package uk.co.bitcat.dto;

public class TransactionInput {

    private String txId;
    private int utxoIndex;

    public TransactionInput(final String txId, final int utxoIndex) {
        this.txId = txId;
        this.utxoIndex = utxoIndex;
    }

    public String getTxId() {
        return txId;
    }

    public int getUtxoIndex() {
        return utxoIndex;
    }
}
