/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toFile.dataType.Ordinals.Comparator;

import java.util.Comparator;
import toFile.dataType.Ordinals.OrdinableObject;

/**
 *
 * @author DiegoCG
 */
public class CardinalComparator implements Comparator<OrdinableObject>
{
    public Behavior behavior;
    
    public CardinalComparator(Behavior behavior)
    {
        this.behavior = behavior;
    }
    public int compare(OrdinableObject one, OrdinableObject two)
    {
        return ordina(one.getCardinal(), two.getCardinal());
    }

    private int ordina(long uno, long due)
    {
        int valueReturn = 1;
        if(behavior == behavior.INCREASING)
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
