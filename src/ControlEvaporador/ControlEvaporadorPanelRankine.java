/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControlEvaporador;

import Control.ControlPrincipal;
import Model.Ciclo2.ModelEvaporador;
import View.Evaporador.ViewEvaporadorPanelRankine;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;

/**
 *
 * @author leonardo
 */
public class ControlEvaporadorPanelRankine {

    private ViewEvaporadorPanelRankine viewEvaporadorPanel;
    
    private Session session;
    
    private ArrayList<Double> tempEntr = new ArrayList<>();
    private ArrayList<Double> tempSai= new ArrayList<>();
    private ArrayList<Double> pressaoEntr= new ArrayList<>();
    private ArrayList<Double> pressaoSai= new ArrayList<>();
    private ArrayList<Double> deltaPressao= new ArrayList<>();
    
    public ControlEvaporadorPanelRankine(ControlPrincipal ctrlPrincipal) {
        this.session = ctrlPrincipal.getSession();
        viewEvaporadorPanel = new ViewEvaporadorPanelRankine(this);
    }

    
    //Salva no banco de dados os 5 valores mais recentes da temperatura de entrada
    public void saveTempEntr(){ 
        
        Double value = null;
        try{          
            value = (Double) viewEvaporadorPanel.getFieldTempEntr().getSelectedItem();
        }catch(ClassCastException e){
            String text = (String)viewEvaporadorPanel.getFieldTempEntr().getSelectedItem();
            try{
                value = Double.valueOf(text);
            }catch(NumberFormatException e2){
                JOptionPane.showMessageDialog(null, e2);
                return;
            }
        }
        
        if(value == null)
            return;
        
        Transaction tx = this.session.beginTransaction();
        Criteria cr = this.session.createCriteria(ModelEvaporador.class);
        List results = cr.list();
        
        ModelEvaporador evaporador = null;
        if(results.size() < 5){
            evaporador = new ModelEvaporador();
            session.save(evaporador);
        }
                
        for(int i=results.size()-1; i>0; i--){
            ModelEvaporador lineDown = (ModelEvaporador)results.get(i);
            ModelEvaporador lineUp = (ModelEvaporador)results.get(i-1);
            lineDown.setTemperaturaEntr(lineUp.getTemperaturaEntr());
            session.saveOrUpdate(lineDown);
        }
        
        if(!results.isEmpty())
            evaporador = (ModelEvaporador)results.get(0);
        evaporador.setTemperaturaEntr(value);
        session.saveOrUpdate(evaporador);

        tx.commit();
    }
    
    //Retorna as 5 temperaturas mais recentes
    public ArrayList<Double> getValuesTempEntr(){
        Criteria cr = this.session.createCriteria(ModelEvaporador.class);
        cr.setProjection(Projections.property("temperaturaEntr"));
        List results = cr.list();
        
        tempEntr = new ArrayList<>();
        for(int i=0;i<results.size();i++){
            Double valor = (Double)results.get(i);
            tempEntr.add(valor);;
        }
        
        return tempEntr;
    }
    
    public void saveTempSai(){ 
        
        Double value = null;
        try{          
            value = (Double) viewEvaporadorPanel.getFieldTempSai().getSelectedItem();
        }catch(ClassCastException e){
            String text = (String)viewEvaporadorPanel.getFieldTempSai().getSelectedItem();
            try{
                value = Double.valueOf(text);
            }catch(NumberFormatException e2){
                JOptionPane.showMessageDialog(null, e2);
                return;
            }
        }
        
        if(value == null)
            return;
        
        Transaction tx = this.session.beginTransaction();
        Criteria cr = this.session.createCriteria(ModelEvaporador.class);
        List results = cr.list();
        
        ModelEvaporador evaporador = null;
        if(results.size() < 5){
            evaporador = new ModelEvaporador();
            session.save(evaporador);
        }
                
        for(int i=results.size()-1; i>0; i--){
            ModelEvaporador lineDown = (ModelEvaporador)results.get(i);
            ModelEvaporador lineUp = (ModelEvaporador)results.get(i-1);
            lineDown.setTemperaturaSai(lineUp.getTemperaturaSai());
            session.saveOrUpdate(lineDown);
        }
        
        if(!results.isEmpty())
            evaporador = (ModelEvaporador)results.get(0);
        evaporador.setTemperaturaSai(value);
        session.saveOrUpdate(evaporador);

        tx.commit();
    }
    
    public ArrayList<Double> getValuesTempSai(){
        Criteria cr = this.session.createCriteria(ModelEvaporador.class);
        cr.setProjection(Projections.property("temperaturaSai"));
        List results = cr.list();
        
        tempSai = new ArrayList<>();
        for(int i=0;i<results.size();i++){
            Double valor = (Double)results.get(i);
            tempSai.add(valor);;
        }
        
        return tempSai;
    }
    
    public void savePressaoEntr(){ 
        
        Double value = null;
        try{          
            value = (Double) viewEvaporadorPanel.getFieldPressaoEntr().getSelectedItem();
        }catch(ClassCastException e){
            String text = (String)viewEvaporadorPanel.getFieldPressaoEntr().getSelectedItem();
            try{
                value = Double.valueOf(text);
            }catch(NumberFormatException e2){
                JOptionPane.showMessageDialog(null, e2);
                return;
            }
        }
        
        if(value == null)
            return;
        
        Transaction tx = this.session.beginTransaction();
        Criteria cr = this.session.createCriteria(ModelEvaporador.class);
        List results = cr.list();
        
        ModelEvaporador evaporador = null;
        if(results.size() < 5){
            evaporador = new ModelEvaporador();
            session.save(evaporador);
        }
                
        for(int i=results.size()-1; i>0; i--){
            ModelEvaporador lineDown = (ModelEvaporador)results.get(i);
            ModelEvaporador lineUp = (ModelEvaporador)results.get(i-1);
            lineDown.setPressaoEntr(lineUp.getPressaoEntr());
            session.saveOrUpdate(lineDown);
        }
        
        if(!results.isEmpty())
            evaporador = (ModelEvaporador)results.get(0);
        evaporador.setPressaoEntr(value);
        session.saveOrUpdate(evaporador);

        tx.commit();
    }
    
    public ArrayList<Double> getValuesPressaoEntr(){
        Criteria cr = this.session.createCriteria(ModelEvaporador.class);
        cr.setProjection(Projections.property("pressaoEntr"));
        List results = cr.list();
        
        pressaoEntr = new ArrayList<>();
        for(int i=0;i<results.size();i++){
            Double valor = (Double)results.get(i);
            pressaoEntr.add(valor);;
        }
        
        return pressaoEntr;
    }
    
    public void savePressaoSai(){ 
        
        Double value = null;
        try{          
            value = (Double) viewEvaporadorPanel.getFieldPressaoSai().getSelectedItem();
        }catch(ClassCastException e){
            String text = (String)viewEvaporadorPanel.getFieldPressaoSai().getSelectedItem();
            try{
                value = Double.valueOf(text);
            }catch(NumberFormatException e2){
                JOptionPane.showMessageDialog(null, e2);
                return;
            }
        }
        
        if(value == null)
            return;
        
        Transaction tx = this.session.beginTransaction();
        Criteria cr = this.session.createCriteria(ModelEvaporador.class);
        List results = cr.list();
        
        ModelEvaporador evaporador = null;
        if(results.size() < 5){
            evaporador = new ModelEvaporador();
            session.save(evaporador);
        }
                
        for(int i=results.size()-1; i>0; i--){
            ModelEvaporador lineDown = (ModelEvaporador)results.get(i);
            ModelEvaporador lineUp = (ModelEvaporador)results.get(i-1);
            lineDown.setPressaoSai(lineUp.getPressaoSai());
            session.saveOrUpdate(lineDown);
        }
        
        if(!results.isEmpty())
            evaporador = (ModelEvaporador)results.get(0);
        evaporador.setPressaoSai(value);
        session.saveOrUpdate(evaporador);

        tx.commit();
    }
    
    public ArrayList<Double> getValuesPressaoSai(){
        Criteria cr = this.session.createCriteria(ModelEvaporador.class);
        cr.setProjection(Projections.property("pressaoSai"));
        List results = cr.list();
        
        pressaoSai = new ArrayList<>();
        for(int i=0;i<results.size();i++){
            Double valor = (Double)results.get(i);
            pressaoSai.add(valor);;
        }
        
        return pressaoSai;
    }
    
    public void saveDeltaPressao(){ 
        
        Double value = null;
        try{          
            value = (Double) viewEvaporadorPanel.getFieldDelta().getSelectedItem();
        }catch(ClassCastException e){
            String text = (String)viewEvaporadorPanel.getFieldDelta().getSelectedItem();
            try{
                value = Double.valueOf(text);
            }catch(NumberFormatException e2){
                JOptionPane.showMessageDialog(null, e2);
                return;
            }
        }
        
        if(value == null)
            return;
        
        Transaction tx = this.session.beginTransaction();
        Criteria cr = this.session.createCriteria(ModelEvaporador.class);
        List results = cr.list();
        
        ModelEvaporador evaporador = null;
        if(results.size() < 5){
            evaporador = new ModelEvaporador();
            session.save(evaporador);
        }
                
        for(int i=results.size()-1; i>0; i--){
            ModelEvaporador lineDown = (ModelEvaporador)results.get(i);
            ModelEvaporador lineUp = (ModelEvaporador)results.get(i-1);
            lineDown.setDeltaPressao(lineUp.getDeltaPressao());
            session.saveOrUpdate(lineDown);
        }
        
        if(!results.isEmpty())
            evaporador = (ModelEvaporador)results.get(0);
        evaporador.setDeltaPressao(value);
        session.saveOrUpdate(evaporador);

        tx.commit();
    }
    
    public ArrayList<Double> getValuesDeltaPressao(){
        Criteria cr = this.session.createCriteria(ModelEvaporador.class);
        cr.setProjection(Projections.property("deltaPressao"));
        List results = cr.list();
        
        deltaPressao = new ArrayList<>();
        for(int i=0;i<results.size();i++){
            Double valor = (Double)results.get(i);
            deltaPressao.add(valor);;
        }
        
        return deltaPressao;
    }
    
    public ViewEvaporadorPanelRankine getViewEvaporadorPanel() {
        return viewEvaporadorPanel;
    }

    public void setViewEvaporadorPanel(ViewEvaporadorPanelRankine viewEvaporadorPanel) {
        this.viewEvaporadorPanel = viewEvaporadorPanel;
    }
    
}
