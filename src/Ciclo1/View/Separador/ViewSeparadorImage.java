/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.View.Separador;

import Ciclo1.Control.ControlPrincipal;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author alysonmp
 */
public class ViewSeparadorImage extends JPanel{
    
    ControlPrincipal ctrlPrincipal;
    
    //CONSTRUTOR DO SEPARADOR
    public ViewSeparadorImage(ControlPrincipal ctrlPrincipal, int ciclo){
        this.ctrlPrincipal = ctrlPrincipal;
        
        this.setName("Separador");
        
        //FUNÇÃO ATIVADA AO SE CLICAR NO ELEMENTO SEPARADOR NAS IMAGENS
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //APAGA TODAS AS BORDAS DOS ELEMENTOS CLICADOS
                Component[] components = ViewSeparadorImage.this.ctrlPrincipal.getViewPrincipal().getPainelCiclos().getComponents();
                for(Component c:components){
                    JPanel panel = (JPanel)c;
                    panel.setBorder(null);
                }
                
                //VERIFICA SE O PAINEL JÁ FOI ADICIONADO AO PAINEL LATERAL
                if(ViewSeparadorImage.this.ctrlPrincipal.getPanel_usado().contains((JPanel)e.getSource())){
                    for(int i = 0; i < ViewSeparadorImage.this.ctrlPrincipal.getPanel_usado().size(); i++) {
                        if(e.getSource() == ViewSeparadorImage.this.ctrlPrincipal.getPanel_usado().get(i)){
                            ViewSeparadorImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanel().setSelectedIndex(i);
                            ViewSeparadorImage.this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
                            return;
                        }
                    }
                }
                if(ViewSeparadorImage.this.ctrlPrincipal.getPanel_usado().size() > 3){
                    ViewSeparadorImage.this.ctrlPrincipal.removeTab();
                }
                ViewSeparadorImage.this.ctrlPrincipal.getPanel_usado().add((JPanel) e.getSource());
                
                //CALCULA A DIMENSÃO DO PAINEL LATERAL
                int x = (int) (ctrlPrincipal.getViewPrincipal().getPainelLateral().getWidth()*0.9);
                int y = (int) (ctrlPrincipal.getViewPrincipal().getPainelLateral().getHeight()*0.86);
                
                //CASO O CICLO SEJA O PRIMEIRO CHAMA A CLASSE VIEWSEPARADORPANELKCS
                if(ciclo == 1){
                    ViewSeparadorPanelKCS viewSeparadorPanel = new ViewSeparadorPanelKCS();
                    viewSeparadorPanel.setPreferredSize(new Dimension(x, y));
                    ViewSeparadorImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanel().addTab("Separador", null, viewSeparadorPanel);
                    ViewSeparadorImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanel().setSelectedComponent(viewSeparadorPanel);
                    ViewSeparadorImage.this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
                }else{
                    //CASO O CICLO SEJA O PRIMEIRO CHAMA A CLASSE VIEWSEPARADORPANELKCS
                    if(ciclo == 2){
                        /*ViewSeparadorPanelRankine viewSeparadorPanel = new ViewSeparadorPanelRankine();
                        viewSeparadorPanel.setPreferredSize(new Dimension(x, y));
                        ViewSeparadorImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanel().addTab("Separador", null, viewSeparadorPanel);
                        ViewSeparadorImage.this.setBorder(BorderFactory.createLineBorder(Color.black, 2));*/
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
