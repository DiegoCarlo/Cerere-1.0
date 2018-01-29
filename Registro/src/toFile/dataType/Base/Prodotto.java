/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toFile.dataType.Base;

import toFile.dataType.Ordinals.OrdinableObject;

/**
 * represents a product that can be sold,
 * each product has a specific unit of measurement, weight or quantity
 * @author DiegoCG
 */
public class Prodotto extends OrdinableObject
{
    private UnitOfMeasure unitOfMeasure;

    /**
     * the product r
     * @param id
     * @param cardinal
     * @param name
     * @param unitOfMeasure 
     */
    public Prodotto(long id, long cardinal, String name, UnitOfMeasure unitOfMeasure)
    {
        super(id, cardinal, name);
        this.unitOfMeasure = unitOfMeasure;
    }
    public String toFile(String splitter)
    {
        return getId() + splitter +
                getCardinal() + splitter +
                getName() + splitter +
                unitOfMeasure;
    }
    
    public static Prodotto fromFile(String[] value)
    {
        try
        {
            long id = Long.parseLong(value[0]);
            long cardinal = Long.parseLong(value[1]);
            String name = value[2];
            UnitOfMeasure unitOfMeasure = UnitOfMeasure.QUANTITY;
            if(value[3].compareTo(UnitOfMeasure.KILOGRAMMO.toString()) == 0)
            {
                unitOfMeasure = UnitOfMeasure.KILOGRAMMO;
            }
            
            return new Prodotto(id, cardinal, name, unitOfMeasure);
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
}

