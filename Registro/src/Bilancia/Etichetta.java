/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bilancia;

import java.awt.Font;
import java.awt.print.Paper;
import java.util.ArrayList;
import toFile.Utility.Utility;

/**
 *
 * @author DiegoCG
 */
public class Etichetta
{
    
    String intestazione;
    String cliente;
    ArrayList<String> prodotto;
    String chiusura;
    String data;
    Paper pagina;
    int gapLine;
    Font font;
    int line = 0;
    int x = 10;
    int y = 20;

    String totPeso;
    String totNume;
    
    public Etichetta(String data, String cliente, ArrayList<String> prodotto, String totPeso, String totNume)
    {
        this.intestazione = "Forneria Voglia di Pane\n"
                + "via Ducco n 13, 25123 Brescia\n"
                + "TEL 030/302648 3397315658\n"
                + "Cliente: \n"
                + "Data: \n"
                + "consegna di pane art.24 legge4-7-67 n°580\n"
                + "distinta da conservare fino all'esaurimento\n"
                + "del prodotto\n"
                + "cessione riguardante contratto di somministrazione\n"
                + "ai sensi dell'art 6 lettera a dpr 26-10-72, 632\n"
                + "esonerata dalla emissione della bolla di\n"
                + "accompagnamento merci viaggianti ai sensi\n"
                + "dell'art. 4-6 dpr 627 del 6-10-78 segue fattura";
        this.cliente = cliente;
        this.data = data;
        this.prodotto = prodotto;
        this.chiusura = "chiusura";
        this.totPeso = totPeso;
        this.totNume = totNume;
    }
    
    
    public String getZebraCode()
    {
        /*
            ^XA
            ^LH30,30
            ^FO20,10
            ^ADN,90,50
            ^AD^FDWikipedia^FS
            ^XZ
        */
        int maxLarghezza = 24;
        char backslash = '\\';
        String newLine = "\\&";
        String s = "^XA\n"
                + "^CF0,30,30^FO25,50"
                + "^FB405,24,,C,"
                + "^FD"
                + data /*+ "^FS\n"
                + "^CF0,30,30"
                + "^FB405,35,,C,"
                + "^FD"*/
                + newLine + newLine + cliente.toUpperCase() + newLine;
                for(int i=0; i<prodotto.size(); i++)
                {
                    s = s + newLine;
                    
                    s = s + Utility.getMax1Dot(maxLarghezza, prodotto.get(i).toUpperCase());
                }
                if(totPeso != null || totNume != null)
                {
                    s = s + newLine;
                }
                if(totPeso != null)
                {
                    s = s + newLine + Utility.getMax(maxLarghezza, totPeso);
                }
                if(totNume != null)
                {
                    s = s + newLine + Utility.getMax(maxLarghezza, totNume);
                }
                s = s + "^FS\n"
                + "^XZ";
        return s;
        
        /**
^XA
^CF0,30,20^FO25,50
^FB490,20,,
^FD
\&
\&
\&Forneria Voglia di Pane
\&via Ducco n 13, 25123 Brescia
\&TEL 030/302648 3397315658
\&29/11 19:4\&ragioneSociale c
\&Fondo Pizza 9.0 n
\&consegna di pane art.24 legge4-7-67 n580
\&------------------------------
\&distinta da conservare fino all'esaurimento
\&del prodotto
\&cessione riguardante contratto di somministrazione
\&ai sensi dell'art 6 lettera a dpr 26-10-72, 632
\&esonerata dalla emissione della bolla di
\&accompagnamento merci viaggianti ai sensi
\&dell'art. 4-6 dpr 627 del 6-10-78 segue fattura
^FS
^XZ
         */
    }
    public String getIntestazione()
    {
        /*
            ^XA
            ^LH30,30
            ^FO20,10
            ^ADN,90,50
            ^AD^FDWikipedia^FS
            ^XZ
        */
        int maxLarghezza = 30;
        char backslash = '\\';
        String newLine = "\\&";

        String s = "^XA\n"
                + "^CFF,9,5^FO25,50"
                + "^FB405,24,,C,"
                + "^FD"
                + newLine + newLine 
                + newLine + "Forneria Voglia di Pane"
                + newLine + "via Ducco n 13, 25123 Brescia"
                + newLine + "TEL 030/302648 3397315658"
                + newLine + "-------------"
                + newLine + "consegna di pane art.24 legge4-7-67 n580"
                + newLine + "-------------"
                + newLine + "distinta da conservare fino all'esaurimento del prodotto "
                + "cessione riguardante contratto di somministrazione "
                + "ai sensi dell'art 6 lettera a dpr 26-10-72, 632 "
                + "esonerata dalla emissione della bolla di "
                + "accompagnamento merci viaggianti ai sensi "
                + "dell'art. 4-6 dpr 627 del 6-10-78 segue fattura"
                + "^FS\n"
                + "^XZ";
        return s;
        
        /**
^XA
^CF0,30,20^FO25,50
^FB490,20,,
^FD
\&
\&
\&Forneria Voglia di Pane
\&via Ducco n 13, 25123 Brescia
\&TEL 030/302648 3397315658
\&29/11 19:4\&ragioneSociale c
\&Fondo Pizza 9.0 n
\&consegna di pane art.24 legge4-7-67 n580
\&------------------------------
\&distinta da conservare fino all'esaurimento
\&del prodotto
\&cessione riguardante contratto di somministrazione
\&ai sensi dell'art 6 lettera a dpr 26-10-72, 632
\&esonerata dalla emissione della bolla di
\&accompagnamento merci viaggianti ai sensi
\&dell'art. 4-6 dpr 627 del 6-10-78 segue fattura
^FS
^XZ
         */
    }
    
    private String getLinea(String testo)
    {
        return 
                    "^AD^FD" + testo + "^FS";
    }
    private String getLinea(String testo, int n, int m)
    {
        return "^FO"+x+","+getLine()+"\n" +
                    "^ADN," + n + "," + m + "\n" +
                    "^AD^FD" + testo + "^FS\n";
    }
    private int getLine()
    {
        int temp = y*line;
        line++;
        return temp;
    }
}
