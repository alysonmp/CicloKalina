/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo2.View;

import Control.ControlPrincipal;
import Control.Conversao.ControlConverte;
import Ciclo2.Control.ControlLateral;
import View.ViewEspera;
import Util.DropdownComboBox;
import View.ViewPrincipal;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author alysonmp
 */
public class ViewLateral extends JPanel{
            
    private JLabel labelCompressor = new JLabel("Fonte de Calor");
            
    private JLabel labelMassa = new JLabel("Massa (m): ");
    
    private JLabel labelTemp = new JLabel("Temperatura: ");
    private JLabel labelPressao = new JLabel("Pressão: ");
    
    private JLabel labelTempCond = new JLabel("<html><body>Temperatura <br>de Condensação: </html></body> ");
    private JLabel labelEfetiv = new JLabel("Efetividade (%): ");
    private JLabel labelKm = new JLabel("Km: ");
    
    private JLabel labelLimitTemp = new JLabel("");
    private JLabel labelLimitPressao = new JLabel("");
    
    //TEXTFIELDS
    private DropdownComboBox fieldMassa = new DropdownComboBox();
    
    private DropdownComboBox fieldTemp = new DropdownComboBox();
    private DropdownComboBox fieldPressao = new DropdownComboBox();
    
    private DropdownComboBox fieldTempCond = new DropdownComboBox();
    private DropdownComboBox fieldEfetiv = new DropdownComboBox();
    
    private DropdownComboBox fieldSup = new DropdownComboBox();
    private DropdownComboBox fieldPinch = new DropdownComboBox();
    private DropdownComboBox fieldKm = new DropdownComboBox();
    
    private String[] fluidos = {"Água", "Ar", "Compressor 1", "Compressor 2", "Compressor 3", "Compressor 4", "Compressor 5", "Gases"};
    private JComboBox comboCompressores = new JComboBox(fluidos);
    
    private String[] massas = {"kg/s", "kg/m", "kg/h", "lb/s", "lb/m", "lb/h"};
    private JComboBox<String> comboMassa = new JComboBox<>(massas);
    
    private String[] temps = {"K", "°C", "°F"};
    private JComboBox<String> comboTemp = new JComboBox<>(temps);
    private JComboBox<String> comboTempCond = new JComboBox<>(temps);
    
    private String[] pressoes = {"kPa", "atm", "bar"};
    private JComboBox<String> comboPressao = new JComboBox<>(pressoes);
    
    private JLabel fieldLimitTemp = new JLabel("");
    private JLabel fieldLimitPressao = new JLabel("");
    
    JLabel labelFluidos = new JLabel("Fluído");
    JComboBox<String> comboFluidos;
    
    //CHECKBOX
    JCheckBox checkSup = new JCheckBox("Superaquecimento");
    JCheckBox checkPinch = new JCheckBox("Pinch");
    
    //JBUTTON
    JButton botaoInicia = new JButton("Cálculos");
    
    JDialog dialogEspera;
    JFrame frameEspera;
    
    private JPanel painelDados;
    
    private ControlPrincipal ctrlPrincipal;
    
    private ControlConverte controlConverte = new ControlConverte();
    private ControlLateral controlLateral = null;
    
    //Casas decimais do JCombodow
    private final int precision = 2;
    
    public ViewLateral(ControlPrincipal ctrlPrincipal){
        this.ctrlPrincipal = ctrlPrincipal;
        controlLateral = new ControlLateral(ctrlPrincipal,this);
        
        painelDados = new JPanel(new GridBagLayout());
        
        this.setPreferredSize(new Dimension(400, 400));
        this.setLayout(new GridBagLayout());
        this.setName("Dados de Entrada");
        
        GridBagConstraints g = new GridBagConstraints();
        
        g.gridx = 0;
        g.gridy = 1;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(labelCompressor, g);
        
        g.gridx = 1;
        g.gridy = 1;
        g.gridwidth = 2;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(comboCompressores, g);

        g.gridx = 0;
        g.gridy = 2;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(labelTemp, g);
        
        g.gridx = 1;
        g.gridy = 2;
        g.gridwidth = 2;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(fieldTemp, g);
        
        g.gridx = 3;
        g.gridy = 2;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(comboTemp, g);
        
        g.gridx = 0;
        g.gridy = 3;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(labelPressao, g);
        
        g.gridx = 1;
        g.gridy = 3;
        g.gridwidth = 2;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(fieldPressao, g);
        
        g.gridx = 3;
        g.gridy = 3;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(comboPressao, g);
                
        g.gridx = 0;
        g.gridy = 4;
        g.gridwidth = 2;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(labelMassa, g);
        
        g.gridx = 1;
        g.gridy = 4;
        g.gridwidth = 2;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(fieldMassa, g);
        
        g.gridx = 3;
        g.gridy = 4;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(comboMassa, g);
        
        JLabel labelSeparator = new JLabel();
        labelSeparator.setFont(new Font("Times New Roman", 1, 12));
        labelSeparator.setText("Dados de Operação");
        
        JSeparator separator = new JSeparator();
        separator.setBackground(Color.BLACK);
        
        g.gridx = 0;
        g.gridy = 5;
        g.gridwidth = 1;
        g.insets = new Insets(10, 0, 10, 0);
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(labelSeparator, g);
        
        g.gridx = 1;
        g.gridy = 5;
        g.gridwidth = 3;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(separator, g);
        
        List results = this.ctrlPrincipal.getFluidos();
        Iterator iterator = results.iterator();
        
        Vector<String> fluidos = new Vector<>();
        while(iterator.hasNext()){
            fluidos.add((String) iterator.next());
        }
        comboFluidos = new JComboBox<String>(fluidos);
        comboFluidos.setFont(new Font("Arial", 0, 11));
        
        g.gridx = 0;
        g.gridy = 6;
        g.gridwidth = 1;
        g.insets = new Insets(0, 0, 0, 0);
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(labelFluidos, g);
        
        g.gridx = 1;
        g.gridy = 6;
        g.gridwidth = 3;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(comboFluidos, g);
        
        g.gridx = 0;
        g.gridy = 7;
        g.gridwidth = 1;
        g.insets = new Insets(0, 0, 0, 0);
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(labelTempCond, g);
        
        g.gridx = 1;
        g.gridy = 7;
        g.gridwidth = 2;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(fieldTempCond, g);
        
        g.gridx = 3;
        g.gridy = 7;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(comboTempCond, g);
        
        g.gridx = 0;
        g.gridy = 8;
        g.gridwidth = 1;
        g.insets = new Insets(0, 0, 5, 0);
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(checkSup, g);
        
        g.gridx = 1;
        g.gridy = 8;
        g.gridwidth = 2;
        painelDados.add(fieldSup, g);
        
        g.gridx = 0;
        g.gridy = 9;
        g.gridwidth = 1;
        painelDados.add(checkPinch, g);
        
        g.gridx = 1;
        g.gridy = 9;
        g.gridwidth = 2;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(fieldPinch, g);
        
        g.gridx = 0;
        g.gridy = 10;
        g.gridwidth = 3;
        painelDados.add(labelKm, g);
        
        g.gridx = 1;
        g.gridy = 10;
        g.gridwidth = 2;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(fieldKm, g);
        
        g.gridx = 0;
        g.gridy = 11;
        g.gridwidth = 3;
        g.insets = new Insets(20, 0, 0, 0);
        painelDados.add(labelLimitTemp, g);
        
        g.gridx = 3;
        g.gridy = 11;
        g.gridwidth = 1;
        painelDados.add(fieldLimitTemp, g);
        
        g.gridx = 0;
        g.gridy = 12;
        g.gridwidth = 3;
        g.insets = new Insets(10, 0, 0, 0);
        painelDados.add(labelLimitPressao, g);
        
        g.gridx = 3;
        g.gridy = 12;
        g.gridwidth = 1;
        painelDados.add(fieldLimitPressao, g);
        
        g.gridx = 3;
        g.gridy = 13;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(botaoInicia, g);
        
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.PAGE_START;
        this.add(painelDados, g);
        
        labelLimitPressao.setText("Pressão Máxima: ");
        labelLimitTemp.setText("Temperatura Máxima: ");
        fieldLimitPressao.setText(controlConverte.round(ctrlPrincipal.getPMax(),2)+"");
        fieldLimitTemp.setText(controlConverte.round(ctrlPrincipal.getTMax(),2)+"");
        
        fieldPinch.getEditor().setItem("0");
        fieldSup.getEditor().setItem("0");
        
        controlLateral.getValues();
        
        if(!fieldPinch.getSelectedItem().toString().equals("0")){
            fieldPinch.setEnabled(true);
            checkPinch.setSelected(true);
        }else{
            fieldPinch.setEnabled(false);
            checkPinch.setSelected(false);
        }

        if(!fieldSup.getSelectedItem().toString().equals("0")){
            fieldSup.setEnabled(true);
            checkSup.setSelected(true);
        }else{
            fieldSup.setEnabled(false);
            checkSup.setSelected(false);
        }

        fieldTemp.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                fieldTemp.showPopup();
                fieldTemp.getEditor().selectAll();
               
            }

            @Override
            public void focusLost(FocusEvent e) {
                /*if(Double.parseDouble(fieldTemp.getSelectedItem().toString()) > ctrlPrincipal.getTMax()){
                    JOptionPane.showMessageDialog(null,"Valor da temperatura acima do permitido!");
                    fieldTemp.setSelectedItem("0.0");
                }else{*/
                    //fieldTemp.setSelectedItem(controlConverte.round(Double.parseDouble(fieldTemp.getSelectedItem().toString()),precision));
                    controlLateral.atualizaTemperatura();
                //}
            }
        });fieldTemp.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
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
                fieldPressao.showPopup();
                fieldPressao.getEditor().selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                /*if(Double.parseDouble(fieldPressao.getSelectedItem().toString()) > ctrlPrincipal.getPMax()){
                    JOptionPane.showMessageDialog(null,"Valor da pressão acima do permitido!");
                    fieldPressao.setSelectedItem("0.0");
                }else{*/
                    //fieldPressao.setSelectedItem(controlConverte.round(Double.parseDouble(fieldPressao.getSelectedItem().toString()),precision));
                    controlLateral.atualizaPressao();
                //}
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
        fieldMassa.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                fieldMassa.showPopup();
                fieldMassa.getEditor().selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                controlLateral.atualizaMassa();
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
        fieldTempCond.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                fieldTempCond.showPopup();
                fieldTempCond.getEditor().selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                fieldTempCond.setSelectedItem(controlConverte.round(Double.parseDouble(fieldTempCond.getSelectedItem().toString()),precision));
                controlLateral.atualizaTemperaturaCond();
            }
        });
        fieldTempCond.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                fieldTempCond.hidePopup();
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
                controlLateral.atualizaEfetividade();
                ViewPrincipal viewPrincipal = ctrlPrincipal.getViewPrincipal();
                for(int i=0;i<viewPrincipal.getPainelCiclos().getComponentCount();i++)
                   System.out.println(viewPrincipal.getPainelCiclos().getComponent(i));
                
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
        
        fieldSup.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                fieldSup.showPopup();
                fieldSup.getEditor().selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                fieldSup.setSelectedItem(controlConverte.round(Double.parseDouble(fieldSup.getSelectedItem().toString()),precision));
                controlLateral.atualizaSuperaquecimento();
            }
        });
        fieldSup.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                fieldSup.hidePopup();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        checkSup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkSup.isSelected()){
                    ViewLateral.this.fieldSup.getEditor().setItem("");
                    ViewLateral.this.fieldSup.setEnabled(true);
                }else{
                    ViewLateral.this.fieldSup.getEditor().setItem("0");
                    ViewLateral.this.fieldSup.setEnabled(false);
                }
            }
        });
        
        fieldPinch.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                fieldPinch.showPopup();
                fieldPinch.getEditor().selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                fieldPinch.setSelectedItem(controlConverte.round(Double.parseDouble(fieldPinch.getSelectedItem().toString()),precision));
                controlLateral.atualizaPinch();
            }
        });
        fieldPinch.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                fieldPinch.hidePopup();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        checkPinch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkPinch.isSelected()){
                    ViewLateral.this.fieldPinch.getEditor().setItem("");
                    ViewLateral.this.fieldPinch.setEnabled(true);
                }else{
                    ViewLateral.this.fieldPinch.getEditor().setItem("0");
                    ViewLateral.this.fieldPinch.setEnabled(false);
                }
            }
        });
        
        
        botaoInicia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fieldTemp.getItemCount() == 0 || fieldTemp.getSelectedItem().equals("")){
                    JOptionPane.showMessageDialog(null, "Campo Temperatura não preenchido\n");
                    return;
                }else if(fieldPressao.getItemCount() == 0 || fieldPressao.getSelectedItem().equals("")){
                    JOptionPane.showMessageDialog(null, "Campo Pressão não preenchido\n");
                    return;
                }else if(fieldMassa.getItemCount() == 0 || fieldMassa.getSelectedItem().equals("")){
                    JOptionPane.showMessageDialog(null, "Campo Massa não preenchido\n");
                    return;
                }
                
                Thread t1 = new Thread(new Runnable() {
                @Override
                    public void run() {
                        ctrlPrincipal.iniciaCalculos();
                    }
                });
                t1.start();
                
                new Thread(new Runnable() {
                    @Override
                    public void run() {    
                        try {
                            ctrlPrincipal.getViewPrincipal().getFramePrincipal().setEnabled(false);
                            ViewEspera painelEspera = new ViewEspera(ViewLateral.this.ctrlPrincipal);

                            frameEspera = new JFrame();
                            frameEspera.setUndecorated(true);
                            frameEspera.add(painelEspera.getPanelEspera());
                            frameEspera.setAlwaysOnTop(true);
                            frameEspera.setBounds((ctrlPrincipal.getViewPrincipal().getFramePrincipal().getWidth()/2)-100, (ctrlPrincipal.getViewPrincipal().getFramePrincipal().getHeight()/2)-50, 200, 100);
                            frameEspera.setResizable(false);
                            frameEspera.setVisible(true);
                            frameEspera.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            frameEspera.revalidate();
                            frameEspera.repaint();

                            t1.join();
                            //frameEspera.dispose();
                            ctrlPrincipal.getViewPrincipal().getFramePrincipal().setEnabled(true);
                        }catch(InterruptedException ex){
                            Logger.getLogger(ViewLateral.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }).start();
            }
        });
        
        /*comboMassa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //Conversao
                String valor = fieldMassa.getSelectedItem().toString();
                if(!valor.isEmpty()){
                    fieldMassa.setSelectedItem(String.valueOf(controlConverte.converte(comboMassa.getSelectedItem().toString(),Double.parseDouble(valor))));
                }           
            }
        });*/
        
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
                        fieldLimitTemp.setText(String.valueOf(controlConverte.converte(tipo[0],tipo[1],Double.parseDouble(fieldLimitTemp.getText()))));
                        
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
                        fieldLimitPressao.setText(String.valueOf(controlConverte.converte(tipo[0],tipo[1],Double.parseDouble(fieldLimitPressao.getText()))));

                    } 
                    tip = 0;
                }
            }
        });
        
        comboTempCond.addItemListener(new ItemListener() {
            
            String[] tipo = new String[2];
            int tip = 0;
            
            @Override
            public void itemStateChanged(ItemEvent ie) {
                
                tipo[tip] = ie.getItem().toString();
                tip++;
                if(tip == 2){
                    String valor = fieldTempCond.getSelectedItem().toString();
                    if(!valor.isEmpty()){
                        fieldTempCond.setSelectedItem(String.valueOf(controlConverte.converte(tipo[0],tipo[1],Double.parseDouble(valor))));
                    } 
                    tip = 0;
                }
            }
        });
        
        /*comboMassa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //Conversao
                String valor = fieldMassa.getSelectedItem().toString();
                if(!valor.isEmpty()){
                    fieldMassa.setSelectedItem(String.valueOf(controlConverte.converte(comboMassa.getSelectedItem().toString(),Double.parseDouble(valor))));
                }           
            }
        });
        
        comboPressao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //Conversao
                String valor = fieldPressao.getSelectedItem().toString();
                if(!valor.isEmpty()){
                    fieldPressao.setSelectedItem(String.valueOf(controlConverte.converte(comboPressao.getSelectedItem().toString(),Double.parseDouble(valor))));
                }           
            }
        });
        
        comboTemp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //Conversao
                String valor = fieldTemp.getSelectedItem().toString();
                if(!valor.isEmpty()){
                    fieldTemp.setSelectedItem(String.valueOf(controlConverte.converte(comboTemp.getSelectedItem().toString(),Double.parseDouble(valor))));
                }         
            }
        });
        
        comboTempCond.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //Conversao
                String valor = fieldTempCond.getSelectedItem().toString();
                if(!valor.isEmpty()){
                    fieldTempCond.setSelectedItem(String.valueOf(controlConverte.converte(comboTempCond.getSelectedItem().toString(),Double.parseDouble(valor))));
                } 
            }
        });*/
        
        comboCompressores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int FON = comboCompressores.getSelectedIndex()+1;
                if(FON == 1 || FON == 2 || FON == 8){
                    fieldTemp.setEnabled(true);
                    fieldPressao.setEnabled(true);
                    fieldMassa.setEnabled(true);
                    
                    comboTemp.setEnabled(true);
                    comboPressao.setEnabled(true);
                    comboMassa.setEnabled(true);
                }else{
                    double Tf = 0,Pf = 0,m=0;
                    fieldTemp.setEnabled(false);
                    fieldPressao.setEnabled(false);
                    fieldMassa.setEnabled(false);
                    
                    comboTemp.setEnabled(false);
                    comboPressao.setEnabled(false);
                    comboMassa.setEnabled(false);
                            
                    switch(FON){
                    case 3:
                        m = 55.54;
                        Tf = 415.15;
                        Pf = 1144.4;
                        break;
                    case 4:
                        m = 55.54;
                        Tf = 417.15;
                        Pf = 3133.8;
                        break;
                    case 5: 
                        m = 55.54;
                        Tf = 418.15;
                        Pf = 8825.6;
                        break;
                    case 6:
                        m = 55.54;
                        Tf = 408.15;
                        Pf = 25109;
                        break;
                    case 7: 
                        m = 61.78;
                        Tf = 431.15;
                        Pf = 8196.1;
                        break;
                }

                    fieldMassa.setSelectedItem(""+m);
                    fieldPressao.setSelectedItem(""+Pf);
                    fieldTemp.setSelectedItem(""+Tf);
                }
            }
        });
        
        fieldKm.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                fieldKm.showPopup();
                fieldKm.getEditor().selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                fieldKm.setSelectedItem(controlConverte.round(Double.parseDouble(fieldKm.getSelectedItem().toString()),precision));
                controlLateral.atualizaTemperaturaCond();
            }
        });
        fieldKm.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                fieldKm.hidePopup();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        
        comboFluidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                ctrlPrincipal.calculaLimites();
                labelLimitPressao.setText("Pressão Máxima: ");
                labelLimitTemp.setText("Temperatura Máxima: ");
                fieldLimitPressao.setText(controlConverte.round(ctrlPrincipal.getPMax(),2)+"");
                fieldLimitTemp.setText(controlConverte.round(ctrlPrincipal.getTMax(),2)+"");
            }
        });
    }

    public JPanel getPainelDados() {
        return painelDados;
    }
    
    public DropdownComboBox getFieldMassa() {
        return fieldMassa;
    }

    public void setFieldMassa(DropdownComboBox fieldMassa) {
        this.fieldMassa = fieldMassa;
    }

    public JComboBox<String> getComboMassa() {
        return comboMassa;
    }

    public void setComboMassa(JComboBox<String> comboMassa) {
        this.comboMassa = comboMassa;
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

    public DropdownComboBox getFieldTempCond() {
        return fieldTempCond;
    }

    public void setFieldTempCond(DropdownComboBox fieldTempCond) {
        this.fieldTempCond = fieldTempCond;
    }

    public DropdownComboBox getFieldEfetiv() {
        return fieldEfetiv;
    }

    public void setFieldEfetiv(DropdownComboBox fieldEfetiv) {
        this.fieldEfetiv = fieldEfetiv;
    }

    public DropdownComboBox getFieldSup() {
        return fieldSup;
    }

    public void setFieldSup(DropdownComboBox fieldSup) {
        this.fieldSup = fieldSup;
    }

    public DropdownComboBox getFieldPinch() {
        return fieldPinch;
    }

    public void setFieldPinch(DropdownComboBox fieldPinch) {
        this.fieldPinch = fieldPinch;
    }

    public JComboBox getComboCompressores() {
        return comboCompressores;
    }

    public void setComboCompressores(JComboBox comboCompressores) {
        this.comboCompressores = comboCompressores;
    }

    public JComboBox<String> getComboTemp() {
        return comboTemp;
    }

    public void setComboTemp(JComboBox<String> comboTemp) {
        this.comboTemp = comboTemp;
    }

    public JComboBox<String> getComboTempCond() {
        return comboTempCond;
    }

    public void setComboTempCond(JComboBox<String> comboTempCond) {
        this.comboTempCond = comboTempCond;
    }

    public JComboBox<String> getComboPressao() {
        return comboPressao;
    }

    public void setComboPressao(JComboBox<String> comboPressao) {
        this.comboPressao = comboPressao;
    }

    public JCheckBox getCheckSup() {
        return checkSup;
    }

    public void setCheckSup(JCheckBox checkSup) {
        this.checkSup = checkSup;
    }

    public JCheckBox getCheckPinch() {
        return checkPinch;
    }

    public void setCheckPinch(JCheckBox checkPinch) {
        this.checkPinch = checkPinch;
    }

    public JLabel getLabelLimitTemp() {
        return labelLimitTemp;
    }

    public void setLabelLimitTemp(JLabel labelLimitTemp) {
        this.labelLimitTemp = labelLimitTemp;
    }

    public JLabel getLabelLimitPressao() {
        return labelLimitPressao;
    }

    public void setLabelLimitPressao(JLabel labelLimitPressao) {
        this.labelLimitPressao = labelLimitPressao;
    }

    public JFrame getFrameEspera() {
        return frameEspera;
    }

    public void setFrameEspera(JFrame frameEspera) {
        this.frameEspera = frameEspera;
    }

    public DropdownComboBox getFieldKm() {
        return fieldKm;
    }

    public void setFieldKm(DropdownComboBox fieldKm) {
        this.fieldKm = fieldKm;
    }
    
    public JComboBox<String> getComboFluidos() {
        return comboFluidos;
    }

    public void setComboFluidos(JComboBox<String> comboFluidos) {
        this.comboFluidos = comboFluidos;
    } 

    public JLabel getFieldLimitTemp() {
        return fieldLimitTemp;
    }

    public void setFieldLimitTemp(JLabel fieldLimitTemp) {
        this.fieldLimitTemp = fieldLimitTemp;
    }

    public JLabel getFieldLimitPressao() {
        return fieldLimitPressao;
    }

    public void setFieldLimitPressao(JLabel fieldLimitPressao) {
        this.fieldLimitPressao = fieldLimitPressao;
    }
}
