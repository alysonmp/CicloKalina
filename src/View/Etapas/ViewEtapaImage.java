/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Etapas;

import Control.ControlCaixaResposta;
import Control.ControlPrincipal;
import Control.Start;
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
public class ViewEtapaImage extends JPanel{
    
    private ControlPrincipal ctrlPrincipal;
    private ControlCaixaResposta ctrlResposta;
    Start start;
    
    //CONSTRUTOR DA Etapa
    public ViewEtapaImage(ControlPrincipal ctrlPrincipal, int ciclo, String etapa, Start start){
        this.start = start;
        this.ctrlPrincipal = ctrlPrincipal;
        
        this.setName("Etapa"+etapa);
        this.setBackground(Color.black);
        //FUNÇÃO ATIVADA AO SE CLICAR NO ELEMENTO Etapa NAS IMAGENS
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //APAGA TODAS AS BORDAS DOS ELEMENTOS CLICADOS
                Component[] components = ViewEtapaImage.this.ctrlPrincipal.getViewPrincipal().getPainelCiclos().getComponents();
                for(Component c:components){
                    JPanel panel = (JPanel)c;
                    panel.setBorder(null);
                }
                
                //CALCULA A DIMENSÃO DO PAINEL LATERAL
                int x = (int) (ctrlPrincipal.getViewPrincipal().getPainelLateral().getWidth()*0.9);
                int y = (int) (ctrlPrincipal.getViewPrincipal().getPainelLateral().getHeight()*0.86);
                
                //ControlEtapaPanelRankine Etapa = new ControlEtapaPanelRankine(ViewEtapaImage.this.ctrlPrincipal.getSession());
                //Etapa.getViewEtapa().setPreferredSize(new Dimension(x, y));
                //ViewEtapaImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanel().addTab("Etapa", null, Etapa.getViewEtapa());
                //ViewEtapaImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanel().setSelectedComponent(Etapa.getViewEtapa());
                ViewEtapaImage.this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                //Etapa.criaComboBoxes();
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
                ctrlResposta = new ControlCaixaResposta(x, y, ctrlPrincipal.getSession(), etapa);
                ctrlResposta.getViewCaixa().getFrameCaixa().setBounds(ViewEtapaImage.this.getLocationOnScreen().x+ViewEtapaImage.this.getWidth(), ViewEtapaImage.this.getLocationOnScreen().y-ViewEtapaImage.this.getHeight(), 250, 125);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ctrlResposta.getViewCaixa().getFrameCaixa().dispose();
            }
        });
    }
}
