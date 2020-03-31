package fraud.detection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Optional;

public class CsvFileReader {

    public static List<String[]> read(String path) throws IOException {
        var result = new ArrayList<String[]>();

        BufferedReader csvReader = new BufferedReader(new FileReader(Optional.fromNullable(path).or("")));
        var row = "";
        while (Optional.fromNullable(row = csvReader.readLine()).isPresent()) {
            var data = row.split(",");
            result.add(data);
        }

        csvReader.close();

        return result;
    }

    public static List<Transaction> toTransactions(List<String[]> transactionsInString)
            throws NumberFormatException, IOException, ParseException {
        var transactions = new ArrayList<Transaction>();

        for (var item : transactionsInString) {
            var epoch = DateStringToEpoch.parse(item[1]);
            transactions.add(Transaction.builder().hashCreditCardNumber(item[0]).epoch(epoch)
                    .amount(Double.parseDouble(item[2])).build());
        }
        return transactions;
    }
}