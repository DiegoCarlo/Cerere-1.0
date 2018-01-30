package Bilancia;

import java.nio.charset.StandardCharsets;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import registro.Registro;

/**
 *
 * @author DiegoCG
 */
public class Bilancia implements SerialPortEventListener
{
    static SerialPort serialPort;
    String peso;
    String temp;
    
    public void inizializza(String s)
    {
        serialPort = new SerialPort(s); 
        try
        {
            serialPort.openPort();//Open port
            serialPort.setParams(9600, 8, 1, 0);//Set params
            int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;//Prepare mask
            serialPort.setEventsMask(mask);//Set mask
            serialPort.addEventListener(new Bilancia());//Add SerialPortEventListener
            log("aggiungo il Listener");
        }
        catch (SerialPortException ex)
        {
            System.out.println(ex);
            log(ex.toString());
            System.exit(1);
        }
    }
    /*
     * In this class must implement the method serialEvent, through it we learn about 
     * events that happened to our port. But we will not report on all events but only 
     * those that we put in the mask. In this case the arrival of the data and change the 
     * status lines CTS and DSR
     */
    public Bilancia()
    {
        super();
    }
    public Bilancia(String porta)
    {
        inizializza(porta);
    }
    public void richiedi()
    {
        try {
            //00000010 01001110 00000100 
            byte b[] = new byte[3];
            b[0] = Byte.parseByte("00000010", 2);
            b[1] = Byte.parseByte("01001110", 2);
            b[2] = Byte.parseByte("00000100", 2);
            
            
            if(!Registro.interfaccia.getSimBilancia())
            {
                serialPort.writeBytes(b);
            }
            else
            {
                double val = Math.random()*10;
                val = ((double)((int)(1000*val)))/1000;
                Registro.interfaccia.updatePeso((float)val);
            }
            //log(new String(b, StandardCharsets.UTF_8));
        }
        catch (SerialPortException ex)
        {
            System.out.println(ex);
            log(ex.toString());
        }
    }
    
    public void serialEvent(SerialPortEvent event)
    {
        if(event.isRXCHAR())
        {
            //String a = "NS  2,320  0,000  0,000     0000000000000F02433466 ";
            if(event.getEventValue() >= 53)
            {
                try
                {
                    byte buffer[] = serialPort.readBytes(event.getEventValue());
                    
                    String s =  new String(buffer, StandardCharsets.UTF_8)+"\n";
                    
                    if(Registro.interfaccia != null)
                    {
                        String lettura = s.substring(4, 9);
                        try
                        {
                            float val = Float.parseFloat(lettura.replace(',', '.'));
                            val = (float)(((double)((int)(1000*val)))/1000);
                            Registro.interfaccia.updatePeso(val);
                        }
                        catch(NumberFormatException c)
                        {
                            //lettura persa
                        }
                    }
                }
                catch (SerialPortException ex)
                {
                }
            }
        }
        else if(event.isCTS())//If CTS line has changed state
        {
            if(event.getEventValue() == 1)//If line is ON
            {
                System.out.println("CTS - ON");
            }
            else {
                System.out.println("CTS - OFF");
            }
        }
        else if(event.isDSR())//If DSR line has changed state
        {
            if(event.getEventValue() == 1)//If line is ON
            {
                System.out.println("DSR - ON");
            }
            else
            {
                System.out.println("DSR - OFF");
            }
        }
    }
    
    private void log(String s)
    {
        Registro.log(s);
    }
}

