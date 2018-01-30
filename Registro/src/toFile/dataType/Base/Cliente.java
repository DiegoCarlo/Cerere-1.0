/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toFile.dataType.Base;

import toFile.dataType.Ordinals.OrdinableObject;

/**
 * an entity to whom the products are sold,
 * it can also be ordered for the average minute of use
 * @author DiegoCG
 */
public class Cliente extends OrdinableObject
{
    public FrequentMinute frequentMinute;

    /**
     * 
     * @param id long unique id, never change
     * @param cardinal long unique cardinal value, it can be exchanged with another object
     * @param name String not unique.
     * @param frequentMinute the average minute during the 24h in which the customer is used
     */
    public Cliente(long id, long cardinal, String name, FrequentMinute frequentMinute)
    {
        super(id, cardinal, name);
        this.frequentMinute = frequentMinute;
    }
    
    public String toFile(String splitter)
    {
        return 
                getId() + splitter + 
                getCardinal() + splitter + 
                getName() + splitter + 
                frequentMinute.getAverageMinute() + splitter +
                frequentMinute.getWeight();
    }
    
    public static Cliente fromFile(String[] value)
    {
        try
        {
            long id = Long.parseLong(value[0]);
            long cardinal = Long.parseLong(value[1]);
            String name = value[2];
            int averageMinute = Integer.parseInt(value[3]);
            int weight = Integer.parseInt(value[4]);
            
            return new Cliente(id, cardinal, name, new FrequentMinute(averageMinute, weight));
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
    
    public String toString()
    {
        return getName();
    }
    
}
