/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.View.Recuperador;

import Ciclo1.Control.ControlPrincipal;
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
public class ViewRecuperadorImage extends JPanel{
    
    ControlPrincipal ctrlPrincipal;
    
    //CONSTRUTOR DO RECUPERADOR
    public ViewRecuperadorImage(ControlPrincipal ctrlPrincipal, int ciclo){
        this.ctrlPrincipal = ctrlPrincipal;
        
        this.setName("Recuperador");
        
        //FUNÇÃO ATIVADA AO SE CLICAR NO ELEMENTO RECUPERADOR NAS IMAGENS
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //APAGA TODAS AS BORDAS DOS ELEMENTOS CLICADOS
                Component[] components = ViewRecuperadorImage.this.ctrlPrincipal.getViewPrincipal().getPainelCiclos().getComponents();
                for(Component c:components){
                    JPanel panel = (JPanel)c;
                    panel.setBorder(null);
                }
                
                //VERIFICA SE O PAINEL JÁ FOI ADICIONADO AO PAINEL LATERAL
                if(ViewRecuperadorImage.this.ctrlPrincipal.getPanel_usado().contains((JPanel)e.getSource())){
                    for(int i = 0; i < ViewRecuperadorImage.this.ctrlPrincipal.getPanel_usado().size(); i++) {
                        if(e.getSource() == ViewRecuperadorImage.this.ctrlPrincipal.getPanel_usado().get(i)){
                            ViewRecuperadorImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanel().setSelectedIndex(i);
                            ViewRecuperadorImage.this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
                            return;
                        }
                    }
                }
                if(ViewRecuperadorImage.this.ctrlPrincipal.getPanel_usado().size() > 2){
                    ViewRecuperadorImage.this.ctrlPrincipal.removeTab();
                }
                ViewRecuperadorImage.this.ctrlPrincipal.getPanel_usado().add((JPanel) e.getSource());
                
                //CALCULA A DIMENSÃO DO PAINEL LATERAL
                int x = (int) (ctrlPrincipal.getViewPrincipal().getPainelLateral().getWidth()*0.9);
                int y = (int) (ctrlPrincipal.getViewPrincipal().getPainelLateral().getHeight()*0.86);
                
                //CASO O CICLO SEJA O PRIMEIRO CHAMA A CLASSE VIEWRECUPERADORPANELKCS
                if(ciclo == 1){
                    ViewRecuperadorPanelKCS viewRecuperadorPanel = new ViewRecuperadorPanelKCS();
                    viewRecuperadorPanel.setPreferredSize(new Dimension(x, y));
                    ViewRecuperadorImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanel().addTab("Recuperador", null, viewRecuperadorPanel);
                    ViewRecuperadorImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanel().setSelectedComponent(viewRecuperadorPanel);
                    ViewRecuperadorImage.this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
                }else{
                    //CASO O CICLO SEJA O SEGUNDO CHAMA A CLASSE VIEWRECUPERADORPANELKCS
                    if(ciclo == 2){
                        /*ViewRecuperadorPanelRankine viewRecuperadorPanel = new ViewRecuperadorPanelRankine();
                        viewRecuperadorPanel.setPreferredSize(new Dimension(x, y));
                        ViewRecuperadorImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanel().addTab("Recuperador", null, viewRecuperadorPanel);
                        ViewRecuperadorImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanel().setSelectedComponent(viewRecuperadorPanel);
                        ViewRecuperadorImage.this.setBorder(BorderFactory.createLineBorder(Color.black, 2));*/
                    }
                }
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
