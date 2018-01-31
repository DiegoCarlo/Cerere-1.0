/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stampante;

import java.nio.charset.StandardCharsets;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.PrintServiceAttribute;
import javax.print.attribute.standard.PrinterName;

/**
 *
 * @author DiegoCG
 */
public class Stampante
{
    public static String stampa(Etichetta etichetta)
    {
        String intestazione = etichetta.getIntestazione();
        String pesate = etichetta.getZebraCode();
        stampaParziale(pesate);
        stampaParziale(intestazione);
        return (intestazione + "\n" + pesate).replaceAll(Etichetta.backslash+Etichetta.newLine, "\n");
    }
    public static void stampaParziale(String s)
    {
        try {

        PrintService psZebra = null;
        String sPrinterName = null;
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);


        for (int i = 0; i < services.length; i++) {


        PrintServiceAttribute attr = services[i].getAttribute(PrinterName.class);
        sPrinterName = ((PrinterName) attr).getValue();


        if (sPrinterName.indexOf("ZEBRA GK420d") >= 0) {
        psZebra = services[i];
        break;
        }
        }

//123456789123456789123456789123 lunghezza massima
        System.out.println(s);


        byte[] by = s.getBytes();
        System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
        System.out.println(new String(by, StandardCharsets.UTF_8));
        System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        Doc doc = new SimpleDoc(by, flavor, null);
        if (psZebra == null) {
        System.out.println("Stampante non trovata!");
        return;
        }

        
        DocPrintJob job = psZebra.createPrintJob();
        job.print(doc, null);


        } catch (PrintException a) {
        a.printStackTrace();
        }

    }
}
