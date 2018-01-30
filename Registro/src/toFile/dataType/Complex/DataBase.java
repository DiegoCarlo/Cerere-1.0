/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toFile.dataType.Complex;

import toFile.Utility.Utility;
import Interface.ProgressionBar;
import java.util.GregorianCalendar;
import registro.Registro;
import registro.Settings;
import toFile.Utility.IO;
import toFile.dataType.Base.Cliente;
import toFile.dataType.Base.Pesata;
import toFile.dataType.Base.Prodotto;
import toFile.dataType.Base.Tara;
import toFile.dataType.Frequency.ClientsFrequencyList;
import toFile.dataType.Ordinals.PesateArray;
import toFile.dataType.Ordinals.OrdinableArray;
import toFile.dataType.Ordinals.OrdinableObject;

/**
 *
 * @author DiegoCG
 */
public class DataBase
{
    public OrdinableArray clienti;
    public OrdinableArray removedClienti;
    public OrdinableArray prodotti;
    public OrdinableArray removedProdotti;
    public OrdinableArray tare;
    
    public ClientsFrequencyList frequency;
    
    public PesateArray pesate;
    public PesateArray removedPesate;

    public DataBase()
    {
        clienti = new OrdinableArray(0, 0);
        removedClienti = new OrdinableArray(0, 0);
        prodotti = new OrdinableArray(0, 0);
        removedProdotti = new OrdinableArray(0, 0);
        tare = new OrdinableArray(0, 0);

        frequency = new ClientsFrequencyList();

        pesate = new PesateArray(0);
        removedPesate = new PesateArray(0);
    }
    public void log(String msg)
    {
        Registro.log(msg);
    }
    public void load(ProgressionBar progressionBar)
    {
        progressionBar.progress("Carico i Clienti");
        loadClienti();
        progressionBar.progress("Carico i Clienti...");
        loadRemovedClienti();
        progressionBar.progress("Carico i Prodotti");
        loadProdotti();
        progressionBar.progress("Carico i Prodotti...");
        loadRemovedProdotti();
        progressionBar.progress("Carico le Tare");
        loadTare();
        progressionBar.progress("Carico le Frequenze");
        loadFrequency();
        progressionBar.progress("Carico le Pesate");
        loadPesate(Utility.getYearMonth(GregorianCalendar.getInstance()));
        progressionBar.progress("Carico le Pesate...");
        loadRemovedPesate();
        
        
    }
    public void save()
    {
        saveClienti();
        saveRemovedClienti();
        saveProdotti();
        saveRemovedProdotti();
        saveFrequency();
        savePesate(Utility.getYearMonth(GregorianCalendar.getInstance()));
        saveRemovedPesate();
    }
   
    public void loadClienti()
    {
        String lettura = IO.readStringFile(Settings.CLIENTI);
        if(lettura != null)
        {
            String[] split = lettura.split(Settings.SPLITTER_LINE);
            String[] idCardinal = split[0].split(Settings.SPLITTER_VAR);
            try
            {
                long id = Long.parseLong(idCardinal[0]);
                long cardinal = Long.parseLong(idCardinal[1]);
                clienti = new OrdinableArray(id, cardinal);
                for(int i=2; i<split.length; i++)
                {
                    Cliente c = Cliente.fromFile(split[i].split(Settings.SPLITTER_VAR));
                    clienti.add(c);
                }
            }
            catch(NumberFormatException | ArrayIndexOutOfBoundsException n)
            {
                n.printStackTrace();
            }
        }
        else
        {
            clienti = new OrdinableArray(0, 0);
        }
    }
    public void saveClienti()
    {
        String scrittura = clienti.getId() + Settings.SPLITTER_VAR + clienti.getCardinale() + Settings.SPLITTER_LINE;
        for(OrdinableObject o: clienti)
        {
            Cliente c = (Cliente)o;
            scrittura += c.toFile(Settings.SPLITTER_VAR) + Settings.SPLITTER_LINE;
        }
        IO.writeStringFile(Settings.CLIENTI, scrittura);
    }
    public void loadRemovedClienti()
    {
        String lettura = IO.readStringFile(Settings.CLIENTI_ELIMINATI);
        if(lettura != null)
        {
            String[] split = lettura.split(Settings.SPLITTER_LINE);
            String[] idCardinal = split[0].split(Settings.SPLITTER_VAR);
            try
            {
                long id = Long.parseLong(idCardinal[0]);
                long cardinal = Long.parseLong(idCardinal[1]);
                removedClienti = new OrdinableArray(id, cardinal);
                for(int i=1; i<split.length; i++)
                {
                    Cliente c = Cliente.fromFile(split[i].split(Settings.SPLITTER_VAR));
                    removedClienti.add(c);
                }
            }
            catch(NumberFormatException | ArrayIndexOutOfBoundsException n)
            {
                n.printStackTrace();
            }
        }
        else
        {
            removedClienti = new OrdinableArray(0, 0);
        }
    }
    public void saveRemovedClienti()
    {
        String scrittura = removedClienti.getId() + Settings.SPLITTER_VAR + removedClienti.getCardinale() + Settings.SPLITTER_LINE;
        for(OrdinableObject o: removedClienti)
        {
            Cliente c = (Cliente)o;
            scrittura += c.toFile(Settings.SPLITTER_VAR) + Settings.SPLITTER_LINE;
        }
        IO.writeStringFile(Settings.CLIENTI_ELIMINATI, scrittura);
    }
    public void loadProdotti()
    {
        String lettura = IO.readStringFile(Settings.PRODOTTI);
        if(lettura != null)
        {
            String[] split = lettura.split(Settings.SPLITTER_LINE);
            String[] idCardinal = split[0].split(Settings.SPLITTER_VAR);
            try
            {
                long id = Long.parseLong(idCardinal[0]);
                long cardinal = Long.parseLong(idCardinal[1]);
                prodotti = new OrdinableArray(id, cardinal);
                for(int i=2; i<split.length; i++)
                {
                    Prodotto p = Prodotto.fromFile(split[i].split(Settings.SPLITTER_VAR));
                    prodotti.add(p);
                }
            }
            catch(NumberFormatException | ArrayIndexOutOfBoundsException n)
            {
                n.printStackTrace();
            }
        }
        else
        {
            prodotti = new OrdinableArray(0, 0);
        }
    }
    public void saveProdotti()
    {
        String scrittura = prodotti.getId() + Settings.SPLITTER_VAR + prodotti.getCardinale() + Settings.SPLITTER_LINE;
        for(OrdinableObject o: prodotti)
        {
            Prodotto p = (Prodotto)o;
            scrittura += p.toFile(Settings.SPLITTER_VAR) + Settings.SPLITTER_LINE;
        }
        IO.writeStringFile(Settings.PRODOTTI, scrittura);
    }
    public void loadRemovedProdotti()
    {
        String lettura = IO.readStringFile(Settings.PRODOTTI_ELIMINATI);
        if(lettura != null)
        {
            String[] split = lettura.split(Settings.SPLITTER_LINE);
            String[] idCardinal = split[0].split(Settings.SPLITTER_VAR);
            try
            {
                long id = Long.parseLong(idCardinal[0]);
                long cardinal = Long.parseLong(idCardinal[1]);
                removedProdotti = new OrdinableArray(id, cardinal);
                for(int i=1; i<split.length; i++)
                {
                    Prodotto p = Prodotto.fromFile(split[i].split(Settings.SPLITTER_VAR));
                    removedProdotti.add(p);
                }
            }
            catch(NumberFormatException | ArrayIndexOutOfBoundsException n)
            {
                n.printStackTrace();
            }
        }
        else
        {
            removedProdotti = new OrdinableArray(0, 0);
        }
    }
    public void saveRemovedProdotti()
    {
        String scrittura = removedProdotti.getId() + Settings.SPLITTER_VAR + removedProdotti.getCardinale() + Settings.SPLITTER_LINE;
        for(OrdinableObject o: prodotti)
        {
            Prodotto p = (Prodotto)o;
            scrittura += p.toFile(Settings.SPLITTER_VAR) + Settings.SPLITTER_LINE;
        }
        IO.writeStringFile(Settings.PRODOTTI_ELIMINATI, scrittura);
    }
    public void loadTare()
    {
        String lettura = IO.readStringFile(Settings.DATA_BASE_DIRECTORY + Settings.TARE + Settings.EXT);
        if(lettura != null)
        {
            String[] split = lettura.split(Settings.SPLITTER_LINE);
            String[] idCardinal = split[0].split(Settings.SPLITTER_VAR);
            try
            {
                long id = Long.parseLong(idCardinal[0]);
                long cardinal = Long.parseLong(idCardinal[1]);
                tare = new OrdinableArray(id, cardinal);
                for(int i=1; i<split.length; i++)
                {
                    Tara p = Tara.fromFile(split[i].split(Settings.SPLITTER_VAR));
                    tare.add(p);
                }
            }
            catch(NumberFormatException | ArrayIndexOutOfBoundsException n)
            {
                n.printStackTrace();
            }
        }
        else
        {
            tare = new OrdinableArray(0, 0);
        }
    }
    public void saveTare()
    {
        String scrittura = tare.getId() + Settings.SPLITTER_VAR + tare.getCardinale() + Settings.SPLITTER_LINE;
        for(OrdinableObject o: tare)
        {
            Tara p = (Tara)o;
            scrittura += p.toFile(Settings.SPLITTER_VAR) + Settings.SPLITTER_LINE;
        }
        IO.writeStringFile(Settings.TARE, scrittura);
    }
    
    public void loadFrequency()
    {
        String lettura = IO.readStringFile(Settings.FREQUENCY);
        if(lettura != null)
        {
            frequency = ClientsFrequencyList.fromFile(lettura);
        }
        else
        {
            frequency = new ClientsFrequencyList();
        }
    }
    
    public void saveFrequency()
    {
        IO.writeStringFile(Settings.FREQUENCY, frequency.toFile());
    }
    
    public void loadPesate(String fileName)
    {
        String lettura = IO.readStringFile(Settings.MESI_PESATE_DIRECTORY + "/" + fileName + Settings.EXT);
        if(lettura != null)
        {
            String[] split = lettura.split(Settings.SPLITTER_LINE);
            try
            {
                long id = Long.parseLong(split[0]);
                pesate = new PesateArray(id);
                for(int i=2; i<split.length; i++)
                {
                    Pesata p = Pesata.fromFile(split[i].split(Settings.SPLITTER_VAR));
                    pesate.add(p);
                }
            }
            catch(NumberFormatException | ArrayIndexOutOfBoundsException n)
            {
                n.printStackTrace();
            }
        }
        else
        {
            pesate = new PesateArray(0);
        }
    }
    public void savePesate(String fileName)
    {
        String scrittura = pesate.getId() + Settings.SPLITTER_LINE;
        for(Pesata p: pesate)
        {
            scrittura += p.toFile(Settings.SPLITTER_VAR) + Settings.SPLITTER_LINE;
        }
        IO.writeStringFile(Settings.MESI_PESATE_DIRECTORY + "/" + fileName + Settings.EXT, scrittura);
    }
    public void loadRemovedPesate()
    {
        String lettura = IO.readStringFile(Settings.PESATE_ELIMINATE);
        if(lettura != null)
        {
            String[] split = lettura.split(Settings.SPLITTER_LINE);
            try
            {
                long id = Long.parseLong(split[0]);
                pesate = new PesateArray(id);
                for(int i=2; i<split.length; i++)
                {
                    Pesata p = Pesata.fromFile(split[i].split(Settings.SPLITTER_VAR));
                    pesate.add(p);
                }
            }
            catch(NumberFormatException | ArrayIndexOutOfBoundsException n)
            {
                n.printStackTrace();
            }
        }
        else
        {
            pesate = new PesateArray(0);
        }
    }
    public void saveRemovedPesate()
    {
        String scrittura = pesate.getId() + Settings.SPLITTER_LINE;
        for(Pesata p: pesate)
        {
            scrittura += p.toFile(Settings.SPLITTER_VAR) + Settings.SPLITTER_LINE;
        }
        IO.writeStringFile(Settings.PESATE_ELIMINATE, scrittura);
    }
}
