package fraud.detection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.IOException;
import java.text.ParseException;
import org.junit.jupiter.api.Test;

public class CsvFileReaderTest {
    @Test
    public void testReadCsvToListOfStringArray() throws IOException {
        // Act
        var result = CsvFileReader.read("data/cumulativeTransactions.csv");

        // Arrange
        assertEquals(9, result.size());
    }

    @Test
    public void testInvalidPathReadCsvToListOfStringArray() {
        var exception = assertThrows(IOException.class, () -> CsvFileReader.read("data/test.csv"));
        assertEquals("data/test.csv (No such file or directory)", exception.getMessage());
    }

    @Test
    public void testReadCsvToTransactions() throws IOException, NumberFormatException, ParseException {
        // Arrange
        var transactions = CsvFileReader.read("data/cumulativeTransactions.csv");

        // Act
        var result = CsvFileReader.toTransactions(transactions);

        // Assert
        assertEquals(9, result.size());
    }
}
