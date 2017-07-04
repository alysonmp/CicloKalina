/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Etapa;

import Control.ControlPrincipal;
import Model.Ciclo2.ModelEtapa1;
import View.Etapas.ViewEtapa1PanelRankine;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author leonardo
 */
public class ControlEtapa1PanelRankine {
    private ViewEtapa1PanelRankine viewEtapa1;
    private ControlPrincipal ctrlPrincipal;
    private Session session;
    
    public ControlEtapa1PanelRankine(ControlPrincipal ctrlPrincipal){
        this.ctrlPrincipal = ctrlPrincipal;
        this.session = ctrlPrincipal.getSession();
        viewEtapa1 = new ViewEtapa1PanelRankine(this);
    }
    
    public void atualizaComboBoxes(Vector valores, JComboBox combo){
        Collections.reverse(valores);
        combo.removeAllItems();
        final DefaultComboBoxModel model = new DefaultComboBoxModel(valores);
        combo.setModel(model);
    }
    
    public void criaComboBoxes() {
        Criteria cr = this.session.createCriteria((ModelEtapa1.class));
        List results = cr.list();
        
        for(int i = 0; i < results.size(); i++){
            ModelEtapa1 m = (ModelEtapa1)results.get(i);
            this.viewEtapa1.getFieldTemp().addItem(m.getTemperatura());
            this.viewEtapa1.getFieldPressao().addItem(m.getPressao());
        }
    }
    
    public void atualizaTemperatura() {
        if(viewEtapa1.getFieldTemp().getSelectedItem().equals("")){
            return;
        }
        
        Vector valores = new Vector();
        
        Criteria cr = this.session.createCriteria(ModelEtapa1.class);
        List result = cr.list();
        
        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelEtapa1)result.get(i)).getTemperatura(), Double.parseDouble(this.viewEtapa1.getFieldTemp().getSelectedItem().toString()))){
                return;
            }
        }
        
        Transaction tx = session.beginTransaction();
        
        if(result.size() < 5){
            ModelEtapa1 m1 = new ModelEtapa1();
            m1.setTemperatura(Double.parseDouble(this.viewEtapa1.getFieldTemp().getSelectedItem().toString()));
            this.session.saveOrUpdate(m1);
            
            valores.add(m1.getTemperatura()+"");
        }else{
            for(int i = result.size()-1; i > 0; i--){
                ModelEtapa1 m1 = (ModelEtapa1) result.get(i);
                ModelEtapa1 m2 = (ModelEtapa1) result.get(i-1);

                m1.setTemperatura(m2.getTemperatura());

                this.session.saveOrUpdate(m1);
                valores.add(m1.getTemperatura()+"");
            }

            ModelEtapa1 m1 = (ModelEtapa1) result.get(0);
            m1.setTemperatura(Double.parseDouble(this.viewEtapa1.getFieldTemp().getEditor().getItem().toString()));
            
            this.session.saveOrUpdate(m1);
            valores.add(m1.getTemperatura()+"");
        }
        
        tx.commit();
        
        atualizaComboBoxes(valores, this.viewEtapa1.getFieldTemp());
    }
    
    public void atualizaPressao() {
        if(viewEtapa1.getFieldPressao().getSelectedItem().equals("")){
            return;
        }
        
        Vector valores = new Vector();
        
        Criteria cr = this.session.createCriteria(ModelEtapa1.class);
        List result = cr.list();
        
        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelEtapa1)result.get(i)).getPressao(), Double.parseDouble(this.viewEtapa1.getFieldPressao().getSelectedItem().toString()))){
                return;
            }
        }
        
        Transaction tx = session.beginTransaction();
        
        if(result.size() < 5){
            ModelEtapa1 m1 = new ModelEtapa1();
            m1.setPressao(Double.parseDouble(this.viewEtapa1.getFieldPressao().getSelectedItem().toString()));
            this.session.saveOrUpdate(m1);
            
            valores.add(m1.getPressao()+"");
        }else{
            for(int i = result.size()-1; i > 0; i--){
                ModelEtapa1 m1 = (ModelEtapa1) result.get(i);
                ModelEtapa1 m2 = (ModelEtapa1) result.get(i-1);

                m1.setPressao(m2.getPressao());

                this.session.saveOrUpdate(m1);
                valores.add(m1.getPressao()+"");
            }
            
            ModelEtapa1 m1 = (ModelEtapa1) result.get(0);
            m1.setPressao(Double.parseDouble(this.viewEtapa1.getFieldPressao().getEditor().getItem().toString()));
            
            this.session.saveOrUpdate(m1);
            valores.add(m1.getPressao()+"");
        }
        
        tx.commit();
        
        atualizaComboBoxes(valores, this.viewEtapa1.getFieldPressao());
    }

    public ViewEtapa1PanelRankine getViewEtapa1() {
        return viewEtapa1;
    }

    public void setViewEtapa1(ViewEtapa1PanelRankine viewEtapa1) {
        this.viewEtapa1 = viewEtapa1;
    }

    
    public ControlPrincipal getCtrlPrincipal() {
        return ctrlPrincipal;
    }

    public void setCtrlPrincipal(ControlPrincipal ctrlPrincipal) {
        this.ctrlPrincipal = ctrlPrincipal;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
    
    
}
