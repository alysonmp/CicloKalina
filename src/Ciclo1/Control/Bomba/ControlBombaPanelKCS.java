/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.Bomba;

import Ciclo2.Model.Ciclo2.ModelBomba;
import Ciclo2.Model.Ciclo2.ModelMassa;
import Ciclo2.View.Bomba.ViewBombaPanelRankine;
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
public class ControlBombaPanelKCS {
    private ViewBombaPanelRankine viewBomba;
    private Session session;
    
    public ControlBombaPanelKCS(Session session){
        this.session = session;
        viewBomba = new ViewBombaPanelRankine(this);
    }

    public ViewBombaPanelRankine getViewBomba() {
        return viewBomba;
    }

    public void setViewBomba(ViewBombaPanelRankine viewBomba) {
        this.viewBomba = viewBomba;
    }
    
    public void atualizaMassa(){
        if(viewBomba.getFieldMassa().getSelectedItem().equals("")){
            return;
        }
        
        Vector valores = new Vector();
        
        Criteria cr = this.session.createCriteria(ModelMassa.class);
        List result = cr.list();
        
        DefaultComboBoxModel model = (DefaultComboBoxModel)this.viewBomba.getFieldMassa().getModel();
        
        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelMassa)result.get(i)).getMassa(), Double.parseDouble(this.viewBomba.getFieldMassa().getSelectedItem().toString()))){
                return;
            }
        }
        
        Transaction tx = session.beginTransaction();
        
        if(result.size() < 5){
            ModelMassa m1 = new ModelMassa(Double.parseDouble(""+this.viewBomba.getFieldMassa().getEditor().getItem()));
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
            m1.setMassa(Double.parseDouble(this.viewBomba.getFieldMassa().getEditor().getItem().toString()));
            
            this.session.saveOrUpdate(m1);
            valores.add(m1.getMassa()+"");
        }
        
        tx.commit();
        
        atualizaComboBoxes(valores, this.viewBomba.getFieldMassa());
    }

    public void atualizaTempEntrada() {
        if(viewBomba.getFieldTempEntr().getSelectedItem().equals("")){
            return;
        }
        
        Vector valores = new Vector();
        
        Criteria cr = this.session.createCriteria(ModelBomba.class);
        List result = cr.list();
        
        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelBomba)result.get(i)).getTemperaturaEntr(), Double.parseDouble(this.viewBomba.getFieldTempEntr().getSelectedItem().toString()))){
                return;
            }
        }
        
        Transaction tx = session.beginTransaction();
        
        if(result.size() < 5){
            ModelBomba m1 = new ModelBomba();
            m1.setTemperaturaEntr(Double.parseDouble(this.viewBomba.getFieldTempEntr().getSelectedItem().toString()));
            this.session.saveOrUpdate(m1);
            
            valores.add(m1.getTemperaturaEntr()+"");
        }else{
            for(int i = result.size()-1; i > 0; i--){
                ModelBomba m1 = (ModelBomba) result.get(i);
                ModelBomba m2 = (ModelBomba) result.get(i-1);

                m1.setTemperaturaEntr(m2.getTemperaturaEntr());

                this.session.saveOrUpdate(m1);
                valores.add(m1.getTemperaturaSai()+"");
            }

            ModelBomba m1 = (ModelBomba) result.get(0);
            m1.setTemperaturaEntr(Double.parseDouble(this.viewBomba.getFieldTempEntr().getEditor().getItem().toString()));
            
            this.session.saveOrUpdate(m1);
            valores.add(m1.getTemperaturaEntr()+"");
        }
        
        tx.commit();
        
        atualizaComboBoxes(valores, this.viewBomba.getFieldTempEntr());
    }

    public void atualizaTempSaida() {
        if(viewBomba.getFieldTempSai().getSelectedItem().equals("")){
            return;
        }
        
        Vector valores = new Vector();
        
        Criteria cr = this.session.createCriteria(ModelBomba.class);
        List result = cr.list();
        
        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelBomba)result.get(i)).getTemperaturaSai(), Double.parseDouble(this.viewBomba.getFieldTempSai().getSelectedItem().toString()))){
                return;
            }
        }
        
        Transaction tx = session.beginTransaction();
        
        if(result.size() < 5){
            ModelBomba m1 = new ModelBomba();
            m1.setTemperaturaSai(Double.parseDouble(this.viewBomba.getFieldTempSai().getSelectedItem().toString()));
            this.session.saveOrUpdate(m1);
            
            valores.add(m1.getTemperaturaSai()+"");
        }else{
            for(int i = result.size()-1; i > 0; i--){
                ModelBomba m1 = (ModelBomba) result.get(i);
                ModelBomba m2 = (ModelBomba) result.get(i-1);

                m1.setTemperaturaSai(m2.getTemperaturaSai());

                this.session.saveOrUpdate(m1);
                valores.add(m1.getTemperaturaSai()+"");
            }

            ModelBomba m1 = (ModelBomba) result.get(0);
            m1.setTemperaturaSai(Double.parseDouble(this.viewBomba.getFieldTempSai().getEditor().getItem().toString()));
            
            this.session.saveOrUpdate(m1);
            valores.add(m1.getTemperaturaSai()+"");
        }
        
        tx.commit();
        
        atualizaComboBoxes(valores, this.viewBomba.getFieldTempSai());
    }

    public void atualizaPressaoEntrada() {
        if(viewBomba.getFieldPressaoEntr().getSelectedItem().equals("")){
            return;
        }
        
        Vector valores = new Vector();
        
        Criteria cr = this.session.createCriteria(ModelBomba.class);
        List result = cr.list();
        
        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelBomba)result.get(i)).getPressaoEntr(), Double.parseDouble(this.viewBomba.getFieldPressaoEntr().getSelectedItem().toString()))){
                return;
            }
        }
        
        Transaction tx = session.beginTransaction();
        
        if(result.size() < 5){
            ModelBomba m1 = new ModelBomba();
            m1.setPressaoEntr(Double.parseDouble(this.viewBomba.getFieldPressaoEntr().getSelectedItem().toString()));
            this.session.saveOrUpdate(m1);
            
            valores.add(m1.getPressaoEntr()+"");
        }else{
            for(int i = result.size()-1; i > 0; i--){
                ModelBomba m1 = (ModelBomba) result.get(i);
                ModelBomba m2 = (ModelBomba) result.get(i-1);

                m1.setPressaoEntr(m2.getPressaoEntr());

                this.session.saveOrUpdate(m1);
                valores.add(m1.getPressaoEntr()+"");
            }

            ModelBomba m1 = (ModelBomba) result.get(0);
            m1.setPressaoEntr(Double.parseDouble(this.viewBomba.getFieldPressaoEntr().getEditor().getItem().toString()));
            
            this.session.saveOrUpdate(m1);
            valores.add(m1.getPressaoEntr()+"");
        }
        
        tx.commit();
        
        atualizaComboBoxes(valores, this.viewBomba.getFieldPressaoEntr());
    }

    public void atualizaPressaoSaida() {
        if(viewBomba.getFieldPressaoSai().getSelectedItem().equals("")){
            return;
        }
        
        Vector valores = new Vector();
        
        Criteria cr = this.session.createCriteria(ModelBomba.class);
        List result = cr.list();
        
        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelBomba)result.get(i)).getPressaoSai(), Double.parseDouble(this.viewBomba.getFieldPressaoSai().getSelectedItem().toString()))){
                return;
            }
        }
        
        Transaction tx = session.beginTransaction();
        
        if(result.size() < 5){
            ModelBomba m1 = new ModelBomba();
            m1.setPressaoSai(Double.parseDouble(this.viewBomba.getFieldPressaoSai().getSelectedItem().toString()));
            this.session.saveOrUpdate(m1);
            
            valores.add(m1.getPressaoSai()+"");
        }else{
            for(int i = result.size()-1; i > 0; i--){
                ModelBomba m1 = (ModelBomba) result.get(i);
                ModelBomba m2 = (ModelBomba) result.get(i-1);

                m1.setPressaoSai(m2.getPressaoSai());

                this.session.saveOrUpdate(m1);
                valores.add(m1.getPressaoSai()+"");
            }

            ModelBomba m1 = (ModelBomba) result.get(0);
            m1.setPressaoSai(Double.parseDouble(this.viewBomba.getFieldPressaoSai().getEditor().getItem().toString()));
            
            this.session.saveOrUpdate(m1);
            valores.add(m1.getPressaoSai()+"");
        }
        
        tx.commit();
        
        atualizaComboBoxes(valores, this.viewBomba.getFieldPressaoSai());
    }

    public void atualizaEficiencia() {
        if(viewBomba.getFieldEfic().getSelectedItem().equals("")){
            return;
        }
        
        Vector valores = new Vector();
        
        Criteria cr = this.session.createCriteria(ModelBomba.class);
        List result = cr.list();
        
        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelBomba)result.get(i)).getEficiencia(), Double.parseDouble(this.viewBomba.getFieldEfic().getSelectedItem().toString()))){
                return;
            }
        }
        
        Transaction tx = session.beginTransaction();
        
        if(result.size() < 5){
            ModelBomba m1 = new ModelBomba();
            m1.setEficiencia(Double.parseDouble(this.viewBomba.getFieldEfic().getSelectedItem().toString()));
            this.session.saveOrUpdate(m1);
            
            valores.add(m1.getEficiencia()+"");
        }else{
            for(int i = result.size()-1; i > 0; i--){
                ModelBomba m1 = (ModelBomba) result.get(i);
                ModelBomba m2 = (ModelBomba) result.get(i-1);

                m1.setEficiencia(m2.getEficiencia());

                this.session.saveOrUpdate(m1);
                valores.add(m1.getEficiencia()+"");
            }

            ModelBomba m1 = (ModelBomba) result.get(0);
            m1.setEficiencia(Double.parseDouble(this.viewBomba.getFieldEfic().getEditor().getItem().toString()));
            
            this.session.saveOrUpdate(m1);
            valores.add(m1.getEficiencia()+"");
        }
        
        tx.commit();
        
        atualizaComboBoxes(valores, this.viewBomba.getFieldEfic());
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
            this.viewBomba.getFieldMassa().addItem(m.getMassa());
        }
        
        cr = this.session.createCriteria((ModelBomba.class));
        results = cr.list();
        
        for(int i = 0; i < results.size()-1; i++){
            ModelBomba m = (ModelBomba)results.get(i);
            this.viewBomba.getFieldPressaoEntr().addItem(m.getPressaoEntr());
            this.viewBomba.getFieldPressaoSai().addItem(m.getPressaoSai());
            this.viewBomba.getFieldTempEntr().addItem(m.getTemperaturaEntr());
            this.viewBomba.getFieldTempSai().addItem(m.getTemperaturaSai());
            this.viewBomba.getFieldEfic().addItem(m.getEficiencia());
        }
    }
}
