/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toFile.dataType.Ordinals;

import java.util.ArrayList;
import toFile.dataType.Ordinals.Comparator.Behavior;

/**
 *
 * @author DiegoCG
 */
public class OrdinableArray extends ArrayList<OrdinableObject>
{
    private long id;
    private long cardinale;
    
    public OrdinableArray(long id, long cardinale)
    {
        this.id = id;
        this.cardinale = cardinale;
    }

    public long getId()
    {
        return id;
    }
    public long getNewId()
    {
        return ++id;
    }
    public long getCardinale()
    {
        return cardinale;
    }
    public long getNewCardinale()
    {
        return ++cardinale;
    }
    /**
     * return the first object found with the same id
     * @param id 
     * @return 
     */
    public OrdinableObject get(long id)
    {
        for(OrdinableObject o: this)
        {
            if(o.getId() == id)
            {
                return o;
            }
        }
        return null;
    }
    
    public boolean remove(long id)
    {
        for(OrdinableObject o: this)
        {
            if(o.getId() == id)
            {
                return super.remove(o);
            }
        }
        return false;
    }
    
    /**
     * exchange the cardinal of an object with the next or the previous one
     * @param id object to move 
     * @param behavior the next or the previous one
     */
    public void moveCardinal(long id, Behavior behavior)
    {
        OrdinableObject one = get(id);
        long cardinal;
        OrdinableObject two = null;
        long nearCardinal;
        
        if(behavior == Behavior.DECREASING)
        {
            nearCardinal = Long.MIN_VALUE;
            for(OrdinableObject o: this)
            {
                cardinal = o.getCardinal();
                if( nearCardinal < cardinal && cardinal < one.getCardinal())
                {
                    nearCardinal = cardinal;
                    two = o;
                }
            }
        }
        else
        {
            nearCardinal = Long.MAX_VALUE;
            for(OrdinableObject o: this)
            {
                cardinal = o.getCardinal();
                if(one.getCardinal() < cardinal && cardinal < nearCardinal)
                {
                    nearCardinal = cardinal;
                    two = o;
                }
            }
        }
        if(two != null)
        {
            one.swapCardinal(two);
        }
    }

    public OrdinableObject exist(String text)
    {
        for(OrdinableObject o: this)
        {
            if(o.getName().compareTo(text) == 0)
            {
                return o;
            }
        }
        return null;
    }

    public void swapCardinal(long id, Behavior behavior)
    {
        OrdinableObject x = get(id);
        long cardinaleT;
        OrdinableObject t =  null;
        long cardinaleTVicino = 0;

        if(behavior == Behavior.DECREASING)
        {
            for(int i=0; i<size(); i++)
            {
                OrdinableObject c = get(i);
                cardinaleT = c.getCardinal();
                if(x.getCardinal()> cardinaleT)
                {
                    if(cardinaleTVicino <= cardinaleT)
                    {
                        cardinaleTVicino = cardinaleT;
                        t = c;
                    }
                }
            }
        }
        else
        {
            cardinaleTVicino = Long.MAX_VALUE;
            for(int i=0; i<size(); i++)
            {
                OrdinableObject c = get(i);
                cardinaleT = c.getCardinal();
                if(x.getCardinal() < cardinaleT)
                {
                    if(cardinaleTVicino > cardinaleT)
                    {
                        cardinaleTVicino = cardinaleT;
                        t = c;
                    }
                }
            }
        }
        if(t != null)
        {
            x.swapCardinal(t);
        }
    }
}
