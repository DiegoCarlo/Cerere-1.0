/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package registro;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import toFile.Utility.IO;

/**
 *
 * @author DiegoCG
 */
public class Settings
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
    public static final String DATA_BASE_DIRECTORY = "Data Base";
    public static final String SALVATAGGI_DIRECTORY = "Salvataggi";
    public static final String SETTIMANALI_DIRECTORY = "Settimanali";
    public static final String MESI_PESATE_DIRECTORY = DATA_BASE_DIRECTORY + "/Mensili";
    
    public static final String EXT = ".txt";
    public static final String EXT_EXCEL = ".txte";
    
    public static final String CLIENTI = DATA_BASE_DIRECTORY + "/Clienti" + EXT;
    public static final String CLIENTI_ELIMINATI = DATA_BASE_DIRECTORY + "/Clienti Eliminati" + EXT;
    public static final String PRODOTTI = DATA_BASE_DIRECTORY + "/Prodotti" + EXT;
    public static final String PRODOTTI_ELIMINATI = DATA_BASE_DIRECTORY + "/Prodotti Eliminati" + EXT;
    public static final String TARE = DATA_BASE_DIRECTORY + "/Tare" + EXT;
    public static final String FREQUENCY = DATA_BASE_DIRECTORY + "/Frequenze" + EXT;
    public static final String PESATE_ELIMINATE = DATA_BASE_DIRECTORY + "/Pesate Eliminate" + EXT;
    public static final String SETTINGS = "Settings" + EXT;
    
    public static final String SPLITTER_LINE = "\n";
    public static final String SPLITTER_VAR = "~";
    public static final String SPLITTER_ARRAY = "§";
    public static final String NEW_LINE = "\r\n";
    public static final String TAB = "\t";
    public static final String BILANCIA_COM = "COM4";
    
    public int maxWeightMinuteAverage = 8;
    public int font = 20;
    public int fontMedium = 25;
    public int fontBig = 30;
    public int orderClienti = 0;
    public int orderProdotti = 0;
    public int orderTare = 0;
    public int widthScrollBar = 40;
    public String salvataggiDirectory = "";

    
    public Settings(
            int maxWeightMinuteAverage,
            int font, int fontMedium,
            int fontBig,
            int orderClienti,
            int orderProdotti,
            int orderTare,
            int widthScrollBar,
            String salvataggiDirectory)
    {
        this.maxWeightMinuteAverage = maxWeightMinuteAverage;
        this.font = font;
        this.fontMedium = fontMedium;
        this.fontBig = fontBig;
        this.orderClienti = orderClienti;
        this.orderProdotti = orderProdotti;
        this.orderTare = orderTare;
        this.widthScrollBar = widthScrollBar;
        this.salvataggiDirectory = salvataggiDirectory;
    }
    public Settings()
    {
    }
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Field f: getClass().getDeclaredFields()) {
            try {
            result
            .append(f.get(this))
            .append(SPLITTER_VAR)
            .append(f.getName())
            .append(System.getProperty("line.separator"));
            }
            catch (IllegalStateException ise) {
                result
                .append("")
                .append(SPLITTER_VAR)
                .append(f.getName())
                .append(System.getProperty("line.separator"));
            }
            // nope
            catch (IllegalAccessException iae) {}
        }
        return result.toString();
    }
    public String toFile()
    {
        StringBuilder result = new StringBuilder();
        for (Field f: getClass().getDeclaredFields()) {
            try {
            result
            .append(f.get(this))
            .append(SPLITTER_VAR);
            }
            catch (IllegalStateException ise) {
                result
                .append("")
                .append(SPLITTER_VAR);
            }
            // nope
            catch (IllegalAccessException iae) {}
        }
        //return result.toString();
        return toString();
    }
    
    public static Settings fromFile(String var)
    {
        try
        {
            String[] split2 = var.split(SPLITTER_LINE);
            String[][] split = new String[split2.length][2];
            int j=0;
            for(String s: split2)
            {
                String[] split3 = s.split(SPLITTER_VAR);
                split[j++][0] = split3[0];
            }
            int i=21;
            int maxWeightMinuteAverage = Integer.parseInt(split[++i][0]);
            int font = Integer.parseInt(split[++i][0]);
            int fontMedium = Integer.parseInt(split[++i][0]);
            int fontBig = Integer.parseInt(split[++i][0]);
            int orderClienti = Integer.parseInt(split[++i][0]);
            int orderProdotti = Integer.parseInt(split[++i][0]);
            int orderTare = Integer.parseInt(split[++i][0]);
            int widthScrollBar = Integer.parseInt(split[++i][0]);
            String saveDirectory = split[++i][0];
            Settings settings = new Settings(
                    maxWeightMinuteAverage,
                    font,
                    fontMedium,
                    fontBig,
                    orderClienti,
                    orderProdotti,
                    orderTare,
                    widthScrollBar,
                    saveDirectory
            );
            return settings;
        }
        catch(ArrayIndexOutOfBoundsException | NumberFormatException a)
        {
            a.printStackTrace();
        }
        return null;
    }
    public String getSalvataggiDirectory()
    {
        Path path = Paths.get(salvataggiDirectory);
        if (Files.exists(path))
        {
            if(!salvataggiDirectory.isEmpty())
            {
                return salvataggiDirectory + "/" + SALVATAGGI_DIRECTORY;
            }
            return SALVATAGGI_DIRECTORY;
        }
        return SALVATAGGI_DIRECTORY;
    }
    public String getSalvataggiSettimanaliDirectory()
    {
        Path path = Paths.get(salvataggiDirectory);
        if (Files.exists(path))
        {
            if(!salvataggiDirectory.isEmpty())
            {
                Path path2 = Paths.get(salvataggiDirectory + "/" + SALVATAGGI_DIRECTORY);
                if (!Files.exists(path2))
                {
                    IO.creaPath(salvataggiDirectory + "/" + SALVATAGGI_DIRECTORY);
                }
                return salvataggiDirectory + "/" + SALVATAGGI_DIRECTORY + "/" + SETTIMANALI_DIRECTORY;
            }
            else
            {
                Path path2 = Paths.get(SALVATAGGI_DIRECTORY);
                if (!Files.exists(path2))
                {
                    IO.creaPath(SALVATAGGI_DIRECTORY);
                }
                return SALVATAGGI_DIRECTORY + "/" + SETTIMANALI_DIRECTORY;
            }
            
        }
        return SALVATAGGI_DIRECTORY + "/" + SETTIMANALI_DIRECTORY;
    }
}
