/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toFile.dataType.Ordinals;

import java.util.ArrayList;
import toFile.dataType.Base.Pesata;

/**
 *
 * @author DiegoCG
 */
public class PesateArray extends ArrayList<Pesata>
{
    private long id;
    
    public PesateArray(long id)
    {
        this.id = id;
    }

    public long getId()
    {
        return id;
    }
    public long getNewId()
    {
        return ++id;
    }
    /**
     * return the first object found with the same id
     * @param id 
     * @return 
     */
    public Pesata get(long id)
    {
        for(Pesata o: this)
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
        for(Pesata o: this)
        {
            if(o.getId() == id)
            {
                return super.remove(o);
            }
        }
        return false;
    }
}
