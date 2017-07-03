/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Evaporador;

import Control.ControlPrincipal;
import Model.Ciclo2.ModelEvaporador;
import Model.Ciclo2.ModelMassa;
import View.Evaporador.ViewEvaporadorPanelRankine;
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
public class ControlEvaporadorPanelRankine {

    private ViewEvaporadorPanelRankine viewEvaporadorPanel;
    private ControlPrincipal ctrlPrincipal;
    private Session session;
        
    public ControlEvaporadorPanelRankine(ControlPrincipal ctrlPrincipal) {
        this.ctrlPrincipal = ctrlPrincipal;
        this.session = ctrlPrincipal.getSession();
        viewEvaporadorPanel = new ViewEvaporadorPanelRankine(this);
    }
    
    public void atualizaComboBox(Vector valor,JComboBox combo){
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
            value = Double.parseDouble(viewEvaporadorPanel.getFieldTempEntr().getSelectedItem().toString());
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }
        
        Criteria cr = this.session.createCriteria(ModelEvaporador.class);
        List results = cr.list();
        ModelEvaporador evaporador = null;

        for(int i=0;i<results.size();i++){
            if(Objects.equals(value, ((ModelEvaporador)results.get(i)).getTemperaturaEntr())){
                return;
            }
        }
        
        Transaction tx = this.session.beginTransaction();
        Vector<Double> valores = new Vector<>();
        
        if(results.size() < 5){
            evaporador = new ModelEvaporador();
            session.save(evaporador);
        }
         
        for(int i=results.size()-1; i>0; i--){
            ((ModelEvaporador)results.get(i)).setTemperaturaEntr(((ModelEvaporador)results.get(i-1)).getTemperaturaEntr());
            session.saveOrUpdate((ModelEvaporador)results.get(i));
            valores.add(((ModelEvaporador)results.get(i)).getTemperaturaEntr());
        }
        
        if(!results.isEmpty())
            evaporador = (ModelEvaporador)results.get(0);
        valores.add(value);
        evaporador.setTemperaturaEntr(value);
        session.saveOrUpdate(evaporador);
        
        atualizaComboBox(valores, viewEvaporadorPanel.getFieldTempEntr());
        
        tx.commit();        
    }
    
    public void atualizaTempSaida(){ 
        if(viewEvaporadorPanel.getFieldTempSai().getSelectedItem().equals("")){
            return;
        }
        
        Double value = null;
        try{
            value = Double.parseDouble(viewEvaporadorPanel.getFieldTempSai().getSelectedItem().toString());
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }
        
        Criteria cr = this.session.createCriteria(ModelEvaporador.class);
        List results = cr.list();
        ModelEvaporador evaporador = null;

        for(int i=0;i<results.size();i++){
            if(Objects.equals(value, ((ModelEvaporador)results.get(i)).getTemperaturaSai())){
                return;
            }
        }
        
        Transaction tx = this.session.beginTransaction();
        Vector<Double> valores= new Vector<>();
        
        if(results.size() < 5){
            evaporador = new ModelEvaporador();
            session.save(evaporador);
        }
         
        for(int i=results.size()-1; i>0; i--){
            ((ModelEvaporador)results.get(i)).setTemperaturaSai(((ModelEvaporador)results.get(i-1)).getTemperaturaSai());
            session.saveOrUpdate((ModelEvaporador)results.get(i));
            valores.add(((ModelEvaporador)results.get(i)).getTemperaturaSai());
        }
        
        if(!results.isEmpty())
            evaporador = (ModelEvaporador)results.get(0);
        valores.add(value);
        evaporador.setTemperaturaSai(value);
        session.saveOrUpdate(evaporador);
        
        JComboBox combo = viewEvaporadorPanel.getFieldTempSai();
        atualizaComboBox(valores, combo);
        
        tx.commit();        
    }
    
    public void atualizaPressaoEntrada(){ 
        if(viewEvaporadorPanel.getFieldPressaoEntr().getSelectedItem().equals("")){
            return;
        }
        
        Double value = null;
        try{          
            value = Double.parseDouble(viewEvaporadorPanel.getFieldPressaoEntr().getSelectedItem().toString());
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }

        Criteria cr = this.session.createCriteria(ModelEvaporador.class);
        List results = cr.list();
        ModelEvaporador evaporador = null;

        for(int i=0;i<results.size();i++){
            if(Objects.equals(value, ((ModelEvaporador)results.get(i)).getPressaoEntr())){
                return;
            }
        }
        
        Transaction tx = this.session.beginTransaction();
        Vector<Double> valores= new Vector<>();
    
        if(results.size() < 5){
            evaporador = new ModelEvaporador();
            session.save(evaporador);
        }
                
        for(int i=results.size()-1; i>0; i--){
            ((ModelEvaporador)results.get(i)).setPressaoEntr(((ModelEvaporador)results.get(i-1)).getPressaoEntr());
            session.saveOrUpdate((ModelEvaporador)results.get(i));
            valores.add(((ModelEvaporador)results.get(i)).getPressaoEntr());
        }
        
        if(!results.isEmpty())
            evaporador = (ModelEvaporador)results.get(0);
        valores.add(value);
        evaporador.setPressaoEntr(value);
        session.saveOrUpdate(evaporador);
        
        JComboBox combo = viewEvaporadorPanel.getFieldPressaoEntr();
        atualizaComboBox(valores, combo);
        
        tx.commit();        
    }
    
    public void atualizaPressaoSaida(){ 
        if(viewEvaporadorPanel.getFieldPressaoSai().getSelectedItem().equals("")){
            return;
        }
        
        Double value = null;
        try{          
            value = Double.parseDouble(viewEvaporadorPanel.getFieldPressaoSai().getSelectedItem().toString());
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }

        Criteria cr = this.session.createCriteria(ModelEvaporador.class);
        List results = cr.list();
        ModelEvaporador evaporador = null;

        for(int i=0;i<results.size();i++){
            if(Objects.equals(value, ((ModelEvaporador)results.get(i)).getPressaoSai())){
                return;
            }
        }
        
        Transaction tx = this.session.beginTransaction();
        Vector<Double> valores= new Vector<>();

        if(results.size() < 5){
            evaporador = new ModelEvaporador();
            session.save(evaporador);
        }
        
        for(int i=results.size()-1; i>0; i--){
            ((ModelEvaporador)results.get(i)).setPressaoSai(((ModelEvaporador)results.get(i-1)).getPressaoSai());
            session.saveOrUpdate((ModelEvaporador)results.get(i));
            valores.add(((ModelEvaporador)results.get(i)).getPressaoSai());
        }
        
        if(!results.isEmpty())
            evaporador = (ModelEvaporador)results.get(0);
        valores.add(value);
        evaporador.setPressaoSai(value);
        session.saveOrUpdate(evaporador);
        
        JComboBox combo = viewEvaporadorPanel.getFieldPressaoSai();
        atualizaComboBox(valores, combo);
        
        tx.commit();        
    }
      
    public void atualizaDeltaPressao(){ 
        if(viewEvaporadorPanel.getFieldDelta().getSelectedItem().equals("")){
            return;
        }
        
        Double value = null;
        try{          
            value = Double.parseDouble(viewEvaporadorPanel.getFieldDelta().getSelectedItem().toString());
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }
        
        Criteria cr = this.session.createCriteria(ModelEvaporador.class);
        List results = cr.list();
        ModelEvaporador evaporador = null;

        for(int i=0;i<results.size();i++){
            if(Objects.equals(value, ((ModelEvaporador)results.get(i)).getDeltaPressao())){
                return;
            }
        }
        
        Transaction tx = this.session.beginTransaction();
        Vector<Double> valores= new Vector<>();

        if(results.size() < 5){
            evaporador = new ModelEvaporador();
            session.save(evaporador);
        }
         
        for(int i=results.size()-1; i>0; i--){
            ((ModelEvaporador)results.get(i)).setDeltaPressao(((ModelEvaporador)results.get(i-1)).getDeltaPressao());
            session.saveOrUpdate((ModelEvaporador)results.get(i));
            valores.add(((ModelEvaporador)results.get(i)).getDeltaPressao());
        }
        
        if(!results.isEmpty())
            evaporador = (ModelEvaporador)results.get(0);
        valores.add(value);
        evaporador.setDeltaPressao(value);
        session.saveOrUpdate(evaporador);
        
        JComboBox combo = viewEvaporadorPanel.getFieldDelta();
        atualizaComboBox(valores, combo);
        
        tx.commit();        
    }
    
    public ViewEvaporadorPanelRankine getViewEvaporadorPanel() {
        return viewEvaporadorPanel;
    }

    public void setViewEvaporadorPanel(ViewEvaporadorPanelRankine viewEvaporadorPanel) {
        this.viewEvaporadorPanel = viewEvaporadorPanel;
    }

    public ControlPrincipal getCtrlPrincipal() {
        return ctrlPrincipal;
    }
    
    
}
