package weather.util;


import org.apache.commons.lang.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {

    public static Date formatStartDate(Date date) {
        return DateUtils.truncate(date, Calendar.DATE);
    }

    public static Date formatEndDate(Date date) {
        return DateUtils.addMilliseconds(DateUtils.ceiling(date, Calendar.DATE), -1);
    }

}


