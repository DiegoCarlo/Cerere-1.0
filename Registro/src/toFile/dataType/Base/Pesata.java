/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toFile.dataType.Base;

import java.util.Calendar;
import toFile.dataType.Ordinals.IdObject;

/**
 *
 * @author DiegoCG
 */
public class Pesata extends IdObject
{

    public long idCliente;
    public long idProdotto;
    public float quantita;
    public Calendar time;

    public Pesata(long id, long idCliente, long idProdotto, float quantita, Calendar time)
    {
        super(id);
        this.idCliente = idCliente;
        this.idProdotto = idProdotto;
        this.quantita = quantita;
        this.time = time;
    }
    
    public String toFile(String splitter)
    {
        String value = "";
        value += getId() + splitter + idCliente + splitter + idProdotto + splitter + quantita + splitter + time.getTimeInMillis();
        return value;
    }
    
    public static Pesata fromFile(String[] value)
    {   
        try
        {
            long id = Long.parseLong(value[0]);
            long idCliente = Long.parseLong(value[1]);
            long idProdotto = Long.parseLong(value[2]);
            float quantita = Float.parseFloat(value[3]);
            long timeMillis = Long.parseLong(value[4]);
            
            Calendar time = Calendar.getInstance();
            time.setTimeInMillis(timeMillis);
            
            return new Pesata(id, idCliente, idProdotto, quantita, time);
        }
        catch(NumberFormatException n)
        {
            n.printStackTrace();
        }
        return null;
    }
}
