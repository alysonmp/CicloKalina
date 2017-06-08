/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Bomba;

import Control.Bomba.ControlBombaPanelRankine;
import Control.ControlCaixaResposta;
import Control.ControlPrincipal;
import View.ViewCiclos;
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
    private ControlCaixaResposta ctrlResposta;
    
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
                            ViewBombaImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanelComp().setSelectedIndex(i);
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
                int y = (int) (ctrlPrincipal.getViewPrincipal().getPainelLateral().getHeight()*0.55);
                
                ControlBombaPanelRankine Bomba = new ControlBombaPanelRankine(ViewBombaImage.this.ctrlPrincipal.getSession());
                Bomba.getViewBomba().setPreferredSize(new Dimension(x, y));
                ViewBombaImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanelComp().addTab("Bomba", null, Bomba.getViewBomba());
                ViewBombaImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanelComp().setSelectedComponent(Bomba.getViewBomba());
                ViewBombaImage.this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                Bomba.criaComboBoxes();
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
                int x = e.getX();
                int y = e.getY();
                //ctrlResposta = new ControlCaixaResposta(x, y, ctrlPrincipal.getSession());
                //ctrlResposta.getViewCaixa().getFrameCaixa().setBounds(ViewBombaImage.this.getLocationOnScreen().x+ViewBombaImage.this.getWidth(), ViewBombaImage.this.getLocationOnScreen().y-ViewBombaImage.this.getHeight(), 200, 100);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //ctrlResposta.getViewCaixa().getFrameCaixa().dispose();
            }
        });
    }
}
