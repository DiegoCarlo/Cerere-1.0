/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toFile.dataType.Ordinals;

/**
 * i can order this type of object in different ways
 * @author DiegoCG
 */
public class OrdinableObject extends IdObject
{
    private long cardinal;
    private String name;

    /**
     * 
     * @param id unique id, never change.
     * @param cardinal unique cardinal value, it can be exchanged with another object
     * @param name not unique.
     */
    public OrdinableObject(long id, long cardinal, String name)
    {
        super(id);
        this.cardinal = cardinal;
        this.name = name;
    }
    /**
     * exchange the cardinals
     * @param ordinableObject 
     */
    public void swapCardinal(OrdinableObject ordinableObject)
    {
        long temp = ordinableObject.cardinal;
        ordinableObject.cardinal = this.cardinal;
        this.cardinal = temp;
    }

    public long getCardinal()
    {
        return cardinal;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
