/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package fraud.detection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.text.ParseException;
import org.junit.jupiter.api.Test;

public class FraudDetectionTest {

    @Test
    public void testNoTransaction() throws NumberFormatException, IOException, ParseException {
        // Arrange
        var transactions = CsvFileReader.toTransactions(CsvFileReader.read("data/noTransaction.csv"));

        // Act
        var result = FraudDetection.identify(transactions, Long.valueOf(86400), 5.0);

        // Assert
        assertEquals(0, result.size());
    }

    @Test
    public void testOneTransactionOverPriceThreshold() throws NumberFormatException, IOException, ParseException {
        // Arrange
        var transactions = CsvFileReader.toTransactions(CsvFileReader.read("data/oneTransaction.csv"));

        // Act
        var result = FraudDetection.identify(transactions, Long.valueOf(86400), 5.0);

        // Assert
        assertEquals("ebce663e1de045dfb1b20721f3049c5c", result.get(0));
    }

    @Test
    public void testOneTransactionUnderPriceThreshold() throws NumberFormatException, IOException, ParseException {
        // Arrange
        var transactions = CsvFileReader.toTransactions(CsvFileReader.read("data/oneTransaction.csv"));

        // Act
        var result = FraudDetection.identify(transactions, Long.valueOf(86400), 15.0);

        // Assert
        assertEquals(0, result.size());
    }

    @Test
    public void testMultipleTransactionsWithMultipleUniqueHashOverPriceThreshold()
            throws NumberFormatException, IOException, ParseException {
        // Arrange
        var transactions = CsvFileReader.toTransactions(CsvFileReader.read("data/cumulativeTransactions.csv"));

        // Act
        var result = FraudDetection.identify(transactions, Long.valueOf(86400), 15.0);

        // Assert
        assertEquals("66bd64fb3019500acd03605dfa992bff", result.get(0));
        assertEquals("ebce663e1de045dfb1b20721f3049c5c", result.get(1));
        assertEquals(2, result.size());
    }

    @Test
    public void testMultipleTransactionsWithMultipleUniqueHashUnderPriceThreshold()
            throws NumberFormatException, IOException, ParseException {
        // Arrange
        var transactions = CsvFileReader.toTransactions(CsvFileReader.read("data/uniqueHashTransactions.csv"));

        // Act
        var result = FraudDetection.identify(transactions, Long.valueOf(86400), 50.0);

        // Assert
        assertEquals(0, result.size());
    }

    @Test
    public void testMultipleTransactionsWithSingleUniqueHashOverPriceThreshold()
            throws NumberFormatException, IOException, ParseException {
        // Arrange
        var transactions = CsvFileReader.toTransactions(CsvFileReader.read("data/singleUniqueHashTransactions.csv"));

        // Act
        var result = FraudDetection.identify(transactions, Long.valueOf(86400), 19.0);

        // Assert
        assertEquals("ebce663e1de045dfb1b20721f3049c5c", result.get(0));
        assertEquals(1, result.size());
    }

    @Test
    public void testMultipleTransactionsWithSingleUniqueHashUnderPriceThreshold()
            throws NumberFormatException, IOException, ParseException {
        // Arrange
        var transactions = CsvFileReader.toTransactions(CsvFileReader.read("data/singleUniqueHashTransactions.csv"));

        // Act
        var result = FraudDetection.identify(transactions, Long.valueOf(86400), 10.0);

        // Assert
        assertEquals("ebce663e1de045dfb1b20721f3049c5c", result.get(0));
        assertEquals(1, result.size());
    }

    @Test
    public void testSparseTransactionsUnderPriceThreshold()
            throws NumberFormatException, IOException, ParseException {
        // Arrange
        var transactions = CsvFileReader.toTransactions(CsvFileReader.read("data/sparseTransactions.csv"));

        // Act
        var result = FraudDetection.identify(transactions, Long.valueOf(86400), 50.0);

        // Assert
        assertEquals(0, result.size());
    }

    @Test
    public void testSparseTransactionsOverPriceThreshold()
            throws NumberFormatException, IOException, ParseException {
        // Arrange
        var transactions = CsvFileReader.toTransactions(CsvFileReader.read("data/sparseTransactions.csv"));

        // Act
        var result = FraudDetection.identify(transactions, Long.valueOf(86400), 49.0);

        // Assert
        assertEquals("ebce663e1de045dfb1b20721f3049c5c", result.get(0));
        assertEquals(1, result.size());
    }
}
