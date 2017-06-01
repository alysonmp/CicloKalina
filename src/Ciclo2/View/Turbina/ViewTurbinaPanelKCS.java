/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo2.View.Turbina;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author alysonmp
 */
public class ViewTurbinaPanelKCS extends JPanel{
    
    private JLabel labelNome = new JLabel("Nome");
    
    private JTextField fieldNome = new JTextField(10);
    
    private JPanel painelDados;
    
    public ViewTurbinaPanelKCS(){
        painelDados = new JPanel(new GridBagLayout());
        
        this.setLayout(new FlowLayout(FlowLayout.LEADING));
        //this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        
        GridBagConstraints g = new GridBagConstraints();
        
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(labelNome, g);
        
        g.gridx = 1;
        g.gridy = 0;
        g.gridwidth = 2;
        painelDados.add(fieldNome, g);
        
        this.add(painelDados);
    }
    
}
