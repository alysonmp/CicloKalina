package View.Etapas;

import Control.ControlCaixaResposta;
import Control.ControlPrincipal;
import Control.Etapa.ControlEtapa1PanelRankine;
import Control.Start;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class ViewEtapa1Image extends JPanel{
    
    private ControlPrincipal ctrlPrincipal;
    private ControlCaixaResposta ctrlResposta;
    Start start;
    
    //CONSTRUTOR DA Etapa
    public ViewEtapa1Image(ControlPrincipal ctrlPrincipal){
        this.start = start;
        this.ctrlPrincipal = ctrlPrincipal;
        
        this.setName("Etapa 1");

        //FUNÇÃO ATIVADA AO SE CLICAR NO ELEMENTO Etapa NAS IMAGENS
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {                
                //APAGA TODAS AS BORDAS DOS ELEMENTOS CLICADOS
                Component[] components = ViewEtapa1Image.this.ctrlPrincipal.getViewPrincipal().getPainelCiclos().getComponents();
                for(Component c:components){
                    JPanel panel = (JPanel)c;
                    panel.setBorder(null);
                }
                
                ViewEtapa1Image.this.ctrlPrincipal.getViewPrincipal().getFrameDados().setVisible(true);
                
                //VERIFICA SE O PAINEL JÁ FOI ADICIONADO AO PAINEL LATERAL
                if(ViewEtapa1Image.this.ctrlPrincipal.getPanel_usado().contains((JPanel)e.getSource())){
                    for(int i = 0; i < ViewEtapa1Image.this.ctrlPrincipal.getPanel_usado().size(); i++) {
                        if(e.getSource() == ViewEtapa1Image.this.ctrlPrincipal.getPanel_usado().get(i)){
                            ViewEtapa1Image.this.ctrlPrincipal.getViewPrincipal().getTabbedPanelComp().setSelectedIndex(i);
                            ViewEtapa1Image.this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
                            return;
                        }
                    }
                }
                if(ViewEtapa1Image.this.ctrlPrincipal.getPanel_usado().size() > 2){
                    ViewEtapa1Image.this.ctrlPrincipal.removeTab();
                }
                ViewEtapa1Image.this.ctrlPrincipal.getPanel_usado().add((JPanel) e.getSource());
                
                //CALCULA A DIMENSÃO DO PAINEL LATERAL
                int x = (int) (ctrlPrincipal.getViewPrincipal().getPainelLateral().getWidth()*0.9);
                int y = (int) (ctrlPrincipal.getViewPrincipal().getPainelLateral().getHeight()*0.86);
                
                ControlEtapa1PanelRankine Etapa1 = new ControlEtapa1PanelRankine(ViewEtapa1Image.this.ctrlPrincipal);
                Etapa1.getViewEtapa1().setPreferredSize(new Dimension(x,y));
                ViewEtapa1Image.this.ctrlPrincipal.getViewPrincipal().getTabbedPanelComp().addTab("Etapa 1",null,Etapa1.getViewEtapa1());
                ViewEtapa1Image.this.ctrlPrincipal.getViewPrincipal().getTabbedPanelComp().setSelectedComponent(Etapa1.getViewEtapa1());
                ViewEtapa1Image.this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
                Etapa1.criaComboBoxes();
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
                ctrlResposta = new ControlCaixaResposta(x, y, ctrlPrincipal.getSession(),"Etapa 1");
                ctrlResposta.getViewCaixa().getFrameCaixa().setBounds(ViewEtapa1Image.this.getLocationOnScreen().x+ViewEtapa1Image.this.getWidth(), ViewEtapa1Image.this.getLocationOnScreen().y-ViewEtapa1Image.this.getHeight(), 250, 125);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ctrlResposta.getViewCaixa().getFrameCaixa().dispose();
            }
        });
    }
}
