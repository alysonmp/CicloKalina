/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Condensador;

import View.Condensador.ViewCondensadorPanelRankine;
import org.hibernate.Session;

/**
 *
 * @author alysonmp
 */
public class ControlCondensadorPanelRankine {
    ViewCondensadorPanelRankine viewCondensador;
    
    public ControlCondensadorPanelRankine(Session session){
        viewCondensador = new ViewCondensadorPanelRankine(this);
    }

    public ViewCondensadorPanelRankine getViewCondensador() {
        return viewCondensador;
    }

    public void setViewCondensador(ViewCondensadorPanelRankine viewCondensador) {
        this.viewCondensador = viewCondensador;
    }
    
    public void atualizaMassa(){
        
    }
}
