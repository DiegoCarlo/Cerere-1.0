/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toFile.dataType.Ordinals.Comparator;

import java.util.Comparator;
import toFile.dataType.Base.Pesata;


/**
 * it can be used only with a Pesata Class
 * @author DiegoCG
 */
public class CalendarComparator implements Comparator<Pesata>
{
    public Behavior behavior;
    public CalendarComparator(Behavior behavior)
    {
        this.behavior = behavior;
    }
    public int compare(Pesata one, Pesata two)
    {
        return ordina(one.time.getTimeInMillis(), two.time.getTimeInMillis());
    }

    private int ordina(long uno, long due)
    {
        int valueReturn = 1;
        if(behavior == behavior.INCREASING)
        {
            valueReturn = - valueReturn;
        }
        if(uno <= due)
        {
            return - valueReturn;
        }
        return + valueReturn;
    }
}
