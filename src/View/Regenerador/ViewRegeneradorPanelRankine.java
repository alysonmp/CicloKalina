/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Regenerador;

import Control.Conversao.ControlConverte;
import Control.Regenerador.ControlRegeneradorPanelRankine;
import Util.DropdownComboBox;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author alysonmp
 */
public class ViewRegeneradorPanelRankine extends JPanel{
    
    //LABELS 
    private JLabel labelMassa = new JLabel("Massa (m): ");
    
    private JLabel labelTempEntr = new JLabel("Temperatura: ");
    private JLabel labelPressaoEntr = new JLabel("Pressão: ");
    
    private JLabel labelTempSai = new JLabel("Temperatura: ");
    private JLabel labelPressaoSai = new JLabel("Pressão: ");
    
    private JLabel labelTempEntr2 = new JLabel("Temperatura: ");
    private JLabel labelPressaoEntr2 = new JLabel("Pressão: ");
    
    private JLabel labelTempSai2 = new JLabel("Temperatura: ");
    private JLabel labelPressaoSai2 = new JLabel("Pressão: ");
    
    private JLabel labelEfetiv = new JLabel("<html><body>Temperatura <br>de Condensação: </html></body>");
    private JLabel labelDelta = new JLabel("Efetividade: ");
    
    //TEXTFIELDS
    private DropdownComboBox fieldMassa = new DropdownComboBox();;
    
    private DropdownComboBox fieldTempEntr = new DropdownComboBox();
    
    private DropdownComboBox fieldPressaoEntr = new DropdownComboBox();
    
    private DropdownComboBox fieldTempSai = new DropdownComboBox();
    
    private DropdownComboBox fieldPressaoSai = new DropdownComboBox();
    
    private DropdownComboBox fieldDelta = new DropdownComboBox();
    
    private DropdownComboBox fieldEfetiv = new DropdownComboBox();
    
    private DropdownComboBox fieldTempEntr2 = new DropdownComboBox();
    
    private DropdownComboBox fieldTempSai2 = new DropdownComboBox();

    private DropdownComboBox fieldPressaoEntr2 = new DropdownComboBox();
    
    private DropdownComboBox fieldPressaoSai2 = new DropdownComboBox();
    
    //COMBOBOX
    private String[] massas = {"kg/s", "kg/m", "kg/h", "lb/s", "lb/m", "lb/h"};
    private JComboBox<String> comboMassa = new JComboBox<>(massas);
    
    private String[] temps = {"K", "°C", "°F"};
    private JComboBox<String> comboTempEntr = new JComboBox<>(temps);
    private JComboBox<String> comboTempSai = new JComboBox<>(temps);
    
    private String[] pressoes = {"kPa", "atm", "bar"};
    private JComboBox<String> comboPressaoEntr = new JComboBox<>(pressoes);
    private JComboBox<String> comboPressaoSai = new JComboBox<>(pressoes);
    
    private JComboBox<String> comboTempEntr2 = new JComboBox<>(temps);
    private JComboBox<String> comboTempSai2 = new JComboBox<>(temps);
    
    private JComboBox<String> comboPressaoEntr2 = new JComboBox<>(pressoes);
    private JComboBox<String> comboPressaoSai2 = new JComboBox<>(pressoes);
    
    private JPanel painelDados;
    private JPanel painelEntrada;
    private JPanel painelSaida;
    private JPanel painelEntrada2;
    private JPanel painelSaida2;
        
    private ControlConverte controlConverte = new ControlConverte();
    
    private final int precision = 2;
    
    public ViewRegeneradorPanelRankine(ControlRegeneradorPanelRankine ctrlRegenerador){
        fieldTempSai.setName("tempSaiRegenerador1");
        fieldTempEntr.setName("tempEntrRegenerador1");
        fieldPressaoEntr.setName("pressaoEntrRegenerador1");
        fieldPressaoSai.setName("pressaoSaiRegenerador1");
        fieldTempSai2.setName("tempSaiRegenerador2");
        fieldTempEntr2.setName("tempEntrRegenerador2");
        fieldPressaoEntr2.setName("pressaoEntrRegenerador2");
        fieldPressaoSai2.setName("pressaoSaiRegenerador2");
                
        painelDados = new JPanel(new GridBagLayout());
        painelEntrada = new JPanel(new GridBagLayout());
        painelSaida = new JPanel(new GridBagLayout());
        painelEntrada2 = new JPanel(new GridBagLayout());
        painelSaida2 = new JPanel(new GridBagLayout());
        
        painelEntrada.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1, Color.lightGray, Color.lightGray), "Entrada Turbina", 1, 2, new Font("Times New Roman", 1, 12), Color.darkGray));
        painelSaida.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1, Color.lightGray, Color.lightGray), "Saída Condensador", 1, 2, new Font("Times New Roman", 1, 12), Color.darkGray));
        painelEntrada2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1, Color.lightGray, Color.lightGray), "Entrada Bomba", 1, 2, new Font("Times New Roman", 1, 12), Color.darkGray));
        painelSaida2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1, Color.lightGray, Color.lightGray), "Saída Evaporador", 1, 2, new Font("Times New Roman", 1, 12), Color.darkGray));

                
        //this.setLayout(new FlowLayout(FlowLayout.LEADING));
        this.setLayout(new GridBagLayout());
        this.setName("Regenerador");
        //this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        
        GridBagConstraints g = new GridBagConstraints();
        
        //INSERINDOS OS ELEMENTOS NO JPANEL DE ENTRADA
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 1;
        g.weighty = 1;
        g.weightx = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelEntrada.add(labelTempEntr, g);
        
        g.gridx = 1;
        g.gridy = 0;
        g.gridwidth = 2;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelEntrada.add(fieldTempEntr, g);
        
        g.gridx = 3;
        g.gridy = 0;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelEntrada.add(comboTempEntr, g);
        
        g.gridx = 0;
        g.gridy = 1;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelEntrada.add(labelPressaoEntr, g);
        
        g.gridx = 1;
        g.gridy = 1;
        g.gridwidth = 2;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelEntrada.add(fieldPressaoEntr, g);
        
        g.gridx = 3;
        g.gridy = 1;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelEntrada.add(comboPressaoEntr, g);

        
        //INSERINDO OS ELEMENTOS NO JPANEL DE SAÍDA
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelSaida.add(labelTempSai, g);
        
        g.gridx = 1;
        g.gridy = 0;
        g.gridwidth = 2;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelSaida.add(fieldTempSai, g);
        
        g.gridx = 3;
        g.gridy = 0;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelSaida.add(comboTempSai, g);
        
        g.gridx = 0;
        g.gridy = 1;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelSaida.add(labelPressaoSai, g);
        
        g.gridx = 1;
        g.gridy = 1;
        g.gridwidth = 2;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelSaida.add(fieldPressaoSai, g);
        
        g.gridx = 3;
        g.gridy = 1;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelSaida.add(comboPressaoSai, g);
        
        //INSERINDO OS ELEMENTOS NO JPANEL ENTRADA 2
        
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 1;
        g.weighty = 1;
        g.weightx = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelEntrada2.add(labelTempEntr2, g);
        
        g.gridx = 1;
        g.gridy = 0;
        g.gridwidth = 2;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelEntrada2.add(fieldTempEntr2, g);
        
        g.gridx = 3;
        g.gridy = 0;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelEntrada2.add(comboTempEntr2, g);
        
        g.gridx = 0;
        g.gridy = 1;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelEntrada2.add(labelPressaoEntr2, g);
        
        g.gridx = 1;
        g.gridy = 1;
        g.gridwidth = 2;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelEntrada2.add(fieldPressaoEntr2, g);
        
        g.gridx = 3;
        g.gridy = 1;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelEntrada2.add(comboPressaoEntr2, g);
        
        //INSERINDO ELEMENTOS NO JPNEL SAIDA 2
        
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelSaida2.add(labelTempSai2, g);
        
        g.gridx = 1;
        g.gridy = 0;
        g.gridwidth = 2;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelSaida2.add(fieldTempSai2, g);
        
        g.gridx = 3;
        g.gridy = 0;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelSaida2.add(comboTempSai2, g);
        
        g.gridx = 0;
        g.gridy = 1;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelSaida2.add(labelPressaoSai2, g);
        
        g.gridx = 1;
        g.gridy = 1;
        g.gridwidth = 2;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelSaida2.add(fieldPressaoSai2, g);
        
        g.gridx = 3;
        g.gridy = 1;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelSaida2.add(comboPressaoSai2, g);
        
        //DADOS DA TEMPERATURA
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 4;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(painelEntrada, g);
        
        //DADOS DA PRESSÃO
        g.gridx = 0;
        g.gridy = 1;
        g.gridwidth = 4;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(painelSaida, g);
        
        //PAINEL 2 ENTRADA
        
        g.gridx = 0;
        g.gridy = 2;
        g.gridwidth = 4;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(painelEntrada2, g);
        
        //PAINEL 2 SAIDA
        
        g.gridx = 0;
        g.gridy = 3;
        g.gridwidth = 4;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(painelSaida2, g);
        
        //DADOS DA MASSA
        g.gridx = 0;
        g.gridy = 5;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(labelMassa, g);
        
        g.gridx = 1;
        g.gridy = 5;
        g.gridwidth = 2;
        painelDados.add(fieldMassa, g);
        
        g.gridx = 3;
        g.gridy = 5;
        g.gridwidth = 1;
        painelDados.add(comboMassa, g);
        
        //DADOS DO DELTA DE PRESSÃO
        g.gridx = 0;
        g.gridy = 6;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(labelDelta, g);
        
        g.gridx = 1;
        g.gridy = 6;
        g.gridwidth = 2;
        painelDados.add(fieldEfetiv, g);
        
        //DADOS DA EFETIVIDADE
        g.gridx = 0;
        g.gridy = 7;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(labelEfetiv, g);
        
        g.gridx = 1;
        g.gridy = 7;
        g.gridwidth = 2;
        painelDados.add(fieldDelta, g);
  
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.PAGE_START;
        this.add(painelDados, g);
    
        fieldMassa.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //fieldMassa.setBackground(Color.white);
                fieldMassa.showPopup();
                fieldMassa.getEditor().selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                fieldMassa.setSelectedItem(controlConverte.round(Double.parseDouble(fieldMassa.getSelectedItem().toString()),precision));
                ctrlRegenerador.atualizaMassa();
            }
        });
        fieldMassa.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                fieldMassa.hidePopup();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        
        fieldTempEntr.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //fieldMassa.setBackground(Color.white);
                fieldTempEntr.showPopup();
                fieldTempEntr.getEditor().selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                fieldTempEntr.setSelectedItem(controlConverte.round(Double.parseDouble(fieldTempEntr.getSelectedItem().toString()),precision));
                ctrlRegenerador.atualizaTempEntrada();
                ctrlRegenerador.getCtrlPrincipal().getStart().autalizaFieldsTemp(fieldTempEntr);
            }
        });fieldTempEntr.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                fieldTempEntr.hidePopup();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        
        fieldTempSai.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //fieldMassa.setBackground(Color.white);
                fieldTempSai.showPopup();
                fieldTempSai.getEditor().selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                fieldTempSai.setSelectedItem(controlConverte.round(Double.parseDouble(fieldTempSai.getSelectedItem().toString()),precision));
                ctrlRegenerador.atualizaTempSaida();
                ctrlRegenerador.getCtrlPrincipal().getStart().autalizaFieldsTemp(fieldTempSai);
            }
        });
        fieldTempSai.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                fieldTempSai.hidePopup();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        
        fieldPressaoEntr.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //fieldMassa.setBackground(Color.white);
                fieldPressaoEntr.showPopup();
                fieldPressaoEntr.getEditor().selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                fieldPressaoEntr.setSelectedItem(controlConverte.round(Double.parseDouble(fieldPressaoEntr.getSelectedItem().toString()),precision));
                ctrlRegenerador.atualizaPressaoEntrada();
                ctrlRegenerador.getCtrlPrincipal().getStart().atualizaFieldsPressao(fieldPressaoEntr);

            }
        });
        fieldPressaoEntr.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                fieldPressaoEntr.hidePopup();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        
        fieldPressaoSai.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //fieldMassa.setBackground(Color.white);
                fieldPressaoSai.showPopup();
                fieldPressaoSai.getEditor().selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                fieldPressaoSai.setSelectedItem(controlConverte.round(Double.parseDouble(fieldPressaoSai.getSelectedItem().toString()),precision));
                ctrlRegenerador.atualizaPressaoSaida();
                ctrlRegenerador.getCtrlPrincipal().getStart().atualizaFieldsPressao(fieldPressaoSai);
            }
        });
        fieldPressaoSai.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                fieldPressaoSai.hidePopup();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        fieldTempEntr2.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //fieldMassa.setBackground(Color.white);
                fieldTempEntr2.showPopup();
                fieldTempEntr2.getEditor().selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                fieldTempEntr2.setSelectedItem(controlConverte.round(Double.parseDouble(fieldTempEntr2.getSelectedItem().toString()),precision));
                ctrlRegenerador.atualizaTempEntrada2();
                ctrlRegenerador.getCtrlPrincipal().getStart().autalizaFieldsTemp(fieldTempEntr2);
            }
        });
        fieldTempEntr2.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                fieldTempEntr2.hidePopup();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        fieldTempSai2.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //fieldMassa.setBackground(Color.white);
                fieldTempSai2.showPopup();
                fieldTempSai2.getEditor().selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                fieldTempSai2.setSelectedItem(controlConverte.round(Double.parseDouble(fieldTempSai2.getSelectedItem().toString()),precision));
                ctrlRegenerador.atualizaTempSaida2();
                ctrlRegenerador.getCtrlPrincipal().getStart().autalizaFieldsTemp(fieldTempSai2);
            }
        });
        fieldTempSai2.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                fieldTempSai2.hidePopup();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        
        fieldPressaoEntr2.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //fieldMassa.setBackground(Color.white);
                fieldPressaoEntr2.showPopup();
                fieldPressaoEntr2.getEditor().selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                fieldPressaoEntr2.setSelectedItem(controlConverte.round(Double.parseDouble(fieldPressaoEntr2.getSelectedItem().toString()),precision));
                ctrlRegenerador.atualizaPressaoEntrada2();
                ctrlRegenerador.getCtrlPrincipal().getStart().atualizaFieldsPressao(fieldPressaoEntr2);
            }
        });
        fieldPressaoEntr2.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                fieldPressaoEntr2.hidePopup();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        
        fieldPressaoSai2.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //fieldMassa.setBackground(Color.white);
                fieldPressaoSai2.showPopup();
                fieldPressaoSai2.getEditor().selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                fieldPressaoSai2.setSelectedItem(controlConverte.round(Double.parseDouble(fieldPressaoSai2.getSelectedItem().toString()),precision));
                ctrlRegenerador.atualizaPressaoSaida2();
                ctrlRegenerador.getCtrlPrincipal().getStart().atualizaFieldsPressao(fieldPressaoSai2);
            }
        });
        fieldPressaoSai2.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                fieldPressaoSai2.hidePopup();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        
        
        fieldDelta.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                fieldDelta.showPopup();
                fieldDelta.getEditor().selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                fieldDelta.setSelectedItem(controlConverte.round(Double.parseDouble(fieldDelta.getSelectedItem().toString()),precision));
                ctrlRegenerador.atualizaDeltaPressao();
            }
        });
        fieldDelta.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                fieldDelta.hidePopup();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        fieldEfetiv.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                fieldEfetiv.showPopup();
                fieldEfetiv.getEditor().selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                fieldEfetiv.setSelectedItem(controlConverte.round(Double.parseDouble(fieldEfetiv.getSelectedItem().toString()),precision));
                ctrlRegenerador.atualizaEfetividade();
                ctrlRegenerador.getCtrlPrincipal().setEff(Double.parseDouble(fieldEfetiv.getSelectedItem().toString()));
            }
        });
        fieldEfetiv.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                fieldEfetiv.hidePopup();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        comboMassa.addItemListener(new ItemListener() {
            String[] tipo = new String[2];
            int tip = 0;
            
            @Override
            public void itemStateChanged(ItemEvent ie) {
                tipo[tip] = ie.getItem().toString();
                tip++;
                if(tip == 2){
                    String valor = fieldMassa.getSelectedItem().toString();
                    if(!valor.isEmpty()){
                        fieldMassa.setSelectedItem(String.valueOf(controlConverte.converte(tipo[0],tipo[1],Double.parseDouble(valor))));                        
                    } 
                    tip = 0;
                }
            }
        });
        
        comboTempEntr.addItemListener(new ItemListener() {
            String[] tipo = new String[2];
            int tip = 0;
            
            @Override
            public void itemStateChanged(ItemEvent ie) {
                
                tipo[tip] = ie.getItem().toString();
                tip++;
                if(tip == 2){
                    String valor = fieldTempEntr.getSelectedItem().toString();
                    if(!valor.isEmpty()){
                        fieldTempEntr.setSelectedItem(String.valueOf(controlConverte.converte(tipo[0],tipo[1],Double.parseDouble(valor))));                        
                    } 
                    tip = 0;
                }
            }
        });
        
        comboTempSai.addItemListener(new ItemListener() {
            String[] tipo = new String[2];
            int tip = 0;
            
            @Override
            public void itemStateChanged(ItemEvent ie) {
                tipo[tip] = ie.getItem().toString();
                tip++;
                if(tip == 2){
                    String valor = fieldTempSai.getSelectedItem().toString();
                    if(!valor.isEmpty()){
                        fieldTempSai.setSelectedItem(String.valueOf(controlConverte.converte(tipo[0],tipo[1],Double.parseDouble(valor))));                        
                    } 
                    tip = 0;
                }
            }
        });
        
        comboPressaoEntr.addItemListener(new ItemListener() {
            String[] tipo = new String[2];
            int tip = 0;
            
            @Override
            public void itemStateChanged(ItemEvent ie) {
                
                tipo[tip] = ie.getItem().toString();
                tip++;
                if(tip == 2){
                    String valor = fieldPressaoEntr.getSelectedItem().toString();
                    if(!valor.isEmpty()){
                        fieldPressaoEntr.setSelectedItem(String.valueOf(controlConverte.converte(tipo[0],tipo[1],Double.parseDouble(valor)))); 
                    } 
                    tip = 0;
                }
            }
        });
        
        comboPressaoSai.addItemListener(new ItemListener() {
            String[] tipo = new String[2];
            int tip = 0;
            
            @Override
            public void itemStateChanged(ItemEvent ie) {
                
                tipo[tip] = ie.getItem().toString();
                tip++;
                if(tip == 2){
                    String valor = fieldPressaoSai.getSelectedItem().toString();
                    if(!valor.isEmpty()){
                        fieldPressaoSai.setSelectedItem(String.valueOf(controlConverte.converte(tipo[0],tipo[1],Double.parseDouble(valor))));                        
                    } 
                    tip = 0;
                }
            }
        });
        
        comboTempEntr2.addItemListener(new ItemListener() {
            String[] tipo = new String[2];
            int tip = 0;
            
            @Override
            public void itemStateChanged(ItemEvent ie) {
                
                tipo[tip] = ie.getItem().toString();
                tip++;
                if(tip == 2){
                    String valor = fieldTempEntr2.getSelectedItem().toString();
                    if(!valor.isEmpty()){
                        fieldTempEntr2.setSelectedItem(String.valueOf(controlConverte.converte(tipo[0],tipo[1],Double.parseDouble(valor))));                        
                    } 
                    tip = 0;
                }
            }
        });
        
        comboTempSai2.addItemListener(new ItemListener() {
            String[] tipo = new String[2];
            int tip = 0;
            
            @Override
            public void itemStateChanged(ItemEvent ie) {
                tipo[tip] = ie.getItem().toString();
                tip++;
                if(tip == 2){
                    String valor = fieldTempSai2.getSelectedItem().toString();
                    if(!valor.isEmpty()){
                        fieldTempSai2.setSelectedItem(String.valueOf(controlConverte.converte(tipo[0],tipo[1],Double.parseDouble(valor))));                        
                    } 
                    tip = 0;
                }
            }
        });
        
        comboPressaoEntr2.addItemListener(new ItemListener() {
            String[] tipo = new String[2];
            int tip = 0;
            
            @Override
            public void itemStateChanged(ItemEvent ie) {
                
                tipo[tip] = ie.getItem().toString();
                tip++;
                if(tip == 2){
                    String valor = fieldPressaoEntr2.getSelectedItem().toString();
                    if(!valor.isEmpty()){
                        fieldPressaoEntr2.setSelectedItem(String.valueOf(controlConverte.converte(tipo[0],tipo[1],Double.parseDouble(valor)))); 
                    } 
                    tip = 0;
                }
            }
        });
        
        comboPressaoSai2.addItemListener(new ItemListener() {
            String[] tipo = new String[2];
            int tip = 0;
            
            @Override
            public void itemStateChanged(ItemEvent ie) {
                
                tipo[tip] = ie.getItem().toString();
                tip++;
                if(tip == 2){
                    String valor = fieldPressaoSai2.getSelectedItem().toString();
                    if(!valor.isEmpty()){
                        fieldPressaoSai2.setSelectedItem(String.valueOf(controlConverte.converte(tipo[0],tipo[1],Double.parseDouble(valor))));                        
                    } 
                    tip = 0;
                }
            }
        });
    }

    public DropdownComboBox getFieldMassa() {
        return fieldMassa;
    }

    public void setFieldMassa(DropdownComboBox fieldMassa) {
        this.fieldMassa = fieldMassa;
    }

    public DropdownComboBox getFieldTempEntr() {
        return fieldTempEntr;
    }

    public void setFieldTempEntr(DropdownComboBox fieldTempEntr) {
        this.fieldTempEntr = fieldTempEntr;
    }

    public DropdownComboBox getFieldPressaoEntr() {
        return fieldPressaoEntr;
    }

    public void setFieldPressaoEntr(DropdownComboBox fieldPressaoEntr) {
        this.fieldPressaoEntr = fieldPressaoEntr;
    }

    public DropdownComboBox getFieldTempSai() {
        return fieldTempSai;
    }

    public void setFieldTempSai(DropdownComboBox fieldTempSai) {
        this.fieldTempSai = fieldTempSai;
    }

    public DropdownComboBox getFieldPressaoSai() {
        return fieldPressaoSai;
    }

    public void setFieldPressaoSai(DropdownComboBox fieldPressaoSai) {
        this.fieldPressaoSai = fieldPressaoSai;
    }

    public DropdownComboBox getFieldTempEntr2() {
        return fieldTempEntr2;
    }

    public void setFieldTempEntr2(DropdownComboBox fieldTempEntr2) {
        this.fieldTempEntr2 = fieldTempEntr2;
    }

    public DropdownComboBox getFieldTempSai2() {
        return fieldTempSai2;
    }

    public void setFieldTempSai2(DropdownComboBox fieldTempSai2) {
        this.fieldTempSai2 = fieldTempSai2;
    }

    public DropdownComboBox getFieldPressaoEntr2() {
        return fieldPressaoEntr2;
    }

    public void setFieldPressaoEntr2(DropdownComboBox fieldPressaoEntr2) {
        this.fieldPressaoEntr2 = fieldPressaoEntr2;
    }

    public DropdownComboBox getFieldPressaoSai2() {
        return fieldPressaoSai2;
    }

    public void setFieldPressaoSai2(DropdownComboBox fieldPressaoSai2) {
        this.fieldPressaoSai2 = fieldPressaoSai2;
    }
    
    

    public DropdownComboBox getFieldDelta() {
        return fieldDelta;
    }

    public void setFieldDelta(DropdownComboBox fieldDelta) {
        this.fieldDelta = fieldDelta;
    }

    public DropdownComboBox getFieldEfetiv() {
        return fieldEfetiv;
    }

    public void setFieldEfetiv(DropdownComboBox fieldEfetiv) {
        this.fieldEfetiv = fieldEfetiv;
    }

    public JComboBox<String> getComboMassa() {
        return comboMassa;
    }

    public void setComboMassa(JComboBox<String> comboMassa) {
        this.comboMassa = comboMassa;
    }

    public JComboBox<String> getComboTempEntr() {
        return comboTempEntr;
    }

    public void setComboTempEntr(JComboBox<String> comboTempEntr) {
        this.comboTempEntr = comboTempEntr;
    }

    public JComboBox<String> getComboTempSai() {
        return comboTempSai;
    }

    public void setComboTempSai(JComboBox<String> comboTempSai) {
        this.comboTempSai = comboTempSai;
    }

    public JComboBox<String> getComboPressaoEntr() {
        return comboPressaoEntr;
    }

    public void setComboPressaoEntr(JComboBox<String> comboPressaoEntr) {
        this.comboPressaoEntr = comboPressaoEntr;
    }

    public JComboBox<String> getComboPressaoSai() {
        return comboPressaoSai;
    }

    public void setComboPressaoSai(JComboBox<String> comboPressaoSai) {
        this.comboPressaoSai = comboPressaoSai;
    }
    
}
