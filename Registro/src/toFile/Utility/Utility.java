package toFile.Utility;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Calendar;

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
                    (t == '&')
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
}
