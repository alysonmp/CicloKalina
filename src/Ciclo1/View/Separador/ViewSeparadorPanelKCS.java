/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.View.Separador;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author alysonmp
 */
public class ViewSeparadorPanelKCS extends JPanel{
    
    //LABELS 
    private JLabel labelMassa = new JLabel("Massa (m): ");
    private JLabel labelTemp = new JLabel("Temperatura: ");
    private JLabel labelPressao = new JLabel("Pressão: ");
    private JLabel labelEfetiv = new JLabel("<html><body>Temperatura <br>de Condensação: </html></body>");
    private JLabel labelDelta = new JLabel("Delta de Pressão: ");
    
    //TEXTFIELDS
    private JTextField fieldMassa = new JTextField(10);
    private JTextField fieldTemp = new JTextField(10);
    private JTextField fieldPressao = new JTextField(10);
    private JTextField fieldEfetiv = new JTextField(10);
    private JTextField fieldDelta = new JTextField(10);
    
    //COMBOBOX
    private String[] massas = {"kg/s", "kg/m", "kg/h", "lb/s", "lb/m", "lb/h"};
    private JComboBox<String> comboMassa = new JComboBox<>(massas);
    
    private String[] temps = {"°C", "°F", "K"};
    private JComboBox<String> comboTemp = new JComboBox<>(temps);
    
    private String[] pressoes = {"atm", "kPa", "bar"};
    private JComboBox<String> comboPressao = new JComboBox<>(pressoes);
    
    private JPanel painelDados;
    
    public ViewSeparadorPanelKCS(){
        painelDados = new JPanel(new GridBagLayout());
        
        this.setLayout(new FlowLayout(FlowLayout.LEADING));
        //this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        
        GridBagConstraints g = new GridBagConstraints();
        
        //DADOS DA MASSA
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(labelMassa, g);
        
        g.gridx = 1;
        g.gridy = 0;
        g.gridwidth = 2;
        painelDados.add(fieldMassa, g);
        
        g.gridx = 3;
        g.gridy = 0;
        g.gridwidth = 1;
        painelDados.add(comboMassa, g);
        
        //DADOS DA TEMPERATURA
        g.gridx = 0;
        g.gridy = 1;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(labelTemp, g);
        
        g.gridx = 1;
        g.gridy = 1;
        g.gridwidth = 2;
        painelDados.add(fieldTemp, g);
        
        g.gridx = 3;
        g.gridy = 1;
        g.gridwidth = 1;
        painelDados.add(comboTemp, g);
        
        //DADOS DA PRESSÃO
        g.gridx = 0;
        g.gridy = 2;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(labelPressao, g);
        
        g.gridx = 1;
        g.gridy = 2;
        g.gridwidth = 2;
        painelDados.add(fieldPressao, g);
        
        g.gridx = 3;
        g.gridy = 2;
        g.gridwidth = 1;
        painelDados.add(comboPressao, g);
        
        //DADOS DO DELTA DE PRESSÃO
        g.gridx = 0;
        g.gridy = 3;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(labelDelta, g);
        
        g.gridx = 1;
        g.gridy = 3;
        g.gridwidth = 2;
        painelDados.add(fieldEfetiv, g);
        
        //DADOS DO DELTA DE PRESSÃO
        g.gridx = 0;
        g.gridy = 4;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(labelEfetiv, g);
        
        g.gridx = 1;
        g.gridy = 4;
        g.gridwidth = 2;
        painelDados.add(fieldDelta, g);
        
        this.add(painelDados);
    }
    
}
