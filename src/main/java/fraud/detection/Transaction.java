package fraud.detection;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Transaction {
    public String hashCreditCardNumber;
    public Double amount;
    public Long epoch;
}