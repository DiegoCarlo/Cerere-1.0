/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package registro;

import Bilancia.Bilancia;
import Bilancia.StartRequest;
import Interface.Interfaccia;
import Interface.ProgressionBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import toFile.Utility.IO;
import toFile.dataType.Complex.DataBase;

/**
 *
 * @author DiegoCG
 */
public class Registro
{
    public static Settings settings;
    public static DataBase dataBase;
    
    public static Bilancia bilancia;
    public static StartRequest startRequest;
    
    public static ProgressionBar progressionBar;
    public static Interfaccia interfaccia;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        loadStyle();
        progressionBar = new ProgressionBar(13);
        Thread barra = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                progressionBar.setVisible(true);
            }
        });
        barra.start();
        
        progressionBar.progress("Carico le Impostazioni");
        loadSettings();
        
        progressionBar.progress("Controllo le cartelle");
        createDirectory();
        
        progressionBar.progress("Carico il Data Base");
        dataBase = new DataBase();
        
        dataBase.load(progressionBar);
        //dataBase.save();
        progressionBar.progress("Carico l'Interfaccia");
        interfaccia = new Interfaccia();
        
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run() {
                interfaccia.setVisible(true);
            }
        });
        progressionBar.progress("Avvio la Bilancia");
        bilancia = new Bilancia(Settings.BILANCIA_COM);
        startRequest = new StartRequest();
        Thread richieste = new Thread(startRequest);
        richieste.start();
        
        progressionBar.progress("Avvio la Stampante");
        
        progressionBar.close();
    }
    
    public static void saveSettings()
    {
        IO.writeStringFile(Settings.SETTINGS, settings.toFile());
    }
    public static void loadSettings()
    {
        String var = IO.readStringFile(Settings.SETTINGS);
        
        if(var == null)
        {
            settings = new Settings();
            IO.writeStringFile(Settings.SETTINGS, settings.toFile());
        }
        else
        {
            settings = Settings.fromFile(var);
            if(settings == null)
            {
                settings = new Settings();
                IO.writeStringFile(Settings.SETTINGS, settings.toFile());
            }
        }
    }
    public static void log(String messaggio)
    {
        System.out.println(messaggio);
    }
    public static void log(int messaggio)
    {
        log(messaggio+"");
    }
    public static void createDirectory()
    {
        /**
        * | -> Salvataggi
        *   | -> 2018 1
        *     | - sakhjdgaksd
        *     | - ashdgajhsdag
        *   | -> Settimanali
        *     | - 21.1 28.1 jahsgdahsgd
        *     | - 21.1 28.1 jahsdfsd
        * | -> Data Base
        *   | - Clienti
        *   | - Prodotti
        *   | - Tare
        *   | - Clienti Eliminati
        *   | - Prodotti Eliminati
        *   | - Frequenze
        *   | - Settings
        *   | -> Lista Path Pesate Mensili
        *     | - 2018 0
        *     | - 2018 1
        *     | - 2018 2             
        */
        
        IO.creaPath(Settings.DATA_BASE_DIRECTORY);
        IO.creaPath(Settings.MESI_PESATE_DIRECTORY);
    }
    static private void loadStyle()
    {
        try
        {
            UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        } 
        catch(UnsupportedLookAndFeelException e) {
        // handle exception
        }
        catch (ClassNotFoundException e) {
           // handle exception
        }
        catch (InstantiationException e) {
           // handle exception
        }
        catch (IllegalAccessException e) {
           // handle exception
        }
    }
}
