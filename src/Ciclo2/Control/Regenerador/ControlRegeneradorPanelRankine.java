/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo2.Control.Regenerador;

import Ciclo2.Control.ControlPrincipal;
import Ciclo2.Model.Ciclo2.ModelMassa;
import Ciclo2.Model.Ciclo2.ModelRegenerador;
import Ciclo2.View.Regenerador.ViewRegeneradorPanelRankine;
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
public class ControlRegeneradorPanelRankine {
    private ViewRegeneradorPanelRankine viewRegeneradorPanel;
    
    private Session session;

    ControlPrincipal ctrlPrincipal;
    
    public ControlRegeneradorPanelRankine(ControlPrincipal ctrlPrincipal) {
        this.session = ctrlPrincipal.getSession();
        viewRegeneradorPanel = new ViewRegeneradorPanelRankine(this);
        this.ctrlPrincipal = ctrlPrincipal;
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
            this.viewRegeneradorPanel.getFieldMassa().addItem(m.getMassa());
        }
        
        cr = this.session.createCriteria(ModelRegenerador.class);
        results = cr.list();
        
        for(int i=0;i<results.size();i++){
            ModelRegenerador regenerador = (ModelRegenerador)results.get(i);
            this.viewRegeneradorPanel.getFieldTempEntr().addItem(regenerador.getTemperaturaEntr());
            this.viewRegeneradorPanel.getFieldTempSai().addItem(regenerador.getTemperaturaSai());
            this.viewRegeneradorPanel.getFieldPressaoEntr().addItem(regenerador.getPressaoEntr());
            this.viewRegeneradorPanel.getFieldPressaoSai().addItem(regenerador.getPressaoSai());
            this.viewRegeneradorPanel.getFieldDelta().addItem(regenerador.getDelaPressao());
            this.viewRegeneradorPanel.getFieldEfetiv().addItem(regenerador.getEfetividade());
        }
    
    }
    
    public void atualizaMassa(){
        if(viewRegeneradorPanel.getFieldMassa().getSelectedItem().equals("")){
            return;
        }
        
        Vector valores = new Vector();
        
        Criteria cr = this.session.createCriteria(ModelMassa.class);
        List result = cr.list();
        
        DefaultComboBoxModel model = (DefaultComboBoxModel)this.viewRegeneradorPanel.getFieldMassa().getModel();
        
        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelMassa)result.get(i)).getMassa(), Double.parseDouble(this.viewRegeneradorPanel.getFieldMassa().getSelectedItem().toString()))){
                return;
            }
        }
        
        Transaction tx = session.beginTransaction();
        
        if(result.size() < 5){
            ModelMassa m1 = new ModelMassa(Double.parseDouble(""+this.viewRegeneradorPanel.getFieldMassa().getEditor().getItem()));
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
            m1.setMassa(Double.parseDouble(this.viewRegeneradorPanel.getFieldMassa().getEditor().getItem().toString()));
            
            this.session.saveOrUpdate(m1);
            valores.add(m1.getMassa()+"");
        }
        
        tx.commit();
        
        JComboBox combo = viewRegeneradorPanel.getFieldMassa();
        atualizaComboBox(valores, combo);
    }
    
        //Salva no banco de dados os 5 valores mais recentes da temperatura de entrada
    public void atualizaTempEntrada(){ 
        if(viewRegeneradorPanel.getFieldTempEntr().getSelectedItem().equals("")){
            return;
        }
        
        Double value = null;
        try{          
            String text = viewRegeneradorPanel.getFieldTempEntr().getSelectedItem().toString();
            value = Double.parseDouble(text);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }
        
        Criteria cr = this.session.createCriteria(ModelRegenerador.class);
        List results = cr.list();
        ModelRegenerador regenerador = null;

        for(int i=0;i<results.size();i++){
            ModelRegenerador ev = (ModelRegenerador)results.get(i);
            if(Objects.equals(value, ev.getTemperaturaEntr())){
                return;
            }
        }
        
        Transaction tx = this.session.beginTransaction();
        Vector<Double> tempEntr = new Vector<>();
        
        if(results.size() < 5){
            regenerador = new ModelRegenerador();
            session.save(regenerador);
        }
         
        for(int i=results.size()-1; i>0; i--){
            ModelRegenerador lineDown = (ModelRegenerador)results.get(i);
            ModelRegenerador lineUp = (ModelRegenerador)results.get(i-1);
            lineDown.setTemperaturaEntr(lineUp.getTemperaturaEntr());
            session.saveOrUpdate(lineDown);
            tempEntr.add(lineDown.getTemperaturaEntr());
        }
        
        if(!results.isEmpty())
            regenerador = (ModelRegenerador)results.get(0);
        tempEntr.add(value);
        regenerador.setTemperaturaEntr(value);
        session.saveOrUpdate(regenerador);
        
        JComboBox combo = viewRegeneradorPanel.getFieldTempEntr();
        atualizaComboBox(tempEntr, combo);
        
        tx.commit();        
    }
    
    public void atualizaTempSaida(){
        if(viewRegeneradorPanel.getFieldTempSai().getSelectedItem().equals("")){
            return;
        }
        
        Double value = null;
        try{          
            String text = viewRegeneradorPanel.getFieldTempSai().getSelectedItem().toString();
            value = Double.parseDouble(text);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }
        
        Criteria cr = this.session.createCriteria(ModelRegenerador.class);
        List results = cr.list();
        ModelRegenerador regenerador = null;

        for(int i=0;i<results.size();i++){
            ModelRegenerador ev = (ModelRegenerador)results.get(i);
            if(Objects.equals(value, ev.getTemperaturaSai())){
                return;
            }
        }
        
        Transaction tx = this.session.beginTransaction();
        Vector<Double> tempSai= new Vector<>();
        
        if(results.size() < 5){
            regenerador = new ModelRegenerador();
            session.save(regenerador);
        }
         
        for(int i=results.size()-1; i>0; i--){
            ModelRegenerador lineDown = (ModelRegenerador)results.get(i);
            ModelRegenerador lineUp = (ModelRegenerador)results.get(i-1);
            lineDown.setTemperaturaSai(lineUp.getTemperaturaSai());
            session.saveOrUpdate(lineDown);
            tempSai.add(lineDown.getTemperaturaSai());
        }
        
        if(!results.isEmpty())
            regenerador = (ModelRegenerador)results.get(0);
        tempSai.add(value);
        regenerador.setTemperaturaSai(value);
        session.saveOrUpdate(regenerador);
        
        JComboBox combo = viewRegeneradorPanel.getFieldTempSai();
        atualizaComboBox(tempSai, combo);
        
        tx.commit();        
    }
    
    public void atualizaPressaoEntrada(){ 
        if(viewRegeneradorPanel.getFieldPressaoEntr().getSelectedItem().equals("")){
            return;
        }
        
        Double value = null;
        try{          
            String text = viewRegeneradorPanel.getFieldPressaoEntr().getSelectedItem().toString();
            value = Double.parseDouble(text);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }
        
        Criteria cr = this.session.createCriteria(ModelRegenerador.class);
        List results = cr.list();
        ModelRegenerador regenerador = null;

        for(int i=0;i<results.size();i++){
            ModelRegenerador ev = (ModelRegenerador)results.get(i);
            if(Objects.equals(value, ev.getPressaoEntr())){
                return;
            }
        }
        
        Transaction tx = this.session.beginTransaction();
        Vector<Double> pressaoEntr= new Vector<>();
    
        if(results.size() < 5){
            regenerador = new ModelRegenerador();
            session.save(regenerador);
        }
                
        for(int i=results.size()-1; i>0; i--){
            ModelRegenerador lineDown = (ModelRegenerador)results.get(i);
            ModelRegenerador lineUp = (ModelRegenerador)results.get(i-1);
            lineDown.setPressaoEntr(lineUp.getPressaoEntr());
            session.saveOrUpdate(lineDown);
            pressaoEntr.add(lineDown.getPressaoEntr());
        }
        
        if(!results.isEmpty())
            regenerador = (ModelRegenerador)results.get(0);
        pressaoEntr.add(value);
        regenerador.setPressaoEntr(value);
        session.saveOrUpdate(regenerador);
        
        JComboBox combo = viewRegeneradorPanel.getFieldPressaoEntr();
        atualizaComboBox(pressaoEntr, combo);
        
        tx.commit();        
    }
    
    public void atualizaPressaoSaida(){  
        if(viewRegeneradorPanel.getFieldPressaoSai().getSelectedItem().equals("")){
            return;
        }
        
        Double value = null;
        try{          
            String text = viewRegeneradorPanel.getFieldPressaoSai().getSelectedItem().toString();
            value = Double.parseDouble(text);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }

        Criteria cr = this.session.createCriteria(ModelRegenerador.class);
        List results = cr.list();
        ModelRegenerador regenerador = null;

        for(int i=0;i<results.size();i++){
            ModelRegenerador ev = (ModelRegenerador)results.get(i);
            if(Objects.equals(value, ev.getPressaoSai())){
                return;
            }
        }
        
        Transaction tx = this.session.beginTransaction();
        Vector<Double> pressaoSai= new Vector<>();

        if(results.size() < 5){
            regenerador = new ModelRegenerador();
            session.save(regenerador);
        }
        
        for(int i=results.size()-1; i>0; i--){
            ModelRegenerador lineDown = (ModelRegenerador)results.get(i);
            ModelRegenerador lineUp = (ModelRegenerador)results.get(i-1);
            lineDown.setPressaoSai(lineUp.getPressaoSai());
            session.saveOrUpdate(lineDown);
            pressaoSai.add(lineDown.getPressaoSai());
        }
        
        if(!results.isEmpty())
            regenerador = (ModelRegenerador)results.get(0);
        pressaoSai.add(value);
        regenerador.setPressaoSai(value);
        session.saveOrUpdate(regenerador);
        
        JComboBox combo = viewRegeneradorPanel.getFieldPressaoSai();
        atualizaComboBox(pressaoSai, combo);
        
        tx.commit();        
    }
      
    public void atualizaDeltaPressao(){ 
        if(viewRegeneradorPanel.getFieldDelta().getSelectedItem().equals("")){
            return;
        }
        
        Double value = null;
        try{          
            String text = viewRegeneradorPanel.getFieldDelta().getSelectedItem().toString();
            value = Double.parseDouble(text);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }
        
        Criteria cr = this.session.createCriteria(ModelRegenerador.class);
        List results = cr.list();
        ModelRegenerador regenerador = null;

        for(int i=0;i<results.size();i++){
            ModelRegenerador ev = (ModelRegenerador)results.get(i);
            if(Objects.equals(value, ev.getDelaPressao())){
                return;
            }
        }
        
        Transaction tx = this.session.beginTransaction();
        Vector<Double> deltaPressao= new Vector<>();

        if(results.size() < 5){
            regenerador = new ModelRegenerador();
            session.save(regenerador);
        }
         
        for(int i=results.size()-1; i>0; i--){
            ModelRegenerador lineDown = (ModelRegenerador)results.get(i);
            ModelRegenerador lineUp = (ModelRegenerador)results.get(i-1);
            lineDown.setDelaPressao(lineUp.getDelaPressao());
            session.saveOrUpdate(lineDown);
            deltaPressao.add(lineDown.getDelaPressao());
        }
        
        if(!results.isEmpty())
            regenerador = (ModelRegenerador)results.get(0);
        deltaPressao.add(value);
        regenerador.setDelaPressao(value);
        session.saveOrUpdate(regenerador);
        
        JComboBox combo = viewRegeneradorPanel.getFieldDelta();
        atualizaComboBox(deltaPressao, combo);
        
        tx.commit();        
    }
    
    public void atualizaEfetividade(){ 
        if(viewRegeneradorPanel.getFieldEfetiv().getSelectedItem().equals("")){
            return;
        }
        
        Double value = null;
        try{          
            String text = viewRegeneradorPanel.getFieldEfetiv().getSelectedItem().toString();
            value = Double.parseDouble(text);
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }
        
        Criteria cr = this.session.createCriteria(ModelRegenerador.class);
        List results = cr.list();
        ModelRegenerador regenerador = null;

        for(int i=0;i<results.size();i++){
            ModelRegenerador ev = (ModelRegenerador)results.get(i);
            if(Objects.equals(value, ev.getEfetividade())){
                return;
            }
        }
        
        Transaction tx = this.session.beginTransaction();
        Vector<Double> efetv = new Vector<>();

        if(results.size() < 5){
            regenerador = new ModelRegenerador();
            session.save(regenerador);
        }
         
        for(int i=results.size()-1; i>0; i--){
            ModelRegenerador lineDown = (ModelRegenerador)results.get(i);
            ModelRegenerador lineUp = (ModelRegenerador)results.get(i-1);
            lineDown.setEfetividade(lineUp.getEfetividade());
            session.saveOrUpdate(lineDown);
            efetv.add(lineDown.getEfetividade());
        }
        
        if(!results.isEmpty())
            regenerador = (ModelRegenerador)results.get(0);
        efetv.add(value);
        regenerador.setEfetividade(value);
        session.saveOrUpdate(regenerador);
        
        JComboBox combo = viewRegeneradorPanel.getFieldEfetiv();
        atualizaComboBox(efetv, combo);
        
        tx.commit();
    }

    public ViewRegeneradorPanelRankine getViewRegeneradorPanel() {
        return viewRegeneradorPanel;
    }

    public void setViewRegeneradorPanel(ViewRegeneradorPanelRankine viewRegeneradorPanel) {
        this.viewRegeneradorPanel = viewRegeneradorPanel;
    }

    public ControlPrincipal getCtrlPrincipal() {
        return ctrlPrincipal;
    }

    public void setCtrlPrincipal(ControlPrincipal ctrlPrincipal) {
        this.ctrlPrincipal = ctrlPrincipal;
    }
    
    
}
