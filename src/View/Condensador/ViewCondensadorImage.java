/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Condensador;

import Control.Condensador.ControlCondensadorPanelRankine;
import Control.ControlCaixaResposta;
import Control.ControlPrincipal;
import View.Bomba.ViewBombaImage;
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
    private ControlCaixaResposta ctrlResposta;
    
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
                            ViewCondensadorImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanelComp().setSelectedIndex(i);
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
                int y = (int) (ctrlPrincipal.getViewPrincipal().getPainelLateral().getHeight()*0.55);
                
                
                ControlCondensadorPanelRankine Condensador = new ControlCondensadorPanelRankine(ViewCondensadorImage.this.ctrlPrincipal, ViewCondensadorImage.this.session);
                Condensador.getViewCondensador().setPreferredSize(new Dimension(x, y));
                ViewCondensadorImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanelComp().addTab("Condensador", null, Condensador.getViewCondensador());
                ViewCondensadorImage.this.ctrlPrincipal.getViewPrincipal().getTabbedPanelComp().setSelectedComponent(Condensador.getViewCondensador());
                ViewCondensadorImage.this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                Condensador.criaComboBoxes();
                
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
                //ctrlResposta = new ControlCaixaResposta(x, y, ctrlPrincipal.getSession());
                //ctrlResposta.getViewCaixa().getFrameCaixa().setBounds(ViewCondensadorImage.this.getLocationOnScreen().x+ViewCondensadorImage.this.getWidth(), ViewCondensadorImage.this.getLocationOnScreen().y-ViewCondensadorImage.this.getHeight(), 200, 100);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //ctrlResposta.getViewCaixa().getFrameCaixa().dispose();
            }
        });
    }
}
