package fraud.detection;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateStringToEpoch {
    public static Long parse(String s) throws ParseException {
        var formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        var date = formatter.parse(s);
        return date.getTime() / 1000;
    }
}
