/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Turbina;

import Control.ControlPrincipal;
import Control.Turbina.ControlTurbinaPanelRankine;
import Util.DropdownComboBox;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
public class ViewTurbinaPanelRankine extends JPanel{
    
    //LABELS 
    private JLabel labelMassa = new JLabel("Massa (m): ");
    
    private JLabel labelTempEntr = new JLabel("Temperatura: ");
    private JLabel labelPressaoEntr = new JLabel("Pressão: ");
    
    private JLabel labelTempSai = new JLabel("Temperatura: ");
    private JLabel labelPressaoSai = new JLabel("Pressão: ");
    
    private JLabel labelEfici = new JLabel("Eficiência");
    
    //TEXTFIELDS
    private DropdownComboBox fieldMassa = new DropdownComboBox();;
    
    private DropdownComboBox fieldTempEntr = new DropdownComboBox();
    
    private DropdownComboBox fieldPressaoEntr = new DropdownComboBox();
    
    private DropdownComboBox fieldTempSai = new DropdownComboBox();
    
    private DropdownComboBox fieldPressaoSai = new DropdownComboBox();
    
    private DropdownComboBox fieldEfici = new DropdownComboBox();
    
    //COMBOBOX
    private String[] massas = {"kg/s", "kg/m", "kg/h", "lb/s", "lb/m", "lb/h"};
    private JComboBox<String> comboMassa = new JComboBox<>(massas);
    
    private String[] temps = {"°C", "°F", "K"};
    private JComboBox<String> comboTempEntr = new JComboBox<>(temps);
    private JComboBox<String> comboTempSai = new JComboBox<>(temps);
    
    private String[] pressoes = {"atm", "kPa", "bar"};
    private JComboBox<String> comboPressaoEntr = new JComboBox<>(pressoes);
    private JComboBox<String> comboPressaoSai = new JComboBox<>(pressoes);
    
    private JPanel painelDados;
    private JPanel painelEntrada;
    private JPanel painelSaida;
    
    ControlPrincipal ctrlPrincipal;
    
    public ViewTurbinaPanelRankine(ControlPrincipal ctrlPrincipal,ControlTurbinaPanelRankine controlTurbinaPanel){
        this.ctrlPrincipal = ctrlPrincipal;
        
        painelDados = new JPanel(new GridBagLayout());
        painelEntrada = new JPanel(new GridBagLayout());
        painelSaida = new JPanel(new GridBagLayout());
        
        painelEntrada.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1, Color.lightGray, Color.lightGray), "Entrada", 1, 2, new Font("Times New Roman", 1, 12), Color.darkGray));
        painelSaida.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1, Color.lightGray, Color.lightGray), "Saída", 1, 2, new Font("Times New Roman", 1, 12), Color.darkGray));
        
        //this.setLayout(new FlowLayout(FlowLayout.LEADING));
        this.setLayout(new GridBagLayout());
        this.setName("Turbina");
        //this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        
        GridBagConstraints g = new GridBagConstraints();
        
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
                controlTurbinaPanel.atualizaTempEntrada();
            }
        });
        
        
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
                controlTurbinaPanel.atualizaTempSaida();

            }
        });
        
        
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
                controlTurbinaPanel.atualizaPressaoEntrada();
                
            }
        });
        
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
                controlTurbinaPanel.atualizaPressaoSaida();
                
            }
        });
        
        fieldEfici.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                fieldEfici.showPopup();
                fieldEfici.getEditor().selectAll();
                fieldEfici.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent ke) {
                    }

                    @Override
                    public void keyPressed(KeyEvent ke) {
                        fieldEfici.setPopupVisible(false);
                    }

                    @Override
                    public void keyReleased(KeyEvent ke) {
                    }
                });
            }

            @Override
            public void focusLost(FocusEvent fe) {
                controlTurbinaPanel.atualizaEficiencia();
            }
        });
        
        fieldMassa.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                fieldMassa.showPopup();
                fieldMassa.getEditor().selectAll();
                fieldMassa.getEditor().getEditorComponent().addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent ke) {
                    }

                    @Override
                    public void keyPressed(KeyEvent ke) {
                        fieldMassa.setPopupVisible(false);
                    }

                    @Override
                    public void keyReleased(KeyEvent ke) {
                    }
                });
            }

            @Override
            public void focusLost(FocusEvent fe) {
                controlTurbinaPanel.atualizaMassa();
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
        
        //DADOS DA EFICIÊNCIA
        g.gridx = 0;
        g.gridy = 3;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(labelEfici, g);
        
        g.gridx = 1;
        g.gridy = 3;
        g.gridwidth = 2;
        painelDados.add(fieldEfici, g);
        
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.PAGE_START;
        g.weighty = 1;
        this.add(painelDados, g);
        
<<<<<<< HEAD
        //ADICIONA OS LISTENERS AOS FIELDS
        fieldMassa.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                fieldMassa.getEditor().selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                //ViewTurbinaPanelRankine.this.ctrlPrincipal.ajustaMassa();
            }
        });
=======
>>>>>>> master
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

    public DropdownComboBox getFieldEfici() {
        return fieldEfici;
    }

    public void setFieldEfici(DropdownComboBox fieldEfici) {
        this.fieldEfici = fieldEfici;
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
