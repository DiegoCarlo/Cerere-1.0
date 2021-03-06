package toFile.Utility;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author DiegoCG
 */
public class Utility
{
    public static String getYearMonth(Calendar date)
    {
        int year = date.get(Calendar.YEAR);
        int month = 1 + date.get(Calendar.MONTH);
        return year + " " + month; 
    }
    public static String getYearMonth(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = 1 + cal.get(Calendar.MONTH);
        return year + " " + month; 
    }
    public static int getMinuteOfTheDay(Calendar date)
    {
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minute = date.get(Calendar.MINUTE);
        return hour * 60 + minute; 
    }
    public static String safeName(String string)
    {
        String result = "";
        char[] temp = string.toCharArray();
        for(char t: temp)
        {
            if(
                    ('a' <= t && t <= 'z') ||
                    ('A' <= t && t <= 'Z') ||
                    ('0' <= t && t <= '9') ||
                    (t == '\'') ||
                    (t == ',') ||
                    (t == '.') ||
                    (t == ' ') ||
                    (t == 'é') ||
                    (t == 'è') ||
                    (t == 'à') ||
                    (t == 'ò') ||
                    (t == 'ù') ||
                    (t == 'ì') ||
                    (t == '&') ||
                    (t == '+') ||
                    (t == '-')
                    )
            {
                result += t;
            }
        }
        return result;
    }

    public static String getMax(int max, String word)
    {
        String res = "";
        if(word.length() <= max)
        {
            return word;
        }
        res = word.substring(0, max-3);
        res += "...";
        return res;
    }
    public static String getMax1Dot(int max, String word)
    {
        String res = "";
        if(word.length() <= max)
        {
            return word;
        }
        res = word.substring(0, max-3);
        res += ".";
        return res;
    }

    public static String getCalendarString(Calendar time)
    {
        return time.get(Calendar.YEAR) + "-" +
                (time.get(Calendar.MONTH) + 1) + "-" +
                time.get(Calendar.DAY_OF_MONTH) + " " +
                time.get(Calendar.HOUR_OF_DAY) + ":" +
                time.get(Calendar.MINUTE);
                
    }

    public static String safeNumber(String string)
    {
        String result = "";
        char[] temp = string.replaceAll(",", ".").toCharArray();
        boolean trovato = false;
        for(char t: temp)
        {
            if(
                    ('0' <= t && t <= '9') ||
                    (t == ',') ||
                    (t == '.')
                    )
            {
                if(t == '.')
                {
                    if(!trovato)
                    {
                        trovato = true;
                        result += t;
                    }
                }
                else
                {
                    result += t;
                }
            }
        }
        
        
        return result;
    }
}
