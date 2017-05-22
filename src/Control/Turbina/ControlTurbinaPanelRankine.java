/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Turbina;

import Control.ControlPrincipal;
import Model.Ciclo2.ModelMassa;
import Model.Ciclo2.ModelTurbina;
import View.Evaporador.ViewEvaporadorPanelRankine;
import View.Turbina.ViewTurbinaPanelRankine;
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
public class ControlTurbinaPanelRankine {
    private ViewTurbinaPanelRankine viewTurbinaPanel;
    
    private Session session;

    public ControlTurbinaPanelRankine(ControlPrincipal ctrlPrincipal) {
        this.session = ctrlPrincipal.getSession();
        viewTurbinaPanel = new ViewTurbinaPanelRankine(ctrlPrincipal,this);
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
            this.viewTurbinaPanel.getFieldMassa().addItem(m.getMassa());
        }
        
        cr = this.session.createCriteria(ModelTurbina.class);
        results = cr.list();
        
        for(int i=0;i<results.size();i++){
            ModelTurbina turbina = (ModelTurbina)results.get(i);
            this.viewTurbinaPanel.getFieldTempEntr().addItem(turbina.getTemperaturaEntr());
            this.viewTurbinaPanel.getFieldTempSai().addItem(turbina.getTemperaturaSai());
            this.viewTurbinaPanel.getFieldPressaoEntr().addItem(turbina.getPressaoEntr());
            this.viewTurbinaPanel.getFieldPressaoSai().addItem(turbina.getPressaoSai());
            this.viewTurbinaPanel.getFieldEfici().addItem(turbina.getEficiencia());
        }
    }
    
    public void atualizaMassa(){
        Vector valores = new Vector();
        
        Criteria cr = this.session.createCriteria(ModelMassa.class);
        List result = cr.list();
        
        DefaultComboBoxModel model = (DefaultComboBoxModel)this.viewTurbinaPanel.getFieldMassa().getModel();
        
        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelMassa)result.get(i)).getMassa(), Double.parseDouble(this.viewTurbinaPanel.getFieldMassa().getSelectedItem().toString()))){
                return;
            }
        }
        
        Transaction tx = session.beginTransaction();
        
        if(result.size() < 5){
            ModelMassa m1 = new ModelMassa(Double.parseDouble(""+this.viewTurbinaPanel.getFieldMassa().getEditor().getItem()));
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
            m1.setMassa(Double.parseDouble(this.viewTurbinaPanel.getFieldMassa().getEditor().getItem().toString()));
            
            this.session.saveOrUpdate(m1);
            valores.add(m1.getMassa()+"");
        }
        
        tx.commit();
        
        JComboBox combo = viewTurbinaPanel.getFieldMassa();
        atualizaComboBox(valores, combo);
    }
    
    //Salva no banco de dados os 5 valores mais recentes da temperatura de entrada
    public void saveTempEntr(){ 
        Double value = null;
        try{          
            value = (Double) viewTurbinaPanel.getFieldTempEntr().getSelectedItem();
        }catch(ClassCastException e){
            String text = (String)viewTurbinaPanel.getFieldTempEntr().getSelectedItem();
            try{
                value = Double.valueOf(text);
            }catch(NumberFormatException e2){
                JOptionPane.showMessageDialog(null, e2);
                return;
            }
        }
        
        if(value == null)
            return;
        
        
        
        Criteria cr = this.session.createCriteria(ModelTurbina.class);
        List results = cr.list();
        ModelTurbina turbina = null;

        if(!results.isEmpty()){
            for(int i=0;i<results.size();i++){
                ModelTurbina ev = (ModelTurbina)results.get(i);
                if(Objects.equals(value, ev.getTemperaturaEntr())){
                    return;
                }
            }
        }
        
        Transaction tx = this.session.beginTransaction();
        Vector<Double> tempEntr = new Vector<>();
        
        if(results.size() < 5){
            turbina = new ModelTurbina();
            session.save(turbina);
        }
         
        for(int i=results.size()-1; i>0; i--){
            ModelTurbina lineDown = (ModelTurbina)results.get(i);
            ModelTurbina lineUp = (ModelTurbina)results.get(i-1);
            lineDown.setTemperaturaEntr(lineUp.getTemperaturaEntr());
            session.saveOrUpdate(lineDown);
            tempEntr.add(lineDown.getTemperaturaEntr());
        }
        
        if(!results.isEmpty())
            turbina = (ModelTurbina)results.get(0);
        tempEntr.add(value);
        turbina.setTemperaturaEntr(value);
        session.saveOrUpdate(turbina);
        
        JComboBox combo = viewTurbinaPanel.getFieldTempEntr();
        atualizaComboBox(tempEntr, combo);
        
        tx.commit();        
    }
    
    public void saveTempSai(){ 
        Double value = null;
        try{          
            value = (Double) viewTurbinaPanel.getFieldTempSai().getSelectedItem();
        }catch(ClassCastException e){
            String text = (String)viewTurbinaPanel.getFieldTempSai().getSelectedItem();
            try{
                value = Double.valueOf(text);
            }catch(NumberFormatException e2){
                JOptionPane.showMessageDialog(null, e2);
                return;
            }
        }
        
        if(value == null)
            return;
        
        Criteria cr = this.session.createCriteria(ModelTurbina.class);
        List results = cr.list();
        ModelTurbina turbina = null;

        if(!results.isEmpty()){
            for(int i=0;i<results.size();i++){
                ModelTurbina ev = (ModelTurbina)results.get(i);
                if(Objects.equals(value, ev.getTemperaturaSai())){
                    return;
                }
            }
        }
        
        Transaction tx = this.session.beginTransaction();
        Vector<Double> tempSai= new Vector<>();
        
        if(results.size() < 5){
            turbina = new ModelTurbina();
            session.save(turbina);
        }
         
        for(int i=results.size()-1; i>0; i--){
            ModelTurbina lineDown = (ModelTurbina)results.get(i);
            ModelTurbina lineUp = (ModelTurbina)results.get(i-1);
            lineDown.setTemperaturaSai(lineUp.getTemperaturaSai());
            session.saveOrUpdate(lineDown);
            tempSai.add(lineDown.getTemperaturaSai());
        }
        
        if(!results.isEmpty())
            turbina = (ModelTurbina)results.get(0);
        tempSai.add(value);
        turbina.setTemperaturaSai(value);
        session.saveOrUpdate(turbina);
        
        JComboBox combo = viewTurbinaPanel.getFieldTempSai();
        atualizaComboBox(tempSai, combo);
        
        tx.commit();        
    }
    
    public void savePressaoEntr(){ 
        Double value = null;
        try{          
            value = (Double) viewTurbinaPanel.getFieldPressaoEntr().getSelectedItem();
        }catch(ClassCastException e){
            String text = (String)viewTurbinaPanel.getFieldPressaoEntr().getSelectedItem();
            try{
                value = Double.valueOf(text);
            }catch(NumberFormatException e2){
                JOptionPane.showMessageDialog(null, e2);
                return;
            }
        }
        
        if(value == null)
            return;
        
        
        Criteria cr = this.session.createCriteria(ModelTurbina.class);
        List results = cr.list();
        ModelTurbina turbina = null;

        if(!results.isEmpty()){
            for(int i=0;i<results.size();i++){
                ModelTurbina ev = (ModelTurbina)results.get(i);
                if(Objects.equals(value, ev.getPressaoEntr())){
                    return;
                }
            }
        }
        
        Transaction tx = this.session.beginTransaction();
        Vector<Double> pressaoEntr= new Vector<>();
    
        if(results.size() < 5){
            turbina = new ModelTurbina();
            session.save(turbina);
        }
                
        for(int i=results.size()-1; i>0; i--){
            ModelTurbina lineDown = (ModelTurbina)results.get(i);
            ModelTurbina lineUp = (ModelTurbina)results.get(i-1);
            lineDown.setPressaoEntr(lineUp.getPressaoEntr());
            session.saveOrUpdate(lineDown);
            pressaoEntr.add(lineDown.getPressaoEntr());
        }
        
        if(!results.isEmpty())
            turbina = (ModelTurbina)results.get(0);
        pressaoEntr.add(value);
        turbina.setPressaoEntr(value);
        session.saveOrUpdate(turbina);
        
        JComboBox combo = viewTurbinaPanel.getFieldPressaoEntr();
        atualizaComboBox(pressaoEntr, combo);
        
        tx.commit();        
    }
    
    public void savePressaoSai(){ 
        
        Double value = null;
        try{          
            value = (Double) viewTurbinaPanel.getFieldPressaoSai().getSelectedItem();
        }catch(ClassCastException e){
            String text = (String)viewTurbinaPanel.getFieldPressaoSai().getSelectedItem();
            try{
                value = Double.valueOf(text);
            }catch(NumberFormatException e2){
                JOptionPane.showMessageDialog(null, e2);
                return;
            }
        }
        
        if(value == null)
            return;
        
        Criteria cr = this.session.createCriteria(ModelTurbina.class);
        List results = cr.list();
        ModelTurbina turbina = null;

        if(!results.isEmpty()){
            for(int i=0;i<results.size();i++){
                ModelTurbina ev = (ModelTurbina)results.get(i);
                if(Objects.equals(value, ev.getPressaoSai())){
                    return;
                }
            }
        }
        
        Transaction tx = this.session.beginTransaction();
        Vector<Double> pressaoSai= new Vector<>();

        if(results.size() < 5){
            turbina = new ModelTurbina();
            session.save(turbina);
        }
        
        for(int i=results.size()-1; i>0; i--){
            ModelTurbina lineDown = (ModelTurbina)results.get(i);
            ModelTurbina lineUp = (ModelTurbina)results.get(i-1);
            lineDown.setPressaoSai(lineUp.getPressaoSai());
            session.saveOrUpdate(lineDown);
            pressaoSai.add(lineDown.getPressaoSai());
        }
        
        if(!results.isEmpty())
            turbina = (ModelTurbina)results.get(0);
        pressaoSai.add(value);
        turbina.setPressaoSai(value);
        session.saveOrUpdate(turbina);
        
        JComboBox combo = viewTurbinaPanel.getFieldPressaoSai();
        atualizaComboBox(pressaoSai, combo);
        
        tx.commit();        
    }
    
    public void saveEfici(){    
        Double value = null;
        try{          
            value = (Double) viewTurbinaPanel.getFieldEfici().getSelectedItem();
        }catch(ClassCastException e){
            String text = (String) viewTurbinaPanel.getFieldEfici().getSelectedItem();
            try{
                value = Double.valueOf(text);
            }catch(NumberFormatException e2){
                JOptionPane.showMessageDialog(null, e2);
                return;
            }
        }
        
        if(value == null)
            return;
        
        Criteria cr = this.session.createCriteria(ModelTurbina.class);
        List results = cr.list();
        ModelTurbina regenerador = null;

        if(!results.isEmpty()){
            for(int i=0;i<results.size();i++){
                ModelTurbina ev = (ModelTurbina)results.get(i);
                if(Objects.equals(value, ev.getEficiencia())){
                    return;
                }
            }
        }
        
        Transaction tx = this.session.beginTransaction();
        Vector<Double> efetv = new Vector<>();

        if(results.size() < 5){
            regenerador = new ModelTurbina();
            session.save(regenerador);
        }
         
        for(int i=results.size()-1; i>0; i--){
            ModelTurbina lineDown = (ModelTurbina)results.get(i);
            ModelTurbina lineUp = (ModelTurbina)results.get(i-1);
            lineDown.setEficiencia(lineUp.getEficiencia());
            session.saveOrUpdate(lineDown);
            efetv.add(lineDown.getEficiencia());
        }
        
        if(!results.isEmpty())
            regenerador = (ModelTurbina)results.get(0);
        efetv.add(value);
        regenerador.setEficiencia(value);
        session.saveOrUpdate(regenerador);
        
        JComboBox combo = viewTurbinaPanel.getFieldEfici();
        atualizaComboBox(efetv, combo);
        
        tx.commit();
    }
    public ViewTurbinaPanelRankine getViewTurbinaPanel() {
        return viewTurbinaPanel;
    }

    public void setViewTurbinaPanel(ViewTurbinaPanelRankine viewTurbinaPanel) {
        this.viewTurbinaPanel = viewTurbinaPanel;
    }
    
    
    
}
