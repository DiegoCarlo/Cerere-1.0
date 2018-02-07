package Interface;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DiegoCG
 */
public class InterfaceUtility
{
    public static void setImage(JLabel jlabel, String path)
    {
        ImageIcon logo = new ImageIcon();
        logo = new ImageIcon(new ImageIcon("Icons\\Logo.png").getImage());
        float larghezza = logo.getIconWidth();
        float altezza = logo.getIconHeight();
        
        float labelLarghezza = jlabel.getWidth();
        float labelAltezza = jlabel.getHeight();
        
        
        float rapportoImmagine = (float)larghezza/(float)altezza;
        float rapportoLabel = (float)labelLarghezza/(float)labelAltezza;
        
        if(rapportoImmagine < rapportoLabel)
        {
            
            labelLarghezza = (float)larghezza / (float)altezza * (float)labelAltezza;
            
        }
        else
        {
            labelAltezza = (float)labelLarghezza * (float)altezza / (float)larghezza;
        }
        rapportoImmagine = larghezza/altezza;
        rapportoLabel = labelLarghezza/labelAltezza;
        //System.err.println(rapportoImmagine +" "+rapportoImmagine);
        logo = new ImageIcon(logo.getImage().getScaledInstance((int)labelLarghezza, (int)labelAltezza, Image.SCALE_DEFAULT));
        
        jlabel.setHorizontalAlignment(SwingConstants.CENTER);
        jlabel.setVerticalAlignment(SwingConstants.CENTER);
        jlabel.setIcon(logo);
    }
}
