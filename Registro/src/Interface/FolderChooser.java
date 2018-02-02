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
/**
 *
 * @author DiegoCG
 */
public class FolderChooser extends JPanel implements ActionListener
{
   JButton go;
   
   JFileChooser chooser;
   String choosertitle;
   
  public FolderChooser() {
    go = new JButton("Seleziona la cartella");
    go.addActionListener(this);
    add(go);
   }

  public void actionPerformed(ActionEvent e) {
    int result;
        
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
      System.out.println("getCurrentDirectory(): " 
         +  chooser.getCurrentDirectory());
      System.out.println("getSelectedFile() : " 
         +  chooser.getSelectedFile());
      
        registro.Registro.settings.salvataggiDirectory = chooser.getSelectedFile().toString();
        registro.Registro.saveSettings();
        registro.Registro.interfaccia.updateSaveDirectory();
      }
    else {
      System.out.println("No Selection ");
      }
     }
}
