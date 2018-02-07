/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Stampante.Etichetta;
import Stampante.Stampante;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.TextArea;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import org.jdesktop.swingx.JXDatePicker;
import registro.Registro;
import toFile.dataType.Complex.DataBase;
import registro.Settings;
import toFile.Utility.Utility;
import toFile.dataType.Base.Cliente;
import toFile.dataType.Base.FrequentMinute;
import toFile.dataType.Base.Pesata;
import toFile.dataType.Base.Prodotto;
import toFile.dataType.Base.Tara;
import toFile.dataType.Base.UnitOfMeasure;
import toFile.dataType.Ordinals.ActiveOrdering;
import toFile.dataType.Ordinals.Comparator.AlphabeticalComparator;
import toFile.dataType.Ordinals.Comparator.Behavior;
import toFile.dataType.Ordinals.Comparator.CardinalComparator;
import toFile.dataType.Ordinals.Comparator.MinuteComparator;
import toFile.dataType.Ordinals.Order;
import toFile.dataType.Ordinals.OrdinableArray;
import toFile.dataType.Ordinals.OrdinableObject;
import toFile.dataType.Ordinals.PesateArray;
/**
 *
 * @author DiegoCG
 */
public class Interfaccia extends javax.swing.JFrame
{

    DataBase dataBase = registro.Registro.dataBase;
    public Font font;
    public Font fontMedium;
    public Font fontBig;
    Color coloreClienti = new Color(226, 239, 255);
    Color coloreProdotti = new Color(255, 247, 216);
    Color colorePesate = new Color(246, 255, 242);
    int maxCharBilancia = 21;
    float netto;
    float tara;
    float lordo;
    Cliente cliente;
    Prodotto prodotto;
    String logo = "Icons\\Logo.png";
    
    Calendar firstDay;
    Calendar lastDay;
    
    int activeWeightRequest;
    float totalePesate = 0;
    
    Settings settings = Registro.settings;
    
    boolean inizialization = true;
    
    IdName[] tableProducts;
    
    PesateArray bilanciaArrayPesate = new PesateArray(0);
    
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
        font = new Font("Sans-serif", 0, settings.font);
        fontMedium = new Font("Sans-serif", 0, settings.fontMedium);
        fontBig = new Font("Sans-serif", 0, settings.fontBig);
        initComponents();
        initComponents2();
        setAllFonts();
        updateLogo();
        
        updateOrder();
        
        orderClienti();
        orderProdotti();
        orderTare();
        inizialization = false;
        
        UIManager.put("OptionPane.buttonFont", new FontUIResource(font));

    }
    public void updatejSpinnerPortaCom()
    {
        jSpinnerPortaCom.setValue(Registro.settings.portaBilancia);
    }
    public void updatejTextFieldNomeStampante()
    {
        jTextFieldNomeStampante.setText(Registro.settings.nomeStampante);
    }
    public void updateSaveDirectory()
    {
        jLabelSaveDirectory.setText("Cartella: " + Registro.settings.salvataggiDirectory);
    }
    private void updateOrder()
    {
        oClienti.setInitial(settings.orderClienti);
        
        oProdotti.setInitial(settings.orderProdotti);
        
        oTare.setInitial(settings.orderTare);
    }
    private void log(String msg)
    {
        registro.Registro.log(msg);
    }
    private void initComponents2()
    {
        jToggleButtonProdottiSaveUnit.setSelected(true);
        jToggleButtonProdottiModifyUnit.setSelected(true);
        jButtonPesateRistampaScontrino.setText("<html>Ristampa<br>Scontrino</html>");
        jButtonSaveTotali.setText("<html>Salva<br>Totali</html>");
        
        int dimensioni = jXDatePickerPesate.getHeight();
        ImageIcon icona = new ImageIcon();
        icona = new ImageIcon(
                        new ImageIcon("Icons\\button.png")
                                .getImage()
                                .getScaledInstance(dimensioni, dimensioni, Image.SCALE_DEFAULT)
                        );
        JButton dateBtn = (JButton) jXDatePickerPesate.getComponent(1);
        dateBtn.setIcon(icona);
        dateBtn.setFocusPainted(false);
        dateBtn.setMargin(new Insets(0, 0, 0, 0));
        dateBtn.setContentAreaFilled(false);
        dateBtn.setBorderPainted(false);
        dateBtn.setOpaque(false);
        
        
        dimensioni = jXDatePickerTotali.getHeight();
        
        ImageIcon icona2 = new ImageIcon();
        icona2 = new ImageIcon(
                        new ImageIcon("Icons\\button.png")
                                .getImage()
                                .getScaledInstance(dimensioni, dimensioni, Image.SCALE_DEFAULT)
                        );
        JButton dateBtn2 = (JButton) jXDatePickerTotali.getComponent(1);
        
        dateBtn2.setIcon(icona2);
        dateBtn2.setFocusPainted(false);
        dateBtn2.setMargin(new Insets(0, 0, 0, 0));
        dateBtn2.setContentAreaFilled(false);
        dateBtn2.setBorderPainted(false);
        dateBtn2.setOpaque(false);
        
        
        
        
        jScrollPaneBilanciaClienti.getVerticalScrollBar().setPreferredSize(new Dimension(settings.widthScrollBar, 0));
        jScrollPaneBilanciaProdotti.getVerticalScrollBar().setPreferredSize(new Dimension(settings.widthScrollBar, 0));
        jScrollPaneBilanciaScontrino.getVerticalScrollBar().setPreferredSize(new Dimension(settings.widthScrollBar, 0));
        jScrollPaneBilanciaClienti.getVerticalScrollBar().setPreferredSize(new Dimension(settings.widthScrollBar, 0));
        jScrollPanePesateClienti.getVerticalScrollBar().setPreferredSize(new Dimension(settings.widthScrollBar, 0));
        jScrollPanePesateCambiaClienti.getVerticalScrollBar().setPreferredSize(new Dimension(settings.widthScrollBar, 0));
        jScrollPanePesate.getVerticalScrollBar().setPreferredSize(new Dimension(settings.widthScrollBar, 0));
        jScrollPaneTotaliClienti.getVerticalScrollBar().setPreferredSize(new Dimension(settings.widthScrollBar, 0));
        jScrollPaneTotali.getVerticalScrollBar().setPreferredSize(new Dimension(settings.widthScrollBar, 0));
        jScrollPaneClienti.getVerticalScrollBar().setPreferredSize(new Dimension(settings.widthScrollBar, 0));
        jScrollPaneProdotti.getVerticalScrollBar().setPreferredSize(new Dimension(settings.widthScrollBar, 0));
        jScrollPaneTare.getVerticalScrollBar().setPreferredSize(new Dimension(settings.widthScrollBar, 0));
        jScrollPaneOpzioni.getVerticalScrollBar().setPreferredSize(new Dimension(settings.widthScrollBar, 0));
        jScrollPaneRemovedClienti.getVerticalScrollBar().setPreferredSize(new Dimension(settings.widthScrollBar, 0));
        jScrollPaneRemovedProdotti.getVerticalScrollBar().setPreferredSize(new Dimension(settings.widthScrollBar, 0));
        jScrollPaneRemovedPesate.getVerticalScrollBar().setPreferredSize(new Dimension(settings.widthScrollBar, 0));
        
        jListBilanciaClienti.setBackground(coloreClienti);
        jListClienti.setBackground(coloreClienti);
        jListTotaliClienti.setBackground(coloreClienti);
        jListPesateClienti.setBackground(coloreClienti);
        jListPesateCambiaCliente.setBackground(coloreClienti);
        jListRemovedClienti.setBackground(coloreClienti);
        
        jListProdotti.setBackground(coloreProdotti);
        jListRemovedProdotti.setBackground(coloreProdotti);
        jListBilanciaProdotti.setBackground(coloreProdotti);
        
        jListPesate.setBackground(colorePesate);
        jListRemovedPesate.setBackground(colorePesate);
        
        updatejComboBox(jComboBoxClienti, oClienti);
        updatejComboBox(jComboBoxProdotti, oProdotti);
        updatejComboBox(jComboBoxTare, oTare);
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
        //jTextFieldBilanciaNetto.setFont(fontBig);
        jTextFieldBilanciaNetto.setHorizontalAlignment(SwingConstants.CENTER);
        jButtonBilanciaTara.setFont(font);
        jTextFieldBilanciaTara.setFont(fontMedium);
        jTextFieldBilanciaTara.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelBilanciaLordo.setFont(font);
        jLabelBilanciaLordo.setHorizontalAlignment(SwingConstants.CENTER);
        jButtonBilanciaAggiungi.setFont(font);
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
        
        jLabelBilanciaTotaleTitle.setFont(font);
        jLabelBilanciaTotaleTitle.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelBilanciaTotalePesaScontrino.setFont(fontBig);
        jLabelBilanciaTotalePesaScontrino.setHorizontalAlignment(SwingConstants.CENTER);
        jListBilanciaScontrino.setFont(fontMedium);
        jButtonBilanciaStampa.setFont(font);
        jButtonBilanciaElimina.setFont(font);
        
        // pesate
        jButtonPesateOrdinamentoClienti.setFont(font);
        jListPesateClienti.setFont(fontMedium);
        jListPesateCambiaCliente.setFont(fontMedium);
        jButtonPesateCambiaCliente.setFont(font);
        
        jListPesate.setFont(fontMedium);
        jButtonPesateModifica.setFont(font);
        jButtonPesateElimina.setFont(font);
        jButtonPesateRistampaScontrino.setFont(font);
        jXDatePickerPesate.setFont(font);
        
        // totali
        jTableTotaliMensili.setFont(font);
        jTableTotaliMensili.setRowHeight(25);
        jTableTotaliMensili.getTableHeader().setFont(font);
        jButtonAddPesata.setFont(font);
        jButtonTotaliOrdinamento.setFont(font);
        jButtonSaveTotali.setFont(font);
        jListTotaliClienti.setFont(fontMedium);
        
        // clienti
        jLabelClientiSalva.setFont(font);
        jTextFieldClientiSalva.setFont(font);
        jButtonClientiSalva.setFont(font);
        
        jLabelClientiModifica.setFont(font);
        jTextFieldClientiModifica.setFont(font);
        jButtonClientiModifica.setFont(font);
        
        jButtonClientiOrdine.setFont(font);
        jButtonClientiGiu.setFont(font);
        jButtonClientiSu.setFont(font);
        
        jListClienti.setFont(fontMedium);
        
        jButtonClientiElimina.setFont(font);
        
        // prodotti
        jLabelProdottiSalva.setFont(font);
        jTextFieldProdottiSalva.setFont(font);
        jToggleButtonProdottiSaveUnit.setFont(font);
        jButtonProdottiSalva.setFont(font);
        jLabelProdottiModifica.setFont(font);
        jTextFieldProdottiModifica.setFont(font);
        jToggleButtonProdottiModifyUnit.setFont(font);
        jButtonProdottiModifica.setFont(font);
        jButtonProdottiOrdine.setFont(font);
        jButtonProdottiSu.setFont(font);
        jButtonProdottiGiu.setFont(font);
        jButtonProdottiElimina.setFont(font);
        jListProdotti.setFont(fontMedium);
        // tare
        jLabelClientiSalva2.setFont(font);
        jTextFieldTareSalva.setFont(font);
        jLabelTare.setFont(font);
        jButtonTareSalva.setFont(font);
        jLabelClientiModifica2.setFont(font);
        jTextFieldTareModifica.setFont(font);
        jLabelTareModifica.setFont(font);
        jButtonTareModifica.setFont(font);
        jButtonTareOrdine.setFont(font);
        jButtonTareSu.setFont(font);
        jButtonTareGiu.setFont(font);
        jButtonTareElimina.setFont(font);
        jListTare.setFont(fontMedium);
        // opzioni
        jLabelSaveDirectory.setFont(font);
        jLabel1.setFont(font);
        jLabel2.setFont(font);
        jLabel3.setFont(font);
        jLabel7.setFont(font);
        jSpinnerPortaCom.setFont(font);
        jLabel4.setFont(font);
        jSpinnerFont.setFont(font);
        jLabel5.setFont(font);
        jSpinnerFontMedium.setFont(font);
        jLabel6.setFont(font);
        jSpinnerFontBig.setFont(font);
        jButtonSalvaImpostazioni.setFont(fontMedium);
        jLabel8.setFont(font);
        jComboBoxClienti.setFont(font);
        jComboBoxProdotti.setFont(font);
        jComboBoxTare.setFont(font);
        jButtonSalvaImpostazioni.setFont(font);
        sviluppatore.setFont(font);
        jToggleButtonDemoBilancia.setFont(font);
        jTextFieldNomeStampante.setFont(font);
        
        // removed
        jListRemovedClienti.setFont(fontMedium);
        jButtonRemovedClienti.setFont(font);
        jListRemovedProdotti.setFont(fontMedium);
        jButton10.setFont(font);
        jListRemovedPesate.setFont(fontMedium);
        jButton11.setFont(font);
        jButton12.setFont(font);
        
        
        
        jLabelBilanciaCliente.setHorizontalAlignment(SwingConstants.CENTER);
        jLabeBilanciaProdotto.setHorizontalAlignment(SwingConstants.CENTER);
        jTextFieldBilanciaNetto.setHorizontalAlignment(SwingConstants.CENTER);
        jTextFieldBilanciaTara.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelBilanciaLordo.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelBilanciaTotaleTitle.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelBilanciaTotalePesaScontrino.setHorizontalAlignment(SwingConstants.CENTER);
        
        
    }
    private void updateLogo()
    {
        InterfaceUtility.setImage(jLabelPesateLogo, logo);
        InterfaceUtility.setImage(jLabelClientiLogo, logo);
        InterfaceUtility.setImage(jLabelProdottiLogo, logo);
        InterfaceUtility.setImage(jLabelTareLogo, logo);
        InterfaceUtility.setImage(jLabelTotaliLogo, logo);
        InterfaceUtility.setImage(jLabelBilanciaLogo, logo);
    }
    private void updatejTabbedPane()
    {
        if(bilanciaIsActive())
        {
            switch(jTabbedPane.getSelectedIndex())
            {
                case 0:
                    activeWeightRequest = 0;
                    dataBase.loadPesate(Utility.getYearMonth(GregorianCalendar.getInstance()));
                    updatejPanelBilancia();
                    break;
                case 1:
                    jXDatePickerPesate.setDate(GregorianCalendar.getInstance().getTime());
                    updatePesate();
                    break;
                case 2:
                    Calendar cal = GregorianCalendar.getInstance();
                    jXDatePickerTotali.setDate(cal.getTime());
                    updateTotali();
                    updateSettimana(jXDatePickerTotali);
                    break;
                case 3:
                    updatejPanelClienti();
                    break;
                case 4:
                    updatejPanelProdotti();
                    break;
                case 5:
                    updatejPanelTare();
                    activeWeightRequest = 5;
                    break;
                case 6:
                    updatejPanelOpzioni();
                    break;
                case 7:
                    updatejPanelRemoved();
                    break;
            }
        }
    }
    private void updatejPanelOpzioni()
    {
        jComboBoxClienti.setSelectedIndex(settings.orderClienti);
        jComboBoxProdotti.setSelectedIndex(settings.orderProdotti);
        jComboBoxTare.setSelectedIndex(settings.orderTare);
        updateSaveDirectory();
        updatejTextFieldNomeStampante();
        updatejSpinnerPortaCom();
        jSpinnerFont.setValue(settings.font);
        jSpinnerFontMedium.setValue(settings.fontMedium);
        jSpinnerFontBig.setValue(settings.fontBig);
    }
    private void setEnableNotBilancia(boolean enable)
    {
        
        jListBilanciaClienti.setEnabled(enable);
        jTabbedPane.setEnabledAt(1, enable);
        jTabbedPane.setEnabledAt(2, enable);
        jTabbedPane.setEnabledAt(3, enable);
        jTabbedPane.setEnabledAt(4, enable);
        jTabbedPane.setEnabledAt(5, enable);
        jTabbedPane.setEnabledAt(6, enable);
        jTabbedPane.setEnabledAt(7, enable);
        jButtoBilanciaClienti.setEnabled(enable);
    }
    private void updatejPanelTare()
    {
        jTextFieldTareSalva.setText("");
        jTextFieldTareModifica.setText("");
        updatejList(jListTare, dataBase.tare);
    }
    public void updateTara(float lettura)
    {
        log("updateTara");
        switch(activeWeightRequest)
        {
            case 0:
                this.tara = lettura;
                jTextFieldBilanciaTara.setText(String.format("%.3f", tara));
                break;
            default:
                log("Nessuna Richiesta tara "+tara);
        }
    }
    public void updatePeso(float lettura)
    {
        try
        {
            //peso = Float.parseFloat(s.replace(',', '.'));
            lordo = lettura;
            switch(activeWeightRequest)
            {
                case 0: 
                    if(prodotto != null && prodotto.unitOfMeasure == UnitOfMeasure.KILOGRAMMO)
                    {
                        netto = lordo - tara;
                        jTextFieldBilanciaNetto.setText(String.format("%.3f", netto));
                        jLabelBilanciaLordo.setText("Lordo: " + String.format("%.3f", lordo));
                        updateTotaleBilancia(netto);
                    }
                    else
                    {
                        updateTotaleBilancia(0);
                    }
                    break;
                case 2: break;
                case 3: break;
                case 4: break;
                case 5:
                    jLabelTare.setText(lordo+"");
                    jLabelTareModifica.setText(lordo+"");
                    break;
                case 6: //jLabelPannelloTara.setText("Peso: "+String.format("%.2f", peso)); break;
                default:
                    log("Nessuna Richiesta Peso "+lordo);
            }
        }
        catch(NumberFormatException c)
        {
            log("errore updatePeso");
        }
    }
    private void updatejComboBox(JComboBox jc, ActiveOrdering orders)
    {
        jc.removeAllItems();
        Order[] lista = orders.getType();
        if(lista.length > 0)
        {
            for(int i=0; i<lista.length; i++)
            {
                jc.addItem(lista[i].toString());
            }
        }
        jc.setSelectedIndex(orders.getIndex());
    }
    
    public void updateTotali()
    {
        updatejList(jListTotaliClienti, dataBase.clienti);
    }
    private void updateSettimana(JXDatePicker jXDatePicker)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(jXDatePicker.getDate());
        updateSettimana(cal);
    }
    private void updateSettimana(Calendar cal)
    {
        firstDay = null;
        lastDay = null;
        Calendar tempF = Calendar.getInstance();
        tempF.setTimeInMillis(cal.getTimeInMillis());
        Calendar tempL = Calendar.getInstance();
        tempL.setTimeInMillis(cal.getTimeInMillis());
        
        do
        {
            if(firstDay == null)
            {
                if(tempF.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
                {
                    firstDay = tempF;
                }
                else
                {
                    tempF.set(Calendar.DAY_OF_WEEK, tempF.get(Calendar.DAY_OF_WEEK)-1);
                }
            }
            if(lastDay == null)
            {
                if(tempL.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                {
                    lastDay = tempL;
                }
                else
                {
                    tempL.set(Calendar.DAY_OF_WEEK, tempL.get(Calendar.DAY_OF_WEEK)+1);
                }
            }
        }
        while(!(firstDay != null && lastDay != null));
        
        jButtonTotaliSalvaSettimana.setText(
                "<html><center>Salva settimana: <br>" 
                        + firstDay.get(Calendar.DAY_OF_MONTH) + "/" + (1 + firstDay.get(Calendar.MONTH)) + 
                        " - " + lastDay.get(Calendar.DAY_OF_MONTH) + "/" + (1 + lastDay.get(Calendar.MONTH)) + "</center></html>");
        jButtonTotaliSalvaSettimana.setHorizontalAlignment(SwingConstants.CENTER);

    }
    public void updatejTable()
    {
        int indiceCliente = jListTotaliClienti.getSelectedIndex();
        if(indiceCliente != -1)
        {
            long idCliente = dataBase.clienti.get(indiceCliente).getId();
            Calendar data = Calendar.getInstance();
            data.setTime(jXDatePickerTotali.getDate());

            tableProducts = dataBase.elaboraTotaliCliente(idCliente, data, jTableTotaliMensili);
        }
        else
        {
            jTableTotaliMensili.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][]
                {
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null}
                },
                new String []
                {
                    "Giorno", "Prodotto 1", "Prodotto 2"
                }
            ));
        }
    }
    public void updateTotaleBilancia(float netto)
    {
        totalePesate = 0;
        for(int i=0; i<bilanciaArrayPesate.size(); i++)
        {
            Pesata p = bilanciaArrayPesate.get(i);
            if(((Prodotto)dataBase.prodotti.get(p.idProdotto)).unitOfMeasure == UnitOfMeasure.KILOGRAMMO)
            {
                totalePesate += p.quantita;
            }
            else
            {
                totalePesate += 0;
            }
        }
        float temp = totalePesate;
        totalePesate += netto;
        jLabelBilanciaTotalePesaScontrino.setText(String.format("%.2f", totalePesate)+ " | " + String.format("%.2f", temp));
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
        boolean abilita = false;
        switch(o)
        {
            case ALPHABETIC:
                Collections.sort(dataBase.clienti, new AlphabeticalComparator(Behavior.INCREASING));
                break;
            case CARDINAL:
                Collections.sort(dataBase.clienti, new CardinalComparator(Behavior.INCREASING));
                abilita = true;
                break;
            case HOUR:
                Collections.sort(dataBase.clienti, new MinuteComparator(Behavior.INCREASING));
                break;
        }
        jButtonClientiSu.setEnabled(abilita);
        jButtonClientiGiu.setEnabled(abilita);
        jButtonClientiOrdine.setText(o.toString());
        jButtoBilanciaClienti.setText(o.toString());
        jButtonPesateOrdinamentoClienti.setText(o.toString());
        jButtonTotaliOrdinamento.setText(o.toString());
    }
    private void orderTare()
    {
        Order o = oTare.get();
        boolean abilita = false;
        switch(o)
        {
            case ALPHABETIC:
                Collections.sort(dataBase.tare, new AlphabeticalComparator(Behavior.INCREASING));
                break;
            case CARDINAL:
                Collections.sort(dataBase.tare, new CardinalComparator(Behavior.INCREASING));
                abilita = true;
                break;
        }
        jButtonTareSu.setEnabled(abilita);
        jButtonTareGiu.setEnabled(abilita);
        jButtonTareOrdine.setText(o.toString());
    }
    private void orderProdotti()
    {
        Order o = oProdotti.get();
        boolean abilita = false;
        switch(o)
        {
            case ALPHABETIC:
                Collections.sort(dataBase.prodotti, new AlphabeticalComparator(Behavior.INCREASING));
                break;
            case CARDINAL:
                Collections.sort(dataBase.prodotti, new CardinalComparator(Behavior.INCREASING));
                abilita = true;
                break;
            case FREQUENCY:
                updateClientFrequency();
                break;
        }
        jButtonProdottiSu.setEnabled(abilita);
        jButtonProdottiGiu.setEnabled(abilita);
        jButtonBilanciaProdotti.setText(o.toString());
        jButtonProdottiOrdine.setText(o.toString());
        //jButtonTotaliOrdinamento.setText(o.toString());
    }
    private void swapCardinalClienti(Behavior behavior)
    {
        int indice = jListClienti.getSelectedIndex();
        if(indice != -1)
        {
            dataBase.clienti.swapCardinal(
                    ((OrdinableObject)jListClienti.getSelectedValue()).getId(),
                    behavior
            );
            oClienti.set(Order.CARDINAL);
            orderClienti();
            dataBase.saveClienti();
            updatejList(jListClienti, dataBase.clienti);
            
            if(behavior == Behavior.DECREASING && indice >= 1)
            {
                indice = indice - 1;
            }
            if(behavior == Behavior.INCREASING && indice < jListClienti.getModel().getSize()-1)
            {
                indice = indice + 1;
            }
            jListClienti.setSelectedIndex(indice);
        }
    }
    private void swapCardinalProdotti(Behavior behavior)
    {
        int indice = jListProdotti.getSelectedIndex();
        if(indice != -1)
        {
            dataBase.prodotti.swapCardinal(
                    ((OrdinableObject)jListProdotti.getSelectedValue()).getId(),
                    behavior
            );
            oProdotti.set(Order.CARDINAL);
            orderProdotti();
            dataBase.saveProdotti();
            updatejList(jListProdotti, dataBase.prodotti);
            
            if(behavior == Behavior.DECREASING && indice >= 1)
            {
                indice = indice - 1;
            }
            if(behavior == Behavior.INCREASING && indice < jListProdotti.getModel().getSize()-1)
            {
                indice = indice + 1;
            }
            jListProdotti.setSelectedIndex(indice);
        }
    }
    private void swapCardinalTare(Behavior behavior)
    {
        int indice = jListTare.getSelectedIndex();
        if(indice != -1)
        {
            dataBase.tare.swapCardinal(
                    ((OrdinableObject)jListTare.getSelectedValue()).getId(),
                    behavior
            );
            oTare.set(Order.CARDINAL);
            orderTare();
            dataBase.saveTare();
            updatejList(jListTare, dataBase.tare);
            
            if(behavior == Behavior.DECREASING && indice >= 1)
            {
                indice = indice - 1;
            }
            if(behavior == Behavior.INCREASING && indice < jListTare.getModel().getSize()-1)
            {
                indice = indice + 1;
            }
            jListTare.setSelectedIndex(indice);
        }
    }
    private void updateClientFrequency()
    {
        if(jListBilanciaClienti.getSelectedIndex() != -1)
        {
            if(oProdotti.get() == Order.FREQUENCY)
            {
                dataBase.updateClientFrequency(jListBilanciaClienti.getSelectedValue().getId());
            }
        }
    }
    private void insTastierino(int t)
    {
        log("insTastierino");
        if(jTextFieldBilanciaNetto.getSelectedText() != null)
        {
            netto = 0;
        }
        if(prodotto != null && prodotto.unitOfMeasure == UnitOfMeasure.QUANTITY)
        {
            netto = netto*10+t;
            jTextFieldBilanciaNetto.setText(String.format("%.0f", netto));
        }
    }
    private void elaboraStampa(PesateArray pesate)
    {
        log("elaboraStampa");
        ArrayList<String> listaProdotti = new ArrayList<>();
        
        float totalePeso = 0;
        int totaleNumero = 0;
        int sommePeso = 0;
        int sommeNumero = 0;
        long idCl = -1;
        Calendar data = GregorianCalendar.getInstance();
        for(int i=0; i<pesate.size(); i++)
        {
            Pesata p = (Pesata)pesate.get(i);
            data = p.time;
            idCl = p.idCliente;
            Prodotto t = (Prodotto)dataBase.prodotti.get(p.idProdotto);
            String numero = "";
            if(t.unitOfMeasure == UnitOfMeasure.KILOGRAMMO)
            {
                totalePeso += p.quantita;
                sommePeso++;
                numero = String.format("%.3f", p.quantita);
            }
            else
            {
                totaleNumero += p.quantita;
                sommeNumero++;
                numero = (int)p.quantita+"";
            }
            listaProdotti.add(numero + " " + t.unitOfMeasure + " " + t.getName());
        }
        String totPeso = "";
        String totNume = "";
        if(sommePeso > 1)
        {
            totPeso = String.format("%.3f", totalePeso) + " " + UnitOfMeasure.KILOGRAMMO + " TOTALE";
        }
        if(sommeNumero > 1)
        {
            totNume = totaleNumero + " " + UnitOfMeasure.QUANTITY + " TOTALE";
        }
        
        int mese = 1+(int)data.get(Calendar.MONTH);
        String stringData = data.get(Calendar.DATE)+"/"+mese+"/"+data.get(Calendar.YEAR);
        jTextAreaOpzioni.setText(
                Stampante.stampa(
                        new Etichetta(
                                stringData,
                                ((Cliente)dataBase.clienti.get(idCl)).getName(),
                                listaProdotti,
                                totPeso,
                                totNume
                        )
                )
        );
        
    }
    private void updatejListBilanciaScontrino()
    {
        
        ArrayList<IdName> list = new ArrayList<>();
        
        for(int i=0; i<bilanciaArrayPesate.size(); i++)
        {
            Pesata pe = bilanciaArrayPesate.get(i);
            String quantita = "";
            Prodotto pr = (Prodotto)dataBase.prodotti.get(pe.idProdotto);
            if(pr.unitOfMeasure == UnitOfMeasure.KILOGRAMMO)
            {
                quantita = String.format("%.3f", pe.quantita);
            }
            else
            {
                quantita = String.format("%.0f", pe.quantita);
            }
            int r = pe.time.get(Calendar.MONTH) + 1;
            list.add(new IdName(
                    pe.getId(),
                    quantita + " " + pr.unitOfMeasure.toString() + " " + pr.getName()
                )
            );
        }
        jListBilanciaScontrino.setModel(new javax.swing.AbstractListModel()
        {
            ArrayList<IdName> in = list;
            public int getSize() { return in.size(); }
            public Object getElementAt(int i) { return in.get(i); }
        });
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
    private void updatejPanelProdotti()
    {
        jTextFieldProdottiSalva.setText("");
        jTextFieldProdottiModifica.setText("");
        updatejList(jListProdotti, dataBase.prodotti);
    }
    public void updatePesate()
    {
        updatejList(jListPesateClienti, dataBase.clienti);
        updatejList(jListPesateCambiaCliente, dataBase.clienti);
        updatejListPesate();
    }
    public void updatejListPesate()
    {
        log("aggiornaListaPesate");
        int indice = jListPesateClienti.getSelectedIndex();
        ArrayList<IdName> list = new ArrayList<>();
        if(indice != -1)
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(jXDatePickerPesate.getDate());
            

            ArrayList<Long> idPesateClienteMese = dataBase.getPesateClienteMese(
                    jListPesateClienti.getSelectedValue().getId(),
                    cal,
                    Behavior.INCREASING);
            
            for(int i=0; i<idPesateClienteMese.size(); i++)
            {
                Pesata pe = dataBase.pesate.get(idPesateClienteMese.get(i));
                String quantita = "";
                Prodotto pr = (Prodotto)dataBase.getAllProducts().get(pe.idProdotto);
                if(pr.unitOfMeasure == UnitOfMeasure.KILOGRAMMO)
                {
                    quantita = String.format("%.3f", pe.quantita);
                }
                else
                {
                    quantita = String.format("%.0f", pe.quantita);
                }
                int r = pe.time.get(Calendar.MONTH) + 1;
                list.add(new IdName(
                        pe.getId(),
                        pe.time.get(Calendar.DAY_OF_MONTH) + "/" + r
                        + "  " + quantita + " " + pr.unitOfMeasure.toString() + " " + pr.getName()
                    )
                );
            }
            jListPesate.setModel(new javax.swing.AbstractListModel()
            {
                ArrayList<IdName> in = list;
                public int getSize() { return in.size(); }
                public Object getElementAt(int i) { return in.get(i); }
            });
        }
        else
        {
            jListPesate.setModel(new javax.swing.AbstractListModel()
            {
                ArrayList<IdName> in = new ArrayList<>();
                public int getSize() { return in.size(); }
                public Object getElementAt(int i) { return in.get(i); }
            });
        }
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
        updatejList(jListRemovedClienti, dataBase.removedClienti);
        updatejList(jListRemovedProdotti, dataBase.removedProdotti);
        updatejListRemovedPesate();
    }
    private void updatejListRemovedPesate()
    {
        ArrayList<IdName> list = new ArrayList<>();
        for(Pesata p: dataBase.removedPesate)
        {
            String quantita = "";
            Prodotto pr = (Prodotto)dataBase.prodotti.get(p.idProdotto);
            OrdinableObject c = dataBase.clienti.get(p.idCliente);
            
            if(pr.unitOfMeasure == UnitOfMeasure.KILOGRAMMO)
            {
                quantita = String.format("%.3f", p.quantita);
            }
            else
            {
                quantita = String.format("%.0f", p.quantita);
            }
            list.add(new IdName(
                    p.getId(),
                    Utility.getMax1Dot(30, c.getName()) + ": "+ Utility.getCalendarString(p.time)
                    + "  " + quantita + " " + pr.unitOfMeasure.toString() + " " + pr.getName()
                )
            );
        }
        jListRemovedPesate.setModel(new javax.swing.AbstractListModel()
        {
            ArrayList<IdName> in = list;
            public int getSize() { return in.size(); }
            public Object getElementAt(int i) { return in.get(i); }
        });
    }
    private boolean bilanciaIsActive()
    {
        return jListBilanciaClienti.isEnabled();
    }
    public boolean getSimBilancia()
    {
        return jToggleButtonDemoBilancia.isSelected();
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
        jScrollPaneBilanciaProdotti = new javax.swing.JScrollPane();
        jListBilanciaProdotti = new javax.swing.JList<>();
        jPanel10 = new javax.swing.JPanel();
        jButtoBilanciaClienti = new javax.swing.JButton();
        jScrollPaneBilanciaClienti = new javax.swing.JScrollPane();
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
        jScrollPaneBilanciaScontrino = new javax.swing.JScrollPane();
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
        jScrollPanePesate = new javax.swing.JScrollPane();
        jListPesate = new javax.swing.JList<>();
        jLabelPesateStats = new javax.swing.JLabel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel23 = new javax.swing.JPanel();
        jScrollPanePesateClienti = new javax.swing.JScrollPane();
        jListPesateClienti = new javax.swing.JList<>();
        jButtonPesateOrdinamentoClienti = new javax.swing.JButton();
        jPanel24 = new javax.swing.JPanel();
        jScrollPanePesateCambiaClienti = new javax.swing.JScrollPane();
        jListPesateCambiaCliente = new javax.swing.JList<>();
        jButtonPesateCambiaCliente = new javax.swing.JButton();
        jPanelTotali = new javax.swing.JPanel();
        jSplitPane3 = new javax.swing.JSplitPane();
        jPanel22 = new javax.swing.JPanel();
        jScrollPaneTotali = new javax.swing.JScrollPane();
        jTableTotaliMensili = new javax.swing.JTable();
        jButtonAddPesata = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        jScrollPaneTotaliClienti = new javax.swing.JScrollPane();
        jListTotaliClienti = new javax.swing.JList<>();
        jButtonTotaliOrdinamento = new javax.swing.JButton();
        jXDatePickerTotali = new org.jdesktop.swingx.JXDatePicker();
        jButtonTotaliSalvaSettimana = new javax.swing.JButton();
        jButtonSaveTotali = new javax.swing.JButton();
        jLabelTotaliLogo = new javax.swing.JLabel();
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
        jButtonClientiSu = new javax.swing.JButton();
        jButtonClientiGiu = new javax.swing.JButton();
        jLabelClientiStats = new javax.swing.JLabel();
        jButtonClientiElimina = new javax.swing.JButton();
        jScrollPaneClienti = new javax.swing.JScrollPane();
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
        jLabelProdottiStats = new javax.swing.JLabel();
        jButtonProdottiElimina = new javax.swing.JButton();
        jScrollPaneProdotti = new javax.swing.JScrollPane();
        jListProdotti = new javax.swing.JList<>();
        jPanelTare = new javax.swing.JPanel();
        jPanelTareInserimento = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabelClientiModifica2 = new javax.swing.JLabel();
        jTextFieldTareModifica = new javax.swing.JTextField();
        jButtonTareModifica = new javax.swing.JButton();
        jLabelTareModifica = new javax.swing.JLabel();
        jLabelTareLogo = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabelClientiSalva2 = new javax.swing.JLabel();
        jTextFieldTareSalva = new javax.swing.JTextField();
        jButtonTareSalva = new javax.swing.JButton();
        jLabelTare = new javax.swing.JLabel();
        jPanelClientiLista2 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jButtonTareOrdine = new javax.swing.JButton();
        jButtonTareSu = new javax.swing.JButton();
        jButtonTareGiu = new javax.swing.JButton();
        jLabelTareStats = new javax.swing.JLabel();
        jButtonTareElimina = new javax.swing.JButton();
        jScrollPaneTare = new javax.swing.JScrollPane();
        jListTare = new javax.swing.JList<>();
        jPanelOptions = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jComboBoxClienti = new javax.swing.JComboBox<>();
        jComboBoxProdotti = new javax.swing.JComboBox<>();
        jComboBoxTare = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jToggleButtonDemoBilancia = new javax.swing.JToggleButton();
        jPanel7 = new javax.swing.JPanel();
        jSpinnerFont = new javax.swing.JSpinner();
        jSpinnerFontMedium = new javax.swing.JSpinner();
        jSpinnerFontBig = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        sviluppatore = new javax.swing.JToggleButton();
        jPanel19 = new javax.swing.JPanel();
        jScrollPaneOpzioni = new javax.swing.JScrollPane();
        jTextAreaOpzioni = new javax.swing.JTextArea();
        jLabelSaveDirectory = new javax.swing.JLabel();
        folderChooser1 = new Interface.FolderChooser();
        jPanel25 = new javax.swing.JPanel();
        jButtonSalvaImpostazioni = new javax.swing.JButton();
        jPanel26 = new javax.swing.JPanel();
        jTextFieldNomeStampante = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jSpinnerPortaCom = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        jPanelRemoved = new javax.swing.JPanel();
        jSplitPane4 = new javax.swing.JSplitPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPaneRemovedClienti = new javax.swing.JScrollPane();
        jListRemovedClienti = new javax.swing.JList<>();
        jButtonRemovedClienti = new javax.swing.JButton();
        jSplitPane5 = new javax.swing.JSplitPane();
        jPanel5 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jScrollPaneRemovedProdotti = new javax.swing.JScrollPane();
        jListRemovedProdotti = new javax.swing.JList<>();
        jPanel6 = new javax.swing.JPanel();
        jButton11 = new javax.swing.JButton();
        jScrollPaneRemovedPesate = new javax.swing.JScrollPane();
        jListRemovedPesate = new javax.swing.JList<>();
        jButton12 = new javax.swing.JButton();

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

        jTabbedPane.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        jTabbedPane.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                jTabbedPaneStateChanged(evt);
            }
        });
        jTabbedPane.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseReleased(java.awt.event.MouseEvent evt)
            {
                jTabbedPaneMouseReleased(evt);
            }
        });

        jSplitPaneBilancia.setDividerSize(10);
        jSplitPaneBilancia.setResizeWeight(0.6);
        jSplitPaneBilancia.setMinimumSize(new java.awt.Dimension(212, 550));

        jButtonBilanciaProdotti.setText("order");
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
        jScrollPaneBilanciaProdotti.setViewportView(jListBilanciaProdotti);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButtonBilanciaProdotti, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPaneBilanciaProdotti, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jButtonBilanciaProdotti)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneBilanciaProdotti, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE))
        );

        jSplitPaneBilancia.setRightComponent(jPanel9);

        jButtoBilanciaClienti.setText("order");
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
        jScrollPaneBilanciaClienti.setViewportView(jListBilanciaClienti);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButtoBilanciaClienti, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPaneBilanciaClienti, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jButtoBilanciaClienti)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneBilanciaClienti, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE))
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
        jPanel8.setMaximumSize(new java.awt.Dimension(400, 550));
        jPanel8.setPreferredSize(new java.awt.Dimension(330, 550));

        jLabelBilanciaTotaleTitle.setText("Totale in pesa | Totale scontrino");

        jLabelBilanciaTotalePesaScontrino.setText("0,00");

        jListBilanciaScontrino.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jListBilanciaScontrinoMouseClicked(evt);
            }
        });
        jScrollPaneBilanciaScontrino.setViewportView(jListBilanciaScontrino);

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
                    .addComponent(jLabelBilanciaTotaleTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(jScrollPaneBilanciaScontrino)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jButtonBilanciaStampa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonBilanciaElimina, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabelBilanciaTotalePesaScontrino, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(jScrollPaneBilanciaScontrino)
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
                .addComponent(jSplitPaneBilancia, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelBilancia1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanelBilanciaLayout.setVerticalGroup(
            jPanelBilanciaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPaneBilancia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelBilancia1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
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

        jListPesate.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                jListPesateValueChanged(evt);
            }
        });
        jScrollPanePesate.setViewportView(jListPesate);

        jLabelPesateStats.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButtonPesateModifica)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelPesateStats, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonPesateElimina))
                    .addComponent(jScrollPanePesate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jScrollPanePesate, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButtonPesateModifica)
                                .addComponent(jButtonPesateElimina))
                            .addComponent(jLabelPesateStats, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(jPanel18);

        jSplitPane2.setDividerSize(10);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setResizeWeight(0.5);

        jListPesateClienti.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                jListPesateClientiValueChanged(evt);
            }
        });
        jScrollPanePesateClienti.setViewportView(jListPesateClienti);

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
            .addComponent(jScrollPanePesateClienti)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addComponent(jButtonPesateOrdinamentoClienti)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPanePesateClienti, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE))
        );

        jSplitPane2.setTopComponent(jPanel23);

        jScrollPanePesateCambiaClienti.setViewportView(jListPesateCambiaCliente);

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
            .addComponent(jScrollPanePesateCambiaClienti)
            .addComponent(jButtonPesateCambiaCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(jScrollPanePesateCambiaClienti, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
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

        jSplitPane3.setResizeWeight(0.2);
        jSplitPane3.setToolTipText("");

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
        jTableTotaliMensili.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jTableTotaliMensiliMouseClicked(evt);
            }
        });
        jScrollPaneTotali.setViewportView(jTableTotaliMensili);

        jButtonAddPesata.setText("Aggiungi pesata");
        jButtonAddPesata.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonAddPesataMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneTotali, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
            .addComponent(jButtonAddPesata, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(jScrollPaneTotali, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonAddPesata))
        );

        jSplitPane3.setRightComponent(jPanel22);

        jListTotaliClienti.setFont(font);
        jListTotaliClienti.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                jListTotaliClientiValueChanged(evt);
            }
        });
        jScrollPaneTotaliClienti.setViewportView(jListTotaliClienti);

        jButtonTotaliOrdinamento.setText("jButton10");
        jButtonTotaliOrdinamento.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonTotaliOrdinamentoMouseClicked(evt);
            }
        });

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

        jButtonTotaliSalvaSettimana.setText("Salva Sett");
        jButtonTotaliSalvaSettimana.setFont(font);
        jButtonTotaliSalvaSettimana.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonTotaliSalvaSettimanaMouseClicked(evt);
            }
        });

        jButtonSaveTotali.setText("Salva Tutti");
        jButtonSaveTotali.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonSaveTotaliMouseClicked(evt);
            }
        });

        jLabelTotaliLogo.setHorizontalAlignment(SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneTotaliClienti, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
            .addComponent(jLabelTotaliLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonTotaliSalvaSettimana, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonTotaliOrdinamento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jXDatePickerTotali, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonSaveTotali, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jXDatePickerTotali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonTotaliOrdinamento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneTotaliClienti, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonTotaliSalvaSettimana)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTotaliLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSaveTotali))
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

        jButtonClientiSu.setText("Su");
        jButtonClientiSu.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonClientiSuMouseClicked(evt);
            }
        });

        jButtonClientiGiu.setText("Giù");
        jButtonClientiGiu.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonClientiGiuMouseClicked(evt);
            }
        });

        jLabelClientiStats.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonClientiOrdine, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelClientiStats, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonClientiSu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonClientiGiu)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonClientiOrdine)
                            .addComponent(jButtonClientiSu)
                            .addComponent(jButtonClientiGiu))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabelClientiStats, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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
        jScrollPaneClienti.setViewportView(jListClienti);

        javax.swing.GroupLayout jPanelClientiListaLayout = new javax.swing.GroupLayout(jPanelClientiLista);
        jPanelClientiLista.setLayout(jPanelClientiListaLayout);
        jPanelClientiListaLayout.setHorizontalGroup(
            jPanelClientiListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButtonClientiElimina, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPaneClienti, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
        );
        jPanelClientiListaLayout.setVerticalGroup(
            jPanelClientiListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelClientiListaLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneClienti, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
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
            .addComponent(jPanelClientiInserimento, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
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
        jToggleButtonProdottiModifyUnit.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                jToggleButtonProdottiModifyUnitStateChanged(evt);
            }
        });

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
        jToggleButtonProdottiSaveUnit.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                jToggleButtonProdottiSaveUnitStateChanged(evt);
            }
        });

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
        jButtonProdottiSu.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonProdottiSuMouseClicked(evt);
            }
        });

        jButtonProdottiGiu.setText("Giù");
        jButtonProdottiGiu.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonProdottiGiuMouseClicked(evt);
            }
        });

        jLabelProdottiStats.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonProdottiOrdine, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelProdottiStats, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonProdottiSu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonProdottiGiu)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonProdottiOrdine)
                            .addComponent(jButtonProdottiSu)
                            .addComponent(jButtonProdottiGiu))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabelProdottiStats, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jButtonProdottiElimina.setText("Elimina");
        jButtonProdottiElimina.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonProdottiEliminaMouseClicked(evt);
            }
        });

        jListProdotti.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                jListProdottiValueChanged(evt);
            }
        });
        jScrollPaneProdotti.setViewportView(jListProdotti);

        javax.swing.GroupLayout jPanelProdottiListaLayout = new javax.swing.GroupLayout(jPanelProdottiLista);
        jPanelProdottiLista.setLayout(jPanelProdottiListaLayout);
        jPanelProdottiListaLayout.setHorizontalGroup(
            jPanelProdottiListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButtonProdottiElimina, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPaneProdotti, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
        );
        jPanelProdottiListaLayout.setVerticalGroup(
            jPanelProdottiListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProdottiListaLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneProdotti, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
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
            .addComponent(jPanelProdottiInserimento, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
        );

        jTabbedPane.addTab("Prodotti", jPanelProdotti);

        jPanelTareInserimento.setPreferredSize(new java.awt.Dimension(330, 550));

        jLabelClientiModifica2.setText("Modifica la Tara");

        jTextFieldTareModifica.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                jTextFieldTareModificaKeyReleased(evt);
            }
        });

        jButtonTareModifica.setText("Modifica");
        jButtonTareModifica.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonTareModificaMouseClicked(evt);
            }
        });

        jLabelTareModifica.setText("0");
        jLabelTareModifica.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jLabelTareModificaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldTareModifica, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonTareModifica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jLabelClientiModifica2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabelTareModifica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelClientiModifica2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldTareModifica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelTareModifica)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonTareModifica)
                .addContainerGap())
        );

        jLabelClientiSalva2.setText("Inserimento di una nuova Tara");

        jTextFieldTareSalva.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                jTextFieldTareSalvaKeyReleased(evt);
            }
        });

        jButtonTareSalva.setText("Salva");
        jButtonTareSalva.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonTareSalvaMouseClicked(evt);
            }
        });

        jLabelTare.setText("0");
        jLabelTare.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jLabelTareMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldTareSalva, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonTareSalva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jLabelClientiSalva2)
                        .addGap(0, 163, Short.MAX_VALUE))
                    .addComponent(jLabelTare, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelClientiSalva2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldTareSalva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTare)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonTareSalva)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelTareInserimentoLayout = new javax.swing.GroupLayout(jPanelTareInserimento);
        jPanelTareInserimento.setLayout(jPanelTareInserimentoLayout);
        jPanelTareInserimentoLayout.setHorizontalGroup(
            jPanelTareInserimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabelTareLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelTareInserimentoLayout.setVerticalGroup(
            jPanelTareInserimentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTareInserimentoLayout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTareLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jButtonTareOrdine.setText("Ordine");
        jButtonTareOrdine.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonTareOrdineMouseClicked(evt);
            }
        });

        jButtonTareSu.setText("Su");
        jButtonTareSu.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonTareSuMouseClicked(evt);
            }
        });

        jButtonTareGiu.setText("Giù");
        jButtonTareGiu.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonTareGiuMouseClicked(evt);
            }
        });

        jLabelTareStats.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonTareOrdine, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTareStats, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonTareSu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonTareGiu)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonTareOrdine)
                            .addComponent(jButtonTareSu)
                            .addComponent(jButtonTareGiu))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabelTareStats, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jButtonTareElimina.setText("Elimina");
        jButtonTareElimina.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonTareEliminaMouseClicked(evt);
            }
        });

        jListTare.addListSelectionListener(new javax.swing.event.ListSelectionListener()
        {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt)
            {
                jListTareValueChanged(evt);
            }
        });
        jScrollPaneTare.setViewportView(jListTare);

        javax.swing.GroupLayout jPanelClientiLista2Layout = new javax.swing.GroupLayout(jPanelClientiLista2);
        jPanelClientiLista2.setLayout(jPanelClientiLista2Layout);
        jPanelClientiLista2Layout.setHorizontalGroup(
            jPanelClientiLista2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButtonTareElimina, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPaneTare, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
        );
        jPanelClientiLista2Layout.setVerticalGroup(
            jPanelClientiLista2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelClientiLista2Layout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneTare, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonTareElimina))
        );

        javax.swing.GroupLayout jPanelTareLayout = new javax.swing.GroupLayout(jPanelTare);
        jPanelTare.setLayout(jPanelTareLayout);
        jPanelTareLayout.setHorizontalGroup(
            jPanelTareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTareLayout.createSequentialGroup()
                .addComponent(jPanelTareInserimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelClientiLista2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelTareLayout.setVerticalGroup(
            jPanelTareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelClientiLista2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanelTareInserimento, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
        );

        jTabbedPane.addTab("Tare", jPanelTare);

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ordinamento all'avvio", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 20))); // NOI18N

        jComboBoxClienti.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBoxProdotti.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBoxTare.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Clienti");

        jLabel2.setText("Prodotti");

        jLabel3.setText("Tare");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxTare, 0, 423, Short.MAX_VALUE)
                    .addComponent(jComboBoxClienti, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBoxProdotti, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxClienti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxProdotti, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxTare, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jToggleButtonDemoBilancia.setText("Demo Bilancia");

        jSpinnerFont.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N

        jSpinnerFontMedium.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N

        jSpinnerFontBig.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N

        jLabel4.setText("Font Generale");

        jLabel5.setText("Font Liste");

        jLabel6.setText("Font Speciali");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpinnerFont, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSpinnerFontMedium, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinnerFontBig, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinnerFont, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinnerFontMedium, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinnerFontBig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sviluppatore.setText("Sviluppatore");
        sviluppatore.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                sviluppatoreStateChanged(evt);
            }
        });

        jTextAreaOpzioni.setColumns(20);
        jTextAreaOpzioni.setRows(5);
        jTextAreaOpzioni.setLineWrap(true);
        jTextAreaOpzioni.setAlignmentX(TextArea.CENTER_ALIGNMENT);
        jScrollPaneOpzioni.setViewportView(jTextAreaOpzioni);

        jLabelSaveDirectory.setText("Directory dei salvataggi:");

        folderChooser1.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(folderChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jLabelSaveDirectory)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPaneOpzioni, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneOpzioni)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelSaveDirectory)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(folderChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jButtonSalvaImpostazioni.setBackground(new java.awt.Color(255, 255, 102));
        jButtonSalvaImpostazioni.setForeground(new java.awt.Color(255, 51, 51));
        jButtonSalvaImpostazioni.setText("SALVA IMPOSTAZIONI");
        jButtonSalvaImpostazioni.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButtonSalvaImpostazioniMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonSalvaImpostazioni, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonSalvaImpostazioni, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTextFieldNomeStampante.setText("jTextField1");

        jLabel7.setText("COM");

        jSpinnerPortaCom.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                jSpinnerPortaComStateChanged(evt);
            }
        });

        jLabel8.setText("Nome della stampante");

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldNomeStampante, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addComponent(jSpinnerPortaCom))
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNomeStampante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinnerPortaCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanelOptionsLayout = new javax.swing.GroupLayout(jPanelOptions);
        jPanelOptions.setLayout(jPanelOptionsLayout);
        jPanelOptionsLayout.setHorizontalGroup(
            jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                        .addComponent(sviluppatore, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jToggleButtonDemoBilancia, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelOptionsLayout.setVerticalGroup(
            jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelOptionsLayout.createSequentialGroup()
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jToggleButtonDemoBilancia)
                            .addComponent(sviluppatore)))
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane.addTab("Opzioni", jPanelOptions);

        jSplitPane4.setDividerSize(15);
        jSplitPane4.setResizeWeight(0.33);

        jListRemovedClienti.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        jListRemovedClienti.setModel(new javax.swing.AbstractListModel<OrdinableObject>()
            {
                ArrayList<OrdinableObject> oa = dataBase.removedClienti;
                public int getSize() { return oa.size(); }
                public OrdinableObject getElementAt(int i) { return oa.get(i); }

            });
            jScrollPaneRemovedClienti.setViewportView(jListRemovedClienti);

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
                .addComponent(jScrollPaneRemovedClienti, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addComponent(jButtonRemovedClienti, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                    .addComponent(jScrollPaneRemovedClienti, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jButtonRemovedClienti))
            );

            jSplitPane4.setLeftComponent(jPanel4);

            jSplitPane5.setDividerSize(15);
            jSplitPane5.setResizeWeight(0.66);

            jButton10.setText("Ripristina Prodotto");
            jButton10.addMouseListener(new java.awt.event.MouseAdapter()
            {
                public void mouseClicked(java.awt.event.MouseEvent evt)
                {
                    jButton10MouseClicked(evt);
                }
            });

            jListRemovedProdotti.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
            jScrollPaneRemovedProdotti.setViewportView(jListRemovedProdotti);

            javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
            jPanel5.setLayout(jPanel5Layout);
            jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPaneRemovedProdotti, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            );
            jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                    .addComponent(jScrollPaneRemovedProdotti, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jButton10))
            );

            jSplitPane5.setLeftComponent(jPanel5);

            jButton11.setText("Ripristina Pesata");
            jButton11.addMouseListener(new java.awt.event.MouseAdapter()
            {
                public void mouseClicked(java.awt.event.MouseEvent evt)
                {
                    jButton11MouseClicked(evt);
                }
            });

            jListRemovedPesate.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
            jScrollPaneRemovedPesate.setViewportView(jListRemovedPesate);

            jButton12.setText("ELIMINA PESATE");
            jButton12.addMouseListener(new java.awt.event.MouseAdapter()
            {
                public void mouseClicked(java.awt.event.MouseEvent evt)
                {
                    jButton12MouseClicked(evt);
                }
            });

            javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
            jPanel6.setLayout(jPanel6Layout);
            jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPaneRemovedPesate)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jButton12))
            );
            jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                    .addComponent(jScrollPaneRemovedPesate, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton11)
                        .addComponent(jButton12)))
            );

            jSplitPane5.setRightComponent(jPanel6);

            jSplitPane4.setRightComponent(jSplitPane5);

            javax.swing.GroupLayout jPanelRemovedLayout = new javax.swing.GroupLayout(jPanelRemoved);
            jPanelRemoved.setLayout(jPanelRemovedLayout);
            jPanelRemovedLayout.setHorizontalGroup(
                jPanelRemovedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelRemovedLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jSplitPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
                    .addContainerGap())
            );
            jPanelRemovedLayout.setVerticalGroup(
                jPanelRemovedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanelRemovedLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jSplitPane4)
                    .addContainerGap())
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
            if(dataBase.clienti.exist(jTextFieldClientiSalva.getText()) == null)
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
                
                orderClienti();
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
                if(sviluppatore.isSelected())
                {
                    jLabelClientiStats.setText(
                            cl.getId() + " " +
                            cl.getCardinal() + " " +
                            cl.frequentMinute.getAverageMinute() + " " +
                            cl.frequentMinute.getWeight());
                }
            }
        }
    }//GEN-LAST:event_jListClientiValueChanged

    private void jButtonClientiModificaMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonClientiModificaMouseClicked
    {//GEN-HEADEREND:event_jButtonClientiModificaMouseClicked
        if(jListClienti.getSelectedIndex() != -1)
        {
            if(!jTextFieldClientiModifica.getText().isEmpty())
            {
                if(dataBase.clienti.exist(jTextFieldClientiModifica.getText()) == null)
                {
                    OrdinableObject c = jListClienti.getSelectedValue();
                    Cliente cl = (Cliente)dataBase.clienti.get(c.getId());
                    cl.setName(jTextFieldClientiModifica.getText());
                    dataBase.saveClienti();
                    updatejList(jListClienti, dataBase.clienti);
                    jTextFieldClientiModifica.setText("");
                }
                else
                {
                    infoBox("ATTENZIONE", "Cliente già esistente", Color.red);
                }
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
                orderClienti();
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
        updateTara(lordo);        //updateTara(lordo);
    }//GEN-LAST:event_jButtonBilanciaTaraMouseClicked

    private void jTextFieldBilanciaNettoMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jTextFieldBilanciaNettoMouseClicked
    {//GEN-HEADEREND:event_jTextFieldBilanciaNettoMouseClicked
        jTextFieldBilanciaNetto.selectAll();
    }//GEN-LAST:event_jTextFieldBilanciaNettoMouseClicked

    private void jTextFieldBilanciaNettoKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTextFieldBilanciaNettoKeyReleased
    {//GEN-HEADEREND:event_jTextFieldBilanciaNettoKeyReleased
        try
        {
            jTextFieldBilanciaNetto.setText(Utility.safeNumber(jTextFieldBilanciaNetto.getText()));
            float valore = Float.parseFloat(jTextFieldBilanciaNetto.getText());
            netto = valore;
        }
        catch(NumberFormatException e)
        {
            netto = 0;
            log(e.toString());
        }
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
        if(jListBilanciaClienti.getSelectedIndex() != -1 && jListBilanciaProdotti.getSelectedIndex() != -1 && netto > 0)
        {
            if(bilanciaArrayPesate == null)
            {
                bilanciaArrayPesate = new PesateArray(0);
            }

            bilanciaArrayPesate.add(new Pesata(
                bilanciaArrayPesate.getNewId(),
                jListBilanciaClienti.getSelectedValue().getId(),
                jListBilanciaProdotti.getSelectedValue().getId(),
                netto,
                GregorianCalendar.getInstance()));
            
            setEnableNotBilancia(false);
            
            netto = 0;
            jTextFieldBilanciaNetto.setText("");
            updatejListBilanciaScontrino();
        }
    }//GEN-LAST:event_jButtonBilanciaAggiungiMouseClicked

    
    private void jTextFieldBilanciaTaraMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jTextFieldBilanciaTaraMouseClicked
    {//GEN-HEADEREND:event_jTextFieldBilanciaTaraMouseClicked
        InterfacciaTara interfacciaTara = new InterfacciaTara(font, fontBig);
        oTare.set(Order.CARDINAL);
        orderTare();
        this.setEnabled(false);
        interfacciaTara.setVisible(true);
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
        int indice = jListBilanciaScontrino.getSelectedIndex();
        if(indice != -1)
        {
            bilanciaArrayPesate.remove(jListBilanciaScontrino.getSelectedValue().id);
            updatejListBilanciaScontrino();
            if(bilanciaArrayPesate.isEmpty())
            {
                setEnableNotBilancia(true);
            }
        }
    }//GEN-LAST:event_jButtonBilanciaEliminaMouseClicked

    private void jButtonBilanciaStampaMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonBilanciaStampaMouseClicked
    {//GEN-HEADEREND:event_jButtonBilanciaStampaMouseClicked
        if(!bilanciaArrayPesate.isEmpty())
        {
            Runnable runner = new Runnable()
            {
                public void run()
                {
                    long idCliente = -1;
                    Registro.progressionBar = new ProgressionBar(9, 100);
                    Registro.progressionBar.setVisible(true);

                    setEnabled(false);
                    for(int i=0; i<bilanciaArrayPesate.size(); i++)
                    {
                        Pesata p = bilanciaArrayPesate.get(i);
                        idCliente = p.idCliente;
                        long id = dataBase.pesate.getNewId();
                        dataBase.pesate.add(new Pesata(
                            id,
                            p.idCliente,
                            p.idProdotto,
                            p.quantita,
                            GregorianCalendar.getInstance()));
                    }

                    dataBase.savePesate(GregorianCalendar.getInstance());
                    Registro.progressionBar.progress();

                    elaboraStampa(bilanciaArrayPesate);
                    Registro.progressionBar.progress();

                    dataBase.frequency.updateFrequency(idCliente, bilanciaArrayPesate);
                    dataBase.saveFrequency();
                    Registro.progressionBar.progress();

                    ((Cliente)dataBase.clienti.get(idCliente)).frequentMinute.update(GregorianCalendar.getInstance());
                    dataBase.saveClienti();
                    Registro.progressionBar.progress();

                    bilanciaArrayPesate = new PesateArray(0);
                    updatejListBilanciaScontrino();
                    Registro.progressionBar.progress();

                    setEnableNotBilancia(true);
                    float tara = 0;
                    updateTara(tara);
                    Registro.progressionBar.progress();

                    orderClienti();
                    Registro.progressionBar.progress();

                    orderProdotti();
                    Registro.progressionBar.progress();

                    updatejPanelBilancia();
                    Registro.progressionBar.progress();

                    Registro.progressionBar.close();
                    Registro.progressionBar = null;
                    Registro.interfaccia.setEnabled(true);
                    Registro.interfaccia.requestFocus();

                }
            };
            Thread t = new Thread(runner, "Code Executer");
            t.start();
        }
    }//GEN-LAST:event_jButtonBilanciaStampaMouseClicked

    private void jButtoBilanciaClientiMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtoBilanciaClientiMouseClicked
    {//GEN-HEADEREND:event_jButtoBilanciaClientiMouseClicked
        if(bilanciaIsActive())
        {
            oClienti.getNext();
            orderClienti();
            updatejList(jListBilanciaClienti, dataBase.clienti);
        }
        
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
                    jLabelBilanciaCliente.setText(Utility.getMax(maxCharBilancia, c.getName()));
                    if(oProdotti.get() == Order.FREQUENCY)
                    {
                        updateClientFrequency();
                        updatejList(jListBilanciaProdotti, dataBase.prodotti);
                    }
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
                    jLabeBilanciaProdotto.setText(Utility.getMax(maxCharBilancia, prodotto.toString()));
                    if(prodotto.unitOfMeasure == UnitOfMeasure.QUANTITY)
                    {
                        netto = 0;
                        lordo = 0;
                        tara = 0;
                        jTextFieldBilanciaNetto.setText("");
                        jTextFieldBilanciaTara.setText("");
                        jLabelBilanciaLordo.setText("Lordo: ");
                    }
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
        jTextFieldProdottiModifica.setText(Utility.safeName(jTextFieldProdottiModifica.getText()));
    }//GEN-LAST:event_jTextFieldProdottiModificaKeyReleased

    private void jButtonProdottiModificaMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonProdottiModificaMouseClicked
    {//GEN-HEADEREND:event_jButtonProdottiModificaMouseClicked
        if(jListProdotti.getSelectedIndex() != -1)
        {
            if(!jTextFieldProdottiModifica.getText().isEmpty())
            {
                Prodotto esistente = (Prodotto)dataBase.prodotti.exist(jTextFieldProdottiModifica.getText());
                UnitOfMeasure unitOfMeasure = UnitOfMeasure.QUANTITY;
                if(jToggleButtonProdottiModifyUnit.isSelected())
                {
                    unitOfMeasure = UnitOfMeasure.KILOGRAMMO;
                }
                if(!(esistente != null && esistente.unitOfMeasure == unitOfMeasure))
                {
                    OrdinableObject c = jListProdotti.getSelectedValue();
                    Prodotto p = (Prodotto)dataBase.prodotti.get(c.getId());
                    p.setName(jTextFieldProdottiModifica.getText());
                    p.unitOfMeasure = unitOfMeasure;
                    dataBase.saveProdotti();
                    updatejList(jListProdotti, dataBase.prodotti);
                    jTextFieldProdottiModifica.setText("");
                }
                else
                {
                    infoBox("ATTENZIONE", "Cliente già esistente", Color.red);
                }
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
    }//GEN-LAST:event_jButtonProdottiModificaMouseClicked

    private void jTextFieldProdottiSalvaKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTextFieldProdottiSalvaKeyReleased
    {//GEN-HEADEREND:event_jTextFieldProdottiSalvaKeyReleased
        jTextFieldProdottiSalva.setText(Utility.safeName(jTextFieldProdottiSalva.getText()));
    }//GEN-LAST:event_jTextFieldProdottiSalvaKeyReleased

    private void jButtonProdottiSalvaMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonProdottiSalvaMouseClicked
    {//GEN-HEADEREND:event_jButtonProdottiSalvaMouseClicked
        if(!jTextFieldProdottiSalva.getText().isEmpty())
        {
            if(dataBase.prodotti.exist(jTextFieldProdottiSalva.getText()) == null)
            {
                UnitOfMeasure unitOfMeasure = UnitOfMeasure.QUANTITY;
                if(jToggleButtonProdottiSaveUnit.isSelected())
                {
                    unitOfMeasure = UnitOfMeasure.KILOGRAMMO;
                }
                Prodotto c = new Prodotto(
                        dataBase.prodotti.getNewId(),
                        dataBase.prodotti.getNewCardinale(),
                        jTextFieldProdottiSalva.getText(),
                        unitOfMeasure
                );
                dataBase.prodotti.add(c);
                
                orderProdotti();
                infoBox("Inserimento nuovo Prodotto", jTextFieldProdottiSalva.getText() + " è stato inserito");
                
                dataBase.saveProdotti();
                
                jTextFieldProdottiSalva.setText("");
                updatejPanelProdotti();
            }
            else
            {
                infoBox("ATTENZIONE", jTextFieldProdottiSalva.getText() + "\n è già presente! Non è stato inserito", Color.red);
            }
        }
        else
        {
            infoBox("ATTENZIONE", "Nessun nome inserito", Color.red);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonProdottiSalvaMouseClicked

    private void jButtonProdottiOrdineMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonProdottiOrdineMouseClicked
    {//GEN-HEADEREND:event_jButtonProdottiOrdineMouseClicked
        Order order = oProdotti.getNext();
        if(order == Order.FREQUENCY)
        {
            oProdotti.getNext();
        }
        orderProdotti();
        updatejList(jListProdotti, dataBase.prodotti);
    }//GEN-LAST:event_jButtonProdottiOrdineMouseClicked

    private void jButtonProdottiEliminaMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonProdottiEliminaMouseClicked
    {//GEN-HEADEREND:event_jButtonProdottiEliminaMouseClicked
        if(jListProdotti.getSelectedIndex() != -1)
        {
            OrdinableObject cl = jListProdotti.getSelectedValue();
             
            String name = cl.getName();
            
            boolean esito = dataBase.prodotti.remove(cl.getId());
            
            if(esito)
            {
                infoBox("Eliminazione Prodotto", name + "\nè stato eliminato");
                dataBase.removedProdotti.add(cl);
                dataBase.saveProdotti();
                dataBase.saveRemovedProdotti();
                updatejPanelProdotti();
            }
            else
            {
                infoBox("ATTENZIONE", "Non è stato possibile eliminare\n" + name, Color.red);
            }
        }
        else
        {
            infoBox("ATTENZIONE", "Seleziona prima un prodotto dalla lista", Color.red);
        }
    }//GEN-LAST:event_jButtonProdottiEliminaMouseClicked

    private void jListProdottiValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_jListProdottiValueChanged
    {//GEN-HEADEREND:event_jListProdottiValueChanged
        if(!evt.getValueIsAdjusting())
        {
            if(jListProdotti.getSelectedIndex() != -1)
            {
                OrdinableObject c = jListProdotti.getSelectedValue();
                Prodotto p = (Prodotto)dataBase.prodotti.get(c.getId());
                jTextFieldProdottiModifica.setText(p.getName());
                jToggleButtonProdottiModifyUnit.setSelected(
                        p.unitOfMeasure == UnitOfMeasure.KILOGRAMMO
                );
                if(sviluppatore.isSelected())
                {
                    jLabelProdottiStats.setText(
                            p.getId() + " " +
                            p.getCardinal() + " " +
                            p.unitOfMeasure + " " +
                            p.frequency);
                }
            }
        }
    }//GEN-LAST:event_jListProdottiValueChanged

    private void jTextFieldTareModificaKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTextFieldTareModificaKeyReleased
    {//GEN-HEADEREND:event_jTextFieldTareModificaKeyReleased
        jTextFieldTareModifica.setText(Utility.safeName(jTextFieldTareModifica.getText()));
    }//GEN-LAST:event_jTextFieldTareModificaKeyReleased

    private void jButtonTareModificaMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonTareModificaMouseClicked
    {//GEN-HEADEREND:event_jButtonTareModificaMouseClicked
        if(jListTare.getSelectedIndex() != -1)
        {
            if(!jTextFieldTareModifica.getText().isEmpty())
            {
                Tara esistente = (Tara)dataBase.tare.exist(jTextFieldTareModifica.getText());
                
                if(!(esistente != null && esistente.quantity == lordo))
                {
                    OrdinableObject c = jListTare.getSelectedValue();
                    Tara t = (Tara)dataBase.tare.get(c.getId());
                    t.setName(jTextFieldTareModifica.getText());
                    t.quantity = lordo;
                    dataBase.saveTare();
                    updatejPanelTare();
                }
                else
                {
                    infoBox("ATTENZIONE", "Tara già esistente", Color.red);
                }
            }
            else
            {
                infoBox("ATTENZIONE", "Nessun nome inserito", Color.red);
            }
        }
        else
        {
            infoBox("ATTENZIONE", "Seleziona prima una tara dalla lista", Color.red);
        }
    }//GEN-LAST:event_jButtonTareModificaMouseClicked

    private void jTextFieldTareSalvaKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTextFieldTareSalvaKeyReleased
    {//GEN-HEADEREND:event_jTextFieldTareSalvaKeyReleased
        jTextFieldTareSalva.setText(Utility.safeName(jTextFieldTareSalva.getText()));
    }//GEN-LAST:event_jTextFieldTareSalvaKeyReleased

    private void jButtonTareSalvaMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonTareSalvaMouseClicked
    {//GEN-HEADEREND:event_jButtonTareSalvaMouseClicked
        if(!jTextFieldTareSalva.getText().isEmpty())
        {
            if(dataBase.tare.exist(jTextFieldTareSalva.getText()) == null)
            {
                Tara c = new Tara(
                        dataBase.tare.getNewId(),
                        dataBase.tare.getNewCardinale(),
                        jTextFieldTareSalva.getText(),
                        lordo
                );
                dataBase.tare.add(c);
                orderTare();
                infoBox("Inserimento nuova Tara", jTextFieldTareSalva.getText() + " è stata inserita");
                
                dataBase.saveTare();
                
                updatejPanelTare();
            }
            else
            {
                infoBox("ATTENZIONE", jTextFieldClientiSalva.getText() + "\n è già presente! Non è stata inserita", Color.red);
            }
        }
        else
        {
            infoBox("ATTENZIONE", "Nessun nome inserito", Color.red);
        }
    }//GEN-LAST:event_jButtonTareSalvaMouseClicked

    private void jButtonTareOrdineMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonTareOrdineMouseClicked
    {//GEN-HEADEREND:event_jButtonTareOrdineMouseClicked
        oTare.getNext();
        orderTare();
        updatejList(jListTare, dataBase.tare);     // TODO add your handling code here:
    }//GEN-LAST:event_jButtonTareOrdineMouseClicked

    private void jButtonTareEliminaMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonTareEliminaMouseClicked
    {//GEN-HEADEREND:event_jButtonTareEliminaMouseClicked
        if(jListTare.getSelectedIndex() != -1)
        {
            OrdinableObject cl = jListTare.getSelectedValue();
             
            String name = cl.getName();
            
            boolean esito = dataBase.tare.remove(cl.getId());
            
            if(esito)
            {
                infoBox("Eliminazione Tara", name + "\nè stata eliminata");
                dataBase.saveTare();
                updatejPanelTare();
            }
            else
            {
                infoBox("ATTENZIONE", "Non è stato possibile eliminare\n" + name, Color.red);
            }
        }
        else
        {
            infoBox("ATTENZIONE", "Seleziona prima una Tara dalla lista", Color.red);
        }
    }//GEN-LAST:event_jButtonTareEliminaMouseClicked

    private void jListTareValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_jListTareValueChanged
    {//GEN-HEADEREND:event_jListTareValueChanged
        if(!evt.getValueIsAdjusting())
        {
            if(jListTare.getSelectedIndex() != -1)
            {
                OrdinableObject c = jListTare.getSelectedValue();
                Tara t = (Tara)dataBase.tare.get(c.getId());
                jTextFieldTareModifica.setText(t.getName());
                if(sviluppatore.isSelected())
                {
                    jLabelTareStats.setText(
                            t.getId() + " " +
                            t.getCardinal() + " " +
                            t.quantity
                    );
                }
            }
        }
    }//GEN-LAST:event_jListTareValueChanged

    private void jListTotaliClientiValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_jListTotaliClientiValueChanged
    {//GEN-HEADEREND:event_jListTotaliClientiValueChanged
        if(!evt.getValueIsAdjusting())
        {
            updatejTable();
        }
    }//GEN-LAST:event_jListTotaliClientiValueChanged

    private void jXDatePickerTotaliPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt)//GEN-FIRST:event_jXDatePickerTotaliPopupMenuWillBecomeInvisible
    {//GEN-HEADEREND:event_jXDatePickerTotaliPopupMenuWillBecomeInvisible
        int cliente = jListTotaliClienti.getSelectedIndex();
        updateTotali();
        updateSettimana(jXDatePickerTotali);
        jListTotaliClienti.setSelectedIndex(cliente);
    }//GEN-LAST:event_jXDatePickerTotaliPopupMenuWillBecomeInvisible

    private void jButtonSaveTotaliMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonSaveTotaliMouseClicked
    {//GEN-HEADEREND:event_jButtonSaveTotaliMouseClicked
        Runnable runner = new Runnable()
        {
            public void run() {
                Calendar data = Calendar.getInstance();
                data.setTime(jXDatePickerTotali.getDate());
                dataBase.saveFileTotali(data);
            }
        };
        Thread t = new Thread(runner, "Code Executer");
        t.start();
    }//GEN-LAST:event_jButtonSaveTotaliMouseClicked

    private void jButtonTotaliSalvaSettimanaMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonTotaliSalvaSettimanaMouseClicked
    {//GEN-HEADEREND:event_jButtonTotaliSalvaSettimanaMouseClicked
        Runnable runner = new Runnable()
        {
            public void run() {
                int indiceCliente = jListTotaliClienti.getSelectedIndex();
                if(indiceCliente != -1)
                {
                    long idCliente = dataBase.clienti.get(jListTotaliClienti.getSelectedValue().getId()).getId();
                    dataBase.saveTotaliSettimana(idCliente, firstDay, lastDay);
                }
            }
        };
        Thread t = new Thread(runner, "Code Executer");
        t.start();
    }//GEN-LAST:event_jButtonTotaliSalvaSettimanaMouseClicked

    private void jButtonTotaliOrdinamentoMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonTotaliOrdinamentoMouseClicked
    {//GEN-HEADEREND:event_jButtonTotaliOrdinamentoMouseClicked
        oClienti.getNext();
        orderClienti();
        updatejList(jListTotaliClienti, dataBase.clienti);        // TODO add your handling code here:*/
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
        if(jListPesate.getSelectedIndex() != -1)
        {
            IdName cl = jListPesate.getSelectedValue();
            
            Pesata p = dataBase.pesate.get(cl.id);
            boolean esito = dataBase.pesate.remove(cl.id);
            
            if(esito)
            {
                infoBox("Ripristino Pesata", cl.name + "\nè stata eliminata");
                dataBase.removedPesate.add(p);
                dataBase.savePesate(p.time);
                dataBase.saveRemovedPesate();
                updatejListPesate();
            }
            else
            {
                infoBox("ATTENZIONE", "Non è stato possibile eliminare la Pesata\n" + cl.name, Color.red);
            }
        }
        else
        {
            infoBox("ATTENZIONE", "Seleziona prima una Pesata dalla lista", Color.red);
        }
        
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
        updatejListPesate();
    }//GEN-LAST:event_jXDatePickerPesatePopupMenuWillBecomeInvisible

    private void jButtonPesateRistampaScontrinoMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonPesateRistampaScontrinoMouseClicked
    {//GEN-HEADEREND:event_jButtonPesateRistampaScontrinoMouseClicked
        int indicePesata = jListPesate.getSelectedIndex();
        int indiceCliente = jListPesateClienti.getSelectedIndex();
        if(indicePesata != -1 && indiceCliente != -1)
        {
            Pesata p = (Pesata) dataBase.pesate.get(jListPesate.getSelectedValue().id);
            PesateArray ristampaPesate = new PesateArray(0);
            for(int i=0; i<jListPesate.getModel().getSize(); i++)
            {
                
                Pesata p2 = (Pesata) dataBase.pesate.get(jListPesate.getModel().getElementAt(i).id);
                if(p2.time.get(Calendar.DAY_OF_MONTH) == p.time.get(Calendar.DAY_OF_MONTH))
                {
                    ristampaPesate.add(p2);
                }
            }
            elaboraStampa(ristampaPesate);
        }
    }//GEN-LAST:event_jButtonPesateRistampaScontrinoMouseClicked

    private void jListPesateClientiValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_jListPesateClientiValueChanged
    {//GEN-HEADEREND:event_jListPesateClientiValueChanged
        if(!evt.getValueIsAdjusting())
        {
            log("jListPesateClientiValueChanged");
            updatejListPesate();
        }
    }//GEN-LAST:event_jListPesateClientiValueChanged

    private void jButtonPesateOrdinamentoClientiMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonPesateOrdinamentoClientiMouseClicked
    {//GEN-HEADEREND:event_jButtonPesateOrdinamentoClientiMouseClicked
        oClienti.getNext();
        orderClienti();
        updatePesate();
    }//GEN-LAST:event_jButtonPesateOrdinamentoClientiMouseClicked

    private void jButtonPesateCambiaClienteMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonPesateCambiaClienteMouseClicked
    {//GEN-HEADEREND:event_jButtonPesateCambiaClienteMouseClicked
        int indice = jListPesate.getSelectedIndex();
        int indiceCliente1 = jListPesateClienti.getSelectedIndex();
        int indiceCliente2 = jListPesateCambiaCliente.getSelectedIndex();
        if(indice != -1 && indiceCliente1 != -1 && indiceCliente2 != -1)
        {
            long idPesata = jListPesate.getSelectedValue().id;
            long idCliente2 = jListPesateCambiaCliente.getSelectedValue().getId();
            dataBase.pesate.get(idPesata).idCliente = idCliente2;

            
            dataBase.savePesate(jXDatePickerPesate.getDate());
            updatejListPesate();
        }
    }//GEN-LAST:event_jButtonPesateCambiaClienteMouseClicked

    private void jToggleButtonProdottiSaveUnitStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_jToggleButtonProdottiSaveUnitStateChanged
    {//GEN-HEADEREND:event_jToggleButtonProdottiSaveUnitStateChanged
        if(jToggleButtonProdottiSaveUnit.isSelected())
        {
            jToggleButtonProdottiSaveUnit.setText("Kg");
        }
        else
        {
            jToggleButtonProdottiSaveUnit.setText("Numero");
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButtonProdottiSaveUnitStateChanged

    private void jToggleButtonProdottiModifyUnitStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_jToggleButtonProdottiModifyUnitStateChanged
    {//GEN-HEADEREND:event_jToggleButtonProdottiModifyUnitStateChanged
        if(jToggleButtonProdottiModifyUnit.isSelected())
        {
            jToggleButtonProdottiModifyUnit.setText("Kg");
        }
        else
        {
            jToggleButtonProdottiModifyUnit.setText("Numero");
        }
    }//GEN-LAST:event_jToggleButtonProdottiModifyUnitStateChanged

    private void jListPesateValueChanged(javax.swing.event.ListSelectionEvent evt)//GEN-FIRST:event_jListPesateValueChanged
    {//GEN-HEADEREND:event_jListPesateValueChanged
        if(!evt.getValueIsAdjusting())
        {
            if(sviluppatore.isSelected() && jListPesate.getSelectedIndex() != -1)
            {
                IdName pe = jListPesate.getSelectedValue();
                Pesata p = dataBase.pesate.get(pe.id);
                jLabelPesateStats.setText(
                        p.getId() + " " +
                        p.idCliente + " " +
                        p.idProdotto + " " +
                        p.quantita + " " +
                        Utility.getCalendarString(p.time));
            }
        }
    }//GEN-LAST:event_jListPesateValueChanged

    private void jButton11MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton11MouseClicked
    {//GEN-HEADEREND:event_jButton11MouseClicked
        if(jListRemovedPesate.getSelectedIndex() != -1)
        {
            IdName cl = jListRemovedPesate.getSelectedValue();
            
            Pesata p = dataBase.removedPesate.get(cl.id);
            boolean esito = dataBase.removedPesate.remove(cl.id);
            
            if(esito)
            {
                
                dataBase.loadPesate(p.time);
                dataBase.pesate.add(p);
                dataBase.savePesate(p.time);
                dataBase.saveRemovedPesate();
                updatejListRemovedPesate();
                infoBox("Ripristino Pesata", cl.name + "\nè stata ripristinata");
                
            }
            else
            {
                infoBox("ATTENZIONE", "Non è stato possibile ripristinare la Pesata\n" + cl.name, Color.red);
            }
        }
        else
        {
            infoBox("ATTENZIONE", "Seleziona prima una Pesata dalla lista", Color.red);
        }
    }//GEN-LAST:event_jButton11MouseClicked

    private void jButton12MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton12MouseClicked
    {//GEN-HEADEREND:event_jButton12MouseClicked
        JLabel label = new JLabel("ELIMINA TUTTE LE PESATE IN MODO PERMANENTE");
        label.setFont(fontBig);
        label.setForeground(Color.red);
        int reply = JOptionPane.showConfirmDialog(null, label, "ELIMINAZIONE PESATE SCARTATE", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION)
        {
            JLabel label2 = new JLabel("Sei sicuro di eliminare tutte le pesate scartate?");
            label2.setFont(fontBig);
            label2.setForeground(Color.red);
            int reply2 = JOptionPane.showConfirmDialog(null, label2, "ELIMINAZIONE PESATE SCARTATE", JOptionPane.YES_NO_OPTION);
            if(reply2 == JOptionPane.YES_OPTION)
            {
                dataBase.removedPesate = new PesateArray(0);
                dataBase.saveRemovedPesate();
                updatejListRemovedPesate();
            }
        }
    }//GEN-LAST:event_jButton12MouseClicked

    private void jTabbedPaneMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jTabbedPaneMouseReleased
    {//GEN-HEADEREND:event_jTabbedPaneMouseReleased
        if(!bilanciaIsActive())
        {
            jTabbedPane.setSelectedIndex(0);
        }
    }//GEN-LAST:event_jTabbedPaneMouseReleased

    private void jButtonClientiSuMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonClientiSuMouseClicked
    {//GEN-HEADEREND:event_jButtonClientiSuMouseClicked
        if(jButtonClientiSu.isEnabled())
        {
            swapCardinalClienti(Behavior.DECREASING);
        }
    }//GEN-LAST:event_jButtonClientiSuMouseClicked

    private void jButtonClientiGiuMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonClientiGiuMouseClicked
    {//GEN-HEADEREND:event_jButtonClientiGiuMouseClicked
        if(jButtonClientiGiu.isEnabled())
        {
            swapCardinalClienti(Behavior.INCREASING);
        }
    }//GEN-LAST:event_jButtonClientiGiuMouseClicked

    private void jButtonProdottiSuMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonProdottiSuMouseClicked
    {//GEN-HEADEREND:event_jButtonProdottiSuMouseClicked
        if(jButtonProdottiSu.isEnabled())
        {
            swapCardinalProdotti(Behavior.DECREASING);
        }
    }//GEN-LAST:event_jButtonProdottiSuMouseClicked

    private void jButtonProdottiGiuMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonProdottiGiuMouseClicked
    {//GEN-HEADEREND:event_jButtonProdottiGiuMouseClicked
        if(jButtonProdottiGiu.isEnabled())
        {
            swapCardinalProdotti(Behavior.INCREASING);
        }
    }//GEN-LAST:event_jButtonProdottiGiuMouseClicked

    private void jButtonTareSuMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonTareSuMouseClicked
    {//GEN-HEADEREND:event_jButtonTareSuMouseClicked
        if(jButtonTareSu.isEnabled())
        {
            swapCardinalTare(Behavior.DECREASING);
        }
    }//GEN-LAST:event_jButtonTareSuMouseClicked

    private void jButtonTareGiuMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonTareGiuMouseClicked
    {//GEN-HEADEREND:event_jButtonTareGiuMouseClicked
        if(jButtonTareGiu.isEnabled())
        {
            swapCardinalTare(Behavior.INCREASING);
        }
    }//GEN-LAST:event_jButtonTareGiuMouseClicked

    private void jLabelTareMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jLabelTareMouseClicked
    {//GEN-HEADEREND:event_jLabelTareMouseClicked
        jTextFieldTareSalva.setText(String.format("%.3f", lordo) + "");        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelTareMouseClicked

    private void jLabelTareModificaMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jLabelTareModificaMouseClicked
    {//GEN-HEADEREND:event_jLabelTareModificaMouseClicked
        jTextFieldTareModifica.setText(String.format("%.3f", lordo) + "");
    }//GEN-LAST:event_jLabelTareModificaMouseClicked

    private void jButton10MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButton10MouseClicked
    {//GEN-HEADEREND:event_jButton10MouseClicked
        if(jListRemovedProdotti.getSelectedIndex() != -1)
        {
            OrdinableObject cl = jListRemovedProdotti.getSelectedValue();
             
            String name = cl.getName();
            
            boolean esito = dataBase.removedProdotti.remove(cl.getId());
            
            if(esito)
            {
                infoBox("Ripristino Prodotto", name + "\nè stato ripristinato");
                dataBase.prodotti.add(cl);
                orderProdotti();
                dataBase.saveProdotti();
                dataBase.saveRemovedProdotti();
                updatejPanelRemoved();
            }
            else
            {
                infoBox("ATTENZIONE", "Non è stato possibile Ripristinare\n" + name, Color.red);
            }
        }
        else
        {
            infoBox("ATTENZIONE", "Seleziona prima un Prodotto dalla lista", Color.red);
        }
    }//GEN-LAST:event_jButton10MouseClicked

    private void sviluppatoreStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_sviluppatoreStateChanged
    {//GEN-HEADEREND:event_sviluppatoreStateChanged
        
    }//GEN-LAST:event_sviluppatoreStateChanged

    private void jTableTotaliMensiliMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jTableTotaliMensiliMouseClicked
    {//GEN-HEADEREND:event_jTableTotaliMensiliMouseClicked
        
        int giorno = jTableTotaliMensili.getSelectedRow() + 1;
        int indiceProdotto = jTableTotaliMensili.getSelectedColumn();
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(jXDatePickerTotali.getDate());
        
        if(
                tableProducts != null &&
                1 <= giorno && giorno <= cal.getActualMaximum(Calendar.DAY_OF_MONTH) &&
                indiceProdotto != 0 &&
                jListTotaliClienti.getSelectedIndex() != -1
                )
        {
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), giorno, 7, 30);
            updateSettimana(cal);
        }
    }//GEN-LAST:event_jTableTotaliMensiliMouseClicked

    private void jButtonAddPesataMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonAddPesataMouseClicked
    {//GEN-HEADEREND:event_jButtonAddPesataMouseClicked
        int giorno = jTableTotaliMensili.getSelectedRow() + 1;
        int indiceProdotto = jTableTotaliMensili.getSelectedColumn();
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(jXDatePickerTotali.getDate());
        
        if(
                tableProducts != null &&
                1 <= giorno && giorno <= cal.getActualMaximum(Calendar.DAY_OF_MONTH) &&
                indiceProdotto != 0 &&
                jListTotaliClienti.getSelectedIndex() != -1
                )
        {
            long idProdotto = tableProducts[indiceProdotto - 1].id;
            Prodotto prodotto = (Prodotto) dataBase.getAllProducts().get(idProdotto);
            log(Utility.getCalendarString(cal));
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), giorno, 7, 30);
            log(Utility.getCalendarString(cal));
            setEnabled(false);
            InterfacciaTastierino interfacciaTastierino = new InterfacciaTastierino(font, fontMedium, prodotto.unitOfMeasure);
            interfacciaTastierino.setVisible(true);
        }
    }//GEN-LAST:event_jButtonAddPesataMouseClicked

    private void jSpinnerPortaComStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_jSpinnerPortaComStateChanged
    {//GEN-HEADEREND:event_jSpinnerPortaComStateChanged
        
        int value = (int)jSpinnerPortaCom.getModel().getValue();
        if(value < 1)
        {
            value = 1;
            jSpinnerPortaCom.setValue(value);
            
        }
    }//GEN-LAST:event_jSpinnerPortaComStateChanged

    private void jButtonSalvaImpostazioniMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jButtonSalvaImpostazioniMouseClicked
    {//GEN-HEADEREND:event_jButtonSalvaImpostazioniMouseClicked

        Registro.settings.orderClienti = jComboBoxClienti.getSelectedIndex();
        Registro.settings.orderProdotti = jComboBoxProdotti.getSelectedIndex();
        Registro.settings.orderTare = jComboBoxTare.getSelectedIndex();
        Registro.settings.font = (int)jSpinnerFont.getModel().getValue();
        Registro.settings.fontMedium = (int)jSpinnerFontMedium.getModel().getValue();
        Registro.settings.fontBig = (int)jSpinnerFontBig.getModel().getValue();
        Registro.settings.nomeStampante = jTextFieldNomeStampante.getText();
        Registro.settings.portaBilancia = (int)jSpinnerPortaCom.getModel().getValue();
        Registro.saveSettings();
        infoBox("SALVATAGGIO E RIAVVIO", "<html>Per poter applicare le nuove impostazioni<br>è necessario avviare nuovamente il programma</html>", Color.red);
        System.exit(0);
    }//GEN-LAST:event_jButtonSalvaImpostazioniMouseClicked

    public void salvaPesata(float peso)
    {
        int giorno = jTableTotaliMensili.getSelectedRow() + 1;
        int indiceProdotto = jTableTotaliMensili.getSelectedColumn();
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(jXDatePickerTotali.getDate());
        
        if(
                tableProducts != null &&
                1 <= giorno && giorno <= cal.getActualMaximum(Calendar.DAY_OF_MONTH) &&
                indiceProdotto != 0 &&
                jListTotaliClienti.getSelectedIndex() != -1
                )
        {
            long idProdotto = tableProducts[indiceProdotto - 1].id;
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), giorno, 7, 30);
            dataBase.loadPesate(cal);
            long id = dataBase.pesate.getNewId();
            long idCliente = jListTotaliClienti.getSelectedValue().getId();
            Pesata pesata = new Pesata(id, idCliente, idProdotto, peso, cal);
            dataBase.pesate.add(pesata);
            dataBase.savePesate(cal);
            updatejTable();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Interface.FolderChooser folderChooser1;
    private javax.swing.JButton jButtoBilanciaClienti;
    private javax.swing.JButton jButton0;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButtonAddPesata;
    private javax.swing.JButton jButtonBilanciaAggiungi;
    private javax.swing.JButton jButtonBilanciaElimina;
    private javax.swing.JButton jButtonBilanciaProdotti;
    private javax.swing.JButton jButtonBilanciaStampa;
    private javax.swing.JButton jButtonBilanciaTara;
    private javax.swing.JButton jButtonClientiElimina;
    private javax.swing.JButton jButtonClientiGiu;
    private javax.swing.JButton jButtonClientiModifica;
    private javax.swing.JButton jButtonClientiOrdine;
    private javax.swing.JButton jButtonClientiSalva;
    private javax.swing.JButton jButtonClientiSu;
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
    private javax.swing.JButton jButtonSalvaImpostazioni;
    private javax.swing.JButton jButtonSaveTotali;
    private javax.swing.JButton jButtonTareElimina;
    private javax.swing.JButton jButtonTareGiu;
    private javax.swing.JButton jButtonTareModifica;
    private javax.swing.JButton jButtonTareOrdine;
    private javax.swing.JButton jButtonTareSalva;
    private javax.swing.JButton jButtonTareSu;
    private javax.swing.JButton jButtonTotaliOrdinamento;
    private javax.swing.JButton jButtonTotaliSalvaSettimana;
    private javax.swing.JComboBox<String> jComboBoxClienti;
    private javax.swing.JComboBox<String> jComboBoxProdotti;
    private javax.swing.JComboBox<String> jComboBoxTare;
    private javax.swing.JLabel jLabeBilanciaProdotto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelBilanciaCliente;
    private javax.swing.JLabel jLabelBilanciaLogo;
    private javax.swing.JLabel jLabelBilanciaLordo;
    private javax.swing.JLabel jLabelBilanciaTotalePesaScontrino;
    private javax.swing.JLabel jLabelBilanciaTotaleTitle;
    private javax.swing.JLabel jLabelClientiLogo;
    private javax.swing.JLabel jLabelClientiModifica;
    private javax.swing.JLabel jLabelClientiModifica2;
    private javax.swing.JLabel jLabelClientiSalva;
    private javax.swing.JLabel jLabelClientiSalva2;
    private javax.swing.JLabel jLabelClientiStats;
    private javax.swing.JLabel jLabelPesateLogo;
    private javax.swing.JLabel jLabelPesateStats;
    private javax.swing.JLabel jLabelProdottiLogo;
    private javax.swing.JLabel jLabelProdottiModifica;
    private javax.swing.JLabel jLabelProdottiSalva;
    private javax.swing.JLabel jLabelProdottiStats;
    private javax.swing.JLabel jLabelSaveDirectory;
    private javax.swing.JLabel jLabelTare;
    private javax.swing.JLabel jLabelTareLogo;
    private javax.swing.JLabel jLabelTareModifica;
    private javax.swing.JLabel jLabelTareStats;
    private javax.swing.JLabel jLabelTotaliLogo;
    private javax.swing.JList<OrdinableObject> jListBilanciaClienti;
    private javax.swing.JList<OrdinableObject> jListBilanciaProdotti;
    private javax.swing.JList<IdName> jListBilanciaScontrino;
    private javax.swing.JList<OrdinableObject> jListClienti;
    private javax.swing.JList<IdName> jListPesate;
    private javax.swing.JList<OrdinableObject> jListPesateCambiaCliente;
    private javax.swing.JList<OrdinableObject> jListPesateClienti;
    private javax.swing.JList<OrdinableObject> jListProdotti;
    private javax.swing.JList<OrdinableObject> jListRemovedClienti;
    private javax.swing.JList<IdName> jListRemovedPesate;
    private javax.swing.JList<OrdinableObject> jListRemovedProdotti;
    private javax.swing.JList<OrdinableObject> jListTare;
    private javax.swing.JList<OrdinableObject> jListTotaliClienti;
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
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelBilancia;
    private javax.swing.JPanel jPanelBilancia1;
    private javax.swing.JPanel jPanelBilancia2;
    private javax.swing.JPanel jPanelBilanciaLogo;
    private javax.swing.JPanel jPanelClienti;
    private javax.swing.JPanel jPanelClientiInserimento;
    private javax.swing.JPanel jPanelClientiLista;
    private javax.swing.JPanel jPanelClientiLista2;
    private javax.swing.JPanel jPanelOptions;
    private javax.swing.JPanel jPanelPesate;
    private javax.swing.JPanel jPanelProdotti;
    private javax.swing.JPanel jPanelProdottiInserimento;
    private javax.swing.JPanel jPanelProdottiLista;
    private javax.swing.JPanel jPanelRemoved;
    private javax.swing.JPanel jPanelTare;
    private javax.swing.JPanel jPanelTareInserimento;
    private javax.swing.JPanel jPanelTotali;
    private javax.swing.JScrollPane jScrollPaneBilanciaClienti;
    private javax.swing.JScrollPane jScrollPaneBilanciaProdotti;
    private javax.swing.JScrollPane jScrollPaneBilanciaScontrino;
    private javax.swing.JScrollPane jScrollPaneClienti;
    private javax.swing.JScrollPane jScrollPaneOpzioni;
    private javax.swing.JScrollPane jScrollPanePesate;
    private javax.swing.JScrollPane jScrollPanePesateCambiaClienti;
    private javax.swing.JScrollPane jScrollPanePesateClienti;
    private javax.swing.JScrollPane jScrollPaneProdotti;
    private javax.swing.JScrollPane jScrollPaneRemovedClienti;
    private javax.swing.JScrollPane jScrollPaneRemovedPesate;
    private javax.swing.JScrollPane jScrollPaneRemovedProdotti;
    private javax.swing.JScrollPane jScrollPaneTare;
    private javax.swing.JScrollPane jScrollPaneTotali;
    private javax.swing.JScrollPane jScrollPaneTotaliClienti;
    private javax.swing.JSpinner jSpinnerFont;
    private javax.swing.JSpinner jSpinnerFontBig;
    private javax.swing.JSpinner jSpinnerFontMedium;
    private javax.swing.JSpinner jSpinnerPortaCom;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JSplitPane jSplitPane4;
    private javax.swing.JSplitPane jSplitPane5;
    private javax.swing.JSplitPane jSplitPaneBilancia;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTable jTableTotaliMensili;
    private javax.swing.JTextArea jTextAreaOpzioni;
    private javax.swing.JTextField jTextFieldBilanciaNetto;
    private javax.swing.JTextField jTextFieldBilanciaTara;
    private javax.swing.JTextField jTextFieldClientiModifica;
    private javax.swing.JTextField jTextFieldClientiSalva;
    private javax.swing.JTextField jTextFieldNomeStampante;
    private javax.swing.JTextField jTextFieldProdottiModifica;
    private javax.swing.JTextField jTextFieldProdottiSalva;
    private javax.swing.JTextField jTextFieldTareModifica;
    private javax.swing.JTextField jTextFieldTareSalva;
    private javax.swing.JToggleButton jToggleButtonDemoBilancia;
    private javax.swing.JToggleButton jToggleButtonProdottiModifyUnit;
    private javax.swing.JToggleButton jToggleButtonProdottiSaveUnit;
    private org.jdesktop.swingx.JXDatePicker jXDatePickerPesate;
    private org.jdesktop.swingx.JXDatePicker jXDatePickerTotali;
    private javax.swing.JToggleButton sviluppatore;
    // End of variables declaration//GEN-END:variables

}
