/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package fraud.detection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

public class TransactionTest {
    @Test
    public void testTransactionBuild() {
        // Arrange
        var expectedHash = DigestUtils.md5Hex("4564 3421 2321 1234").toLowerCase();
        var expectedEpoch = Long.valueOf(86400);
        var expectedAmount = 20.0;

        // Act
        var transaction = Transaction.builder().epoch(expectedEpoch).hashCreditCardNumber(expectedHash)
                .amount(expectedAmount).build();

        // Assert
        assertEquals(expectedHash, transaction.getHashCreditCardNumber());
        assertEquals(expectedEpoch, transaction.getEpoch());
        assertEquals(expectedAmount, transaction.getAmount());
    }
}
