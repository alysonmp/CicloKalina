/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Regenerador;

import Control.ControlCaixaResposta;
import Control.ControlPrincipal;
import Control.Regenerador.ControlRegeneradorPanelRankine;
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
public class ViewRegeneradorImage extends JPanel{
    
    private ControlPrincipal ctrlPrincipal;
    private ControlCaixaResposta ctrlResposta;
    
    //CONSTRUTOR DA TURBINA
    public ViewRegeneradorImage(ControlPrincipal ctrlPrincipal){
        this.ctrlPrincipal = ctrlPrincipal;
        
        this.setName("Regenerador");
        
        //FUNÇÃO ATIVADA AO SE CLICAR NO ELEMENTO TURBINA NAS IMAGENS
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //APAGA TODAS AS BORDAS DOS ELEMENTOS CLICADOS
                Component[] components = ViewRegeneradorImage.this.ctrlPrincipal.getViewPrincipal().getPainelCiclos().getComponents();
                for(Component c:components){
                    JPanel panel = (JPanel)c;
                    panel.setBorder(null);
                }
                
                //VERIFICA SE O PAINEL JÁ FOI ADICIONADO AO PAINEL LATERAL
                if(ViewRegeneradorImage.this.ctrlPrincipal.getPanel_usado().contains((JPanel)e.getSource())){
                    for(int i = 0; i < ViewRegeneradorImage.this.ctrlPrincipal.getPanel_usado().size(); i++) {
                        if(e.getSource() == ViewRegeneradorImage.this.ctrlPrincipal.getPanel_usado().get(i)){
                            ViewRegeneradorImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanelComp().setSelectedIndex(i);
                            ViewRegeneradorImage.this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
                            return;
                        }
                    }
                }
                if(ViewRegeneradorImage.this.ctrlPrincipal.getPanel_usado().size() > 2){
                    ViewRegeneradorImage.this.ctrlPrincipal.removeTab();
                }
                ViewRegeneradorImage.this.ctrlPrincipal.getPanel_usado().add((JPanel) e.getSource());
                
                //CALCULA A DIMENSÃO DO PAINEL LATERAL
                int x = (int) (ctrlPrincipal.getViewPrincipal().getPainelLateral().getWidth()*0.9);
                int y = (int) (ctrlPrincipal.getViewPrincipal().getPainelLateral().getHeight()*0.35);
                
                ControlRegeneradorPanelRankine controlRegeneradorPanel = new ControlRegeneradorPanelRankine(ViewRegeneradorImage.this.ctrlPrincipal);
                controlRegeneradorPanel.getViewRegeneradorPanel().setPreferredSize(new Dimension(x, y));
                ViewRegeneradorImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanelComp().addTab("Regenerador", null, controlRegeneradorPanel.getViewRegeneradorPanel());
                ViewRegeneradorImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanelComp().setSelectedComponent(controlRegeneradorPanel.getViewRegeneradorPanel());
                ViewRegeneradorImage.this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
                controlRegeneradorPanel.getValuesComboBox();
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
                ctrlResposta = new ControlCaixaResposta(x, y, ctrlPrincipal.getSession(), "regenerador");
                ctrlResposta.getViewCaixa().getFrameCaixa().setBounds(ViewRegeneradorImage.this.getLocationOnScreen().x+ViewRegeneradorImage.this.getWidth(), ViewRegeneradorImage.this.getLocationOnScreen().y-ViewRegeneradorImage.this.getHeight(), 250, 125);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ctrlResposta.getViewCaixa().getFrameCaixa().dispose();
            }
        });
    }
}
