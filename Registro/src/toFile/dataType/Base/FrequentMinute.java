/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toFile.dataType.Base;

import java.io.Serializable;
import java.util.Calendar;

/**
 * weighted average of the minutes in which the customer will be used
 * @author DiegoCG
 */
public class FrequentMinute implements Serializable
{
    private int weight;
    private int averageMinute;
    
    /**
     * 0 = 00:00,
     * 1439 = (23*60)+59 = 23:59.
     * @param averageMinute 
     */
    public FrequentMinute(int averageMinute)
    {
        this.weight = 1;
        this.averageMinute = averageMinute;
    }
    /**
     * 0 = 00:00,
     * 1439 = (23*60)+59 = 23:59.
     * @param averageMinute 
     * @param pesoDellaMedia 
     */
    public FrequentMinute(int averageMinute , int weight)
    {
        this.weight = weight;
        this.averageMinute = averageMinute ;
    }
    /**
     * update the average
     * @param data 
     */
    public void update(Calendar data)
    {
        int minute = data.get(Calendar.HOUR_OF_DAY) * 60 + data.get(Calendar.MINUTE);
        averageMinute  = (averageMinute * weight + minute) / (weight + 1);
        if(weight < registro.Registro.settings.maxWeightMinuteAverage)
        {
            weight ++;
        }
    }
    public int getAverageMinute ()
    {
        return averageMinute ;
    }
    public int getWeight()
    {
        return weight;
    }
    public String toString()
    {
        return ((int)(averageMinute /60)) + ":" + ((int)(averageMinute %60));
    }
}
            
