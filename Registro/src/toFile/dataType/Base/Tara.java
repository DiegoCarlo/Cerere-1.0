/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toFile.dataType.Base;

import toFile.dataType.Ordinals.OrdinableObject;

/**
 * the tares can be memorized for faster use
 * @author DiegoCG
 */
public class Tara extends OrdinableObject
{
    public float quantity;

    /**
     * 
     * @param id long unique id, never change
     * @param cardinal long unique cardinal value, it can be exchanged with another object
     * @param name String not unique.
     * @param quantity the tare
     */
    public Tara(long id, long cardinal, String name, float quantity)
    {
        super(id, cardinal, name);
        this.quantity = quantity;
    }
    
    public String toFile(String splitter)
    {
        return getId() + splitter +
                getCardinal() + splitter +
                getName() + splitter +
                quantity;
    }
    
    public static Tara fromFile(String[] value)
    {
        try
        {
            long id = Long.parseLong(value[0]);
            long cardinal = Long.parseLong(value[1]);
            String name = value[2];
            float quantity = Float.parseFloat(value[3]);
            
            return new Tara(id, cardinal, name, quantity);
        }
        catch(NumberFormatException n)
        {
            n.printStackTrace();
        }
        catch(ArrayIndexOutOfBoundsException a)
        {
            a.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString()
    {
        return getName() + quantity; //To change body of generated methods, choose Tools | Templates.
    }
    
}
