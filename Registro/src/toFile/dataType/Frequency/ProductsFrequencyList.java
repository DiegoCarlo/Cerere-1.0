/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toFile.dataType.Frequency;

import java.io.Serializable;
import java.util.ArrayList;
import registro.Settings;

/**
 *
 * @author DiegoCG
 */
public class ProductsFrequencyList extends ArrayList<Frequency>
{
    public long idCliente;

    public ProductsFrequencyList(long idCliente)
    {
        super();
        this.idCliente = idCliente;
    }
    
    public boolean add(long idProdotto)
    {
        boolean found = false;
        for(Frequency f: this)
        {
            if(f.idProdotto == idProdotto)
            {
                found = true;
                f.frequency++;
                return true;
            }
        }
        if(!found)
        {
            return super.add(new Frequency(idProdotto, 1));
        }
        
        return false;
    }
    
    public String toFile()
    {
        String string = idCliente + Settings.SPLITTER_LINE;
        for(Frequency f: this)
        {
            string += f.toFile() + Settings.SPLITTER_LINE;
        }
        return string;
    }
    
    public static ProductsFrequencyList fromFile(String string)
    {
        String[] split = string.split(Settings.SPLITTER_LINE);
        try
        {
            long idCliente = Long.parseLong(split[0]);
            ProductsFrequencyList p = new ProductsFrequencyList(idCliente);
            for(int i=1; i < split.length; i++)
            {
                Frequency f = Frequency.fromFile(split[i]);
                p.add(f);
            }
            return p;
        }
        catch(NumberFormatException n)
        {
            n.printStackTrace();
        }
        return null;
    }
}
