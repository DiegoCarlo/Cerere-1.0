/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toFile.dataType.Ordinals;

/**
 *
 * @author DiegoCG
 */
public class ActiveOrdering
{
    Order[] list;
    int i;

    public ActiveOrdering(Order[] lista)
    {
        this.list = lista;
        this.i = 0;
    }
    
    public Order getNext()
    {
        i++;
        if(i >= list.length)
        {
            i=0;
        }
        return list[i];
    }
    public Order getAndNext()
    {
        Order temp = list[i];
        i++;
        if(i >= list.length)
        {
            i=0;
        }
        return temp;
    }

    public int getIndex()
    {
        return i;
    }
    public int length()
    {
        return list.length;
    }
    public Order get()
    {
        return list[i];
    }
    public Order[] getType()
    {
        return list;
    }
    public void setInitial(int i)
    {
        if(i<list.length)
        {
            this.i = i;
        }
    }
    public boolean set(Order order)
    {
        boolean esito = false;
        for(int i=0; i<list.length; i++)
        {
            if(getNext() == order)
            {
                esito = true;
                break;
            }
        }
        return esito;
    }
}
