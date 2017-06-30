/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Condensador;

import Control.Condensador.ControlCondensadorPanelRankine;
import Control.Conversao.ControlConverte;
import Util.DropdownComboBox;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusAdapter;
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
import javax.swing.JTextField;

/**
 *
 * @author alysonmp
 */
public class ViewCondensadorPanelRankine extends JPanel{
    
    //LABELS 
    private JLabel labelMassa = new JLabel("Massa (m): ");
    
    private JLabel labelTempEntr = new JLabel("Temperatura: ");
    private JLabel labelPressaoEntr = new JLabel("Pressão: ");
    
    private JLabel labelTempSai = new JLabel("Temperatura: ");
    private JLabel labelPressaoSai = new JLabel("Pressão: ");
    
    private JLabel labelTempCond = new JLabel("<html><body>Temperatura <br>de Condensação: </html></body>");
    private JLabel labelDelta = new JLabel("Delta de Pressão: ");
    
    //TEXTFIELDS
    private DropdownComboBox fieldMassa = new DropdownComboBox();
    
    private DropdownComboBox fieldTempEntr = new DropdownComboBox();
    private DropdownComboBox fieldPressaoEntr = new DropdownComboBox();
    
    private DropdownComboBox fieldTempSai = new DropdownComboBox();
    private DropdownComboBox fieldPressaoSai = new DropdownComboBox();
    
    private DropdownComboBox fieldTempCond = new DropdownComboBox();
    private DropdownComboBox fieldDelta = new DropdownComboBox();
    
    //COMBOBOX
    private String[] massas = {"kg/s", "kg/m", "kg/h", "lb/s", "lb/m", "lb/h"};
    private JComboBox<String> comboMassa = new JComboBox<>(massas);
    
    private String[] temps = {"K", "°C", "°F"};
    private JComboBox<String> comboTempEntr = new JComboBox<>(temps);
    private JComboBox<String> comboTempSai = new JComboBox<>(temps);
    private JComboBox<String> comboTempCond = new JComboBox<>(temps);
    
    private String[] pressoes = {"kPa", "atm", "bar"};
    private JComboBox<String> comboPressaoEntr = new JComboBox<>(pressoes);
    private JComboBox<String> comboPressaoSai = new JComboBox<>(pressoes);
    
    //JPANELS
    private JPanel painelDados;
    private JPanel painelEntrada;
    private JPanel painelSaida;
    
    ControlCondensadorPanelRankine ctrlCondensador;
    private ControlConverte controlConverte = new ControlConverte();;
    
    private final int precision = 2;
    
    public ViewCondensadorPanelRankine(ControlCondensadorPanelRankine ctrlCondensador){
        this.ctrlCondensador = ctrlCondensador;
        
        //INICIALIZA NOMES
        fieldTempSai.setName("tempSaiCondensador");
        
        //INICIALIZANDO OS JPANELS
        painelDados = new JPanel(new GridBagLayout());
        painelEntrada = new JPanel(new GridBagLayout());
        painelSaida = new JPanel(new GridBagLayout());
        
        painelEntrada.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1, Color.lightGray, Color.lightGray), "Entrada", 1, 2, new Font("Times New Roman", 1, 12), Color.darkGray));
        painelSaida.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1, Color.lightGray, Color.lightGray), "Saída", 1, 2, new Font("Times New Roman", 1, 12), Color.darkGray));
        
        //this.setLayout(new FlowLayout(FlowLayout.LEADING));
        this.setLayout(new GridBagLayout());
        this.setName("Condensador");
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
        
        //DADOS DA TEMPERATURA DE ENTRADA
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 4;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(painelEntrada, g);
        
        //DADOS DA TEMPERATURA DE SAÍDA
        g.gridx = 0;
        g.gridy = 1;
        g.gridwidth = 4;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(painelSaida, g);
        
        //DADOS DA MASSA
        g.gridx = 0;
        g.gridy = 2;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(labelMassa, g);
        
        g.gridx = 1;
        g.gridy = 2;
        g.gridwidth = 2;
        painelDados.add(fieldMassa, g);
        
        g.gridx = 3;
        g.gridy = 2;
        g.gridwidth = 1;
        painelDados.add(comboMassa, g);
        
        //DADOS DA TEMPERATURA DE CONDENSAÇÃO
        g.gridx = 0;
        g.gridy = 3;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(labelTempCond, g);
        
        g.gridx = 1;
        g.gridy = 3;
        g.gridwidth = 2;
        painelDados.add(fieldTempCond, g);
        
        g.gridx = 3;
        g.gridy = 3;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(comboTempCond, g);
        
        //DADOS DO DELTA DE PRESSÃO
        g.gridx = 0;
        g.gridy = 4;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(labelDelta, g);
        
        g.gridx = 1;
        g.gridy = 4;
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
                ctrlCondensador.atualizaMassa();
                ViewCondensadorPanelRankine.this.ctrlCondensador.getCtrlPrincipal().ajustaMassa(fieldMassa.getSelectedItem().toString());
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
                ctrlCondensador.atualizaTempEntrada();
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
                ctrlCondensador.atualizaTempSaida();
                ctrlCondensador.getCtrlPrincipal().getStart().autalizaFields(fieldTempSai);
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
                ctrlCondensador.atualizaPressaoEntrada();
                ctrlCondensador.getCtrlPrincipal().getStart().atualizaFieldsPressao(fieldPressaoEntr);
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
                ctrlCondensador.atualizaPressaoSaida();
                ctrlCondensador.getCtrlPrincipal().getStart().atualizaFieldsPressao(fieldPressaoSai);
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
        
        fieldTempCond.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //fieldMassa.setBackground(Color.white);
                fieldTempCond.showPopup();
                fieldTempCond.getEditor().selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                fieldTempCond.setSelectedItem(controlConverte.round(Double.parseDouble(fieldTempCond.getSelectedItem().toString()),precision));
                ctrlCondensador.atualizaTempCond();
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
        
        fieldDelta.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //fieldMassa.setBackground(Color.white);
                fieldDelta.showPopup();
                fieldDelta.getEditor().selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                fieldDelta.setSelectedItem(controlConverte.round(Double.parseDouble(fieldDelta.getSelectedItem().toString()),precision));
                ctrlCondensador.atualizaDelta();
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

    public DropdownComboBox getFieldTempCond() {
        return fieldTempCond;
    }

    public void setFieldTempCond(DropdownComboBox fieldTempCond) {
        this.fieldTempCond = fieldTempCond;
    }

    public DropdownComboBox getFieldDelta() {
        return fieldDelta;
    }

    public void setFieldDelta(DropdownComboBox fieldDelta) {
        this.fieldDelta = fieldDelta;
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

    public JComboBox<String> getComboTempCond() {
        return comboTempCond;
    }

    public void setComboTempCond(JComboBox<String> comboTempCond) {
        this.comboTempCond = comboTempCond;
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
