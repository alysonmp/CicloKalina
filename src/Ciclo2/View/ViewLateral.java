/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo2.View;

import Ciclo2.Control.ControlPrincipal;
import Ciclo2.Util.DropdownComboBox;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author alysonmp
 */
public class ViewLateral extends JPanel{
    
    //int compressor, int flu, double Tf, double Pf, double SUP, double PINCH, double Tconop, double eff, Session session
    
    private JLabel labelFluidos = new JLabel("Fluído");
            
    private JLabel labelCompressor = new JLabel("Compressor");
            
    private JLabel labelMassa = new JLabel("Massa (m): ");
    
    private JLabel labelTemp = new JLabel("Temperatura: ");
    private JLabel labelPressao = new JLabel("Pressão: ");
    
    private JLabel labelTempCond = new JLabel("<html><body>Temperatura <br>de Condensação: </html></body> ");
    private JLabel labelEfetiv = new JLabel("Efetividade: "); 
    
    //TEXTFIELDS
    private DropdownComboBox fieldMassa = new DropdownComboBox();
    
    private DropdownComboBox fieldTemp = new DropdownComboBox();
    private DropdownComboBox fieldPressao = new DropdownComboBox();
    
    private DropdownComboBox fieldTempCond = new DropdownComboBox();
    private DropdownComboBox fieldEfetiv = new DropdownComboBox();
    
    private DropdownComboBox fieldSup = new DropdownComboBox();
    private DropdownComboBox fieldPinch = new DropdownComboBox();
    
    //COMBOBOX
    JComboBox<String> comboFluidos;
    
    private String[] compressores = {"1", "2", "3", "4", "5"};
    private JComboBox comboCompressores = new JComboBox(compressores);
    
    private String[] massas = {"kg/s", "kg/m", "kg/h", "lb/s", "lb/m", "lb/h"};
    private JComboBox<String> comboMassa = new JComboBox<>(massas);
    
    private String[] temps = {"K", "°C", "°F"};
    private JComboBox<String> comboTemp = new JComboBox<>(temps);
    private JComboBox<String> comboTempCond = new JComboBox<>(temps);
    
    private String[] pressoes = {"kPa", "atm", "bar"};
    private JComboBox<String> comboPressao = new JComboBox<>(pressoes);
    
    //CHECKBOX
    JCheckBox checkSup = new JCheckBox("Superaquecimento");
    JCheckBox checkPinch = new JCheckBox("Pinch");
    
    private JPanel painelDados;
    
    private ControlPrincipal ctrlPrincipal;
    
    public ViewLateral(ControlPrincipal ctrlPrincipal){
        this.ctrlPrincipal = ctrlPrincipal;
        
        painelDados = new JPanel(new GridBagLayout());
        
        this.setLayout(new GridBagLayout());
        this.setName("Dados de Entrada");
        
        GridBagConstraints g = new GridBagConstraints();
        
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        //painelDados.add(labelFluidos, g);
        
        g.gridx = 1;
        g.gridy = 0;
        g.gridwidth = 2;
        g.fill = GridBagConstraints.HORIZONTAL;
        //painelDados.add(comboFluidos, g);
        
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
        g.gridwidth = 2;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(labelMassa, g);
        
        g.gridx = 1;
        g.gridy = 2;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(fieldMassa, g);
        
        g.gridx = 3;
        g.gridy = 2;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(comboMassa, g);

        g.gridx = 0;
        g.gridy = 3;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(labelTemp, g);
        
        g.gridx = 1;
        g.gridy = 3;
        g.gridwidth = 2;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(fieldTemp, g);
        
        g.gridx = 3;
        g.gridy = 3;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(comboTemp, g);
        
        g.gridx = 0;
        g.gridy = 4;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(labelPressao, g);
        
        g.gridx = 1;
        g.gridy = 4;
        g.gridwidth = 2;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(fieldPressao, g);
        
        g.gridx = 3;
        g.gridy = 4;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(comboPressao, g);
        
        g.gridx = 0;
        g.gridy = 5;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(labelTempCond, g);
        
        g.gridx = 1;
        g.gridy = 5;
        g.gridwidth = 2;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(fieldTempCond, g);
        
        g.gridx = 3;
        g.gridy = 5;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(comboTempCond, g);
        
        g.gridx = 0;
        g.gridy = 6;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(labelEfetiv, g);
        
        g.gridx = 1;
        g.gridy = 6;
        g.gridwidth = 2;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(fieldEfetiv, g);
        
        g.gridx = 0;
        g.gridy = 7;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(checkSup, g);
        
        g.gridx = 1;
        g.gridy = 7;
        g.gridwidth = 2;
        painelDados.add(fieldSup, g);
        
        g.gridx = 0;
        g.gridy = 8;
        g.gridwidth = 1;
        painelDados.add(checkPinch, g);
        
        g.gridx = 1;
        g.gridy = 8;
        g.gridwidth = 2;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(fieldPinch, g);
        
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.anchor = GridBagConstraints.PAGE_START;
        this.add(painelDados, g);
        
        ViewLateral.this.fieldPinch.getEditor().setItem("0");
        ViewLateral.this.fieldPinch.setEnabled(false);

        ViewLateral.this.fieldSup.getEditor().setItem("0");
        ViewLateral.this.fieldSup.setEnabled(false);
        
        fieldMassa.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                fieldMassa.showPopup();
                fieldMassa.getEditor().selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
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
        
        fieldTemp.getEditor().getEditorComponent().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                fieldTemp.showPopup();
                fieldTemp.getEditor().selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
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

    public JComboBox<String> getComboFluidos() {
        return comboFluidos;
    }

    public void setComboFluidos(JComboBox<String> comboFluidos) {
        this.comboFluidos = comboFluidos;
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
}
