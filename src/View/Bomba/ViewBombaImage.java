/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Bomba;

import Control.ControlPrincipal;
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
public class ViewBombaImage extends JPanel{
    
    private ControlPrincipal ctrlPrincipal;
    
    //CONSTRUTOR DA BOMBA
    public ViewBombaImage(ControlPrincipal ctrlPrincipal, int ciclo){
        this.ctrlPrincipal = ctrlPrincipal;
        
        this.setName("Bomba");
        
        //FUNÇÃO ATIVADA AO SE CLICAR NO ELEMENTO BOMBA NAS IMAGENS
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //APAGA TODAS AS BORDAS DOS ELEMENTOS CLICADOS
                Component[] components = ViewBombaImage.this.ctrlPrincipal.getViewPrincipal().getPainelCiclos().getComponents();
                for(Component c:components){
                    JPanel panel = (JPanel)c;
                    panel.setBorder(null);
                }
                
                //VERIFICA SE O PAINEL JÁ FOI ADICIONADO AO PAINEL LATERAL
                if(ViewBombaImage.this.ctrlPrincipal.getPanel_usado().contains((JPanel)e.getSource())){
                    for(int i = 0; i < ViewBombaImage.this.ctrlPrincipal.getPanel_usado().size(); i++) {
                        if(e.getSource() == ViewBombaImage.this.ctrlPrincipal.getPanel_usado().get(i)){
                            ViewBombaImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanel().setSelectedIndex(i);
                            ViewBombaImage.this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
                            return;
                        }
                    }
                }
                if(ViewBombaImage.this.ctrlPrincipal.getPanel_usado().size() > 2){
                    ViewBombaImage.this.ctrlPrincipal.removeTab();
                }
                ViewBombaImage.this.ctrlPrincipal.getPanel_usado().add((JPanel) e.getSource());
                
                //CALCULA A DIMENSÃO DO PAINEL LATERAL
                int x = (int) (ctrlPrincipal.getViewPrincipal().getPainelLateral().getWidth()*0.9);
                int y = (int) (ctrlPrincipal.getViewPrincipal().getPainelLateral().getHeight()*0.86);
                
                //CASO O CICLO SEJA O PRIMEIRO CHAMA A CLASSE VIEWBOMBAPANELKCS
                if(ciclo == 1){
                    ViewBombaPanelKCS viewBombaPanel = new ViewBombaPanelKCS();
                    viewBombaPanel.setPreferredSize(new Dimension(x, y));
                    ViewBombaImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanel().addTab("Bomba", null, viewBombaPanel);
                    ViewBombaImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanel().setSelectedComponent(viewBombaPanel);
                    ViewBombaImage.this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                }else{
                    //CASO O CICLO SEJA O SEGUNDO, CHAMA A CLASSE VIEWBOMBAPANELKALINA
                    if(ciclo == 2){
                        ViewBombaPanelRankine viewBombaPanel = new ViewBombaPanelRankine();
                        viewBombaPanel.setPreferredSize(new Dimension(x, y));
                        ViewBombaImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanel().addTab("Bomba", null, viewBombaPanel);
                        ViewBombaImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanel().setSelectedComponent(viewBombaPanel);
                        ViewBombaImage.this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
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
