/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import toFile.dataType.Complex.DataBase;
import registro.Settings;
import toFile.Utility.Utility;
import toFile.dataType.Base.Cliente;
import toFile.dataType.Base.FrequentMinute;
import toFile.dataType.Base.Prodotto;
import toFile.dataType.Base.UnitOfMeasure;
import toFile.dataType.Ordinals.ActiveOrdering;
import toFile.dataType.Ordinals.Comparator.AlphabeticalComparator;
import toFile.dataType.Ordinals.Comparator.Behavior;
import toFile.dataType.Ordinals.Comparator.CardinalComparator;
import toFile.dataType.Ordinals.Comparator.MinuteComparator;
import toFile.dataType.Ordinals.Order;
import toFile.dataType.Ordinals.OrdinableArray;
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
    
    float netto;
    float tara;
    float lordo;
    Cliente cliente;
    Prodotto prodotto;
    
    
    
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
                Order.FREQUENCY
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
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        initComponents();
        setAllFonts();
        updateLogo();
        orderClienti();
        orderProdotti();
    }
    
    private void log(String msg)
    {
        registro.Registro.log(msg);
    }
    private void setAllFonts()
    {
        jTabbedPane.setFont(font);
        
        // bilancia 
        jButtoBilanciaClienti.setFont(font);
        jListBilanciaClienti.setFont(fontMedium);
        jButtonBilanciaProdotti.setFont(font);
        jListBilanciaProdotti.setFont(fontMedium);
        
        jLabelBilanciaCliente.setFont(font);
        jLabelBilanciaCliente.setHorizontalAlignment(SwingConstants.CENTER);
        
        jLabeBilanciaProdotto.setFont(font);
        jLabeBilanciaProdotto.setHorizontalAlignment(SwingConstants.CENTER);
        
        jTextFieldBilanciaNetto.setFont(fontBig);
        jTextFieldBilanciaNetto.setHorizontalAlignment(SwingConstants.CENTER);
        
        jButtonBilanciaTara.setFont(font);
        
        jTextFieldBilanciaTara.setFont(font.deriveFont(25));
        jTextFieldBilanciaTara.setHorizontalAlignment(SwingConstants.CENTER);
        
        jLabelBilanciaLordo.setFont(font);
        jLabelBilanciaLordo.setHorizontalAlignment(SwingConstants.CENTER);
        
        jButtonBilanciaAggiungi.setFont(font);
        
        jLabelBilanciaTotaleTitle.setFont(font);
        jLabelBilanciaTotalePesaScontrino.setFont(font);
        
        jLabelBilanciaTotaleTitle.setFont(font);
        jLabelBilanciaTotaleTitle.setHorizontalAlignment(SwingConstants.CENTER);
        
        jLabelBilanciaTotalePesaScontrino.setFont(fontBig);
        jLabelBilanciaTotalePesaScontrino.setHorizontalAlignment(SwingConstants.CENTER);
        
        jListBilanciaScontrino.setFont(fontMedium);
        
        jButtonBilanciaStampa.setFont(font);
        jButtonBilanciaElimina.setFont(font);
        
        // clienti
        jLabelClientiSalva.setFont(font);
        jTextFieldClientiSalva.setFont(font);
        jButtonClientiSalva.setFont(font);
        
        jLabelClientiModifica.setFont(font);
        jTextFieldClientiModifica.setFont(font);
        jButtonClientiModifica.setFont(font);
        
        jButtonClientiOrdine.setFont(font);
        jButtonClientiGiu.setFont(font);
        jButtonClientiiSu.setFont(font);
        
        jListClienti.setFont(font);
        
        jButtonClientiElimina.setFont(font);
        
        // prodotti
        
        // removed
        jListRemovedClienti.setFont(font);
        jButtonRemovedClienti.setFont(font);
        
        
        jButton0.setFont(font);
        jButton1.setFont(font);
        jButton2.setFont(font);
        jButton3.setFont(font);
        jButton4.setFont(font);
        jButton5.setFont(font);
        jButton6.setFont(font);
        jButton7.setFont(font);
        jButton8.setFont(font);
        jButton9.setFont(font);
        
    }
    private void updateLogo()
    {
        ImageIcon logoBilancia = new ImageIcon();
        int larghezza = jPanelBilanciaLogo.getWidth();
        int altezza = (int)((float)larghezza * (float)0.48);
        int altezza2 = jPanelBilanciaLogo.getHeight();
        int larghezza2 = (int)((float)altezza2 * (float)2.083);
        if(altezza * larghezza > altezza2 * larghezza2)
        {
            larghezza = larghezza2;
            altezza = altezza2;
            
        }
        jLabelBilanciaLogo.setHorizontalAlignment(SwingConstants.CENTER);
        logoBilancia = new ImageIcon(
                        new ImageIcon("Icons\\Logo VDP.png")
                                .getImage()
                                .getScaledInstance(larghezza, altezza, Image.SCALE_DEFAULT)
                        );
        jLabelBilanciaLogo.setIcon(logoBilancia);
        /*// Pesate
        int larghezza = jButtonPesateRistampaScontrino.getWidth();
        int altezza = (int)((float)larghezza * (float)0.48);
        ImageIcon logoPesate = new ImageIcon();
        logoPesate = new ImageIcon(
                        new ImageIcon("Icone\\Logo VDP.png")
                                .getImage()
                                .getScaledInstance(larghezza, altezza, Image.SCALE_DEFAULT)
                        );
        jLabelPesateLogo.setIcon(logoPesate);
        
       */
        // Clienti
        ImageIcon logoClienti = new ImageIcon();
        larghezza = jPanelClientiInserimento.getWidth();
        altezza = (int)((float)larghezza * (float)0.48);
        logoClienti = new ImageIcon(
                        new ImageIcon("Icons\\Logo VDP.png")
                                .getImage()
                                .getScaledInstance(larghezza, altezza, Image.SCALE_DEFAULT)
                        );
        jLabelClientiLogo.setIcon(logoClienti);
        jLabelClientiLogo.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Prodotti
        ImageIcon logoProdotti = new ImageIcon();
        larghezza = jPanelProdottiInserimento.getWidth();
        altezza = (int)((float)larghezza * (float)0.48);
        logoProdotti = new ImageIcon(
                        new ImageIcon("Icons\\Logo VDP.png")
                                .getImage()
                                .getScaledInstance(larghezza, altezza, Image.SCALE_DEFAULT)
                        );
        jLabelProdottiLogo.setIcon(logoProdotti);
        jLabelProdottiLogo.setHorizontalAlignment(SwingConstants.CENTER);
        /*
        // Tare
        ImageIcon logoTare = new ImageIcon();
        larghezza = jPanel11.getWidth();
        altezza = (int)((float)larghezza * (float)0.48);
        logoTare = new ImageIcon(
                        new ImageIcon("Icone\\Logo VDP.png")
                                .getImage()
                                .getScaledInstance(larghezza, altezza, Image.SCALE_DEFAULT)
                        );
        jLabelTareLogo.setIcon(logoTare);

        // Tare
        ImageIcon logoTotali = new ImageIcon();
        larghezza = jButtonSaveTotali.getWidth();
        altezza = (int)((float)larghezza * (float)0.48);
        logoTotali = new ImageIcon(
                        new ImageIcon("Icone\\Logo VDP.png")
                                .getImage()
                                .getScaledInstance(larghezza, altezza, Image.SCALE_DEFAULT)
                        );
        jLabelTotaliLogo.setIcon(logoTotali);*/
    }
    private void updatejTabbedPane()
    {
        if(!bilanciaIsActive())
        {
            switch(jTabbedPane.getSelectedIndex())
            {
                case 0: updatejPanelBilancia();
                case 3: updatejPanelClienti();
                case 7: updatejPanelRemoved();
            }
        }
    }
    
    public void updatePeso(float f)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    private void orderClienti()
    {
        Order o = oClienti.get();
        switch(o)
        {
            case ALPHABETIC:
                Collections.sort(dataBase.clienti, new AlphabeticalComparator(Behavior.INCREASING));
                break;
            case CARDINAL:
                Collections.sort(dataBase.clienti, new CardinalComparator(Behavior.INCREASING));
                break;
            case HOUR:
                Collections.sort(dataBase.clienti, new MinuteComparator(Behavior.INCREASING));
                break;
        }
        jButtonClientiOrdine.setText(o.toString());
        jButtoBilanciaClienti.setText(o.toString());
        //jButtonPesateOrdinamentoClienti.setText(o.toString());
        //jButtonTotaliOrdinamento.setText(o.toString());
    }
    private void orderProdotti()
    {
        Order o = oProdotti.get();
        switch(o)
        {
            case ALPHABETIC:
                Collections.sort(dataBase.prodotti, new AlphabeticalComparator(Behavior.INCREASING));
                break;
            case CARDINAL:
                Collections.sort(dataBase.prodotti, new CardinalComparator(Behavior.INCREASING));
                break;
            case FREQUENCY:
                
                break;
        }
        jButtonBilanciaProdotti.setText(o.toString());
        //jButtonPesateOrdinamentoClienti.setText(o.toString());
        //jButtonTotaliOrdinamento.setText(o.toString());
    }
    private void insTastierino(int t)
    {
        log("insTastierino");
        if(jTextFieldBilanciaNetto.getSelectedText() != null)
        {
            netto = 0;
        }
        if(prodotto.unitOfMeasure == UnitOfMeasure.QUANTITY)
        {
            netto = netto*10+t;
            jTextFieldBilanciaNetto.setText(String.format("%.0f", netto));
        }
    }
    private void updatejPanelBilancia()
    {
        updatejList(jListBilanciaClienti, dataBase.clienti);
        updatejList(jListBilanciaProdotti, dataBase.prodotti);
    }
    private void updatejPanelClienti()
    {
        jTextFieldClientiSalva.setText("");
        jTextFieldClientiModifica.setText("");
        updatejList(jListClienti, dataBase.clienti);
    }
    private void updatejList(JList<OrdinableObject> jlist, OrdinableArray list)
    {
        jlist.setModel(new javax.swing.AbstractListModel<OrdinableObject>()
        {
            ArrayList<OrdinableObject> oa = list;
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
    public boolean getSimBilancia()
    {
        return !jToggleButtonDemoBilancia.isSelected();
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
        jSplitPaneBilancia = new javax.swing.JSplitPane();
        jPanel9 = new javax.swing.JPanel();
        jButtonBilanciaProdotti = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jListBilanciaProdotti = new javax.swing.JList<>();
        jPanel10 = new javax.swing.JPanel();
        jButtoBilanciaClienti = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jListBilanciaClienti = new javax.swing.JList<>();
        jPanelBilancia1 = new javax.swing.JPanel();
        jButtonBilanciaTara = new javax.swing.JButton();
        jTextFieldBilanciaNetto = new javax.swing.JTextField();
        jLabeBilanciaProdotto = new javax.swing.JLabel();
        jLabelBilanciaCliente = new javax.swing.JLabel();
        jPanelBilancia2 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton0 = new javax.swing.JButton();
        jButtonBilanciaAggiungi = new javax.swing.JButton();
        jTextFieldBilanciaTara = new javax.swing.JTextField();
        jLabelBilanciaLordo = new javax.swing.JLabel();
        jPanelBilanciaLogo = new javax.swing.JPanel();
        jLabelBilanciaLogo = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabelBilanciaTotaleTitle = new javax.swing.JLabel();
        jLabelBilanciaTotalePesaScontrino = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListBilanciaScontrino = new javax.swing.JList<>();
        jButtonBilanciaElimina = new javax.swing.JButton();
        jButtonBilanciaStampa = new javax.swing.JButton();
        jPanelPesate = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel18 = new javax.swing.JPanel();
        jButtonPesateModifica = new javax.swing.JButton();
        jButtonPesateElimina = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jXDatePickerPesate = new org.jdesktop.swingx.JXDatePicker();
        jButtonPesateRistampaScontrino = new javax.swing.JButton();
        jLabelPesateLogo = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jListPesatePesate = new javax.swing.JList<>();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jListPesateClienti = new javax.swing.JList<>();
        jButtonPesateOrdinamentoClienti = new javax.swing.JButton();
        jPanel24 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jListPesateCambiaCliente = new javax.swing.JList<>();
        jButtonPesateCambiaCliente = new javax.swing.JButton();
        jPanelTotali = new javax.swing.JPanel();
        jSplitPane3 = new javax.swing.JSplitPane();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTableTotaliMensili = new javax.swing.JTable();
        jPanel21 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jListTotaliClienti = new javax.swing.JList<>();
        jPanel19 = new javax.swing.JPanel();
        jXDatePickerTotali = new org.jdesktop.swingx.JXDatePicker();
        jLabelTotaliLogo = new javax.swing.JLabel();
        jButtonSaveTotali = new javax.swing.JButton();
        jButtonTotaliSalvaSettimana = new javax.swing.JButton();
        jButtonTotaliOrdinamento = new javax.swing.JButton();
        jPanelClienti = new javax.swing.JPanel();
        jPanelClientiInserimento = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabelClientiModifica = new javax.swing.JLabel();
        jTextFieldClientiModifica = new javax.swing.JTextField();
        jButtonClientiModifica = new javax.swing.JButton();
        jLabelClientiLogo = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabelClientiSalva = new javax.swing.JLabel();
        jTextFieldClientiSalva = new javax.swing.JTextField();
        jButtonClientiSalva = new javax.swing.JButton();
        jPanelClientiLista = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jButtonClientiOrdine = new javax.swing.JButton();
        jButtonClientiiSu = new javax.swing.JButton();
        jButtonClientiGiu = new javax.swing.JButton();
        jButtonClientiElimina = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListClienti = new javax.swing.JList<>();
        jPanelProdotti = new javax.swing.JPanel();
        jPanelProdottiInserimento = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabelProdottiModifica = new javax.swing.JLabel();
        jTextFieldProdottiModifica = new javax.swing.JTextField();
        jButtonProdottiModifica = new javax.swing.JButton();
        jToggleButtonProdottiModifyUnit = new javax.swing.JToggleButton();
        jLabelProdottiLogo = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabelProdottiSalva = new javax.swing.JLabel();
        jTextFieldProdottiSalva = new javax.swing.JTextField();
        jButtonProdottiSalva = new javax.swing.JButton();
        jToggleButtonProdottiSaveUnit = new javax.swing.JToggleButton();
        jPanelProdottiLista = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jButtonProdottiOrdine = new javax.swing.JButton();
        jButtonProdottiSu = new javax.swing.JButton();
        jButtonProdottiGiu = new javax.swing.JButton();
        jButtonProdottiElimina = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jListClienti1 = new javax.swing.JList<>();
        jPanelTare = new javax.swing.JPanel();
        jPanelClientiInserimento2 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabelClientiModifica2 = new javax.swing.JLabel();
        jTextFieldClientiModifica2 = new javax.swing.JTextField();
        jButtonClientiModifica2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabelClientiLogo2 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabelClientiSalva2 = new javax.swing.JLabel();
        jTextFieldClientiSalva2 = new javax.swing.JTextField();
        jButtonClientiSalva2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanelClientiLista2 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jButtonClientiOrdine2 = new javax.swing.JButton();
        jButtonClientiSu2 = new javax.swing.JButton();
        jButtonClientiGiu2 = new javax.swing.JButton();
        jButtonClientiElimina2 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        jListClienti2 = new javax.swing.JList<>();
        jPanelOptions = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jToggleButtonDemoBilancia = new javax.swing.JToggleButton();
        jPanelRemoved = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListRemovedClienti = new javax.swing.JList<>();
        jButtonRemovedClienti = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jScrollPane13 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jPanel6 = new javax.swing.JPanel();
        jButton11 = new javax.swing.JButton();
        jScrollPane15 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Registro Voglia di Pane");
        addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener()
        {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt)
            {
            }
            public void ancestorResized(java.awt.event.HierarchyEvent evt)
            {
                formAncestorResized(evt);
            }
        });

        jTabbedPane.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                jTabbedPaneStateChanged(evt);
            }
        });

        jSplitPaneBilancia.setDividerSize(10);
        jSplitPaneBilancia.setResizeWeight(0.6);
        jSplitPaneBilancia.setMinimumSize(new java.awt.Dimension(212, 550));

        jButtonBilanciaProdotti.setText("jButton11");
        jButtonBilanciaProdotti.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonBilanciaProdottiMouseClicked(evt);
            }
        });

        jListBilanciaProdotti.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                jListBilanciaProdottiValueChanged(evt);
            }
        });
        jScrollPane5.setViewportView(jListBilanciaProdotti);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButtonBilanciaProdotti, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jButtonBilanciaProdotti)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE))
        );

        jSplitPaneBilancia.setRightComponent(jPanel9);

        jButtoBilanciaClienti.setText("jButton10");
        jButtoBilanciaClienti.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtoBilanciaClientiMouseClicked(evt);
            }
        });

        jListBilanciaClienti.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                jListBilanciaClientiValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(jListBilanciaClienti);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButtoBilanciaClienti, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jButtoBilanciaClienti)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE))
        );

        jSplitPaneBilancia.setLeftComponent(jPanel10);

        jButtonBilanciaTara.setText("Tara");
        jButtonBilanciaTara.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonBilanciaTaraMouseClicked(evt);
            }
        });

        jTextFieldBilanciaNetto.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jTextFieldBilanciaNettoMouseClicked(evt);
            }
        });
        jTextFieldBilanciaNetto.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                jTextFieldBilanciaNettoKeyReleased(evt);
            }
        });

        jLabeBilanciaProdotto.setText("Prodotto");

        jLabelBilanciaCliente.setText("Cliente");

        jPanelBilancia2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton7.setText("7");
        jButton7.setPreferredSize(new java.awt.Dimension(80, 60));
        jButton7.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButton7MouseClicked(evt);
            }
        });

        jButton4.setText("4");
        jButton4.setPreferredSize(new java.awt.Dimension(80, 60));
        jButton4.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButton4MouseClicked(evt);
            }
        });

        jButton8.setText("8");
        jButton8.setPreferredSize(new java.awt.Dimension(80, 60));
        jButton8.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButton8MouseClicked(evt);
            }
        });

        jButton5.setText("5");
        jButton5.setPreferredSize(new java.awt.Dimension(80, 60));
        jButton5.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButton5MouseClicked(evt);
            }
        });

        jButton9.setText("9");
        jButton9.setPreferredSize(new java.awt.Dimension(80, 60));
        jButton9.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButton9MouseClicked(evt);
            }
        });

        jButton6.setText("6");
        jButton6.setPreferredSize(new java.awt.Dimension(80, 60));
        jButton6.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButton6MouseClicked(evt);
            }
        });

        jButton1.setText("1");
        jButton1.setPreferredSize(new java.awt.Dimension(80, 60));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButton1MouseClicked(evt);
            }
        });

        jButton2.setText("2");
        jButton2.setPreferredSize(new java.awt.Dimension(80, 60));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButton2MouseClicked(evt);
            }
        });

        jButton3.setText("3");
        jButton3.setPreferredSize(new java.awt.Dimension(80, 60));
        jButton3.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButton3MouseClicked(evt);
            }
        });

        jButton0.setText("0");
        jButton0.setPreferredSize(new java.awt.Dimension(80, 60));
        jButton0.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButton0MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelBilancia2Layout = new javax.swing.GroupLayout(jPanelBilancia2);
        jPanelBilancia2.setLayout(jPanelBilancia2Layout);
        jPanelBilancia2Layout.setHorizontalGroup(
            jPanelBilancia2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBilancia2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelBilancia2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelBilancia2Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelBilancia2Layout.createSequentialGroup()
                        .addGroup(jPanelBilancia2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanelBilancia2Layout.createSequentialGroup()
                                .addGroup(jPanelBilancia2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelBilancia2Layout.createSequentialGroup()
                                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanelBilancia2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jButton5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanelBilancia2Layout.createSequentialGroup()
                                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanelBilancia2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanelBilancia2Layout.setVerticalGroup(
            jPanelBilancia2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBilancia2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelBilancia2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelBilancia2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelBilancia2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelBilancia2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButtonBilanciaAggiungi.setText("Pesa");
        jButtonBilanciaAggiungi.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonBilanciaAggiungiMouseClicked(evt);
            }
        });

        jTextFieldBilanciaTara.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jTextFieldBilanciaTaraMouseClicked(evt);
            }
        });
        jTextFieldBilanciaTara.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                jTextFieldBilanciaTaraKeyReleased(evt);
            }
        });

        jLabelBilanciaLordo.setText("Lordo: ");

        javax.swing.GroupLayout jPanelBilanciaLogoLayout = new javax.swing.GroupLayout(jPanelBilanciaLogo);
        jPanelBilanciaLogo.setLayout(jPanelBilanciaLogoLayout);
        jPanelBilanciaLogoLayout.setHorizontalGroup(
            jPanelBilanciaLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelBilanciaLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelBilanciaLogoLayout.setVerticalGroup(
            jPanelBilanciaLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelBilanciaLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanelBilancia1Layout = new javax.swing.GroupLayout(jPanelBilancia1);
        jPanelBilancia1.setLayout(jPanelBilancia1Layout);
        jPanelBilancia1Layout.setHorizontalGroup(
            jPanelBilancia1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBilancia1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelBilancia1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldBilanciaNetto)
                    .addComponent(jPanelBilancia2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelBilanciaLordo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelBilancia1Layout.createSequentialGroup()
                        .addComponent(jButtonBilanciaTara, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldBilanciaTara))
                    .addComponent(jButtonBilanciaAggiungi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelBilanciaCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabeBilanciaProdotto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jPanelBilanciaLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelBilancia1Layout.setVerticalGroup(
            jPanelBilancia1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBilancia1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelBilanciaCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabeBilanciaProdotto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldBilanciaNetto, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelBilancia1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldBilanciaTara, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                    .addComponent(jButtonBilanciaTara, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelBilanciaLordo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonBilanciaAggiungi, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelBilancia2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelBilanciaLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel8.setMaximumSize(new java.awt.Dimension(330, 550));
        jPanel8.setPreferredSize(new java.awt.Dimension(330, 550));

        jLabelBilanciaTotaleTitle.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabelBilanciaTotaleTitle.setText("Totale in pesa | Totale scontrino");

        jLabelBilanciaTotalePesaScontrino.setFont(new java.awt.Font("Tahoma", 0, 25)); // NOI18N
        jLabelBilanciaTotalePesaScontrino.setText("0,00");

        jListBilanciaScontrino.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jListBilanciaScontrino.setModel(new javax.swing.AbstractListModel<String>()
        {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jListBilanciaScontrino.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jListBilanciaScontrinoMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jListBilanciaScontrino);

        jButtonBilanciaElimina.setText("Elimina");
        jButtonBilanciaElimina.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonBilanciaEliminaMouseClicked(evt);
            }
        });

        jButtonBilanciaStampa.setText("Stampa");
        jButtonBilanciaStampa.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonBilanciaStampaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(jLabelBilanciaTotalePesaScontrino, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabelBilanciaTotaleTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(1, 1, 1))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jButtonBilanciaStampa, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonBilanciaElimina, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelBilanciaTotaleTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelBilanciaTotalePesaScontrino)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonBilanciaStampa, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(jButtonBilanciaElimina, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelBilanciaLayout = new javax.swing.GroupLayout(jPanelBilancia);
        jPanelBilancia.setLayout(jPanelBilanciaLayout);
        jPanelBilanciaLayout.setHorizontalGroup(
            jPanelBilanciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBilanciaLayout.createSequentialGroup()
                .addComponent(jSplitPaneBilancia, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelBilancia1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelBilanciaLayout.setVerticalGroup(
            jPanelBilanciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPaneBilancia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelBilancia1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
        );

        jTabbedPane.addTab("Bilancia", jPanelBilancia);

        jSplitPane1.setDividerLocation(400);

        jButtonPesateModifica.setText("Modifica Valore");
        jButtonPesateModifica.setFont(font);
        jButtonPesateModifica.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonPesateModificaMouseClicked(evt);
            }
        });

        jButtonPesateElimina.setText("Elimina");
        jButtonPesateElimina.setFont(font);
        jButtonPesateElimina.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonPesateEliminaMouseClicked(evt);
            }
        });

        jXDatePickerPesate.setFont(font);
        jXDatePickerPesate.addPopupMenuListener(new javax.swing.event.PopupMenuListener()
        {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt)
            {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt)
            {
                jXDatePickerPesatePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt)
            {
            }
        });

        jButtonPesateRistampaScontrino.setText("Ristampa");
        jButtonPesateRistampaScontrino.setFont(font);
        jButtonPesateRistampaScontrino.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonPesateRistampaScontrinoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jXDatePickerPesate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonPesateRistampaScontrino, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelPesateLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXDatePickerPesate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonPesateRistampaScontrino)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelPesateLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jListPesatePesate.setModel(new javax.swing.AbstractListModel<String>()
        {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jListPesatePesate.setFont(font);
        jScrollPane11.setViewportView(jListPesatePesate);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButtonPesateModifica)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonPesateElimina))
                    .addComponent(jScrollPane11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonPesateModifica)
                            .addComponent(jButtonPesateElimina)))
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(jPanel18);

        jSplitPane2.setDividerLocation(400);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jListPesateClienti.setModel(new javax.swing.AbstractListModel<String>()
        {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jListPesateClienti.setFont(font);
        jListPesateClienti.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                jListPesateClientiValueChanged(evt);
            }
        });
        jScrollPane10.setViewportView(jListPesateClienti);

        jButtonPesateOrdinamentoClienti.setText("Ordinamento");
        jButtonPesateOrdinamentoClienti.setFont(font);
        jButtonPesateOrdinamentoClienti.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonPesateOrdinamentoClientiMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButtonPesateOrdinamentoClienti, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
            .addComponent(jScrollPane10)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addComponent(jButtonPesateOrdinamentoClienti)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE))
        );

        jSplitPane2.setTopComponent(jPanel23);

        jListPesateCambiaCliente.setModel(new javax.swing.AbstractListModel<String>()
        {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jListPesateCambiaCliente.setFont(font);
        jScrollPane12.setViewportView(jListPesateCambiaCliente);

        jButtonPesateCambiaCliente.setText("Cambia Cliente");
        jButtonPesateCambiaCliente.setFont(font);
        jButtonPesateCambiaCliente.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonPesateCambiaClienteMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane12)
            .addComponent(jButtonPesateCambiaCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonPesateCambiaCliente))
        );

        jSplitPane2.setRightComponent(jPanel24);

        jSplitPane1.setLeftComponent(jSplitPane2);

        javax.swing.GroupLayout jPanelPesateLayout = new javax.swing.GroupLayout(jPanelPesate);
        jPanelPesate.setLayout(jPanelPesateLayout);
        jPanelPesateLayout.setHorizontalGroup(
            jPanelPesateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPesateLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1)
                .addContainerGap())
        );
        jPanelPesateLayout.setVerticalGroup(
            jPanelPesateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPesateLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1)
                .addContainerGap())
        );

        jTabbedPane.addTab("Pesate", jPanelPesate);

        jSplitPane3.setDividerLocation(500);

        jTableTotaliMensili.setFont(font);
        jTableTotaliMensili.setRowHeight(25);
        jTableTotaliMensili.getTableHeader().setFont(font);
        jTableTotaliMensili.setEnabled(false);
        jTableTotaliMensili.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String []
            {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        jScrollPane14.setViewportView(jTableTotaliMensili);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE)
        );

        jSplitPane3.setRightComponent(jPanel22);

        jListTotaliClienti.setModel(new javax.swing.AbstractListModel<String>()
        {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jListTotaliClienti.setFont(font);
        jListTotaliClienti.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                jListTotaliClientiValueChanged(evt);
            }
        });
        jScrollPane9.setViewportView(jListTotaliClienti);

        jPanel19.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jXDatePickerTotali.setFont(font);
        jXDatePickerTotali.addPopupMenuListener(new javax.swing.event.PopupMenuListener()
        {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt)
            {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt)
            {
                jXDatePickerTotaliPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt)
            {
            }
        });

        jLabelTotaliLogo.setHorizontalAlignment(SwingConstants.CENTER);

        jButtonSaveTotali.setText("Salva Tutti");
        jButtonSaveTotali.setFont(fontBig);
        jButtonSaveTotali.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonSaveTotaliMouseClicked(evt);
            }
        });

        jButtonTotaliSalvaSettimana.setText("Salva Sett");
        jButtonTotaliSalvaSettimana.setFont(font);
        jButtonTotaliSalvaSettimana.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonTotaliSalvaSettimanaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelTotaliLogo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonSaveTotali, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jXDatePickerTotali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 23, Short.MAX_VALUE))
                    .addComponent(jButtonTotaliSalvaSettimana, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXDatePickerTotali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTotaliLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonTotaliSalvaSettimana)
                .addGap(18, 18, 18)
                .addComponent(jButtonSaveTotali)
                .addContainerGap())
        );

        jButtonTotaliOrdinamento.setText("jButton10");
        jButtonTotaliOrdinamento.setFont(font);
        jButtonTotaliOrdinamento.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonTotaliOrdinamentoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                    .addComponent(jButtonTotaliOrdinamento, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(jButtonTotaliOrdinamento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE))
        );

        jSplitPane3.setLeftComponent(jPanel21);

        javax.swing.GroupLayout jPanelTotaliLayout = new javax.swing.GroupLayout(jPanelTotali);
        jPanelTotali.setLayout(jPanelTotaliLayout);
        jPanelTotaliLayout.setHorizontalGroup(
            jPanelTotaliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTotaliLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane3)
                .addContainerGap())
        );
        jPanelTotaliLayout.setVerticalGroup(
            jPanelTotaliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTotaliLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane3)
                .addContainerGap())
        );

        jTabbedPane.addTab("Totali", jPanelTotali);

        jPanelClientiInserimento.setPreferredSize(new java.awt.Dimension(330, 550));

        jLabelClientiModifica.setText("Modifica il Cliente");

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

        jLabelClientiSalva.setText("Inserimento di un nuovo Cliente");

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
                        .addGap(0, 158, Short.MAX_VALUE)))
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
            .addComponent(jLabelClientiLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelClientiInserimentoLayout.setVerticalGroup(
            jPanelClientiInserimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelClientiInserimentoLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelClientiLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        jButtonClientiiSu.setText("Su");

        jButtonClientiGiu.setText("Giù");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonClientiOrdine)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonClientiiSu)
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
                    .addComponent(jButtonClientiiSu)
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
        );
        jPanelClientiListaLayout.setVerticalGroup(
            jPanelClientiListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelClientiListaLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
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
            .addComponent(jPanelClientiLista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelClientiInserimento, javax.swing.GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
        );

        jTabbedPane.addTab("Clienti", jPanelClienti);

        jPanelProdottiInserimento.setPreferredSize(new java.awt.Dimension(330, 550));

        jLabelProdottiModifica.setText("Modifica il Prodotto");

        jTextFieldProdottiModifica.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                jTextFieldProdottiModificaKeyReleased(evt);
            }
        });

        jButtonProdottiModifica.setText("Modifica");
        jButtonProdottiModifica.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonProdottiModificaMouseClicked(evt);
            }
        });

        jToggleButtonProdottiModifyUnit.setText("jToggleButton1");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldProdottiModifica, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonProdottiModifica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabelProdottiModifica)
                        .addGap(0, 219, Short.MAX_VALUE))
                    .addComponent(jToggleButtonProdottiModifyUnit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelProdottiModifica)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldProdottiModifica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButtonProdottiModifyUnit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonProdottiModifica)
                .addContainerGap())
        );

        jLabelProdottiSalva.setText("Inserimento di un nuovo Prodotto");

        jTextFieldProdottiSalva.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                jTextFieldProdottiSalvaKeyReleased(evt);
            }
        });

        jButtonProdottiSalva.setText("Salva");
        jButtonProdottiSalva.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonProdottiSalvaMouseClicked(evt);
            }
        });

        jToggleButtonProdottiSaveUnit.setText("jToggleButton1");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldProdottiSalva, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonProdottiSalva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabelProdottiSalva)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jToggleButtonProdottiSaveUnit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelProdottiSalva)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldProdottiSalva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButtonProdottiSaveUnit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonProdottiSalva)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelProdottiInserimentoLayout = new javax.swing.GroupLayout(jPanelProdottiInserimento);
        jPanelProdottiInserimento.setLayout(jPanelProdottiInserimentoLayout);
        jPanelProdottiInserimentoLayout.setHorizontalGroup(
            jPanelProdottiInserimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabelProdottiLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelProdottiInserimentoLayout.setVerticalGroup(
            jPanelProdottiInserimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProdottiInserimentoLayout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelProdottiLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jButtonProdottiOrdine.setText("Ordine");
        jButtonProdottiOrdine.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonProdottiOrdineMouseClicked(evt);
            }
        });

        jButtonProdottiSu.setText("Su");

        jButtonProdottiGiu.setText("Giù");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonProdottiOrdine)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonProdottiSu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonProdottiGiu)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonProdottiOrdine)
                    .addComponent(jButtonProdottiSu)
                    .addComponent(jButtonProdottiGiu))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButtonProdottiElimina.setText("Elimina");
        jButtonProdottiElimina.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonProdottiEliminaMouseClicked(evt);
            }
        });

        jListClienti1.setModel(new javax.swing.AbstractListModel<OrdinableObject>()
            {
                ArrayList<OrdinableObject> oa = dataBase.clienti;
                public int getSize() { return oa.size(); }
                public OrdinableObject getElementAt(int i) { return oa.get(i); }

            });
            jListClienti1.addListSelectionListener(new javax.swing.event.ListSelectionListener()
            {
                public void valueChanged(javax.swing.event.ListSelectionEvent evt)
                {
                    jListClienti1ValueChanged(evt);
                }
            });
            jScrollPane6.setViewportView(jListClienti1);

            javax.swing.GroupLayout jPanelProdottiListaLayout = new javax.swing.GroupLayout(jPanelProdottiLista);
            jPanelProdottiLista.setLayout(jPanelProdottiListaLayout);
            jPanelProdottiListaLayout.setHorizontalGroup(
                jPanelProdottiListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonProdottiElimina, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
            );
            jPanelProdottiListaLayout.setVerticalGroup(
                jPanelProdottiListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelProdottiListaLayout.createSequentialGroup()
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jButtonProdottiElimina))
            );

            javax.swing.GroupLayout jPanelProdottiLayout = new javax.swing.GroupLayout(jPanelProdotti);
            jPanelProdotti.setLayout(jPanelProdottiLayout);
            jPanelProdottiLayout.setHorizontalGroup(
                jPanelProdottiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelProdottiLayout.createSequentialGroup()
                    .addComponent(jPanelProdottiInserimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanelProdottiLista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            jPanelProdottiLayout.setVerticalGroup(
                jPanelProdottiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanelProdottiLista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelProdottiInserimento, javax.swing.GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
            );

            jTabbedPane.addTab("Prodotti", jPanelProdotti);

            jPanelClientiInserimento2.setPreferredSize(new java.awt.Dimension(330, 550));

            jLabelClientiModifica2.setText("Modifica il cliente");

            jTextFieldClientiModifica2.addKeyListener(new java.awt.event.KeyAdapter()
            {
                public void keyReleased(java.awt.event.KeyEvent evt)
                {
                    jTextFieldClientiModifica2KeyReleased(evt);
                }
            });

            jButtonClientiModifica2.setText("Modifica");
            jButtonClientiModifica2.addMouseListener(new java.awt.event.MouseAdapter()
            {
                public void mouseClicked(java.awt.event.MouseEvent evt)
                {
                    jButtonClientiModifica2MouseClicked(evt);
                }
            });

            jLabel2.setText("jLabel2");

            javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
            jPanel14.setLayout(jPanel14Layout);
            jPanel14Layout.setHorizontalGroup(
                jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel14Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextFieldClientiModifica2, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButtonClientiModifica2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel14Layout.createSequentialGroup()
                            .addComponent(jLabelClientiModifica2)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
            );
            jPanel14Layout.setVerticalGroup(
                jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel14Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabelClientiModifica2)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jTextFieldClientiModifica2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jButtonClientiModifica2)
                    .addContainerGap())
            );

            jLabelClientiSalva2.setText("Inserimento di nuovo Cliente");

            jTextFieldClientiSalva2.addKeyListener(new java.awt.event.KeyAdapter()
            {
                public void keyReleased(java.awt.event.KeyEvent evt)
                {
                    jTextFieldClientiSalva2KeyReleased(evt);
                }
            });

            jButtonClientiSalva2.setText("Salva");
            jButtonClientiSalva2.addMouseListener(new java.awt.event.MouseAdapter()
            {
                public void mouseClicked(java.awt.event.MouseEvent evt)
                {
                    jButtonClientiSalva2MouseClicked(evt);
                }
            });

            jLabel1.setText("jLabel1");

            javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
            jPanel15.setLayout(jPanel15Layout);
            jPanel15Layout.setHorizontalGroup(
                jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel15Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextFieldClientiSalva2, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButtonClientiSalva2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel15Layout.createSequentialGroup()
                            .addComponent(jLabelClientiSalva2)
                            .addGap(0, 173, Short.MAX_VALUE))
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
            );
            jPanel15Layout.setVerticalGroup(
                jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel15Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabelClientiSalva2)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jTextFieldClientiSalva2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonClientiSalva2)
                    .addContainerGap())
            );

            javax.swing.GroupLayout jPanelClientiInserimento2Layout = new javax.swing.GroupLayout(jPanelClientiInserimento2);
            jPanelClientiInserimento2.setLayout(jPanelClientiInserimento2Layout);
            jPanelClientiInserimento2Layout.setHorizontalGroup(
                jPanelClientiInserimento2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelClientiLogo2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            jPanelClientiInserimento2Layout.setVerticalGroup(
                jPanelClientiInserimento2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelClientiInserimento2Layout.createSequentialGroup()
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabelClientiLogo2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            );

            jButtonClientiOrdine2.setText("Ordine");
            jButtonClientiOrdine2.addMouseListener(new java.awt.event.MouseAdapter()
            {
                public void mouseClicked(java.awt.event.MouseEvent evt)
                {
                    jButtonClientiOrdine2MouseClicked(evt);
                }
            });

            jButtonClientiSu2.setText("Su");

            jButtonClientiGiu2.setText("Giù");

            javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
            jPanel16.setLayout(jPanel16Layout);
            jPanel16Layout.setHorizontalGroup(
                jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel16Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jButtonClientiOrdine2)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonClientiSu2)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jButtonClientiGiu2)
                    .addContainerGap())
            );
            jPanel16Layout.setVerticalGroup(
                jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel16Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonClientiOrdine2)
                        .addComponent(jButtonClientiSu2)
                        .addComponent(jButtonClientiGiu2))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            jButtonClientiElimina2.setText("Elimina");
            jButtonClientiElimina2.addMouseListener(new java.awt.event.MouseAdapter()
            {
                public void mouseClicked(java.awt.event.MouseEvent evt)
                {
                    jButtonClientiElimina2MouseClicked(evt);
                }
            });

            jListClienti2.setModel(new javax.swing.AbstractListModel<OrdinableObject>()
                {
                    ArrayList<OrdinableObject> oa = dataBase.clienti;
                    public int getSize() { return oa.size(); }
                    public OrdinableObject getElementAt(int i) { return oa.get(i); }

                });
                jListClienti2.addListSelectionListener(new javax.swing.event.ListSelectionListener()
                {
                    public void valueChanged(javax.swing.event.ListSelectionEvent evt)
                    {
                        jListClienti2ValueChanged(evt);
                    }
                });
                jScrollPane7.setViewportView(jListClienti2);

                javax.swing.GroupLayout jPanelClientiLista2Layout = new javax.swing.GroupLayout(jPanelClientiLista2);
                jPanelClientiLista2.setLayout(jPanelClientiLista2Layout);
                jPanelClientiLista2Layout.setHorizontalGroup(
                    jPanelClientiLista2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonClientiElimina2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
                );
                jPanelClientiLista2Layout.setVerticalGroup(
                    jPanelClientiLista2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelClientiLista2Layout.createSequentialGroup()
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonClientiElimina2))
                );

                javax.swing.GroupLayout jPanelTareLayout = new javax.swing.GroupLayout(jPanelTare);
                jPanelTare.setLayout(jPanelTareLayout);
                jPanelTareLayout.setHorizontalGroup(
                    jPanelTareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTareLayout.createSequentialGroup()
                        .addComponent(jPanelClientiInserimento2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelClientiLista2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                jPanelTareLayout.setVerticalGroup(
                    jPanelTareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelClientiLista2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelClientiInserimento2, javax.swing.GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
                );

                jTabbedPane.addTab("Tare", jPanelTare);

                jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

                jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

                jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

                javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
                jPanel17.setLayout(jPanel17Layout);
                jPanel17Layout.setHorizontalGroup(
                    jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                );
                jPanel17Layout.setVerticalGroup(
                    jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                jTextArea1.setColumns(20);
                jTextArea1.setRows(5);
                jScrollPane8.setViewportView(jTextArea1);

                jToggleButtonDemoBilancia.setText("Demo Bilancia");

                javax.swing.GroupLayout jPanelOptionsLayout = new javax.swing.GroupLayout(jPanelOptions);
                jPanelOptions.setLayout(jPanelOptionsLayout);
                jPanelOptionsLayout.setHorizontalGroup(
                    jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jToggleButtonDemoBilancia, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
                        .addContainerGap())
                );
                jPanelOptionsLayout.setVerticalGroup(
                    jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane8)
                            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 404, Short.MAX_VALUE)
                                .addComponent(jToggleButtonDemoBilancia)))
                        .addContainerGap())
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
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButtonRemovedClienti))
                    );

                    jButton10.setText("jButton10");

                    jList1.setModel(new javax.swing.AbstractListModel<String>()
                    {
                        String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
                        public int getSize() { return strings.length; }
                        public String getElementAt(int i) { return strings[i]; }
                    });
                    jScrollPane13.setViewportView(jList1);

                    javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
                    jPanel5.setLayout(jPanel5Layout);
                    jPanel5Layout.setHorizontalGroup(
                        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                        .addComponent(jScrollPane13)
                    );
                    jPanel5Layout.setVerticalGroup(
                        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                            .addComponent(jScrollPane13)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton10))
                    );

                    jButton11.setText("jButton11");

                    jList2.setModel(new javax.swing.AbstractListModel<String>()
                    {
                        String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
                        public int getSize() { return strings.length; }
                        public String getElementAt(int i) { return strings[i]; }
                    });
                    jScrollPane15.setViewportView(jList2);

                    javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
                    jPanel6.setLayout(jPanel6Layout);
                    jPanel6Layout.setHorizontalGroup(
                        jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                        .addComponent(jScrollPane15)
                    );
                    jPanel6Layout.setVerticalGroup(
                        jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                            .addComponent(jScrollPane15)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton11))
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
                        .addComponent(jTabbedPane)
                    );
                    layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane)
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
        orderClienti();
        updatejList(jListClienti, dataBase.clienti);
    }//GEN-LAST:event_jButtonClientiOrdineMouseClicked

    private void jButtonBilanciaTaraMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonBilanciaTaraMouseClicked
    {//GEN-HEADEREND:event_jButtonBilanciaTaraMouseClicked
        //updateTara(lordo);
    }//GEN-LAST:event_jButtonBilanciaTaraMouseClicked

    private void jTextFieldBilanciaNettoMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jTextFieldBilanciaNettoMouseClicked
    {//GEN-HEADEREND:event_jTextFieldBilanciaNettoMouseClicked
        jTextFieldBilanciaNetto.selectAll();
    }//GEN-LAST:event_jTextFieldBilanciaNettoMouseClicked

    private void jTextFieldBilanciaNettoKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTextFieldBilanciaNettoKeyReleased
    {//GEN-HEADEREND:event_jTextFieldBilanciaNettoKeyReleased
        /*try
        {
            float valore = Float.parseFloat(jTextFieldQuantitaRichiesta.getText());
            quantita = valore;
        }
        catch(NumberFormatException e)
        {
            quantita = 0;
            System.out.println(e.toString());
        }*/
    }//GEN-LAST:event_jTextFieldBilanciaNettoKeyReleased

    private void jButton7MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton7MouseClicked
    {//GEN-HEADEREND:event_jButton7MouseClicked
        insTastierino(7);
    }//GEN-LAST:event_jButton7MouseClicked

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton4MouseClicked
    {//GEN-HEADEREND:event_jButton4MouseClicked
        insTastierino(4);
    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton8MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton8MouseClicked
    {//GEN-HEADEREND:event_jButton8MouseClicked
        insTastierino(8);
    }//GEN-LAST:event_jButton8MouseClicked

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton5MouseClicked
    {//GEN-HEADEREND:event_jButton5MouseClicked
        insTastierino(5);
    }//GEN-LAST:event_jButton5MouseClicked

    private void jButton9MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton9MouseClicked
    {//GEN-HEADEREND:event_jButton9MouseClicked
        insTastierino(9);
    }//GEN-LAST:event_jButton9MouseClicked

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton6MouseClicked
    {//GEN-HEADEREND:event_jButton6MouseClicked
        insTastierino(6);
    }//GEN-LAST:event_jButton6MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton1MouseClicked
    {//GEN-HEADEREND:event_jButton1MouseClicked
        insTastierino(1);
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton2MouseClicked
    {//GEN-HEADEREND:event_jButton2MouseClicked
        insTastierino(2);
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton3MouseClicked
    {//GEN-HEADEREND:event_jButton3MouseClicked
        insTastierino(3);
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton0MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton0MouseClicked
    {//GEN-HEADEREND:event_jButton0MouseClicked
        insTastierino(0);
    }//GEN-LAST:event_jButton0MouseClicked

    private void jButtonBilanciaAggiungiMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonBilanciaAggiungiMouseClicked
    {//GEN-HEADEREND:event_jButtonBilanciaAggiungiMouseClicked
        /*log("jButtonBilanciaAggiungiMouseClicked");
        if(jListBilanciaClienti.getSelectedIndex() != -1 && jListBilanciaProdotti.getSelectedIndex() != -1 && netto > 0)
        {
            if(bilanciaElencoPesate == null)
            {
                bilanciaElencoPesate = new ElencoOggettiOrdinabili();
            }

            bilanciaElencoPesate.add(new Pesata(
                bilanciaElencoPesate.getNewId(),
                idProdotto,
                idCliente,
                netto,
                GregorianCalendar.getInstance()));
        jListBilanciaClienti.setEnabled(false);
        netto = 0;
        jTextFieldBilanciaNetto.setText("");
        aggiornajListBilanciaScontrino();
        }*/
    }//GEN-LAST:event_jButtonBilanciaAggiungiMouseClicked

    private void jTextFieldBilanciaTaraMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jTextFieldBilanciaTaraMouseClicked
    {//GEN-HEADEREND:event_jTextFieldBilanciaTaraMouseClicked
        /*log("jTextFieldBilanciaTaraMouseClicked");
        InterfacciaTara interfacciaTara = new InterfacciaTara(font, fontBig);
        oTare.set(Ordinamento.CARDINALE);
        ordinaTare();
        this.setEnabled(false);
        interfacciaTara.setVisible(true);*/
    }//GEN-LAST:event_jTextFieldBilanciaTaraMouseClicked

    private void jTextFieldBilanciaTaraKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTextFieldBilanciaTaraKeyReleased
    {//GEN-HEADEREND:event_jTextFieldBilanciaTaraKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBilanciaTaraKeyReleased

    private void jListBilanciaScontrinoMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jListBilanciaScontrinoMouseClicked
    {//GEN-HEADEREND:event_jListBilanciaScontrinoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jListBilanciaScontrinoMouseClicked

    private void jButtonBilanciaEliminaMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonBilanciaEliminaMouseClicked
    {//GEN-HEADEREND:event_jButtonBilanciaEliminaMouseClicked
        /*log("jButtonBilanciaEliminaMouseClicked");
        int indice = jListBilanciaScontrino.getSelectedIndex();
        if(indice != -1)
        {
            bilanciaElencoPesate.remove(bilanciaElencoPesate.get(indice).getId());
            aggiornajListBilanciaScontrino();
            if(bilanciaElencoPesate.isEmpty())
            {
                jListBilanciaClienti.setEnabled(true);
            }
        }*/
    }//GEN-LAST:event_jButtonBilanciaEliminaMouseClicked

    private void jButtonBilanciaStampaMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonBilanciaStampaMouseClicked
    {//GEN-HEADEREND:event_jButtonBilanciaStampaMouseClicked
       /* log("jButtonBilanciaStampaMouseClicked");
        if(!bilanciaElencoPesate.isEmpty())
        {
            Runnable runner = new Runnable()
            {
                public void run()
                {
                    long idCliente = -1;
                    StarterClass.barra = new VisualizzaProgressione(0,9, 100);
                    StarterClass.barra.setVisible(true);

                    setEnabled(false);
                    for(int i=0; i<bilanciaElencoPesate.size(); i++)
                    {
                        Pesata p = (Pesata)bilanciaElencoPesate.get(i);
                        idCliente = p.idCliente;
                        long id = dataBase.elencoPesate.getNewId();
                        dataBase.elencoPesate.add(new Pesata(
                            id,
                            p.idProdotto,
                            p.idCliente,
                            p.quantitaFisica,
                            GregorianCalendar.getInstance()));
                }

                Calendar cal = GregorianCalendar.getInstance();
                dataBase.salvaPesate(new DataPath(cal));
                StarterClass.barra.progress();

                elaboraStampa(bilanciaElencoPesate);
                StarterClass.barra.progress();

                dataBase.aggiornaFrequenze(idCliente, bilanciaElencoPesate);
                StarterClass.barra.progress();

                ((Cliente)dataBase.elencoClienti.get(idCliente)).oraFrequente.aggiungi(GregorianCalendar.getInstance());
                dataBase.salvaClienti();
                StarterClass.barra.progress();

                bilanciaElencoPesate = new ElencoOggettiOrdinabili();
                aggiornajListBilanciaScontrino();
                StarterClass.barra.progress();

                jListBilanciaClienti.setEnabled(true);
                float tara = 0;
                updateTara(tara);
                StarterClass.barra.progress();

                ordinaClienti();
                StarterClass.barra.progress();

                ordinaProdotti();
                StarterClass.barra.progress();

                aggiornaBilancia();
                StarterClass.barra.progress();

                StarterClass.barra.chiudi();
                StarterClass.barra = null;
                StarterClass.interfaccia.setEnabled(true);
                StarterClass.interfaccia.requestFocus();

            }
        };
        Thread t = new Thread(runner, "Code Executer");
        t.start();
        }*/
    }//GEN-LAST:event_jButtonBilanciaStampaMouseClicked

    private void jButtoBilanciaClientiMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtoBilanciaClientiMouseClicked
    {//GEN-HEADEREND:event_jButtoBilanciaClientiMouseClicked
        oClienti.getNext();
        orderClienti();
        updatejList(jListBilanciaClienti, dataBase.clienti);
    }//GEN-LAST:event_jButtoBilanciaClientiMouseClicked

    private void jButtonBilanciaProdottiMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonBilanciaProdottiMouseClicked
    {//GEN-HEADEREND:event_jButtonBilanciaProdottiMouseClicked
        oProdotti.getNext();
        orderProdotti();
        updatejList(jListBilanciaProdotti, dataBase.prodotti);
    }//GEN-LAST:event_jButtonBilanciaProdottiMouseClicked

    private void jListBilanciaClientiValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_jListBilanciaClientiValueChanged
    {//GEN-HEADEREND:event_jListBilanciaClientiValueChanged
        if(!evt.getValueIsAdjusting())
        {
            if(jListBilanciaClienti.getSelectedIndex() != -1)
            {
                OrdinableObject c = jListBilanciaClienti.getSelectedValue();
                if(c != null)
                {
                    cliente = (Cliente)c;
                    jLabelBilanciaCliente.setText(Utility.getMax(30, c.getName()));
                }
            }
        }
    }//GEN-LAST:event_jListBilanciaClientiValueChanged

    private void jListBilanciaProdottiValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_jListBilanciaProdottiValueChanged
    {//GEN-HEADEREND:event_jListBilanciaProdottiValueChanged
        if(!evt.getValueIsAdjusting())
        {
            if(jListBilanciaProdotti.getSelectedIndex() != -1)
            {
                OrdinableObject c = jListBilanciaProdotti.getSelectedValue();
                if(c != null)
                {
                    prodotto = (Prodotto)c;
                    jLabeBilanciaProdotto.setText(Utility.getMax(26, c.getName()));
                }
            }
        }
    }//GEN-LAST:event_jListBilanciaProdottiValueChanged

    private void formAncestorResized(java.awt.event.HierarchyEvent evt)//GEN-FIRST:event_formAncestorResized
    {//GEN-HEADEREND:event_formAncestorResized
        updateLogo();
    }//GEN-LAST:event_formAncestorResized

    private void jTextFieldProdottiModificaKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTextFieldProdottiModificaKeyReleased
    {//GEN-HEADEREND:event_jTextFieldProdottiModificaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldProdottiModificaKeyReleased

    private void jButtonProdottiModificaMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonProdottiModificaMouseClicked
    {//GEN-HEADEREND:event_jButtonProdottiModificaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonProdottiModificaMouseClicked

    private void jTextFieldProdottiSalvaKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTextFieldProdottiSalvaKeyReleased
    {//GEN-HEADEREND:event_jTextFieldProdottiSalvaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldProdottiSalvaKeyReleased

    private void jButtonProdottiSalvaMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonProdottiSalvaMouseClicked
    {//GEN-HEADEREND:event_jButtonProdottiSalvaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonProdottiSalvaMouseClicked

    private void jButtonProdottiOrdineMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonProdottiOrdineMouseClicked
    {//GEN-HEADEREND:event_jButtonProdottiOrdineMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonProdottiOrdineMouseClicked

    private void jButtonProdottiEliminaMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonProdottiEliminaMouseClicked
    {//GEN-HEADEREND:event_jButtonProdottiEliminaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonProdottiEliminaMouseClicked

    private void jListClienti1ValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_jListClienti1ValueChanged
    {//GEN-HEADEREND:event_jListClienti1ValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jListClienti1ValueChanged

    private void jTextFieldClientiModifica2KeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTextFieldClientiModifica2KeyReleased
    {//GEN-HEADEREND:event_jTextFieldClientiModifica2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldClientiModifica2KeyReleased

    private void jButtonClientiModifica2MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonClientiModifica2MouseClicked
    {//GEN-HEADEREND:event_jButtonClientiModifica2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonClientiModifica2MouseClicked

    private void jTextFieldClientiSalva2KeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTextFieldClientiSalva2KeyReleased
    {//GEN-HEADEREND:event_jTextFieldClientiSalva2KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldClientiSalva2KeyReleased

    private void jButtonClientiSalva2MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonClientiSalva2MouseClicked
    {//GEN-HEADEREND:event_jButtonClientiSalva2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonClientiSalva2MouseClicked

    private void jButtonClientiOrdine2MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonClientiOrdine2MouseClicked
    {//GEN-HEADEREND:event_jButtonClientiOrdine2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonClientiOrdine2MouseClicked

    private void jButtonClientiElimina2MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonClientiElimina2MouseClicked
    {//GEN-HEADEREND:event_jButtonClientiElimina2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonClientiElimina2MouseClicked

    private void jListClienti2ValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_jListClienti2ValueChanged
    {//GEN-HEADEREND:event_jListClienti2ValueChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jListClienti2ValueChanged

    private void jListTotaliClientiValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_jListTotaliClientiValueChanged
    {//GEN-HEADEREND:event_jListTotaliClientiValueChanged
        if(!evt.getValueIsAdjusting())
        {
            //aggiornaTotaliCliente();
        }
    }//GEN-LAST:event_jListTotaliClientiValueChanged

    private void jXDatePickerTotaliPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt)//GEN-FIRST:event_jXDatePickerTotaliPopupMenuWillBecomeInvisible
    {//GEN-HEADEREND:event_jXDatePickerTotaliPopupMenuWillBecomeInvisible
        /*aggiornaTotaliCliente();
        aggiornaSettimana();*/
    }//GEN-LAST:event_jXDatePickerTotaliPopupMenuWillBecomeInvisible

    private void jButtonSaveTotaliMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonSaveTotaliMouseClicked
    {//GEN-HEADEREND:event_jButtonSaveTotaliMouseClicked
        /*Runnable runner = new Runnable()
        {
            public void run()
            {
                Calendar data = Calendar.getInstance();
                data.setTime(jXDatePickerTotali.getDate());
                dataBase.salvaFileTotali(data);
                String file = StarterClass.getResource().replace("Bollettario.jar", "");

            }
        };
        Thread t = new Thread(runner, "Code Executer");
        t.start();
        /*
        JLabel label = new JLabel("Apri la cartella dei salvataggi");
        label.setFont(fontBig);
        int reply = JOptionPane.showConfirmDialog(null, label, "Salvataggio Mensile", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION)
        {
            String file = StarterClass.getResource().replace("Bollettario.jar", "");
            file = file.substring(1);
            if(file != null)
            {
                try
                {

                    Runtime.getRuntime().exec(
                        new String[]
                        {
                            "explorer.exe",
                            "/select,",
                            "\"" + file+dataBase.pathSalvataggi+"\""
                        });
                        jTextAreaUltimaStampa.setText("explorer.exe /select,\"" + file+dataBase.pathSalvataggi+"/\"");
                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }*/
    }//GEN-LAST:event_jButtonSaveTotaliMouseClicked

    private void jButtonTotaliSalvaSettimanaMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonTotaliSalvaSettimanaMouseClicked
    {//GEN-HEADEREND:event_jButtonTotaliSalvaSettimanaMouseClicked
        /*int indiceCliente = jListTotaliClienti.getSelectedIndex();
        if(indiceCliente != -1)
        {
            long idCliente = dataBase.elencoClienti.get(indiceCliente).getId();
            dataBase.salvaTotaliSettimana(idCliente, firstDay, lastDay);
        }*/
    }//GEN-LAST:event_jButtonTotaliSalvaSettimanaMouseClicked

    private void jButtonTotaliOrdinamentoMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonTotaliOrdinamentoMouseClicked
    {//GEN-HEADEREND:event_jButtonTotaliOrdinamentoMouseClicked
       /* log("jButtonTotaliOrdinamentoMouseClicked");
        oClienti.getNext();
        ordinaClienti();
        aggiornajListTotaliClienti();        // TODO add your handling code here:*/
    }//GEN-LAST:event_jButtonTotaliOrdinamentoMouseClicked

    private void jButtonPesateModificaMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonPesateModificaMouseClicked
    {//GEN-HEADEREND:event_jButtonPesateModificaMouseClicked
        /*int indicePesata = jListPesatePesate.getSelectedIndex();
        int indiceCliente = jListPesateClienti.getSelectedIndex();
        if(indicePesata != -1 && indiceCliente != -1)
        {
            long idPesata = idPesateClienteMese.get(indicePesata);
            Calendar cal = Calendar.getInstance();
            cal.setTime(jXDatePickerPesate.getDate());
            InterfacciaTastierino it = new InterfacciaTastierino(font, fontBig, idPesata, new DataPath(cal));
            setEnabled(false);
            it.setVisible(true);

        }*/
    }//GEN-LAST:event_jButtonPesateModificaMouseClicked

    private void jButtonPesateEliminaMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonPesateEliminaMouseClicked
    {//GEN-HEADEREND:event_jButtonPesateEliminaMouseClicked
        /*int indicePesata = jListPesatePesate.getSelectedIndex();
        int indiceClienteSelezionato = jListPesateClienti.getSelectedIndex();
        if(indicePesata != -1 && indiceClienteSelezionato != -1)
        {
            long idPesata = idPesateClienteMese.get(indicePesata);
            Pesata p = (Pesata)dataBase.elencoPesate.get(idPesata);
            Prodotto pr = (Prodotto)dataBase.elencoProdotti.get(p.idProdotto);
            String format = "%.3f";
            if(pr.unitaDiMisura == UnitaDiMisura.QUANTITA)
            {
                format = "%.0f";
            }
            String pesata = p.data.get(Calendar.DAY_OF_MONTH) + "/" +
            (p.data.get(Calendar.MONTH)+1)+ " " +
            String.format(format, p.quantitaFisica) + " " +
            pr.unitaDiMisura + " " +
            pr.getNome();
            JLabel label = new JLabel(pesata);
            label.setFont(fontBig);
            int reply = JOptionPane.showConfirmDialog(null, label, "Eliminazione Pesata", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION)
            {
                label = new JLabel("PESATA ELIMINATA");
                label.setFont(fontBig);
                JOptionPane.showMessageDialog(null, label);

            }
            if(reply == 0)
            {
                dataBase.elencoPesate.remove(p);
                Calendar cal = Calendar.getInstance();
                cal.setTime(jXDatePickerPesate.getDate());
                dataBase.salvaPesate(new DataPath(cal));
                aggiornaPesate();
                jListPesateClienti.setSelectedIndex(indiceClienteSelezionato);
            }
        }*/
    }//GEN-LAST:event_jButtonPesateEliminaMouseClicked

    private void jXDatePickerPesatePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt)//GEN-FIRST:event_jXDatePickerPesatePopupMenuWillBecomeInvisible
    {//GEN-HEADEREND:event_jXDatePickerPesatePopupMenuWillBecomeInvisible
        //aggiornaListaPesate();
    }//GEN-LAST:event_jXDatePickerPesatePopupMenuWillBecomeInvisible

    private void jButtonPesateRistampaScontrinoMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonPesateRistampaScontrinoMouseClicked
    {//GEN-HEADEREND:event_jButtonPesateRistampaScontrinoMouseClicked
        /*int indicePesata = jListPesatePesate.getSelectedIndex();
        int indiceCliente = jListPesateClienti.getSelectedIndex();
        if(indicePesata != -1 && indiceCliente != -1)
        {
            long idPesata = idPesateClienteMese.get(indicePesata);
            Pesata p = (Pesata) dataBase.elencoPesate.get(idPesata);
            ElencoOggettiOrdinabili ristampaPesate = new ElencoOggettiOrdinabili();
            for(long idP: idPesateClienteMese)
            {
                Pesata p2 = (Pesata) dataBase.elencoPesate.get(idP);
                if(p2.data.get(Calendar.DAY_OF_MONTH) == p.data.get(Calendar.DAY_OF_MONTH))
                {
                    ristampaPesate.add(p2);
                }
            }
            elaboraStampa(ristampaPesate);
        }*/
    }//GEN-LAST:event_jButtonPesateRistampaScontrinoMouseClicked

    private void jListPesateClientiValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_jListPesateClientiValueChanged
    {//GEN-HEADEREND:event_jListPesateClientiValueChanged
        if(!evt.getValueIsAdjusting())
        {
            log("jListPesateClientiValueChanged");
            //aggiornaListaPesate();
        }
    }//GEN-LAST:event_jListPesateClientiValueChanged

    private void jButtonPesateOrdinamentoClientiMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonPesateOrdinamentoClientiMouseClicked
    {//GEN-HEADEREND:event_jButtonPesateOrdinamentoClientiMouseClicked
        /*log("jButtonPesateOrdinamentoClientiMouseClicked");
        oClienti.getNext();
        ordinaClienti();
        aggiornaPesate();*/
    }//GEN-LAST:event_jButtonPesateOrdinamentoClientiMouseClicked

    private void jButtonPesateCambiaClienteMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonPesateCambiaClienteMouseClicked
    {//GEN-HEADEREND:event_jButtonPesateCambiaClienteMouseClicked
        /*int indice = jListPesatePesate.getSelectedIndex();
        int indiceCliente1 = jListPesateClienti.getSelectedIndex();
        int indiceCliente2 = jListPesateCambiaCliente.getSelectedIndex();
        if(indice != -1 && indiceCliente1 != -1 && indiceCliente2 != -1)
        {
            long idPesata = idPesateClienteMese.get(indice);
            long idCliente2 = dataBase.elencoClienti.get(indiceCliente2).getId();
            ((Pesata)dataBase.elencoPesate.get(idPesata)).idCliente = idCliente2;

            Calendar cal = Calendar.getInstance();
            cal.setTime(jXDatePickerPesate.getDate());
            dataBase.salvaPesate(new DataPath(cal));
            aggiornaListaPesate();
        }*/
    }//GEN-LAST:event_jButtonPesateCambiaClienteMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtoBilanciaClienti;
    private javax.swing.JButton jButton0;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButtonBilanciaAggiungi;
    private javax.swing.JButton jButtonBilanciaElimina;
    private javax.swing.JButton jButtonBilanciaProdotti;
    private javax.swing.JButton jButtonBilanciaStampa;
    private javax.swing.JButton jButtonBilanciaTara;
    private javax.swing.JButton jButtonClientiElimina;
    private javax.swing.JButton jButtonClientiElimina2;
    private javax.swing.JButton jButtonClientiGiu;
    private javax.swing.JButton jButtonClientiGiu2;
    private javax.swing.JButton jButtonClientiModifica;
    private javax.swing.JButton jButtonClientiModifica2;
    private javax.swing.JButton jButtonClientiOrdine;
    private javax.swing.JButton jButtonClientiOrdine2;
    private javax.swing.JButton jButtonClientiSalva;
    private javax.swing.JButton jButtonClientiSalva2;
    private javax.swing.JButton jButtonClientiSu2;
    private javax.swing.JButton jButtonClientiiSu;
    private javax.swing.JButton jButtonPesateCambiaCliente;
    private javax.swing.JButton jButtonPesateElimina;
    private javax.swing.JButton jButtonPesateModifica;
    private javax.swing.JButton jButtonPesateOrdinamentoClienti;
    private javax.swing.JButton jButtonPesateRistampaScontrino;
    private javax.swing.JButton jButtonProdottiElimina;
    private javax.swing.JButton jButtonProdottiGiu;
    private javax.swing.JButton jButtonProdottiModifica;
    private javax.swing.JButton jButtonProdottiOrdine;
    private javax.swing.JButton jButtonProdottiSalva;
    private javax.swing.JButton jButtonProdottiSu;
    private javax.swing.JButton jButtonRemovedClienti;
    private javax.swing.JButton jButtonSaveTotali;
    private javax.swing.JButton jButtonTotaliOrdinamento;
    private javax.swing.JButton jButtonTotaliSalvaSettimana;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabeBilanciaProdotto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelBilanciaCliente;
    private javax.swing.JLabel jLabelBilanciaLogo;
    private javax.swing.JLabel jLabelBilanciaLordo;
    private javax.swing.JLabel jLabelBilanciaTotalePesaScontrino;
    private javax.swing.JLabel jLabelBilanciaTotaleTitle;
    private javax.swing.JLabel jLabelClientiLogo;
    private javax.swing.JLabel jLabelClientiLogo2;
    private javax.swing.JLabel jLabelClientiModifica;
    private javax.swing.JLabel jLabelClientiModifica2;
    private javax.swing.JLabel jLabelClientiSalva;
    private javax.swing.JLabel jLabelClientiSalva2;
    private javax.swing.JLabel jLabelPesateLogo;
    private javax.swing.JLabel jLabelProdottiLogo;
    private javax.swing.JLabel jLabelProdottiModifica;
    private javax.swing.JLabel jLabelProdottiSalva;
    private javax.swing.JLabel jLabelTotaliLogo;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JList<OrdinableObject> jListBilanciaClienti;
    private javax.swing.JList<OrdinableObject> jListBilanciaProdotti;
    private javax.swing.JList<String> jListBilanciaScontrino;
    private javax.swing.JList<OrdinableObject> jListClienti;
    private javax.swing.JList<OrdinableObject> jListClienti1;
    private javax.swing.JList<OrdinableObject> jListClienti2;
    private javax.swing.JList<String> jListPesateCambiaCliente;
    private javax.swing.JList<String> jListPesateClienti;
    private javax.swing.JList<String> jListPesatePesate;
    private javax.swing.JList<OrdinableObject> jListRemovedClienti;
    private javax.swing.JList<String> jListTotaliClienti;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelBilancia;
    private javax.swing.JPanel jPanelBilancia1;
    private javax.swing.JPanel jPanelBilancia2;
    private javax.swing.JPanel jPanelBilanciaLogo;
    private javax.swing.JPanel jPanelClienti;
    private javax.swing.JPanel jPanelClientiInserimento;
    private javax.swing.JPanel jPanelClientiInserimento2;
    private javax.swing.JPanel jPanelClientiLista;
    private javax.swing.JPanel jPanelClientiLista2;
    private javax.swing.JPanel jPanelOptions;
    private javax.swing.JPanel jPanelPesate;
    private javax.swing.JPanel jPanelProdotti;
    private javax.swing.JPanel jPanelProdottiInserimento;
    private javax.swing.JPanel jPanelProdottiLista;
    private javax.swing.JPanel jPanelRemoved;
    private javax.swing.JPanel jPanelTare;
    private javax.swing.JPanel jPanelTotali;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JSplitPane jSplitPaneBilancia;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTable jTableTotaliMensili;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextFieldBilanciaNetto;
    private javax.swing.JTextField jTextFieldBilanciaTara;
    private javax.swing.JTextField jTextFieldClientiModifica;
    private javax.swing.JTextField jTextFieldClientiModifica2;
    private javax.swing.JTextField jTextFieldClientiSalva;
    private javax.swing.JTextField jTextFieldClientiSalva2;
    private javax.swing.JTextField jTextFieldProdottiModifica;
    private javax.swing.JTextField jTextFieldProdottiSalva;
    private javax.swing.JToggleButton jToggleButtonDemoBilancia;
    private javax.swing.JToggleButton jToggleButtonProdottiModifyUnit;
    private javax.swing.JToggleButton jToggleButtonProdottiSaveUnit;
    private org.jdesktop.swingx.JXDatePicker jXDatePickerPesate;
    private org.jdesktop.swingx.JXDatePicker jXDatePickerTotali;
    // End of variables declaration//GEN-END:variables

}
