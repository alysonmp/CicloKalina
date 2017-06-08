/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Turbina;

import Control.ControlCaixaResposta;
import Control.ControlPrincipal;
import Control.Turbina.ControlTurbinaPanelRankine;
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
public class ViewTurbinaImage extends JPanel{
    
    private ControlPrincipal ctrlPrincipal;
    private ControlCaixaResposta ctrlResposta;
    
    //CONSTRUTOR DA TURBINA
    public ViewTurbinaImage(ControlPrincipal ctrlPrincipal, int ciclo){
        this.ctrlPrincipal = ctrlPrincipal;
        
        this.setName("Turbina");
        
        //FUNÇÃO ATIVADA AO SE CLICAR NO ELEMENTO TURBINA NAS IMAGENS
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //APAGA TODAS AS BORDAS DOS ELEMENTOS CLICADOS
                Component[] components = ViewTurbinaImage.this.ctrlPrincipal.getViewPrincipal().getPainelCiclos().getComponents();
                for(Component c:components){
                    JPanel panel = (JPanel)c;
                    panel.setBorder(null);
                }
                
                //VERIFICA SE O PAINEL JÁ FOI ADICIONADO AO PAINEL LATERAL
                if(ViewTurbinaImage.this.ctrlPrincipal.getPanel_usado().contains((JPanel)e.getSource())){
                    for(int i = 0; i < ViewTurbinaImage.this.ctrlPrincipal.getPanel_usado().size(); i++) {
                        if(e.getSource() == ViewTurbinaImage.this.ctrlPrincipal.getPanel_usado().get(i)){
                            ViewTurbinaImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanelComp().setSelectedIndex(i);
                            ViewTurbinaImage.this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
                            return;
                        }
                    }
                }
                if(ViewTurbinaImage.this.ctrlPrincipal.getPanel_usado().size() > 2){
                    ViewTurbinaImage.this.ctrlPrincipal.removeTab();
                }
                ViewTurbinaImage.this.ctrlPrincipal.getPanel_usado().add((JPanel) e.getSource());
                
                //CALCULA A DIMENSÃO DO PAINEL LATERAL
                int x = (int) (ctrlPrincipal.getViewPrincipal().getPainelLateral().getWidth()*0.9);
                int y = (int) (ctrlPrincipal.getViewPrincipal().getPainelLateral().getHeight()*0.55);
                
                ControlTurbinaPanelRankine controlTurbinaPanel = new ControlTurbinaPanelRankine(ViewTurbinaImage.this.ctrlPrincipal);
                controlTurbinaPanel.getViewTurbinaPanel().setPreferredSize(new Dimension(x, y));
                ViewTurbinaImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanelComp().addTab("Turbina", null, controlTurbinaPanel.getViewTurbinaPanel());
                ViewTurbinaImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanelComp().setSelectedComponent(controlTurbinaPanel.getViewTurbinaPanel());
                ViewTurbinaImage.this.setBorder(BorderFactory.createLineBorder(Color.black, 2)); 
                controlTurbinaPanel.getValuesComboBox();
            }

            //NÃO UTILIZADOS
            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                ctrlResposta = new ControlCaixaResposta(x, y, ctrlPrincipal.getSession(), "turbina");
                ctrlResposta.getViewCaixa().getFrameCaixa().setBounds(ViewTurbinaImage.this.getLocationOnScreen().x+ViewTurbinaImage.this.getWidth(), ViewTurbinaImage.this.getLocationOnScreen().y-ViewTurbinaImage.this.getHeight(), 250, 125);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ctrlResposta.getViewCaixa().getFrameCaixa().dispose();
            }
        });
    }
}
