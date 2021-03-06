/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.ControlPrincipal;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
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
    private JPanel painelEntrada;
    private JPanel painelMensagens;
    private JTabbedPane tabbedPanelComp;
    
    JFrame frameDados = new JFrame();
    
    private ControlPrincipal ctrlPrincipal;
    
    public ViewPrincipal(ControlPrincipal ctrlPrincipal){
        
        this.ctrlPrincipal = ctrlPrincipal;
        
        //CRIA A BARRA DE MENU NO TOPO DO FRAME
        criaMenu();
        
        painelCiclos.setBackground(Color.white);
        painelCiclos.setLayout(null);
        
        //PAINEL PRINCIPAL ONDE SERÃO COLOCADOS TODOS OS COMPONENTES
        painelPrincipal = new JPanel(new BorderLayout());
        
        //PAINEL LATERAL ONDE SERÃO COLOCADOS TODOS OS DADOS DE ENTRADA PARA OS CÁLCULOS
        Dimension dim = new Dimension(430, 800);
        painelLateral.setPreferredSize(dim);
        
        painelEntrada = new JPanel();
        painelMensagens = new JPanel();
        
        tabbedPanelComp = new JTabbedPane();
        
        painelLateral.add(painelEntrada,BorderLayout.NORTH);
        painelLateral.add(painelMensagens,BorderLayout.SOUTH);
        //painelLateral.add(tabbedPanelComp);
        //******************************************************************************
        
        painelPrincipal.add(painelLateral, BorderLayout.WEST);
        painelPrincipal.add(painelCiclos, BorderLayout.CENTER);
        
        framePrincipal.setJMenuBar(menuBar);
        framePrincipal.add(painelPrincipal);
        
        framePrincipal.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        framePrincipal.setVisible(true);
        framePrincipal.setResizable(false);
        framePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frameDados.add(tabbedPanelComp);
        frameDados.setResizable(false);
        frameDados.setBounds(100, 100, 400, 600);
        frameDados.setAlwaysOnTop(true);
        frameDados.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
        frameDados.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                Component[] components = painelCiclos.getComponents();
                for(Component c:components){
                    JPanel panel = (JPanel)c;
                    panel.setBorder(null);
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        
        tabbedPanelComp.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                String tabName = tabbedPanelComp.getTitleAt(tabbedPanelComp.getSelectedIndex());
                
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
        
        /*comboFluidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewPrincipal.this.ctrlPrincipal.calculaLimites();
            }
        });*/
    }
    
    //CRIA A BARRA DE MENU NO TOPO DO FRAME
    private void criaMenu(){
        menu = new JMenu("File");
        
        menuItem = new JMenu("Open");
        
        subItem = new JMenuItem("Ciclo 2");
        menuItem.add(subItem);
        subItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ctrlPrincipal.criaCiclo2();
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

    public JPanel getPainelEntrada() {
        return painelEntrada;
    }

    public void setPainelEntrada(JPanel panelEntrada) {
        this.painelEntrada = panelEntrada;
    }

    public JTabbedPane getTabbedPanelComp() {
        return tabbedPanelComp;
    }

    public JFrame getFrameDados() {
        return frameDados;
    }

    public void setFrameDados(JFrame frameDados) {
        this.frameDados = frameDados;
    }

    public JPanel getPainelMensagens() {
        return painelMensagens;
    }

    public void setPainelMensagens(JPanel painelMensagens) {
        this.painelMensagens = painelMensagens;
    }
    
    
}
