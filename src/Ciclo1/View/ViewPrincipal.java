/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.View;

import Ciclo1.Control.ControlPrincipal;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author alysonmp
 */
public class ViewPrincipal {
    
    private JFrame framePrincipal = new JFrame();
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu;
    private JMenuItem menuItem, subItem;
    
    private JPanel painelPrincipal;
    private JPanel painelLateral = new JPanel();
    private JPanel painelCiclos = new JPanel();
    private JTabbedPane tabbedPanel;
    
    JLabel labelFluidos = new JLabel("Fluído");
    JComboBox<String> comboFluidos;
    
    private ControlPrincipal ctrlPrincipal;
    
    public ViewPrincipal(ControlPrincipal ctrlPrincipal){
        
        this.ctrlPrincipal = ctrlPrincipal;
        
        List results = this.ctrlPrincipal.getFluidos();
        Iterator iterator = results.iterator();
        
        Vector<String> fluidos = new Vector<>();
        while(iterator.hasNext()){
            fluidos.add((String) iterator.next());
        }
        comboFluidos = new JComboBox<String>(fluidos);
        comboFluidos.setFont(new Font("Arial", 0, 13));
        
        //CRIA A BARRA DE MENU NO TOPO DO FRAME
        criaMenu();
        
        painelCiclos.setBackground(Color.white);
        painelCiclos.setLayout(null);
        
        //PAINEL PRINCIPAL ONDE SERÃO COLOCADOS TODOS OS COMPONENTES
        painelPrincipal = new JPanel(new BorderLayout());
        
        //PAINEL LATERAL ONDE SERÃO COLOCADOS TODOS OS DADOS DE ENTRADA PARA OS CÁLCULOS
        Dimension dim = new Dimension(380, 500);
        painelLateral.setPreferredSize(dim);
        
        tabbedPanel = new JTabbedPane();
        painelLateral.add(labelFluidos);
        painelLateral.add(comboFluidos);
        painelLateral.add(tabbedPanel);
        //******************************************************************************
        
        painelPrincipal.add(painelLateral, BorderLayout.WEST);
        painelPrincipal.add(painelCiclos, BorderLayout.CENTER);
        
        framePrincipal.setJMenuBar(menuBar);
        framePrincipal.add(painelPrincipal);
        
        framePrincipal.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        framePrincipal.setVisible(true);
        framePrincipal.setResizable(false);
        framePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        tabbedPanel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                String tabName = tabbedPanel.getTitleAt(tabbedPanel.getSelectedIndex());
                
                Component[] paineis = painelCiclos.getComponents();
                for(int i = 0; i < paineis.length; i++){
                    if(ctrlPrincipal.getPanel_usado().contains(paineis[i])){
                        if(paineis[i].getName().equals(tabName)){
                            
                            for(Component c:paineis){
                                JPanel panel = (JPanel)c;
                                panel.setBorder(null);
                            }
                            
                            JPanel painel = (JPanel)paineis[i];
                            painel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
                        }
                    }
                }
            }
        });
        
        comboFluidos.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                ViewPrincipal.this.ctrlPrincipal.calculaLimites();
            }
        });
    }
    
    //CRIA A BARRA DE MENU NO TOPO DO FRAME
    private void criaMenu(){
        menu = new JMenu("File");
        
        menuItem = new JMenu("Open");
        
        subItem = new JMenuItem("Ciclo 1");
        menuItem.add(subItem);
        subItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ctrlPrincipal.criaCiclo1();
            }
        });
        
        menu.add(menuItem);
        menu.addSeparator();
        
        menuItem = new JMenuItem("Exit");
        menu.add(menuItem);
        
        menuBar.add(menu);
    }

    public JFrame getFramePrincipal() {
        return framePrincipal;
    }

    public void setFramePrincipal(JFrame framePrincipal) {
        this.framePrincipal = framePrincipal;
    }

    public JPanel getPainelPrincipal() {
        return painelPrincipal;
    }

    public void setPainelPrincipal(JPanel painelPrincipal) {
        this.painelPrincipal = painelPrincipal;
    }

    public JPanel getPainelLateral() {
        return painelLateral;
    }

    public void setPainelLateral(JPanel painelLateral) {
        this.painelLateral = painelLateral;
    }

    public JPanel getPainelCiclos() {
        return painelCiclos;
    }

    public void setPainelCiclos(JPanel painelCiclos) {
        this.painelCiclos = painelCiclos;
    }

    public JTabbedPane getTabbedPanel() {
        return tabbedPanel;
    }

    public void setTabbedPanel(JTabbedPane tabbedPanel) {
        this.tabbedPanel = tabbedPanel;
    }    

    public JComboBox<String> getComboFluidos() {
        return comboFluidos;
    }

    public void setComboFluidos(JComboBox<String> comboFluidos) {
        this.comboFluidos = comboFluidos;
    }    
}
