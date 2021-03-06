/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toFile.dataType.Complex;

import Interface.IdName;
import toFile.Utility.Utility;
import Interface.ProgressionBar;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import registro.Registro;
import registro.Settings;
import toFile.Utility.IO;
import toFile.dataType.Base.Cliente;
import toFile.dataType.Base.Pesata;
import toFile.dataType.Base.Prodotto;
import toFile.dataType.Base.Tara;
import toFile.dataType.Base.UnitOfMeasure;
import toFile.dataType.Frequency.ClientsFrequencyList;
import toFile.dataType.Frequency.Frequency;
import toFile.dataType.Frequency.ProductsFrequencyList;
import toFile.dataType.Ordinals.Comparator.AlphabeticalComparator;
import toFile.dataType.Ordinals.Comparator.Behavior;
import toFile.dataType.Ordinals.Comparator.CalendarComparator;
import toFile.dataType.Ordinals.Comparator.FrequencyProductComparator;
import toFile.dataType.Ordinals.Comparator.IdComparator;
import toFile.dataType.Ordinals.PesateArray;
import toFile.dataType.Ordinals.OrdinableArray;
import toFile.dataType.Ordinals.OrdinableObject;

/**
 *
 * @author DiegoCG
 */
public class DataBase
{
    //374493560
    public OrdinableArray clienti;
    public OrdinableArray removedClienti;
    public OrdinableArray prodotti;
    public OrdinableArray removedProdotti;
    public OrdinableArray tare;
    
    public ClientsFrequencyList frequency;
    
    public PesateArray pesate;
    public PesateArray removedPesate;

    // spaziatore
    static final String s1 = "~";
    // separatore 2
    static final String s2 = "§";
    // new line
    static final String n = "\n";
    static final String t = "\t";
    static final String nl = "\r\n";
    
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
        savePesate(GregorianCalendar.getInstance());
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
        scrittura += "id cardinal name averageMinute weight" + Settings.SPLITTER_LINE;
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
                for(int i=2; i<split.length; i++)
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
        scrittura += "id cardinal name averageMinute weight" + Settings.SPLITTER_LINE;
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
        scrittura += "id cardinal name unitOfMeesasure" + Settings.SPLITTER_LINE;
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
                for(int i=2; i<split.length; i++)
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
        scrittura += "id cardinal name unitOfMeesasure" + Settings.SPLITTER_LINE;
        for(OrdinableObject o: removedProdotti)
        {
            Prodotto p = (Prodotto)o;
            scrittura += p.toFile(Settings.SPLITTER_VAR) + Settings.SPLITTER_LINE;
        }
        IO.writeStringFile(Settings.PRODOTTI_ELIMINATI, scrittura);
    }
    public void loadTare()
    {
        String lettura = IO.readStringFile(Settings.TARE);
        if(lettura != null)
        {
            String[] split = lettura.split(Settings.SPLITTER_LINE);
            String[] idCardinal = split[0].split(Settings.SPLITTER_VAR);
            try
            {
                long id = Long.parseLong(idCardinal[0]);
                long cardinal = Long.parseLong(idCardinal[1]);
                tare = new OrdinableArray(id, cardinal);
                for(int i=2; i<split.length; i++)
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
        scrittura += "id cardinal name quantity" + Settings.SPLITTER_LINE;
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
    
    public void loadPesate(Calendar calendar)
    {
        loadPesate(Utility.getYearMonth(calendar));
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
    public void savePesate(Date date)
    {
        savePesate(Utility.getYearMonth(date));
    }
    public void savePesate(Calendar calendar)
    {
        savePesate(Utility.getYearMonth(calendar));
    }
    public void savePesate(String fileName)
    {
        String scrittura = pesate.getId() + Settings.SPLITTER_LINE;
        scrittura += "id idClient idProduct quantity timeInMilli" + Settings.SPLITTER_LINE;
        for(Pesata p: pesate)
        {
            scrittura += p.toFile(Settings.SPLITTER_VAR) + Settings.SPLITTER_LINE;
        }
        IO.writeStringFile(Settings.MESI_PESATE_DIRECTORY + "/" + fileName + Settings.EXT, scrittura);
    }
    public void savePesate(String directory, String fileName)
    {
        String scrittura = pesate.getId() + Settings.SPLITTER_LINE;
        scrittura += "id idClient idProduct quantity timeInMilli" + Settings.SPLITTER_LINE;
        for(Pesata p: pesate)
        {
            scrittura += p.toFile(Settings.SPLITTER_VAR) + Settings.SPLITTER_LINE;
        }
        IO.writeStringFile(directory, scrittura);
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
                removedPesate = new PesateArray(id);
                for(int i=2; i<split.length; i++)
                {
                    Pesata p = Pesata.fromFile(split[i].split(Settings.SPLITTER_VAR));
                    removedPesate.add(p);
                }
            }
            catch(NumberFormatException | ArrayIndexOutOfBoundsException n)
            {
                n.printStackTrace();
            }
        }
        else
        {
            removedPesate = new PesateArray(0);
        }
    }
    public void saveRemovedPesate()
    {
        String scrittura = removedPesate.getId() + Settings.SPLITTER_LINE;
        scrittura += "id idClient idProduct quantity timeInMilli" + Settings.SPLITTER_LINE;
        for(Pesata p: removedPesate)
        {
            scrittura += p.toFile(Settings.SPLITTER_VAR) + Settings.SPLITTER_LINE;
        }
        IO.writeStringFile(Settings.PESATE_ELIMINATE, scrittura);
    }

    public ArrayList<Long> getPesateClienteMese(long idCliente, Calendar cal, Behavior verso)
    {
        log("Creo lista di id del cliente di questo mese");
        ArrayList<Long> listaId = new ArrayList<>();
        //DataPath daVisualizzare = new DataPath(cal);
        
        setElencoPesate(idCliente, cal);
        
        Collections.sort(pesate, new CalendarComparator(verso));
                
        for(Pesata o: pesate)
        {
            Pesata p = (Pesata)o;
            if(p.idCliente == idCliente)
            {
                listaId.add(p.getId());
            }
        }
        return listaId;
    }
    public void setElencoPesate(Long idCliente, Calendar cal)
    {
        log("aggiorno l'elenco di pesate in lettura in base al cliente e alla data");
        
        String dp = Utility.getYearMonth(cal);

        if(dp != null)
        {
            log("il mese trovato è"+dp.toString());
            // il mese è presente lo carico in pesateInLettura
            loadPesate(dp);
        }
        else
        {
            pesate = new PesateArray(0) ;
        }
    }
    public IdName[] elaboraTotaliCliente(long idCliente, Calendar date, JTable tabella)
    {
        log("elaboraTotaliCliente");
        ArrayList<Long> idPesate = getPesateClienteMese(idCliente, date, Behavior.INCREASING);
        OrdinableArray prodotti = getAllProducts();
        // rintraccio tutte le pesate del cliente
        //log("carico le pesate \n Giorno");
        ArrayList<Pesata> listaPesate = new ArrayList<>();
        for(long idPesata: idPesate)
        {
            Pesata p = pesate.get(idPesata);
            listaPesate.add(p);
            //log(p.data.get(Calendar.DAY_OF_MONTH)+"\t"+p.idProdotto +"\t"+p.quantitaFisica);
        }
        ArrayList<Prodotto> listaProdotti = new ArrayList<>();
        for(Pesata pe: listaPesate)
        {
            boolean presente = false;
            for(Prodotto pr: listaProdotti)
            {
                if((pe).idProdotto == pr.getId())
                {
                    presente = true;
                    break;
                }
            }
            if(!presente)
            {
                listaProdotti.add((Prodotto)prodotti.get((pe).idProdotto));
            }
        }
        Collections.sort(listaProdotti, new IdComparator(Behavior.INCREASING));
                
        // creo l'intestazione delle colonne con i nomi dei prodotti
        IdName[] nomiProdotti = new IdName[listaProdotti.size()];
        int f=0;
        for(Prodotto pr: listaProdotti)
        {
            nomiProdotti[f] = new IdName(pr.getId(),pr.getName());
            //System.out.println(nomiProdotti[f]);
            f++;
        }
        int numeroGiorniDelMese = date.getActualMaximum(Calendar.DAY_OF_MONTH);
        //numeroGiorniDelMese += 2; // linee per i totali

        Object[][] dati = new Object[numeroGiorniDelMese][nomiProdotti.length];

        float[] totali = new float[listaProdotti.size()];
        Arrays.fill(totali, 0);

        //log("avvio il popolamento della tabella");
        for(int i=0; i<numeroGiorniDelMese; i++)
        {
            //log(i+"");
            for(int j=0; j<nomiProdotti.length; j++)
            {
                Prodotto pr = listaProdotti.get(j);
                //log("cerco le pesate riferite a:");
                //log("id: "+ pr.getId() + " "+pr.getNome());
                float totaleGiorno = 0;
                for(Pesata pe: listaPesate)
                {
                    //log("giorno da trovare: " + (i+1) + " giorno pesata: "+ pe.data.get(Calendar.DAY_OF_MONTH));
                    //log("Pesata idProdotto"+ pe.idProdotto + "/t"+ pe.quantitaFisica);
                    if(
                            pe.time.get(Calendar.DAY_OF_MONTH) == i+1 &&
                            pe.idProdotto == pr.getId())
                    {
                        totaleGiorno += pe.quantita;
                        totali[j] += pe.quantita;
                    }
                }
                //log("totale giorno: "+totaleGiorno);
                //log("totale attuale: "+totali[j]);


                if(totaleGiorno != 0)
                {    
                    String format = "%.3f";
                    if(pr.unitOfMeasure == UnitOfMeasure.QUANTITY)
                    {
                       format = "%.0f";
                    }
                    dati[i][j] = String.format(format, totaleGiorno);
                }
                else
                {
                    dati[i][j] = "";
                }
            }
        }

        IdName[] intestazione = new IdName[nomiProdotti.length+1];
        intestazione[0] = new IdName(-1, "Giorno:");
        for(int i=0; i<nomiProdotti.length; i++)
        {
            intestazione[i+1] = nomiProdotti[i];
        }
        String[] giorni = {"Domenica", "Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato"};
        
        Calendar giorniCalendario = Calendar.getInstance();
        giorniCalendario.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), 1);
        
        Object[][] datiComplessivi = new Object[numeroGiorniDelMese+2][intestazione.length];
        
        for(int i=0; i<numeroGiorniDelMese; i++)
        {
            giorniCalendario.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), 1+i);
            int g = giorniCalendario.get(Calendar.DAY_OF_WEEK);
            datiComplessivi[i][0] = i+1+" "+giorni[g-1];
            for(int j=0; j<nomiProdotti.length; j++)
            {
                datiComplessivi[i][j+1] = dati[i][j];
            }
        }
        for(int i=0; i<totali.length; i++)
        {
            Prodotto pr = listaProdotti.get(i);
            String format = "%.3f";
            if(pr.unitOfMeasure == UnitOfMeasure.QUANTITY)
            {
               format = "%.0f";
            }
            datiComplessivi[numeroGiorniDelMese+1][i+1] = String.format(format, totali[i]);
        }
        TableModel dataModel = new DefaultTableModel(datiComplessivi, intestazione);


        tabella.setModel(dataModel);
        for(int i=0; i<datiComplessivi[0].length; i++)
        {
            DefaultTableCellRenderer center = new DefaultTableCellRenderer();
            center.setHorizontalAlignment(JLabel.CENTER);
            tabella.getColumnModel().getColumn(i).setCellRenderer(center);
        }
        return nomiProdotti;
    }

    public void updateClientFrequency(long id)
    {
        Collections.sort(prodotti, new AlphabeticalComparator(Behavior.INCREASING));
        for(OrdinableObject p: prodotti)
        {
            ((Prodotto)p).frequency = 0;
        }
        ProductsFrequencyList lista = frequency.get(id);
        if(lista != null)
        {
            for(Frequency f: lista)
            {
                Prodotto prodotto = (Prodotto)prodotti.get(f.idProdotto);
                if(prodotto != null)
                {
                    prodotto.frequency = f.frequency;
                }
            }
            
            Collections.sort(prodotti, new FrequencyProductComparator());
        }
    }

    
    public OrdinableArray getAllClients()
    {
        OrdinableArray o = new OrdinableArray(0, 0);
        for(OrdinableObject c: clienti)
        {
            o.add(c);
        }
        for(OrdinableObject c: removedClienti)
        {
            o.add(c);
        }
        return o;
    }
    public OrdinableArray getAllProducts()
    {
        OrdinableArray o = new OrdinableArray(0, 0);
        for(OrdinableObject c: prodotti)
        {
            o.add(c);
        }
        for(OrdinableObject c: removedProdotti)
        {
            o.add(c);
        }
        return o;
    }
    
    public void saveFileTotali(Calendar cal)
    {
        Registro.progressionBar = new ProgressionBar(clienti.size());
        Registro.progressionBar.setVisible(true);
        String riassunto = "";
        
        OrdinableArray clienti = getAllClients();
        
        OrdinableArray prodotti = getAllProducts();
        
        
        for(OrdinableObject o: clienti)
        {
            Cliente c = (Cliente)o;
            ArrayList<Long> idPesate = getPesateClienteMese(c.getId(), cal, Behavior.INCREASING);
            if(idPesate.size() > 0)
            {
                // rintraccio tutte le pesate del cliente
                //log("carico le pesate \n Giorno");
                ArrayList<Pesata> listaPesate = new ArrayList<>();
                for(long idPesata: idPesate)
                {
                    Pesata p = (Pesata)pesate.get(idPesata);
                    listaPesate.add(p);
                    //log(p.data.get(Calendar.DAY_OF_MONTH)+"\t"+p.idProdotto +"\t"+p.quantitaFisica);
                }

                // rintraccio tutti i tipi di prodotti pesati
                ArrayList<Prodotto> listaProdotti = new ArrayList<>();
                
                Collections.sort(listaProdotti, new IdComparator(Behavior.INCREASING));
                for(Pesata pe: listaPesate)
                {
                    boolean presente = false;
                    for(Prodotto pr: listaProdotti)
                    {
                        if((pe).idProdotto == pr.getId())
                        {
                            presente = true;
                            break;
                        }
                    }
                    if(!presente)
                    {
                        listaProdotti.add((Prodotto)prodotti.get((pe).idProdotto));
                    }
                }

                // creo l'intestazione delle colonne con i nomi dei prodotti
                String[] nomiProdotti = new String[listaProdotti.size()];
                int f=0;
                for(Prodotto pr: listaProdotti)
                {
                    nomiProdotti[f] = pr.getName();
                    //System.out.println(nomiProdotti[f]);
                    f++;
                }

                int numeroGiorniDelMese = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                //numeroGiorniDelMese += 2; // linee per i totali

                Object[][] dati = new Object[numeroGiorniDelMese][nomiProdotti.length];

                float[] totali = new float[listaProdotti.size()];
                Arrays.fill(totali, 0);

                //log("avvio il popolamento della tabella");
                for(int i=0; i<numeroGiorniDelMese; i++)
                {
                    //log(i+"");
                    for(int j=0; j<nomiProdotti.length; j++)
                    {
                        Prodotto pr = listaProdotti.get(j);
                        //log("cerco le pesate riferite a:");
                        //log("id: "+ pr.getId() + " "+pr.getNome());
                        float totaleGiorno = 0;
                        for(Pesata pe: listaPesate)
                        {
                            //log("giorno da trovare: " + (i+1) + " giorno pesata: "+ pe.data.get(Calendar.DAY_OF_MONTH));
                            //log("Pesata idProdotto"+ pe.idProdotto + "/t"+ pe.quantitaFisica);
                            if(
                                    pe.time.get(Calendar.DAY_OF_MONTH) == i+1 &&
                                    pe.idProdotto == pr.getId())
                            {
                                totaleGiorno += pe.quantita;
                                totali[j] += pe.quantita;
                            }
                        }
                        //log("totale giorno: "+totaleGiorno);
                        //log("totale attuale: "+totali[j]);


                        if(totaleGiorno != 0)
                        {    
                            String format = "%.3f";
                            if(pr.unitOfMeasure == UnitOfMeasure.QUANTITY)
                            {
                               format = "%.0f";
                            }
                            dati[i][j] = String.format(format, totaleGiorno);
                        }
                        else
                        {
                            dati[i][j] = "";
                        }
                    }
                }

                riassunto += c.getName() + nl;
                String scrittura = c.getName()+ nl;
                scrittura += "Giorno";
                for(int i=0; i<nomiProdotti.length; i++)
                {
                    scrittura += t + nomiProdotti[i];
                    riassunto += t + nomiProdotti[i];
                }

                riassunto += nl;
                scrittura += nl;
                for(int i=0; i<numeroGiorniDelMese; i++)
                {
                    scrittura += (i+1);
                    for(int j=0; j<nomiProdotti.length; j++)
                    {
                        scrittura += t + dati[i][j];
                    }
                    scrittura += nl;
                }
                scrittura += nl;
                for(int i=0; i<totali.length; i++)
                {
                    Prodotto pr = listaProdotti.get(i);
                    String format = "%.3f";
                    if(pr.unitOfMeasure == UnitOfMeasure.QUANTITY)
                    {
                       format = "%.0f";
                    }
                    riassunto += t + String.format(format, totali[i]);
                    scrittura += t + String.format(format, totali[i]);
                }

                riassunto += nl + nl + nl;

                Boolean results = IO.creaPath(Registro.settings.getSalvataggiDirectory());
                
                
                String tot = Registro.settings.getSalvataggiDirectory() + "/"+ Utility.getYearMonth(cal);
                results = IO.creaPath(tot);
                
                IO.writeStringFile(tot + "/"+ c.getName() + Settings.EXT_EXCEL, scrittura);
                Registro.progressionBar.progress(c.getName());
                
            }
        }
        String tot = Registro.settings.getSalvataggiDirectory() + "/"+ Utility.getYearMonth(cal);
        
        IO.writeStringFile(tot + "/" + Utility.getYearMonth(cal) + " Riassunto" + Settings.EXT_EXCEL, riassunto);
        savePesate(tot + "/" + Utility.getYearMonth(cal) + " back up" + Settings.EXT, Utility.getYearMonth(cal));
        
        Registro.progressionBar.close();
    }

    public void saveTotaliSettimana(long idCliente, Calendar firstDay, Calendar lastDay)
    {
        Registro.progressionBar = new ProgressionBar(4,250);
        Registro.progressionBar.setVisible(true);
        
        OrdinableArray prodotti = getAllProducts();
        
        ArrayList<Pesata> listaPesate = new ArrayList<>();
        if(firstDay.get(Calendar.MONTH) == lastDay.get(Calendar.MONTH))
        {
            ArrayList<Long> idPesate = getPesateClienteMese(idCliente, firstDay, Behavior.DECREASING);
            for(long idPesata: idPesate)
            {
                Pesata pesata = pesate.get(idPesata);
                if(
                        firstDay.get(Calendar.DAY_OF_MONTH) <= pesata.time.get(Calendar.DAY_OF_MONTH) &&
                        pesata.time.get(Calendar.DAY_OF_MONTH) <= lastDay.get(Calendar.DAY_OF_MONTH) 
                        )
                {
                    listaPesate.add(pesata);
                }
            }
        }
        else
        {
            ArrayList<Long> idPesate = getPesateClienteMese(idCliente, firstDay, Behavior.DECREASING);
            for(long idPesata: idPesate)
            {
                Pesata pesata = (Pesata)pesate.get(idPesata);
                if(
                        firstDay.get(Calendar.DAY_OF_MONTH) <= pesata.time.get(Calendar.DAY_OF_MONTH) &&
                        pesata.time.get(Calendar.DAY_OF_MONTH) <= firstDay.getActualMaximum(Calendar.DAY_OF_MONTH) 
                        )
                {
                    listaPesate.add(pesata);
                }
            }
            idPesate = getPesateClienteMese(idCliente, lastDay, Behavior.DECREASING);
            for(long idPesata: idPesate)
            {
                Pesata pesata = (Pesata)pesate.get(idPesata);
                if(
                        1 <= pesata.time.get(Calendar.DAY_OF_MONTH) &&
                        pesata.time.get(Calendar.DAY_OF_MONTH) <= lastDay.get(Calendar.DAY_OF_MONTH) 
                        )
                {
                    listaPesate.add(pesata);
                }
            }
        }
        Registro.progressionBar.progress();
       
        if(listaPesate.size() > 0)
        {
            // rintraccio tutti i tipi di prodotti pesati
            ArrayList<Prodotto> listaProdotti = new ArrayList<>();
            for(Pesata pe: listaPesate)
            {
                boolean presente = false;
                for(Prodotto pr: listaProdotti)
                {
                    if((pe).idProdotto == pr.getId())
                    {
                        presente = true;
                        break;
                    }
                }
                if(!presente)
                {
                    listaProdotti.add((Prodotto)prodotti.get((pe).idProdotto));
                }
            }
            
            Collections.sort(listaProdotti, new IdComparator(Behavior.INCREASING));
                
            // creo l'intestazione delle colonne con i nomi dei prodotti
            String[] nomiProdotti = new String[listaProdotti.size()];
            int f=0;
            for(Prodotto pr: listaProdotti)
            {
                nomiProdotti[f] = pr.getName();
                //System.out.println(nomiProdotti[f]);
                f++;
            }

            int numeroGiorniDelMese = 7;
            //numeroGiorniDelMese += 2; // linee per i totali

            Object[][] dati = new Object[numeroGiorniDelMese][nomiProdotti.length];

            float[] totali = new float[listaProdotti.size()];
            Arrays.fill(totali, 0);

            //log("avvio il popolamento della tabella");
            int nuovoMese = 1;
            for(int i=0; i<numeroGiorniDelMese; i++)
            {
                //log(i+"");
                for(int j=0; j<nomiProdotti.length; j++)
                {
                    Prodotto pr = listaProdotti.get(j);
                    //log("cerco le pesate riferite a:");
                    //log("id: "+ pr.getId() + " "+pr.getNome());
                    float totaleGiorno = 0;
                    for(Pesata pe: listaPesate)
                    {
                        //log("giorno da trovare: " + (i+1) + " giorno pesata: "+ pe.data.get(Calendar.DAY_OF_MONTH));
                        //log("Pesata idProdotto"+ pe.idProdotto + "/t"+ pe.quantitaFisica);
                        if(i+firstDay.get(Calendar.DAY_OF_MONTH) <= firstDay.getActualMaximum(Calendar.DAY_OF_MONTH))
                        {
                            if(
                                    pe.time.get(Calendar.DAY_OF_MONTH) == i+firstDay.get(Calendar.DAY_OF_MONTH) &&
                                    pe.idProdotto == pr.getId())
                            {
                                totaleGiorno += pe.quantita;
                                totali[j] += pe.quantita;
                            }
                        }
                        else
                        {
                            if(
                                    pe.time.get(Calendar.DAY_OF_MONTH) == nuovoMese &&
                                    pe.idProdotto == pr.getId())
                            {
                                totaleGiorno += pe.quantita;
                                totali[j] += pe.quantita;
                            }
                        }
                    }
                    //log("totale giorno: "+totaleGiorno);
                    //log("totale attuale: "+totali[j]);


                    if(totaleGiorno != 0)
                    {    
                        String format = "%.3f";
                        if(pr.unitOfMeasure == UnitOfMeasure.QUANTITY)
                        {
                           format = "%.0f";
                        }
                        dati[i][j] = String.format(format, totaleGiorno);
                    }
                    else
                    {
                        dati[i][j] = "";
                    }
                }
                if(i+firstDay.get(Calendar.DAY_OF_MONTH) > firstDay.getActualMaximum(Calendar.DAY_OF_MONTH))
                {
                    nuovoMese ++;
                }
            }
            Cliente c = (Cliente)clienti.get(idCliente);
            String scrittura = c.getName()+ nl;
            scrittura += "Giorno";
            for(int i=0; i<nomiProdotti.length; i++)
            {
                scrittura += t + nomiProdotti[i];
            }

            scrittura += nl;
            
            int numeroGiorniDelMese1 = firstDay.getActualMaximum(Calendar.DAY_OF_MONTH);
            nuovoMese = 0;
            for(int i=0; i<numeroGiorniDelMese; i++)
            {
                
                if(firstDay.get(Calendar.DAY_OF_MONTH) + i <= numeroGiorniDelMese1)
                {
                    scrittura += (firstDay.get(Calendar.DAY_OF_MONTH) + i);
                }
                else
                {
                    scrittura += (1 + nuovoMese++);
                }
                for(int j=0; j<nomiProdotti.length; j++)
                {
                    scrittura += t + dati[i][j];
                }
                scrittura += nl;
            }
            scrittura += nl;
            for(int i=0; i<totali.length; i++)
            {
                Prodotto pr = listaProdotti.get(i);
                String format = "%.3f";
                if(pr.unitOfMeasure == UnitOfMeasure.QUANTITY)
                {
                   format = "%.0f";
                }
                scrittura += t + String.format(format, totali[i]);
            }


            Boolean results = IO.creaPath(Registro.settings.getSalvataggiSettimanaliDirectory());
            Registro.progressionBar.progress();
            //log(pathPesateMensili + " " + results);
            String nomeFile = c.getName()+ " " +
                    firstDay.get(Calendar.DAY_OF_MONTH) + "-" +
                    (1 + firstDay.get(Calendar.MONTH)) + " " +
                    lastDay.get(Calendar.DAY_OF_MONTH) + "-" +
                    (1 + lastDay.get(Calendar.MONTH)) + Settings.EXT_EXCEL;
            Registro.progressionBar.progress();
            //log(pathPesateMensili + " " + results);
            IO.writeStringFile(Registro.settings.getSalvataggiSettimanaliDirectory() + "/" + nomeFile, scrittura);
            Registro.progressionBar.progress();
            
        }
        Registro.progressionBar.close();
    }

}
