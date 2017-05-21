/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Evaporador;

import ControlEvaporador.ControlEvaporadorPanelRankine;
import Util.DropdownComboBox;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author alysonmp
 */
public class ViewEvaporadorPanelRankine extends JPanel{
    
    //LABELS 
    private final JLabel labelMassa = new JLabel("Massa (m): ");
    
    private final JLabel labelTempEntr = new JLabel("Temperatura: ");
    private final JLabel labelPressaoEntr = new JLabel("Pressão: ");
    
    private final JLabel labelTempSai = new JLabel("Temperatura: ");
    private final JLabel labelPressaoSai = new JLabel("Pressão: ");
    
    private final JLabel labelDelta = new JLabel("Delta de Pressão: ");
    
    //TEXTFIELDS
    private JTextField fieldMassa = new JTextField(10);
    
    private DropdownComboBox fieldTempEntr = new DropdownComboBox(new Double[]{});
    
    private DropdownComboBox fieldPressaoEntr = new DropdownComboBox(new Double[]{});
    
    private DropdownComboBox fieldTempSai = new DropdownComboBox(new Double[]{});
    
    private DropdownComboBox fieldPressaoSai = new DropdownComboBox(new Double[]{});
    
    private DropdownComboBox fieldDelta = new DropdownComboBox(new Double[]{});
    
    //COMBOBOX
    private final String[] massas = {"kg/s", "kg/m", "kg/h", "lb/s", "lb/m", "lb/h"};
    private JComboBox<String> comboMassa = new JComboBox<>(massas);
    
    private final String[] temps = {"°C", "°F", "K"};
    private JComboBox<String> comboTempEntr = new JComboBox<>(temps);
    private JComboBox<String> comboTempSai = new JComboBox<>(temps);
    
    private final String[] pressoes = {"atm", "kPa", "bar"};
    private JComboBox<String> comboPressaoEntr = new JComboBox<>(pressoes);
    private JComboBox<String> comboPressaoSai = new JComboBox<>(pressoes);
    
    private JPanel painelDados;
    private JPanel painelEntrada;
    private JPanel painelSaida;
    
    public ViewEvaporadorPanelRankine(ControlEvaporadorPanelRankine controlEvaporadorPanel){
        painelDados = new JPanel(new GridBagLayout());
        painelEntrada = new JPanel(new GridBagLayout());
        painelSaida = new JPanel(new GridBagLayout());
        
        painelEntrada.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1, Color.lightGray, Color.lightGray), "Entrada", 1, 2, new Font("Times New Roman", 1, 12), Color.darkGray));
        painelSaida.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1, Color.lightGray, Color.lightGray), "Saída", 1, 2, new Font("Times New Roman", 1, 12), Color.darkGray));
        
        //this.setLayout(new FlowLayout(FlowLayout.LEADING));
        this.setLayout(new GridBagLayout());
        //this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        
        GridBagConstraints g = new GridBagConstraints();
        
        ArrayList<Double> aux = controlEvaporadorPanel.getValuesTempEntr();        
        DefaultComboBoxModel model = new DefaultComboBoxModel(aux.toArray());
        fieldTempEntr.setModel(model);
        
        
        fieldTempEntr.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                fieldTempEntr.showPopup();
                fieldTempEntr.getEditor().selectAll();
                fieldTempEntr.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent ke) {
                    }

                    @Override
                    public void keyPressed(KeyEvent ke) {
                        fieldTempEntr.setPopupVisible(false);
                    }

                    @Override
                    public void keyReleased(KeyEvent ke) {
                    }
                });
            }
            @Override
            public void focusLost(FocusEvent fe) {
                controlEvaporadorPanel.saveTempEntr();
                
                ArrayList<Double> aux = controlEvaporadorPanel.getValuesTempEntr();
                DefaultComboBoxModel model = new DefaultComboBoxModel(aux.toArray());
                fieldTempEntr.setModel(model);
            }
        });
        
        aux = controlEvaporadorPanel.getValuesTempSai();        
        model = new DefaultComboBoxModel(aux.toArray());
        fieldTempSai.setModel(model);
        
        fieldTempSai.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                fieldTempSai.showPopup();
                fieldTempSai.getEditor().selectAll();
                fieldTempSai.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent ke) {
                    }

                    @Override
                    public void keyPressed(KeyEvent ke) {
                        fieldTempSai.setPopupVisible(false);
                    }

                    @Override
                    public void keyReleased(KeyEvent ke) {
                    }
                });
            }

            @Override
            public void focusLost(FocusEvent fe) {
                controlEvaporadorPanel.saveTempSai();
                
                ArrayList<Double> aux = controlEvaporadorPanel.getValuesTempSai();
                DefaultComboBoxModel model = new DefaultComboBoxModel(aux.toArray());
                fieldTempSai.setModel(model);
            }
        });
        
        aux = controlEvaporadorPanel.getValuesPressaoEntr();        
        model = new DefaultComboBoxModel(aux.toArray());
        fieldPressaoEntr.setModel(model);
        
        fieldPressaoEntr.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                fieldPressaoEntr.showPopup();
                fieldPressaoEntr.getEditor().selectAll();
                fieldPressaoEntr.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent ke) {
                    }

                    @Override
                    public void keyPressed(KeyEvent ke) {
                        fieldPressaoEntr.setPopupVisible(false);
                    }

                    @Override
                    public void keyReleased(KeyEvent ke) {
                    }
                });
            }

            @Override
            public void focusLost(FocusEvent fe) {
                controlEvaporadorPanel.savePressaoEntr();
                
                ArrayList<Double> aux = controlEvaporadorPanel.getValuesPressaoEntr();
                DefaultComboBoxModel model = new DefaultComboBoxModel(aux.toArray());
                fieldPressaoEntr.setModel(model);
            }
        });
        
        aux = controlEvaporadorPanel.getValuesPressaoSai();        
        model = new DefaultComboBoxModel(aux.toArray());
        fieldPressaoSai.setModel(model);
        
        fieldPressaoSai.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                fieldPressaoSai.showPopup();
                fieldPressaoSai.getEditor().selectAll();
                fieldPressaoSai.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent ke) {
                    }

                    @Override
                    public void keyPressed(KeyEvent ke) {
                        fieldPressaoSai.setPopupVisible(false);
                    }

                    @Override
                    public void keyReleased(KeyEvent ke) {
                    }
                });
            }

            @Override
            public void focusLost(FocusEvent fe) {
                controlEvaporadorPanel.savePressaoSai();
                
                ArrayList<Double> aux = controlEvaporadorPanel.getValuesPressaoSai();
                DefaultComboBoxModel model = new DefaultComboBoxModel(aux.toArray());
                fieldPressaoSai.setModel(model);
            }
        });
        
        aux = controlEvaporadorPanel.getValuesDeltaPressao();        
        model = new DefaultComboBoxModel(aux.toArray());
        fieldDelta.setModel(model);
        
        fieldDelta.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                fieldDelta.showPopup();
                fieldDelta.getEditor().selectAll();
                fieldDelta.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent ke) {
                    }

                    @Override
                    public void keyPressed(KeyEvent ke) {
                        fieldDelta.setPopupVisible(false);
                    }

                    @Override
                    public void keyReleased(KeyEvent ke) {
                    }
                });
            }

            @Override
            public void focusLost(FocusEvent fe) {
                controlEvaporadorPanel.saveDeltaPressao();
                
                ArrayList<Double> aux = controlEvaporadorPanel.getValuesDeltaPressao();
                DefaultComboBoxModel model = new DefaultComboBoxModel(aux.toArray());
                fieldDelta.setModel(model);
            }
        });
        
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
        
        //DADOS DO DELTA DE PRESSÃO
        g.gridx = 0;
        g.gridy = 3;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(labelDelta, g);
        
        g.gridx = 1;
        g.gridy = 3;
        g.gridwidth = 2;
        painelDados.add(fieldDelta, g);
        
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.PAGE_START;
        this.add(painelDados, g);
        
    }

    public JTextField getFieldMassa() {
        return fieldMassa;
    }

    public void setFieldMassa(JTextField fieldMassa) {
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
