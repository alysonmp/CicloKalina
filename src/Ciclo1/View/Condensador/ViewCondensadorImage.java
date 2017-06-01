/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.View.Condensador;

import Ciclo1.Control.Condensador.ControlCondensadorPanelKCS;
import Ciclo1.Control.ControlPrincipal;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.hibernate.Session;

/**
 *
 * @author alysonmp
 */
public class ViewCondensadorImage extends JPanel{
    
    private ControlPrincipal ctrlPrincipal;
    private Session session;
    
    //CONSTRUTOR DO CONDENSADOR
    public ViewCondensadorImage(ControlPrincipal ctrlPrincipal, int ciclo, Session session){
        this.ctrlPrincipal = ctrlPrincipal;
        this.session = session;
    
        this.setName("Condensador");
        
        //FUNÇÃO ATIVADA AO SE CLICAR NO ELEMENTO CONDENSADOR NAS IMAGENS
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //APAGA TODAS AS BORDAS DOS ELEMENTOS CLICADOS
                Component[] components = ViewCondensadorImage.this.ctrlPrincipal.getViewPrincipal().getPainelCiclos().getComponents();
                for(Component c:components){
                    JPanel panel = (JPanel)c;
                    panel.setBorder(null);
                }
                
                //VERIFICA SE O PAINEL JÁ FOI ADICIONADO AO PAINEL LATERAL
                if(ViewCondensadorImage.this.ctrlPrincipal.getPanel_usado().contains((JPanel)e.getSource())){
                    for(int i = 0; i < ViewCondensadorImage.this.ctrlPrincipal.getPanel_usado().size(); i++) {
                        if(e.getSource() == ViewCondensadorImage.this.ctrlPrincipal.getPanel_usado().get(i)){
                            ViewCondensadorImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanel().setSelectedIndex(i);
                            ViewCondensadorImage.this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
                            return;
                        }
                    }
                }
                if(ViewCondensadorImage.this.ctrlPrincipal.getPanel_usado().size() > 2){
                    ViewCondensadorImage.this.ctrlPrincipal.removeTab();
                }
                ViewCondensadorImage.this.ctrlPrincipal.getPanel_usado().add((JPanel) e.getSource());
                
                //CALCULA A DIMENSÃO DO PAINEL LATERAL
                int x = (int) (ctrlPrincipal.getViewPrincipal().getPainelLateral().getWidth()*0.9);
                int y = (int) (ctrlPrincipal.getViewPrincipal().getPainelLateral().getHeight()*0.86);
                
                    ControlCondensadorPanelKCS Condensador = new ControlCondensadorPanelKCS(ViewCondensadorImage.this.ctrlPrincipal, ViewCondensadorImage.this.session);
                    Condensador.getViewCondensador().setPreferredSize(new Dimension(x, y));
                    ViewCondensadorImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanel().addTab("Condensador", null, Condensador.getViewCondensador());
                    ViewCondensadorImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanel().setSelectedComponent(Condensador.getViewCondensador());
                    ViewCondensadorImage.this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
                    //Condensador.criaComboBoxes();
            }

            //NÃO UTILIZADOS
            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }
}
