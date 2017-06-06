/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.ModelStart;
import View.ViewCaixaResposta;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 *
 * @author alysonmp
 */
public class ControlCaixaResposta {
    
    ViewCaixaResposta viewCaixa;
    
    public ControlCaixaResposta(int x, int y, Session session, int etapa){
        Criteria cr = session.createCriteria(ModelStart.class);
        List results = cr.list();
        ModelStart start;
        
        if(results.isEmpty()){
            start = null;
        }else{
            start = (ModelStart)results.get(0);
        }
        
        viewCaixa = new ViewCaixaResposta(x, y, start, etapa);
    }

    public ViewCaixaResposta getViewCaixa() {
        return viewCaixa;
    }

    public void setViewCaixa(ViewCaixaResposta viewCaixa) {
        this.viewCaixa = viewCaixa;
    }
    
}
