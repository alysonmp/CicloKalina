/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author leonardo
 */
public class ViewEspera{
    
    private ImageIcon imagem = new ImageIcon(getClass().getResource("/Images/Load/loading4.gif"));
    private JLabel labelEspera = new JLabel(imagem);
    private JPanel panelEspera = new JPanel();
    
    public ViewEspera() {
        panelEspera.setLayout(new FlowLayout());
        
        panelEspera.add(labelEspera, BorderLayout.CENTER);
        panelEspera.setVisible(false);
        panelEspera.setSize(500,500);
        panelEspera.setBackground(new Color(0,0,0,0));
    }

    public JPanel getPanelEspera() {
        return panelEspera;
    }

    public void setPanelEspera(JPanel panelEspera) {
        this.panelEspera = panelEspera;
    }
}
