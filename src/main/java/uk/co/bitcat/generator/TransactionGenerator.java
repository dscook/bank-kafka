package uk.co.bitcat.generator;

import uk.co.bitcat.dto.Transaction;
import uk.co.bitcat.dto.TransactionInput;
import uk.co.bitcat.dto.TransactionOutput;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class TransactionGenerator {

    private final Map<String, Integer> balances = new HashMap<>();
    private final List<Utxo> utxoList = new ArrayList<>();
    private int txIdCounter = 1;
    private Utxo doubleSpendUtxo = null;

    public Transaction generate() throws IOException {
        final Transaction tx;
        String txId = "tx" + txIdCounter;

        // Credit 10 accounts with 100 crypto
        if (txIdCounter <= 10) {
            String accountId = "account" + txIdCounter;

            Map<Integer, TransactionOutput> txOutputs = new HashMap<>();
            TransactionOutput txOutput = new TransactionOutput(100, accountId);
            txOutputs.put(0, txOutput);

            tx = new Transaction(txId, null, txOutputs);

            // Update the account balances
            balances.put(accountId, 100);

            // Maintain the index of unspent transaction output
            Utxo utxo = new Utxo(100, accountId, txId, 0);
            utxoList.add(utxo);

        } else {
            ThreadLocalRandom randomNumGenerator = ThreadLocalRandom.current();
            final Utxo utxo;
            boolean doubleSpending = false;

            // Determine if we are going to do a double spend
            if (doubleSpendUtxo != null && randomNumGenerator.nextInt(0, 100) >= 95) {
                System.out.println("About to do a double spend!");
                utxo = doubleSpendUtxo;
                doubleSpending = true;

            } else {
                // Select some random UTXO
                int randomUtxo = randomNumGenerator.nextInt(0, utxoList.size());
                utxo = utxoList.remove(randomUtxo);

                // Make the UTXO available for double spend
                doubleSpendUtxo = utxo;
            }

            Map<Integer, TransactionInput> txInputs = new HashMap<>();
            TransactionInput txInput = new TransactionInput(utxo.getTxId(), utxo.getUtxoIndex(),
                    utxo.getAmount(), utxo.getAddress());
            txInputs.put(0, txInput);

            // Randomly split the UTXO and allocate to two other random accounts
            final int accountOneAmount;
            final int accountTwoAmount;

            // Check if we can split the UTXO
            if (utxo.getAmount() == 1) {
                accountOneAmount = 1;
                accountTwoAmount = 0;
            } else {
                accountOneAmount = randomNumGenerator.nextInt(1, utxo.getAmount());
                accountTwoAmount = utxo.getAmount() - accountOneAmount;
            }

            String accountOne = "account" + randomNumGenerator.nextInt(1, 11);
            String accountTwo = "account" + randomNumGenerator.nextInt(1, 11);

            Map<Integer, TransactionOutput> txOutputs = new HashMap<>();
            TransactionOutput txOutput1 = new TransactionOutput(accountOneAmount, accountOne);
            txOutputs.put(0, txOutput1);

            if (accountTwoAmount != 0) {
                TransactionOutput txOutput2 = new TransactionOutput(accountTwoAmount, accountTwo);
                txOutputs.put(1, txOutput2);
            }

            tx = new Transaction(txId, txInputs, txOutputs);

            // Only update balances and unspent transaction output index if we are not double spending
            // i.e. the transaction should actually trigger changes
            if (!doubleSpending) {
                // Update the account balances
                balances.computeIfPresent(utxo.getAddress(), (key, value) -> value - utxo.getAmount());
                balances.computeIfPresent(accountOne, (key, value) -> value + accountOneAmount);
                balances.computeIfPresent(accountTwo, (key, value) -> value + accountTwoAmount);

                // Update the index of unspent transaction outputs
                Utxo utxo1 = new Utxo(accountOneAmount, accountOne, txId, 0);
                utxoList.add(utxo1);

                if (accountTwoAmount != 0) {
                    Utxo utxo2 = new Utxo(accountTwoAmount, accountTwo, txId, 1);
                    utxoList.add(utxo2);
                }
            }
        }

        writeAccountBalancesToFile();
        txIdCounter++;

        return tx;
    }

    private void writeAccountBalancesToFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("balances.txt"))) {
            for (Map.Entry<String, Integer> accountBalance : balances.entrySet()) {
                writer.write("address: " + accountBalance.getKey() + ", balance: " + accountBalance.getValue()
                        + "\n");
            }
        }
    }

}
