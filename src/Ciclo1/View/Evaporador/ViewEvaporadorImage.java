/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.View.Evaporador;

import Ciclo1.Control.ControlPrincipal;
import Ciclo1.Control.Evaporador.ControlEvaporadorPanelKCS;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author alysonmp
 */
public class ViewEvaporadorImage extends JPanel{
    
    private ControlPrincipal ctrlPrincipal;
    
    //CONSTRUTOR DO EVAPORADOR
    public ViewEvaporadorImage(ControlPrincipal ctrlPrincipal, int ciclo){
        this.ctrlPrincipal = ctrlPrincipal;
        
        this.setName("Evaporador");
        
        //FUNÇÃO ATIVADA AO SE CLICAR NO ELEMENTO EVAPORADOR NAS IMAGENS
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //APAGA TODAS AS BORDAS DOS ELEMENTOS CLICADOS
                Component[] components = ViewEvaporadorImage.this.ctrlPrincipal.getViewPrincipal().getPainelCiclos().getComponents();
                for(Component c:components){
                    JPanel panel = (JPanel)c;
                    panel.setBorder(null);
                }
                
                //VERIFICA SE O PAINEL JÁ FOI ADICIONADO AO PAINEL LATERAL
                if(ViewEvaporadorImage.this.ctrlPrincipal.getPanel_usado().contains((JPanel)e.getSource())){
                    for(int i = 0; i < ViewEvaporadorImage.this.ctrlPrincipal.getPanel_usado().size(); i++) {
                        if(e.getSource() == ViewEvaporadorImage.this.ctrlPrincipal.getPanel_usado().get(i)){
                            ViewEvaporadorImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanel().setSelectedIndex(i);
                            ViewEvaporadorImage.this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
                            return;
                        }
                    }
                }
                if(ViewEvaporadorImage.this.ctrlPrincipal.getPanel_usado().size() > 2){
                    ViewEvaporadorImage.this.ctrlPrincipal.removeTab();
                }
                ViewEvaporadorImage.this.ctrlPrincipal.getPanel_usado().add((JPanel) e.getSource());
                
                //CALCULA A DIMENSÃO DO PAINEL LATERAL
                int x = (int) (ctrlPrincipal.getViewPrincipal().getPainelLateral().getWidth()*0.9);
                int y = (int) (ctrlPrincipal.getViewPrincipal().getPainelLateral().getHeight()*0.86);
                
                ControlEvaporadorPanelKCS controlEvaporadorPanel = new ControlEvaporadorPanelKCS(ViewEvaporadorImage.this.ctrlPrincipal);
                controlEvaporadorPanel.getViewEvaporadorPanel().setPreferredSize(new Dimension(x, y));
                ViewEvaporadorImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanel().addTab("Evaporador", null, controlEvaporadorPanel.getViewEvaporadorPanel());
                ViewEvaporadorImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanel().setSelectedComponent(controlEvaporadorPanel.getViewEvaporadorPanel());
                ViewEvaporadorImage.this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
                //controlEvaporadorPanel.getValuesComboBox();
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
