/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import toFile.dataType.Complex.DataBase;
import registro.Settings;
import toFile.Utility.Utility;
import toFile.dataType.Base.Cliente;
import toFile.dataType.Base.FrequentMinute;
import toFile.dataType.Ordinals.ActiveOrdering;
import toFile.dataType.Ordinals.Comparator.AlphabeticalComparator;
import toFile.dataType.Ordinals.Comparator.Behavior;
import toFile.dataType.Ordinals.Order;
import toFile.dataType.Ordinals.OrdinableObject;
/**
 *
 * @author DiegoCG
 */
public class Interfaccia extends javax.swing.JFrame
{

    DataBase dataBase = registro.Registro.dataBase;
    Font font = new Font("Sans-serif", 0, Settings.font);
    Font fontMedium = new Font("Sans-serif", 0, Settings.fontMedium);
    Font fontBig = new Font("Sans-serif", 0, Settings.fontBig);
    
    
    ActiveOrdering oClienti = new ActiveOrdering(new Order[]
            {
                Order.ALPHABETIC,
                Order.CARDINAL,
                Order.HOUR
            });
    
    ActiveOrdering oProdotti = new ActiveOrdering(new Order[]
            {
                Order.ALPHABETIC,
                Order.CARDINAL,
                Order.HOUR
            });
    
    ActiveOrdering oTare = new ActiveOrdering(new Order[]
            {
                Order.ALPHABETIC,
                Order.CARDINAL
            });
    
    
    
    
    /**
     * Creates new form Interfaccia
     */
    public Interfaccia()
    {
        initComponents();
        setAllFonts();
    }
    private void log(String msg)
    {
        registro.Registro.log(msg);
    }
    private void setAllFonts()
    {
        jTabbedPane.setFont(font);
        
        jLabelClientiSalva.setFont(font);
        jTextFieldClientiSalva.setFont(font);
        jButtonClientiSalva.setFont(font);
        
        jLabelClientiModifica.setFont(font);
        jTextFieldClientiModifica.setFont(font);
        jButtonClientiModifica.setFont(font);
        
        jButtonClientiOrdine.setFont(font);
        jButtonClientiGiu.setFont(font);
        jButtonClientiSu.setFont(font);
        
        jListClienti.setFont(font);
        
        jButtonClientiElimina.setFont(font);
        
        
        // removed
        jListRemovedClienti.setFont(font);
        jButtonRemovedClienti.setFont(font);
        
    }
    private void updatejTabbedPane()
    {
        if(!bilanciaIsActive())
        {
            switch(jTabbedPane.getSelectedIndex())
            {
                case 3: updatejPanelClienti();
                case 7: updatejPanelRemoved();
            }
        }
    }
    
    public void infoBox(String titolo, String messaggio)
    {
        infoBox(titolo, messaggio, Color.BLACK);
    }
    public void infoBox(String titolo, String messaggio, Color colore)
    {
        String complete = "<html><center>" +
                messaggio.replaceAll("\n", "<br>") +
                "</center></html>";
        JLabel label = new JLabel(complete);
        label.setFont(fontBig);
        label.setForeground(colore);
        JOptionPane.showMessageDialog(null, label, titolo, JOptionPane.WARNING_MESSAGE);
    }
    private void ordinaClienti()
    {
        log("ordinaClienti");
        Order o = oClienti.get();
        switch(o)
        {
            case ALPHABETIC:
                Collections.sort(dataBase.clienti, new AlphabeticalComparator(Behavior.INCREASING));
                break;
            case CARDINAL:
                Collections.sort(dataBase.clienti, new CardinalComparator());
                break;
            case HOUR:
                Collections.sort(dataBase.clienti, new TimeComparator());
                break;
        }
        jButtonBilanciaOrdineCliente.setText(o.toString());
        jButtonClientiOrdinamento.setText(o.toString());
        jButtonPesateOrdinamentoClienti.setText(o.toString());
        jButtonTotaliOrdinamento.setText(o.toString());
    }
    private void updatejPanelClienti()
    {
        jTextFieldClientiSalva.setText("");
        jTextFieldClientiModifica.setText("");
        updatejListClienti();
    }
    private void updatejListClienti()
    {
        jListClienti.setModel(new javax.swing.AbstractListModel<OrdinableObject>()
        {
            ArrayList<OrdinableObject> oa = dataBase.clienti;
            public int getSize() { return oa.size(); }
            public OrdinableObject getElementAt(int i) { return oa.get(i); }
        });
    }
    private void updatejPanelRemoved()
    {
        updatejListRemovedClienti();
    }
    
    private void updatejListRemovedClienti()
    {
        jListRemovedClienti.setModel(new javax.swing.AbstractListModel<OrdinableObject>()
        {
            ArrayList<OrdinableObject> oa = dataBase.removedClienti;
            public int getSize() { return oa.size(); }
            public OrdinableObject getElementAt(int i) { return oa.get(i); }
        });
    }
    private boolean bilanciaIsActive()
    {
        return false;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jTabbedPane = new javax.swing.JTabbedPane();
        jPanelBilancia = new javax.swing.JPanel();
        jPanelPesate = new javax.swing.JPanel();
        jPanelTotali = new javax.swing.JPanel();
        jPanelClienti = new javax.swing.JPanel();
        jPanelClientiInserimento = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabelClientiModifica = new javax.swing.JLabel();
        jTextFieldClientiModifica = new javax.swing.JTextField();
        jButtonClientiModifica = new javax.swing.JButton();
        jLabeClientiLogo = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabelClientiSalva = new javax.swing.JLabel();
        jTextFieldClientiSalva = new javax.swing.JTextField();
        jButtonClientiSalva = new javax.swing.JButton();
        jPanelClientiLista = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jButtonClientiOrdine = new javax.swing.JButton();
        jButtonClientiSu = new javax.swing.JButton();
        jButtonClientiGiu = new javax.swing.JButton();
        jButtonClientiElimina = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListClienti = new javax.swing.JList<>();
        jPanelProdotti = new javax.swing.JPanel();
        jPanelTare = new javax.swing.JPanel();
        jPanelOptions = new javax.swing.JPanel();
        jPanelRemoved = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListRemovedClienti = new javax.swing.JList<>();
        jButtonRemovedClienti = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Registro Voglia di Pane");

        jTabbedPane.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                jTabbedPaneStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanelBilanciaLayout = new javax.swing.GroupLayout(jPanelBilancia);
        jPanelBilancia.setLayout(jPanelBilanciaLayout);
        jPanelBilanciaLayout.setHorizontalGroup(
            jPanelBilanciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 688, Short.MAX_VALUE)
        );
        jPanelBilanciaLayout.setVerticalGroup(
            jPanelBilanciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 489, Short.MAX_VALUE)
        );

        jTabbedPane.addTab("Bilancia", jPanelBilancia);

        javax.swing.GroupLayout jPanelPesateLayout = new javax.swing.GroupLayout(jPanelPesate);
        jPanelPesate.setLayout(jPanelPesateLayout);
        jPanelPesateLayout.setHorizontalGroup(
            jPanelPesateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 688, Short.MAX_VALUE)
        );
        jPanelPesateLayout.setVerticalGroup(
            jPanelPesateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 489, Short.MAX_VALUE)
        );

        jTabbedPane.addTab("Pesate", jPanelPesate);

        javax.swing.GroupLayout jPanelTotaliLayout = new javax.swing.GroupLayout(jPanelTotali);
        jPanelTotali.setLayout(jPanelTotaliLayout);
        jPanelTotaliLayout.setHorizontalGroup(
            jPanelTotaliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 688, Short.MAX_VALUE)
        );
        jPanelTotaliLayout.setVerticalGroup(
            jPanelTotaliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 489, Short.MAX_VALUE)
        );

        jTabbedPane.addTab("Totali", jPanelTotali);

        jLabelClientiModifica.setText("Modifica il cliente");

        jTextFieldClientiModifica.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                jTextFieldClientiModificaKeyReleased(evt);
            }
        });

        jButtonClientiModifica.setText("Modifica");
        jButtonClientiModifica.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonClientiModificaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextFieldClientiModifica)
                    .addComponent(jButtonClientiModifica, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabelClientiModifica)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelClientiModifica)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldClientiModifica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonClientiModifica)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabelClientiSalva.setText("Inserimento di nuovo Cliente");

        jTextFieldClientiSalva.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                jTextFieldClientiSalvaKeyReleased(evt);
            }
        });

        jButtonClientiSalva.setText("Salva");
        jButtonClientiSalva.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonClientiSalvaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextFieldClientiSalva)
                    .addComponent(jButtonClientiSalva, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabelClientiSalva)
                        .addGap(0, 171, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelClientiSalva)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldClientiSalva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonClientiSalva)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelClientiInserimentoLayout = new javax.swing.GroupLayout(jPanelClientiInserimento);
        jPanelClientiInserimento.setLayout(jPanelClientiInserimentoLayout);
        jPanelClientiInserimentoLayout.setHorizontalGroup(
            jPanelClientiInserimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabeClientiLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelClientiInserimentoLayout.setVerticalGroup(
            jPanelClientiInserimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelClientiInserimentoLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabeClientiLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jButtonClientiOrdine.setText("Ordine");
        jButtonClientiOrdine.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonClientiOrdineMouseClicked(evt);
            }
        });

        jButtonClientiSu.setText("Su");

        jButtonClientiGiu.setText("Giù");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonClientiOrdine)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonClientiSu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonClientiGiu)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonClientiOrdine)
                    .addComponent(jButtonClientiSu)
                    .addComponent(jButtonClientiGiu))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButtonClientiElimina.setText("Elimina");
        jButtonClientiElimina.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonClientiEliminaMouseClicked(evt);
            }
        });

        jListClienti.setModel(new javax.swing.AbstractListModel<OrdinableObject>()
            {
                ArrayList<OrdinableObject> oa = dataBase.clienti;
                public int getSize() { return oa.size(); }
                public OrdinableObject getElementAt(int i) { return oa.get(i); }

            });
            jListClienti.addListSelectionListener(new javax.swing.event.ListSelectionListener()
            {
                public void valueChanged(javax.swing.event.ListSelectionEvent evt)
                {
                    jListClientiValueChanged(evt);
                }
            });
            jScrollPane1.setViewportView(jListClienti);

            javax.swing.GroupLayout jPanelClientiListaLayout = new javax.swing.GroupLayout(jPanelClientiLista);
            jPanelClientiLista.setLayout(jPanelClientiListaLayout);
            jPanelClientiListaLayout.setHorizontalGroup(
                jPanelClientiListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonClientiElimina, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
            );
            jPanelClientiListaLayout.setVerticalGroup(
                jPanelClientiListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelClientiListaLayout.createSequentialGroup()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jButtonClientiElimina))
            );

            javax.swing.GroupLayout jPanelClientiLayout = new javax.swing.GroupLayout(jPanelClienti);
            jPanelClienti.setLayout(jPanelClientiLayout);
            jPanelClientiLayout.setHorizontalGroup(
                jPanelClientiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelClientiLayout.createSequentialGroup()
                    .addComponent(jPanelClientiInserimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanelClientiLista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            jPanelClientiLayout.setVerticalGroup(
                jPanelClientiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanelClientiInserimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelClientiLista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );

            jTabbedPane.addTab("Clienti", jPanelClienti);

            javax.swing.GroupLayout jPanelProdottiLayout = new javax.swing.GroupLayout(jPanelProdotti);
            jPanelProdotti.setLayout(jPanelProdottiLayout);
            jPanelProdottiLayout.setHorizontalGroup(
                jPanelProdottiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 688, Short.MAX_VALUE)
            );
            jPanelProdottiLayout.setVerticalGroup(
                jPanelProdottiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 489, Short.MAX_VALUE)
            );

            jTabbedPane.addTab("Prodotti", jPanelProdotti);

            javax.swing.GroupLayout jPanelTareLayout = new javax.swing.GroupLayout(jPanelTare);
            jPanelTare.setLayout(jPanelTareLayout);
            jPanelTareLayout.setHorizontalGroup(
                jPanelTareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 688, Short.MAX_VALUE)
            );
            jPanelTareLayout.setVerticalGroup(
                jPanelTareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 489, Short.MAX_VALUE)
            );

            jTabbedPane.addTab("Tare", jPanelTare);

            javax.swing.GroupLayout jPanelOptionsLayout = new javax.swing.GroupLayout(jPanelOptions);
            jPanelOptions.setLayout(jPanelOptionsLayout);
            jPanelOptionsLayout.setHorizontalGroup(
                jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 688, Short.MAX_VALUE)
            );
            jPanelOptionsLayout.setVerticalGroup(
                jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 489, Short.MAX_VALUE)
            );

            jTabbedPane.addTab("Opzioni", jPanelOptions);

            jListRemovedClienti.setModel(new javax.swing.AbstractListModel<OrdinableObject>()
                {
                    ArrayList<OrdinableObject> oa = dataBase.removedClienti;
                    public int getSize() { return oa.size(); }
                    public OrdinableObject getElementAt(int i) { return oa.get(i); }

                });
                jScrollPane2.setViewportView(jListRemovedClienti);

                jButtonRemovedClienti.setText("Ripristina Cliente");
                jButtonRemovedClienti.addMouseListener(new java.awt.event.MouseAdapter()
                {
                    public void mouseClicked(java.awt.event.MouseEvent evt)
                    {
                        jButtonRemovedClientiMouseClicked(evt);
                    }
                });

                javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
                jPanel4.setLayout(jPanel4Layout);
                jPanel4Layout.setHorizontalGroup(
                    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                    .addComponent(jButtonRemovedClienti, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                jPanel4Layout.setVerticalGroup(
                    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRemovedClienti))
                );

                javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
                jPanel5.setLayout(jPanel5Layout);
                jPanel5Layout.setHorizontalGroup(
                    jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGap(0, 221, Short.MAX_VALUE)
                );
                jPanel5Layout.setVerticalGroup(
                    jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGap(0, 489, Short.MAX_VALUE)
                );

                javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
                jPanel6.setLayout(jPanel6Layout);
                jPanel6Layout.setHorizontalGroup(
                    jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGap(0, 193, Short.MAX_VALUE)
                );
                jPanel6Layout.setVerticalGroup(
                    jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGap(0, 489, Short.MAX_VALUE)
                );

                javax.swing.GroupLayout jPanelRemovedLayout = new javax.swing.GroupLayout(jPanelRemoved);
                jPanelRemoved.setLayout(jPanelRemovedLayout);
                jPanelRemovedLayout.setHorizontalGroup(
                    jPanelRemovedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRemovedLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                jPanelRemovedLayout.setVerticalGroup(
                    jPanelRemovedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );

                jTabbedPane.addTab("Cestino", jPanelRemoved);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTabbedPane)
                        .addGap(0, 0, 0))
                );
                layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTabbedPane)
                        .addGap(0, 0, 0))
                );

                pack();
                setLocationRelativeTo(null);
            }// </editor-fold>//GEN-END:initComponents

    private void jTabbedPaneStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_jTabbedPaneStateChanged
    {//GEN-HEADEREND:event_jTabbedPaneStateChanged
        updatejTabbedPane();
    }//GEN-LAST:event_jTabbedPaneStateChanged

    private void jButtonClientiSalvaMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonClientiSalvaMouseClicked
    {//GEN-HEADEREND:event_jButtonClientiSalvaMouseClicked
        if(!jTextFieldClientiSalva.getText().isEmpty())
        {
            if(!dataBase.clienti.exist(jTextFieldClientiSalva.getText()))
            {
                Cliente c = new Cliente(
                        dataBase.clienti.getNewId(),
                        dataBase.clienti.getNewCardinale(),
                        jTextFieldClientiSalva.getText(),
                        new FrequentMinute(
                                Utility.getMinuteOfTheDay(
                                        GregorianCalendar.getInstance()
                                )
                        )
                );
                dataBase.clienti.add(c);
                infoBox("Inserimento nuovo Cliente", jTextFieldClientiSalva.getText() + " è stato inserito");
                
                dataBase.saveClienti();
                
                jTextFieldClientiSalva.setText("");
                updatejPanelClienti();
            }
            else
            {
                infoBox("ATTENZIONE", jTextFieldClientiSalva.getText() + "\n è già presente! Non è stato inserito", Color.red);
            }
        }
        else
        {
            infoBox("ATTENZIONE", "Nessun nome inserito", Color.red);
        }
    }//GEN-LAST:event_jButtonClientiSalvaMouseClicked

    private void jListClientiValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_jListClientiValueChanged
    {//GEN-HEADEREND:event_jListClientiValueChanged
        if(!evt.getValueIsAdjusting())
        {
            if(jListClienti.getSelectedIndex() != -1)
            {
                OrdinableObject c = jListClienti.getSelectedValue();
                Cliente cl = (Cliente)dataBase.clienti.get(c.getId());
                jTextFieldClientiModifica.setText(cl.getName());
            }
        }
    }//GEN-LAST:event_jListClientiValueChanged

    private void jButtonClientiModificaMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonClientiModificaMouseClicked
    {//GEN-HEADEREND:event_jButtonClientiModificaMouseClicked
        if(jListClienti.getSelectedIndex() != -1)
        {
            if(!jTextFieldClientiModifica.getText().isEmpty())
            {
                OrdinableObject c = jListClienti.getSelectedValue();
                Cliente cl = (Cliente)dataBase.clienti.get(c.getId());
                cl.setName(jTextFieldClientiModifica.getText());
            }
            else
            {
                infoBox("ATTENZIONE", "Nessun nome inserito", Color.red);
            }
        }
        else
        {
            infoBox("ATTENZIONE", "Seleziona prima un cliente dalla lista", Color.red);
        }
    }//GEN-LAST:event_jButtonClientiModificaMouseClicked

    private void jTextFieldClientiSalvaKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTextFieldClientiSalvaKeyReleased
    {//GEN-HEADEREND:event_jTextFieldClientiSalvaKeyReleased
        jTextFieldClientiSalva.setText(Utility.safeName(jTextFieldClientiSalva.getText()));
    }//GEN-LAST:event_jTextFieldClientiSalvaKeyReleased

    private void jTextFieldClientiModificaKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTextFieldClientiModificaKeyReleased
    {//GEN-HEADEREND:event_jTextFieldClientiModificaKeyReleased
        jTextFieldClientiModifica.setText(Utility.safeName(jTextFieldClientiModifica.getText()));
    }//GEN-LAST:event_jTextFieldClientiModificaKeyReleased

    private void jButtonClientiEliminaMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonClientiEliminaMouseClicked
    {//GEN-HEADEREND:event_jButtonClientiEliminaMouseClicked
        if(jListClienti.getSelectedIndex() != -1)
        {
            OrdinableObject cl = jListClienti.getSelectedValue();
             
            String name = cl.getName();
            
            boolean esito = dataBase.clienti.remove(cl.getId());
            
            if(esito)
            {
                infoBox("Eliminazione Cliente", name + "\nè stato eliminato");
                dataBase.removedClienti.add(cl);
                dataBase.saveClienti();
                dataBase.saveRemovedClienti();
                updatejPanelClienti();
            }
            else
            {
                infoBox("ATTENZIONE", "Non è stato possibile eliminare\n" + name, Color.red);
            }
        }
        else
        {
            infoBox("ATTENZIONE", "Seleziona prima un cliente dalla lista", Color.red);
        }
    }//GEN-LAST:event_jButtonClientiEliminaMouseClicked

    private void jButtonRemovedClientiMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonRemovedClientiMouseClicked
    {//GEN-HEADEREND:event_jButtonRemovedClientiMouseClicked
        if(jListRemovedClienti.getSelectedIndex() != -1)
        {
            OrdinableObject cl = jListRemovedClienti.getSelectedValue();
             
            String name = cl.getName();
            
            boolean esito = dataBase.removedClienti.remove(cl.getId());
            
            if(esito)
            {
                infoBox("Ripristino Cliente", name + "\nè stato ripristinato");
                dataBase.clienti.add(cl);
                dataBase.saveClienti();
                dataBase.saveRemovedClienti();
                updatejPanelRemoved();
            }
            else
            {
                infoBox("ATTENZIONE", "Non è stato possibile Ripristinare\n" + name, Color.red);
            }
        }
        else
        {
            infoBox("ATTENZIONE", "Seleziona prima un cliente dalla lista", Color.red);
        }
    }//GEN-LAST:event_jButtonRemovedClientiMouseClicked

    private void jButtonClientiOrdineMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonClientiOrdineMouseClicked
    {//GEN-HEADEREND:event_jButtonClientiOrdineMouseClicked
        oClienti.getNext();
        ordinaClienti();
        updatejListClienti();
    }//GEN-LAST:event_jButtonClientiOrdineMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClientiElimina;
    private javax.swing.JButton jButtonClientiGiu;
    private javax.swing.JButton jButtonClientiModifica;
    private javax.swing.JButton jButtonClientiOrdine;
    private javax.swing.JButton jButtonClientiSalva;
    private javax.swing.JButton jButtonClientiSu;
    private javax.swing.JButton jButtonRemovedClienti;
    private javax.swing.JLabel jLabeClientiLogo;
    private javax.swing.JLabel jLabelClientiModifica;
    private javax.swing.JLabel jLabelClientiSalva;
    private javax.swing.JList<OrdinableObject> jListClienti;
    private javax.swing.JList<OrdinableObject> jListRemovedClienti;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelBilancia;
    private javax.swing.JPanel jPanelClienti;
    private javax.swing.JPanel jPanelClientiInserimento;
    private javax.swing.JPanel jPanelClientiLista;
    private javax.swing.JPanel jPanelOptions;
    private javax.swing.JPanel jPanelPesate;
    private javax.swing.JPanel jPanelProdotti;
    private javax.swing.JPanel jPanelRemoved;
    private javax.swing.JPanel jPanelTare;
    private javax.swing.JPanel jPanelTotali;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTextField jTextFieldClientiModifica;
    private javax.swing.JTextField jTextFieldClientiSalva;
    // End of variables declaration//GEN-END:variables
}
