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
public class AlphabeticalComparator implements Comparator<OrdinableObject>
{
    Behavior behavior;
    public AlphabeticalComparator(Behavior behavior)
    {
        this.behavior = behavior;
    }
    
    public int compare(OrdinableObject one, OrdinableObject two)
    {
        return alphabeticOrder(one.getName().toLowerCase(), two.getName().toLowerCase());
    }

    private int alphabeticOrder(String one, String two) {
        int max = 0; // the maximum number of characters that can be compared
        if(one.length() > two.length())
        {
            max = two.length();
        }
        else
        {
            max = one.length();
        }
        
        int returnValue = 1;
        if(behavior == Behavior.INCREASING)
        {
            returnValue = - returnValue;
        }
        
        for(int i=0; i<max; i++)
        {
            if(one.charAt(i) < two.charAt(i))
            {
                return - returnValue;
            }
            if(one.charAt(i) > two.charAt(i))
            {
                return + returnValue;
            }
        }
        if(one.length() > two.length())
        {
            return + returnValue;
        }
        if(one.length() == two.length())
        {
            return 0;
        }
        return - returnValue;
    }
}
