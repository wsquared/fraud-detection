package fraud.detection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.google.common.base.Optional;

import org.javatuples.Pair;

public class FraudDetection {

    public static List<String> identify(List<Transaction> transactions, Long thresholdWindowInSecs,
            Double priceThreshold) {

        if (transactions.size() == 0) {
            return new ArrayList<String>();
        }

        // Have a minimum of two transactions and assuming transactions are not sorted
        transactions.sort((Transaction t1, Transaction t2) -> t1.getEpoch().compareTo(t2.getEpoch()));

        var twentyFourHoursInSecs = 86400;
        var timeThreshold = Optional.fromNullable(thresholdWindowInSecs).or(Long.valueOf(twentyFourHoursInSecs));

        var hashMap = new HashMap<String, List<Pair<Long, Double>>>();

        // Group transactions by hash number
        for (var transaction : transactions) {
            if (hashMap.containsKey(transaction.getHashCreditCardNumber())) {
                var array = hashMap.get(transaction.getHashCreditCardNumber());
                array.add(Pair.with(transaction.getEpoch(), transaction.getAmount()));
                hashMap.put(transaction.getHashCreditCardNumber(), array);
            } else {
                var array = new ArrayList<Pair<Long, Double>>();
                array.add(Pair.with(transaction.getEpoch(), transaction.getAmount()));
                hashMap.putIfAbsent(transaction.getHashCreditCardNumber(), array);
            }
        }

        var result = new HashSet<String>();

        hashMap.forEach((k, v) -> {
            List<Pair<Long, Double>> array = v;
            var slow = 0;
            var fast = 1;
            var startTimeInSecs = array.get(slow).getValue0();
            var otherStartTimeInSecs = startTimeInSecs + (timeThreshold * 2);
            var windowAmount = array.get(slow).getValue1();
            var elaspsedTime = otherStartTimeInSecs - startTimeInSecs;

            if (windowAmount > priceThreshold) {
                result.add(k);
            }

            // Sliding window technique using fast and slow pointers
            while (array.size() >= 2 && (elaspsedTime > timeThreshold || fast < array.size() + 1)) {
                startTimeInSecs = array.get(slow).getValue0();
                otherStartTimeInSecs = array.get(fast - 1).getValue0();
                elaspsedTime = otherStartTimeInSecs - startTimeInSecs;
                if (elaspsedTime > timeThreshold) {
                    windowAmount -= array.get(slow).getValue1();
                    slow += 1;
                } else {
                    if (windowAmount > priceThreshold) {
                        result.add(k);
                    }
                    if (fast != array.size()) {
                        windowAmount += array.get(fast).getValue1();
                    }
                    fast += 1;
                }

            }
        });

        return new ArrayList<String>(result);
    }
}