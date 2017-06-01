/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.View.Bomba;

import Ciclo2.Control.Bomba.ControlBombaPanelRankine;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author alysonmp
 */
public class ViewBombaPanelKCS extends JPanel{
    
    private JLabel labelNome = new JLabel("Nome");
    private JLabel labelNome2 = new JLabel("Nome");
    
    private JTextField fieldNome = new JTextField(10);
    private JTextField fieldNome2 = new JTextField(10);
    
    private JPanel painelDados;
    
    public ViewBombaPanelKCS(ControlBombaPanelRankine ctrlBomba){
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
        
        g.gridx = 0;
        g.gridy = 1;
        g.gridwidth = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        painelDados.add(labelNome2, g);
        
        g.gridx = 1;
        g.gridy = 1;
        g.gridwidth = 2;
        painelDados.add(fieldNome2, g);
        
        //CRIA OS LISTENERS PARA TODOS OS JTEXTFIELD
        fieldNome.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                fieldNome.selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                System.out.println(e.getSource());
            }
        });
        
        fieldNome2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                fieldNome2.selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                System.out.println(e.getSource());
            }
        });
        
        this.add(painelDados);
    }
}
