package View.Etapas;

import Control.Ciclo2.ControlPdeVapor;
import Control.Ciclo2.ControlTSaida;
import Control.Ciclo2.ControlT_Ref;
import Control.Conversao.ControlConverte;
import Control.Etapa.ControlEtapa1PanelRankine;
import Util.DropdownComboBox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author leonardo
 */
public class ViewEtapa1PanelRankine extends JPanel{
    //LABELS
    private final JLabel labelTemp = new JLabel("Temperatura: ");
    private final JLabel labelPressao = new JLabel("Pressão: ");
 
    //TEXTFIELDS
    private DropdownComboBox fieldTemp = new DropdownComboBox();
   
    private DropdownComboBox fieldPressao = new DropdownComboBox();
    
    //COMBOBOX
    private final String[] temps = {"K", "°C", "°F"};
    private JComboBox<String> comboTemp = new JComboBox<>(temps);
    
    private final String[] pressoes = {"kPa", "atm", "bar"};
    private JComboBox<String> comboPressao = new JComboBox<>(pressoes);

    //BUTTON
    ImageIcon[] refreshIcon = {
        new ImageIcon(new ImageIcon("src/Images/Att/att2.png").getImage()),
        new ImageIcon(new ImageIcon("src/Images/Att/att1.png").getImage())
    };

    private JLabel btnAtualizaTemp = new JLabel(refreshIcon[0]);
    private JLabel btnAtualizaPressao = new JLabel(refreshIcon[0]);

    private JPanel painelEntrada;
    private JPanel painelDados;
    
    private final int precision = 2;
    private ControlConverte controlConverte = new ControlConverte();
    
    public ViewEtapa1PanelRankine(ControlEtapa1PanelRankine ctrlEtapa1Panel) {
        
        painelDados = new JPanel(new GridBagLayout());
        painelEntrada = new JPanel(new GridBagLayout());
        painelEntrada.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1, Color.lightGray, Color.lightGray), "Entrada", 1, 2, new Font("Times New Roman", 1, 12), Color.darkGray));
        
        btnAtualizaTemp.setPreferredSize(new Dimension(20,20));
        btnAtualizaPressao.setPreferredSize(new Dimension(20,20));
        
        this.setLayout(new GridBagLayout());
        this.setName("Etapa1");
        
        GridBagConstraints g = new GridBagConstraints();
        
        //INSERINDO OS ELEMENTOS NO JPANEL
        
        //DADOS DE ENTRADA
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 1;
        g.weightx = 1;
        g.weighty = 1;
        g.insets = new Insets(2, 5, 2, 0);
        g.anchor = GridBagConstraints.CENTER;
        painelEntrada.add(btnAtualizaPressao,g);
        
        g.gridx = 1;
        g.gridy = 0;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelEntrada.add(labelTemp,g);
        
        g.gridx = 2;
        g.gridy = 0;
        g.gridwidth = 2;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelEntrada.add(fieldTemp,g);
        
        g.gridx = 4;
        g.gridy = 0;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelEntrada.add(comboTemp, g);

        g.gridx = 0;
        g.gridy = 1;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.NONE;
        g.anchor = GridBagConstraints.CENTER;
        painelEntrada.add(btnAtualizaTemp, g);

        g.gridx = 1;
        g.gridy = 1;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelEntrada.add(labelPressao, g);
        
        g.gridx = 2;
        g.gridy = 1;
        g.gridwidth = 2;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelEntrada.add(fieldPressao, g);
        
        g.gridx = 4;
        g.gridy = 1;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelEntrada.add(comboPressao, g);
        
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 3;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(painelEntrada, g);
        
        //INSERINDO DADOS NA TELA
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.PAGE_START;
        this.add(painelDados, g);
                
        fieldTemp.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //fieldMassa.setBackground(Color.white);
                fieldTemp.showPopup();
                fieldTemp.getEditor().selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                ctrlEtapa1Panel.atualizaPressao();
            }
        });
        
        fieldTemp.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                fieldTemp.hidePopup();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        
        fieldPressao.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //fieldMassa.setBackground(Color.white);
                fieldPressao.showPopup();
                fieldPressao.getEditor().selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                
                ctrlEtapa1Panel.atualizaTemperatura();
            }
        });
        fieldPressao.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                fieldPressao.hidePopup();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        
        comboTemp.addItemListener(new ItemListener() {
            String[] tipo = new String[2];
            int tip = 0;
            
            @Override
            public void itemStateChanged(ItemEvent ie) {
                
                tipo[tip] = ie.getItem().toString();
                tip++;
                if(tip == 2){
                    String valor = fieldTemp.getSelectedItem().toString();
                    if(!valor.isEmpty()){
                        fieldTemp.setSelectedItem(String.valueOf(controlConverte.converte(tipo[0],tipo[1],Double.parseDouble(valor)))); 
                    } 
                    tip = 0;
                }
            }
        });
        
        comboPressao.addItemListener(new ItemListener() {
            String[] tipo = new String[2];
            int tip = 0;
            
            @Override
            public void itemStateChanged(ItemEvent ie) {
                
                tipo[tip] = ie.getItem().toString();
                tip++;
                if(tip == 2){
                    String valor = fieldPressao.getSelectedItem().toString();
                    if(!valor.isEmpty()){
                        fieldPressao.setSelectedItem(String.valueOf(controlConverte.converte(tipo[0],tipo[1],Double.parseDouble(valor))));                        
                    } 
                    tip = 0;
                }
            }
        });
        
        btnAtualizaTemp.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
            }

            @Override
            public void mousePressed(MouseEvent me) {
                btnAtualizaTemp.setIcon(refreshIcon[1]);
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                btnAtualizaTemp.setIcon(refreshIcon[0]);
                String fluNome = ctrlEtapa1Panel.getCtrlPrincipal().getViewLateral().getComboFluidos().getSelectedItem().toString();
                int flu = ctrlEtapa1Panel.getCtrlPrincipal().getFluidoCod(fluNome);
                
                fieldTemp.setSelectedItem(controlConverte.round(Double.parseDouble(fieldTemp.getSelectedItem().toString()),precision));
                ctrlEtapa1Panel.atualizaTemperatura();
                
                double Tee = Double.parseDouble(fieldTemp.getSelectedItem().toString()) - Double.parseDouble(ctrlEtapa1Panel.getCtrlPrincipal().getViewLateral().getFieldSup().getSelectedItem().toString());
                ControlPdeVapor pdevapor = new ControlPdeVapor(Tee, flu, ctrlEtapa1Panel.getSession());
                
                ctrlEtapa1Panel.getCtrlPrincipal().setP1(pdevapor.getPs2());
                ctrlEtapa1Panel.getCtrlPrincipal().setT1(Tee + Double.parseDouble(ctrlEtapa1Panel.getCtrlPrincipal().getViewLateral().getFieldSup().getSelectedItem().toString()));
                
                fieldPressao.setSelectedItem(controlConverte.round(pdevapor.getPs2(), 2));
                ctrlEtapa1Panel.atualizaPressao();
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            }

            @Override
            public void mouseExited(MouseEvent me) {
            }
        });
        
        btnAtualizaPressao.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
            }

            @Override
            public void mousePressed(MouseEvent me) {
                btnAtualizaPressao.setIcon(refreshIcon[1]);
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                btnAtualizaPressao.setIcon(refreshIcon[0]);
                String fluNome = ctrlEtapa1Panel.getCtrlPrincipal().getViewLateral().getComboFluidos().getSelectedItem().toString();
                int flu = ctrlEtapa1Panel.getCtrlPrincipal().getFluidoCod(fluNome);
                
                fieldPressao.setSelectedItem(controlConverte.round(Double.parseDouble(fieldPressao.getSelectedItem().toString()),precision));
                ctrlEtapa1Panel.atualizaPressao();
                
                double P1 = Double.parseDouble(fieldPressao.getSelectedItem().toString());
                
                ControlT_Ref tRef = new ControlT_Ref(P1, flu, ctrlEtapa1Panel.getSession());
                double Tee = tRef.getTref();
                
                double T1 = Tee + Double.parseDouble(ctrlEtapa1Panel.getCtrlPrincipal().getViewLateral().getFieldSup().getSelectedItem().toString());
                
                ctrlEtapa1Panel.getCtrlPrincipal().setT1(T1);
                ctrlEtapa1Panel.getCtrlPrincipal().setP1(P1);
                fieldTemp.setSelectedItem(controlConverte.round(T1, 2));
                ctrlEtapa1Panel.atualizaTemperatura();
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            }

            @Override
            public void mouseExited(MouseEvent me) {
            }
        });
    }

    public DropdownComboBox getFieldTemp() {
        return fieldTemp;
    }

    public void setFieldTemp(DropdownComboBox fieldTemp) {
        this.fieldTemp = fieldTemp;
    }

    public DropdownComboBox getFieldPressao() {
        return fieldPressao;
    }

    public void setFieldPressao(DropdownComboBox fieldPressao) {
        this.fieldPressao = fieldPressao;
    }
    
    
}
