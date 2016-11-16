package jun.ibuy;
import java.text.DecimalFormat;
/**
 * Created by raojun on 11/7/16.
 */
//let 1000 looks like 1,000
public class Utils {


    public static String formatNumber( int value) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formatted = formatter.format(value);

        return formatted;
    }
}