/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toFile.dataType.Frequency;

import registro.Settings;

/**
 * 
 * @author DiegoCG
 */
public class Frequency
{
    public long idProdotto;
    public long frequenza;
    
    public Frequency(long idProdotto, long frequenza)
    {
        this.idProdotto = idProdotto;
        this.frequenza = frequenza;
    }
    
    public String toFile()
    {
        return idProdotto + Settings.SPLITTER_VAR + frequenza;
    }
    public static Frequency fromFile(String val)
    {
        String[] temp = val.split(Settings.SPLITTER_VAR);
        try
        {
            long idProdotto = Long.parseLong(temp[0]);
            long frequency = Long.parseLong(temp[1]);
            return new Frequency(idProdotto, frequency);
        }
        catch(NumberFormatException c)
        {
            c.printStackTrace();
        }
        return null;
    }
}
