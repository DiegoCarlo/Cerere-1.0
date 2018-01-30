/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bilancia;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DiegoCG
 */
public class AvviaRichieste implements Runnable
{
    public void run()
    {
        try
        {
            while(true)
            {
                registro.Registro.bilancia.richiedi();
                TimeUnit.MILLISECONDS.sleep((long)750);
            }
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(AvviaRichieste.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
