/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.Condensador;

import Ciclo1.Control.ControlPrincipal;
import Ciclo1.Model.Ciclo1.ModelCondensador;
import Ciclo1.Model.Ciclo1.ModelMassa;
import Ciclo1.View.Condensador.ViewCondensadorPanelKCS;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;

/**
 *
 * @author alysonmp
 */
public class ControlCondensadorPanelKCS {
    private ViewCondensadorPanelKCS viewCondensador;
    private Session session;
    private ControlPrincipal ctrlPrincipal;
    
    public ControlCondensadorPanelKCS(ControlPrincipal ctrlPrincipal, Session session){
        this.session = session;
        viewCondensador = new ViewCondensadorPanelKCS(this);
        this.ctrlPrincipal = ctrlPrincipal;
    }

    /*public ViewCondensadorPanelKCS getViewCondensador() {
        return viewCondensador;
    }

    public void setViewCondensador(ViewCondensadorPanelKCS viewCondensador) {
        this.viewCondensador = viewCondensador;
    }
    
    public void atualizaMassa(){
        if(viewCondensador.getFieldMassa().getSelectedItem().equals("")){
            return;
        }
        
        Vector valores = new Vector();
        
        Criteria cr = this.session.createCriteria(ModelMassa.class);
        List result = cr.list();
        
        DefaultComboBoxModel model = (DefaultComboBoxModel)this.viewCondensador.getFieldMassa().getModel();
        
        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelMassa)result.get(i)).getMassa(), Double.parseDouble(this.viewCondensador.getFieldMassa().getSelectedItem().toString()))){
                return;
            }
        }
        
        Transaction tx = session.beginTransaction();
        
        if(result.size() < 5){
            ModelMassa m1 = new ModelMassa(Double.parseDouble(""+this.viewCondensador.getFieldMassa().getEditor().getItem()));
            this.session.saveOrUpdate(m1);
            
            valores.add(m1.getMassa()+"");
        }else{
            for(int i = result.size()-1; i > 0; i--){
                ModelMassa m1 = (ModelMassa) result.get(i);
                ModelMassa m2 = (ModelMassa) result.get(i-1);

                m1.setMassa(m2.getMassa());

                this.session.saveOrUpdate(m1);
                valores.add(m1.getMassa()+"");
            }

            ModelMassa m1 = (ModelMassa) result.get(0);
            m1.setMassa(Double.parseDouble(this.viewCondensador.getFieldMassa().getEditor().getItem().toString()));
            
            this.session.saveOrUpdate(m1);
            valores.add(m1.getMassa()+"");
        }
        
        tx.commit();
        
        atualizaComboBoxes(valores, this.viewCondensador.getFieldMassa());
    }

    public void atualizaTempEntrada() {
        if(viewCondensador.getFieldTempEntr().getSelectedItem().equals("")){
            return;
        }
        
        Vector valores = new Vector();
        
        Criteria cr = this.session.createCriteria(ModelCondensador.class);
        List result = cr.list();
        
        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelCondensador)result.get(i)).getTemperaturaEntr(), Double.parseDouble(this.viewCondensador.getFieldTempEntr().getSelectedItem().toString()))){
                return;
            }
        }
        
        Transaction tx = session.beginTransaction();
        
        if(result.size() < 5){
            ModelCondensador m1 = new ModelCondensador();
            m1.setTemperaturaEntr(Double.parseDouble(this.viewCondensador.getFieldTempEntr().getSelectedItem().toString()));
            this.session.saveOrUpdate(m1);
            
            valores.add(m1.getTemperaturaEntr()+"");
        }else{
            for(int i = result.size()-1; i > 0; i--){
                ModelCondensador m1 = (ModelCondensador) result.get(i);
                ModelCondensador m2 = (ModelCondensador) result.get(i-1);

                m1.setTemperaturaEntr(m2.getTemperaturaEntr());

                this.session.saveOrUpdate(m1);
                valores.add(m1.getTemperaturaSai()+"");
            }

            ModelCondensador m1 = (ModelCondensador) result.get(0);
            m1.setTemperaturaEntr(Double.parseDouble(this.viewCondensador.getFieldTempEntr().getEditor().getItem().toString()));
            
            this.session.saveOrUpdate(m1);
            valores.add(m1.getTemperaturaEntr()+"");
        }
        
        tx.commit();
        
        atualizaComboBoxes(valores, this.viewCondensador.getFieldTempEntr());
    }

    public void atualizaTempSaida() {
        if(viewCondensador.getFieldTempSai().getSelectedItem().equals("")){
            return;
        }
        
        Vector valores = new Vector();
        
        Criteria cr = this.session.createCriteria(ModelCondensador.class);
        List result = cr.list();
        
        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelCondensador)result.get(i)).getTemperaturaSai(), Double.parseDouble(this.viewCondensador.getFieldTempSai().getSelectedItem().toString()))){
                return;
            }
        }
        
        Transaction tx = session.beginTransaction();
        
        if(result.size() < 5){
            ModelCondensador m1 = new ModelCondensador();
            m1.setTemperaturaSai(Double.parseDouble(this.viewCondensador.getFieldTempSai().getSelectedItem().toString()));
            this.session.saveOrUpdate(m1);
            
            valores.add(m1.getTemperaturaSai()+"");
        }else{
            for(int i = result.size()-1; i > 0; i--){
                ModelCondensador m1 = (ModelCondensador) result.get(i);
                ModelCondensador m2 = (ModelCondensador) result.get(i-1);

                m1.setTemperaturaSai(m2.getTemperaturaSai());

                this.session.saveOrUpdate(m1);
                valores.add(m1.getTemperaturaSai()+"");
            }

            ModelCondensador m1 = (ModelCondensador) result.get(0);
            m1.setTemperaturaSai(Double.parseDouble(this.viewCondensador.getFieldTempSai().getEditor().getItem().toString()));
            
            this.session.saveOrUpdate(m1);
            valores.add(m1.getTemperaturaSai()+"");
        }
        
        tx.commit();
        
        atualizaComboBoxes(valores, this.viewCondensador.getFieldTempSai());
    }

    public void atualizaPressaoEntrada() {
        if(viewCondensador.getFieldPressaoEntr().getSelectedItem().equals("")){
            return;
        }
        
        Vector valores = new Vector();
        
        Criteria cr = this.session.createCriteria(ModelCondensador.class);
        List result = cr.list();
        
        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelCondensador)result.get(i)).getPressaoEntr(), Double.parseDouble(this.viewCondensador.getFieldPressaoEntr().getSelectedItem().toString()))){
                return;
            }
        }
        
        Transaction tx = session.beginTransaction();
        
        if(result.size() < 5){
            ModelCondensador m1 = new ModelCondensador();
            m1.setPressaoEntr(Double.parseDouble(this.viewCondensador.getFieldPressaoEntr().getSelectedItem().toString()));
            this.session.saveOrUpdate(m1);
            
            valores.add(m1.getPressaoEntr()+"");
        }else{
            for(int i = result.size()-1; i > 0; i--){
                ModelCondensador m1 = (ModelCondensador) result.get(i);
                ModelCondensador m2 = (ModelCondensador) result.get(i-1);

                m1.setPressaoEntr(m2.getPressaoEntr());

                this.session.saveOrUpdate(m1);
                valores.add(m1.getPressaoEntr()+"");
            }

            ModelCondensador m1 = (ModelCondensador) result.get(0);
            m1.setPressaoEntr(Double.parseDouble(this.viewCondensador.getFieldPressaoEntr().getEditor().getItem().toString()));
            
            this.session.saveOrUpdate(m1);
            valores.add(m1.getPressaoEntr()+"");
        }
        
        tx.commit();
        
        atualizaComboBoxes(valores, this.viewCondensador.getFieldPressaoEntr());
    }

    public void atualizaPressaoSaida() {
        if(viewCondensador.getFieldPressaoSai().getSelectedItem().equals("")){
            return;
        }
        
        Vector valores = new Vector();
        
        Criteria cr = this.session.createCriteria(ModelCondensador.class);
        List result = cr.list();
        
        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelCondensador)result.get(i)).getPressaoSai(), Double.parseDouble(this.viewCondensador.getFieldPressaoSai().getSelectedItem().toString()))){
                return;
            }
        }
        
        Transaction tx = session.beginTransaction();
        
        if(result.size() < 5){
            ModelCondensador m1 = new ModelCondensador();
            m1.setPressaoSai(Double.parseDouble(this.viewCondensador.getFieldPressaoSai().getSelectedItem().toString()));
            this.session.saveOrUpdate(m1);
            
            valores.add(m1.getPressaoSai()+"");
        }else{
            for(int i = result.size()-1; i > 0; i--){
                ModelCondensador m1 = (ModelCondensador) result.get(i);
                ModelCondensador m2 = (ModelCondensador) result.get(i-1);

                m1.setPressaoSai(m2.getPressaoSai());

                this.session.saveOrUpdate(m1);
                valores.add(m1.getPressaoSai()+"");
            }

            ModelCondensador m1 = (ModelCondensador) result.get(0);
            m1.setPressaoSai(Double.parseDouble(this.viewCondensador.getFieldPressaoSai().getEditor().getItem().toString()));
            
            this.session.saveOrUpdate(m1);
            valores.add(m1.getPressaoSai()+"");
        }
        
        tx.commit();
        
        atualizaComboBoxes(valores, this.viewCondensador.getFieldPressaoSai());
    }

    public void atualizaTempCond() {
        if(viewCondensador.getFieldTempCond().getSelectedItem().equals("")){
            return;
        }
        
        Vector valores = new Vector();
        
        Criteria cr = this.session.createCriteria(ModelCondensador.class);
        List result = cr.list();
        
        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelCondensador)result.get(i)).getTempCond(), Double.parseDouble(this.viewCondensador.getFieldTempCond().getSelectedItem().toString()))){
                return;
            }
        }
        
        Transaction tx = session.beginTransaction();
        
        if(result.size() < 5){
            ModelCondensador m1 = new ModelCondensador();
            m1.setTempCond(Double.parseDouble(this.viewCondensador.getFieldTempCond().getSelectedItem().toString()));
            this.session.saveOrUpdate(m1);
            
            valores.add(m1.getTempCond()+"");
        }else{
            for(int i = result.size()-1; i > 0; i--){
                ModelCondensador m1 = (ModelCondensador) result.get(i);
                ModelCondensador m2 = (ModelCondensador) result.get(i-1);

                m1.setTempCond(m2.getTempCond());

                this.session.saveOrUpdate(m1);
                valores.add(m1.getTempCond()+"");
            }

            ModelCondensador m1 = (ModelCondensador) result.get(0);
            m1.setTempCond(Double.parseDouble(this.viewCondensador.getFieldTempCond().getEditor().getItem().toString()));
            
            this.session.saveOrUpdate(m1);
            valores.add(m1.getTempCond()+"");
        }
        
        tx.commit();
        
        atualizaComboBoxes(valores, this.viewCondensador.getFieldTempCond());
    }

    public void atualizaDelta() {
        if(viewCondensador.getFieldDelta().getSelectedItem().equals("")){
            return;
        }
        
        Vector valores = new Vector();
        
        Criteria cr = this.session.createCriteria(ModelCondensador.class);
        List result = cr.list();
        
        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelCondensador)result.get(i)).getDeltaPressao(), Double.parseDouble(this.viewCondensador.getFieldDelta().getSelectedItem().toString()))){
                return;
            }
        }
        
        Transaction tx = session.beginTransaction();
        
        if(result.size() < 5){
            ModelCondensador m1 = new ModelCondensador();
            m1.setDeltaPressao(Double.parseDouble(this.viewCondensador.getFieldDelta().getSelectedItem().toString()));
            this.session.saveOrUpdate(m1);
            
            valores.add(m1.getDeltaPressao()+"");
        }else{
            for(int i = result.size()-1; i > 0; i--){
                ModelCondensador m1 = (ModelCondensador) result.get(i);
                ModelCondensador m2 = (ModelCondensador) result.get(i-1);

                m1.setDeltaPressao(m2.getDeltaPressao());

                this.session.saveOrUpdate(m1);
                valores.add(m1.getDeltaPressao()+"");
            }

            ModelCondensador m1 = (ModelCondensador) result.get(0);
            m1.setDeltaPressao(Double.parseDouble(this.viewCondensador.getFieldDelta().getEditor().getItem().toString()));
            
            this.session.saveOrUpdate(m1);
            valores.add(m1.getDeltaPressao()+"");
        }
        
        tx.commit();
        
        atualizaComboBoxes(valores, this.viewCondensador.getFieldDelta());
    }

    public void atualizaComboBoxes(Vector valores, JComboBox combo){
        Collections.reverse(valores);
        combo.removeAllItems();
        final DefaultComboBoxModel model = new DefaultComboBoxModel(valores);
        combo.setModel(model);
    }
    
    public void criaComboBoxes() {
        Criteria cr = this.session.createCriteria((ModelMassa.class));
        List results = cr.list();
        
        for(int i = 0; i < results.size(); i++){
            ModelMassa m = (ModelMassa)results.get(i);
            this.viewCondensador.getFieldMassa().addItem(m.getMassa());
        }
        
        cr = this.session.createCriteria((ModelCondensador.class));
        results = cr.list();
        
        for(int i = 0; i < results.size()-1; i++){
            ModelCondensador m = (ModelCondensador)results.get(i);
            this.viewCondensador.getFieldDelta().addItem(m.getDeltaPressao());
            this.viewCondensador.getFieldPressaoEntr().addItem(m.getPressaoEntr());
            this.viewCondensador.getFieldPressaoSai().addItem(m.getPressaoSai());
            this.viewCondensador.getFieldTempCond().addItem(m.getTempCond());
            this.viewCondensador.getFieldTempEntr().addItem(m.getTemperaturaEntr());
            this.viewCondensador.getFieldTempSai().addItem(m.getTemperaturaSai());
        }
    }

    public ControlPrincipal getCtrlPrincipal() {
        return ctrlPrincipal;
    }

    public void setCtrlPrincipal(ControlPrincipal ctrlPrincipal) {
        this.ctrlPrincipal = ctrlPrincipal;
    }*/

    public ViewCondensadorPanelKCS getViewCondensador() {
        return viewCondensador;
    }

    public void setViewCondensador(ViewCondensadorPanelKCS viewCondensador) {
        this.viewCondensador = viewCondensador;
    }
}
