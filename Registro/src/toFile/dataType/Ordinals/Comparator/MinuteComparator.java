/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toFile.dataType.Ordinals.Comparator;

import java.util.Comparator;
import toFile.dataType.Base.Cliente;
import toFile.dataType.Ordinals.OrdinableObject;

/**
 *
 * @author DiegoCG
 */
public class MinuteComparator implements Comparator<OrdinableObject>
{

    public Behavior behavior;
    
    public MinuteComparator(Behavior behavior)
    {
        this.behavior = behavior;
    }
    public int compare(OrdinableObject one, OrdinableObject two)
    {
        return ordina(((Cliente)one).frequentMinute.getAverageMinute(), ((Cliente)two).frequentMinute.getAverageMinute());
    }

    private int ordina(int uno, int due)
    {
        int valueReturn = 1;
        if(behavior == behavior.DECREASING)
        {
            valueReturn = - valueReturn;
        }
        if(uno < due)
        {
            return - valueReturn;
        }
        if(uno > due)
        {
            return + valueReturn;
        }
        return 0;
    }
}
