/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
///import static registro.Registro.settings;
/**
 *
 * @author DiegoCG
 */
public class FolderChooser extends JPanel implements ActionListener
{
    JButton go;

    JFileChooser chooser;
    String choosertitle;
   
    public FolderChooser()
    {
        super();
        go = new JButton("Seleziona la cartella");
        go.setFont(new Font("Sans-serif", 0, registro.Registro.settings.font));
        go.addActionListener(this);
        add(go);
     }

    public void actionPerformed(ActionEvent e)
    {

        chooser = new JFileChooser(); 
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(choosertitle);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        //    
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        { 
            /*System.out.println("getCurrentDirectory(): "
                    +  chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : " 
               +  chooser.getSelectedFile());*/

            registro.Registro.settings.salvataggiDirectory = chooser.getSelectedFile().toString();
            registro.Registro.saveSettings();
            registro.Registro.interfaccia.updateSaveDirectory();
        }
        else
        {
            System.out.println("No Selection ");
        }
    }
}
