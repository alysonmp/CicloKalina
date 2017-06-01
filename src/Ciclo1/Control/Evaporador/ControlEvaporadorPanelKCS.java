/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.Evaporador;

import Ciclo1.Control.ControlPrincipal;
import Ciclo1.Model.Ciclo1.ModelEvaporador;
import Ciclo1.Model.Ciclo1.ModelMassa;
import Ciclo1.View.Evaporador.ViewEvaporadorPanelKCS;
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
/**
 *
 * @author leonardo
 */
public class ControlEvaporadorPanelKCS {

    private ViewEvaporadorPanelKCS viewEvaporadorPanel;
    
    private Session session;
        
    public ControlEvaporadorPanelKCS(ControlPrincipal ctrlPrincipal) {
        this.session = ctrlPrincipal.getSession();
        viewEvaporadorPanel = new ViewEvaporadorPanelKCS(this);
    }
    
    /*public void atualizaComboBox(Vector valor,JComboBox combo){
        Collections.reverse(valor);
        DefaultComboBoxModel model = new DefaultComboBoxModel(valor);
        combo.setModel(model);
    }
    
    //,JComboBox tempS,JComboBox pressE,JComboBox pressS,JComboBo
    public void getValuesComboBox(){
        Criteria cr = this.session.createCriteria((ModelMassa.class));
        List results = cr.list();
        
        for(int i = 0; i < results.size(); i++){
            ModelMassa m = (ModelMassa)results.get(i);
            this.viewEvaporadorPanel.getFieldMassa().addItem(m.getMassa());
        }
        
        cr = this.session.createCriteria(ModelEvaporador.class);
        results = cr.list();
        
        for(int i=0;i<results.size();i++){
            ModelEvaporador evaporador = (ModelEvaporador)results.get(i);
            this.viewEvaporadorPanel.getFieldTempEntr().addItem(evaporador.getTemperaturaEntr());
            this.viewEvaporadorPanel.getFieldTempSai().addItem(evaporador.getTemperaturaSai());
            this.viewEvaporadorPanel.getFieldPressaoEntr().addItem(evaporador.getPressaoEntr());
            this.viewEvaporadorPanel.getFieldPressaoSai().addItem(evaporador.getPressaoSai());
            this.viewEvaporadorPanel.getFieldDelta().addItem(evaporador.getDeltaPressao());
        }
        
    }
    
    public void atualizaMassa(){
        if(viewEvaporadorPanel.getFieldMassa().getSelectedItem().equals("")){
            return;
        }
        
        Vector valores = new Vector();
        
        Criteria cr = this.session.createCriteria(ModelMassa.class);
        List result = cr.list();
        
        DefaultComboBoxModel model = (DefaultComboBoxModel)this.viewEvaporadorPanel.getFieldMassa().getModel();
        
        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelMassa)result.get(i)).getMassa(), Double.parseDouble(this.viewEvaporadorPanel.getFieldMassa().getSelectedItem().toString()))){
                return;
            }
        }
        
        Transaction tx = session.beginTransaction();
        
        if(result.size() < 5){
            ModelMassa m1 = new ModelMassa(Double.parseDouble(""+this.viewEvaporadorPanel.getFieldMassa().getEditor().getItem()));
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
            m1.setMassa(Double.parseDouble(this.viewEvaporadorPanel.getFieldMassa().getEditor().getItem().toString()));
            
            this.session.saveOrUpdate(m1);
            valores.add(m1.getMassa()+"");
        }
        
        tx.commit();
        
        JComboBox combo = viewEvaporadorPanel.getFieldMassa();
        atualizaComboBox(valores, combo);
    }
    
    public void atualizaTempEntrada(){ 
        if(viewEvaporadorPanel.getFieldTempEntr().getSelectedItem().equals("")){
            return;
        }
        
        Double value = null;
        try{          
            String text = viewEvaporadorPanel.getFieldTempEntr().getSelectedItem().toString();
            value = Double.parseDouble(text);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }
        
        Criteria cr = this.session.createCriteria(ModelEvaporador.class);
        List results = cr.list();
        ModelEvaporador evaporador = null;

        for(int i=0;i<results.size();i++){
            ModelEvaporador ev = (ModelEvaporador)results.get(i);
            if(Objects.equals(value, ev.getTemperaturaEntr())){
                return;
            }
        }
        
        Transaction tx = this.session.beginTransaction();
        Vector<Double> tempEntr = new Vector<>();
        
        if(results.size() < 5){
            evaporador = new ModelEvaporador();
            session.save(evaporador);
        }
         
        for(int i=results.size()-1; i>0; i--){
            ModelEvaporador lineDown = (ModelEvaporador)results.get(i);
            ModelEvaporador lineUp = (ModelEvaporador)results.get(i-1);
            lineDown.setTemperaturaEntr(lineUp.getTemperaturaEntr());
            session.saveOrUpdate(lineDown);
            tempEntr.add(lineDown.getTemperaturaEntr());
        }
        
        if(!results.isEmpty())
            evaporador = (ModelEvaporador)results.get(0);
        tempEntr.add(value);
        evaporador.setTemperaturaEntr(value);
        session.saveOrUpdate(evaporador);
        
        JComboBox combo = viewEvaporadorPanel.getFieldTempEntr();
        atualizaComboBox(tempEntr, combo);
        
        tx.commit();        
    }
    
    public void atualizaTempSaida(){ 
        if(viewEvaporadorPanel.getFieldTempSai().getSelectedItem().equals("")){
            return;
        }
        
        Double value = null;
        try{
            String text = viewEvaporadorPanel.getFieldTempSai().getSelectedItem().toString();
            value = Double.parseDouble(text);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }
        
        Criteria cr = this.session.createCriteria(ModelEvaporador.class);
        List results = cr.list();
        ModelEvaporador evaporador = null;

        for(int i=0;i<results.size();i++){
            ModelEvaporador ev = (ModelEvaporador)results.get(i);
            if(Objects.equals(value, ev.getTemperaturaSai())){
                return;
            }
        }
        
        Transaction tx = this.session.beginTransaction();
        Vector<Double> tempSai= new Vector<>();
        
        if(results.size() < 5){
            evaporador = new ModelEvaporador();
            session.save(evaporador);
        }
         
        for(int i=results.size()-1; i>0; i--){
            ModelEvaporador lineDown = (ModelEvaporador)results.get(i);
            ModelEvaporador lineUp = (ModelEvaporador)results.get(i-1);
            lineDown.setTemperaturaSai(lineUp.getTemperaturaSai());
            session.saveOrUpdate(lineDown);
            tempSai.add(lineDown.getTemperaturaSai());
        }
        
        if(!results.isEmpty())
            evaporador = (ModelEvaporador)results.get(0);
        tempSai.add(value);
        evaporador.setTemperaturaSai(value);
        session.saveOrUpdate(evaporador);
        
        JComboBox combo = viewEvaporadorPanel.getFieldTempSai();
        atualizaComboBox(tempSai, combo);
        
        tx.commit();        
    }
    
    public void atualizaPressaoEntrada(){ 
        if(viewEvaporadorPanel.getFieldPressaoEntr().getSelectedItem().equals("")){
            return;
        }
        
        Double value = null;
        try{          
            String text =  viewEvaporadorPanel.getFieldPressaoEntr().getSelectedItem().toString();
            value = Double.parseDouble(text);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }

        Criteria cr = this.session.createCriteria(ModelEvaporador.class);
        List results = cr.list();
        ModelEvaporador evaporador = null;

        for(int i=0;i<results.size();i++){
            ModelEvaporador ev = (ModelEvaporador)results.get(i);
            if(Objects.equals(value, ev.getPressaoEntr())){
                return;
            }
        }
        
        Transaction tx = this.session.beginTransaction();
        Vector<Double> pressaoEntr= new Vector<>();
    
        if(results.size() < 5){
            evaporador = new ModelEvaporador();
            session.save(evaporador);
        }
                
        for(int i=results.size()-1; i>0; i--){
            ModelEvaporador lineDown = (ModelEvaporador)results.get(i);
            ModelEvaporador lineUp = (ModelEvaporador)results.get(i-1);
            lineDown.setPressaoEntr(lineUp.getPressaoEntr());
            session.saveOrUpdate(lineDown);
            pressaoEntr.add(lineDown.getPressaoEntr());
        }
        
        if(!results.isEmpty())
            evaporador = (ModelEvaporador)results.get(0);
        pressaoEntr.add(value);
        evaporador.setPressaoEntr(value);
        session.saveOrUpdate(evaporador);
        
        JComboBox combo = viewEvaporadorPanel.getFieldPressaoEntr();
        atualizaComboBox(pressaoEntr, combo);
        
        tx.commit();        
    }
    
    public void atualizaPressaoSaida(){ 
        if(viewEvaporadorPanel.getFieldPressaoSai().getSelectedItem().equals("")){
            return;
        }
        
        Double value = null;
        try{          
            String text = viewEvaporadorPanel.getFieldPressaoSai().getSelectedItem().toString();
            value = Double.parseDouble(text);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }

        Criteria cr = this.session.createCriteria(ModelEvaporador.class);
        List results = cr.list();
        ModelEvaporador evaporador = null;

        for(int i=0;i<results.size();i++){
            ModelEvaporador ev = (ModelEvaporador)results.get(i);
            if(Objects.equals(value, ev.getPressaoSai())){
                return;
            }
        }
        
        Transaction tx = this.session.beginTransaction();
        Vector<Double> pressaoSai= new Vector<>();

        if(results.size() < 5){
            evaporador = new ModelEvaporador();
            session.save(evaporador);
        }
        
        for(int i=results.size()-1; i>0; i--){
            ModelEvaporador lineDown = (ModelEvaporador)results.get(i);
            ModelEvaporador lineUp = (ModelEvaporador)results.get(i-1);
            lineDown.setPressaoSai(lineUp.getPressaoSai());
            session.saveOrUpdate(lineDown);
            pressaoSai.add(lineDown.getPressaoSai());
        }
        
        if(!results.isEmpty())
            evaporador = (ModelEvaporador)results.get(0);
        pressaoSai.add(value);
        evaporador.setPressaoSai(value);
        session.saveOrUpdate(evaporador);
        
        JComboBox combo = viewEvaporadorPanel.getFieldPressaoSai();
        atualizaComboBox(pressaoSai, combo);
        
        tx.commit();        
    }
      
    public void atualizaDeltaPressao(){ 
        if(viewEvaporadorPanel.getFieldDelta().getSelectedItem().equals("")){
            return;
        }
        
        Double value = null;
        try{          
            String text = viewEvaporadorPanel.getFieldDelta().getSelectedItem().toString();
            value = Double.parseDouble(text);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }
        
        Criteria cr = this.session.createCriteria(ModelEvaporador.class);
        List results = cr.list();
        ModelEvaporador evaporador = null;

        for(int i=0;i<results.size();i++){
            ModelEvaporador ev = (ModelEvaporador)results.get(i);
            if(Objects.equals(value, ev.getDeltaPressao())){
                return;
            }
        }
        
        Transaction tx = this.session.beginTransaction();
        Vector<Double> deltaPressao= new Vector<>();

        if(results.size() < 5){
            evaporador = new ModelEvaporador();
            session.save(evaporador);
        }
         
        for(int i=results.size()-1; i>0; i--){
            ModelEvaporador lineDown = (ModelEvaporador)results.get(i);
            ModelEvaporador lineUp = (ModelEvaporador)results.get(i-1);
            lineDown.setDeltaPressao(lineUp.getDeltaPressao());
            session.saveOrUpdate(lineDown);
            deltaPressao.add(lineDown.getDeltaPressao());
        }
        
        if(!results.isEmpty())
            evaporador = (ModelEvaporador)results.get(0);
        deltaPressao.add(value);
        evaporador.setDeltaPressao(value);
        session.saveOrUpdate(evaporador);
        
        JComboBox combo = viewEvaporadorPanel.getFieldDelta();
        atualizaComboBox(deltaPressao, combo);
        
        tx.commit();        
    }*/
    
    public ViewEvaporadorPanelKCS getViewEvaporadorPanel() {
        return viewEvaporadorPanel;
    }

    public void setViewEvaporadorPanel(ViewEvaporadorPanelKCS viewEvaporadorPanel) {
        this.viewEvaporadorPanel = viewEvaporadorPanel;
    }
    
}
