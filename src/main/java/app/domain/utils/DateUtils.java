package app.domain.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

// based on http://www.java2s.com/Code/Java/Data-Type/Checksifacalendardateistoday.htm
public class DateUtils {

    public static boolean isSameDay(Date date1, Date date2)
    {
        if (date1 == null || date2 == null)
        {
            throw new IllegalArgumentException("The dates must not be null");
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        return isSameDay(cal1, cal2);
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2)
    {
        if (cal1 == null || cal2 == null)
        {
            throw new IllegalArgumentException("The dates must not be null");
        }

        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    public static boolean isToday(Date date)
    {
        return isSameDay(date, Calendar.getInstance().getTime());
    }

    public static boolean isToday(LocalDateTime localDateTime)
    {
        Date date1 = java.util.Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return isSameDay(date1, Calendar.getInstance().getTime());
    }

}
