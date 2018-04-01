package uk.co.bitcat.generator;

import uk.co.bitcat.dto.TransactionOutput;

public class Utxo extends TransactionOutput {

    private String txId;
    private int utxoIndex;

    public Utxo(final int amount, final String address, final String txId, final int utxoIndex) {
        super(amount, address);
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
