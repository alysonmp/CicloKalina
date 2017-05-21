/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControlRegenerador;

import Control.ControlPrincipal;
import Model.Ciclo2.ModelRegenerador;
import View.Regenerador.ViewRegeneradorPanelRankine;
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
public class ControlRegeneradorPanelRankine {
    private ViewRegeneradorPanelRankine viewRegeneradorPanel;
    
    private Session session;
    
    private ArrayList<Double> tempEntr = new ArrayList<>();
    private ArrayList<Double> tempSai= new ArrayList<>();
    private ArrayList<Double> pressaoEntr= new ArrayList<>();
    private ArrayList<Double> pressaoSai= new ArrayList<>();
    private ArrayList<Double> deltaPressao= new ArrayList<>();
    private ArrayList<Double> efetividade= new ArrayList<>();

    public ControlRegeneradorPanelRankine(ControlPrincipal ctrlPrincipal) {
        this.session = ctrlPrincipal.getSession();
        viewRegeneradorPanel = new ViewRegeneradorPanelRankine(this);
    }
    
        //Salva no banco de dados os 5 valores mais recentes da temperatura de entrada
    public void saveTempEntr(){ 
        
        Double value = null;
        try{          
            value = (Double) viewRegeneradorPanel.getFieldTempEntr().getSelectedItem();
        }catch(ClassCastException e){
            String text = (String)viewRegeneradorPanel.getFieldTempEntr().getSelectedItem();
            try{
                value = Double.valueOf(text);
            }catch(NumberFormatException e2){
                JOptionPane.showMessageDialog(null, e2);
                return;
            }
        }
        
        if(value == null)
            return;
        
        Criteria cr = this.session.createCriteria(ModelRegenerador.class);
        List results = cr.list();
        ModelRegenerador regenerador = null;

        if(!results.isEmpty()){
            regenerador = (ModelRegenerador)results.get(0);
            if(value == regenerador.getTemperaturaEntr()){
                return;
            }
        }
        
        Transaction tx = this.session.beginTransaction();
        
        if(results.size() < 5){
            regenerador = new ModelRegenerador();
            session.save(regenerador);
        }
                
        for(int i=results.size()-1; i>0; i--){
            ModelRegenerador lineDown = (ModelRegenerador)results.get(i);
            ModelRegenerador lineUp = (ModelRegenerador)results.get(i-1);
            lineDown.setTemperaturaEntr(lineUp.getTemperaturaEntr());
            session.saveOrUpdate(lineDown);
        }
        
        if(!results.isEmpty())
            regenerador = (ModelRegenerador)results.get(0);
        regenerador.setTemperaturaEntr(value);
        session.saveOrUpdate(regenerador);

        tx.commit();
    }
    
    //Retorna as 5 temperaturas mais recentes
    public ArrayList<Double> getValuesTempEntr(){
        Criteria cr = this.session.createCriteria(ModelRegenerador.class);
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
            value = (Double) viewRegeneradorPanel.getFieldTempSai().getSelectedItem();
        }catch(ClassCastException e){
            String text = (String)viewRegeneradorPanel.getFieldTempSai().getSelectedItem();
            try{
                value = Double.valueOf(text);
            }catch(NumberFormatException e2){
                JOptionPane.showMessageDialog(null, e2);
                return;
            }
        }
        
        if(value == null)
            return;
       
        Criteria cr = this.session.createCriteria(ModelRegenerador.class);
        List results = cr.list();
        ModelRegenerador regenerador = null;

        if(!results.isEmpty()){
            regenerador = (ModelRegenerador)results.get(0);
            if(value == regenerador.getTemperaturaSai()){
                return;
            }
        }
        
        Transaction tx = this.session.beginTransaction();

        if(results.size() < 5){
            regenerador = new ModelRegenerador();
            session.save(regenerador);
        }
                
        for(int i=results.size()-1; i>0; i--){
            ModelRegenerador lineDown = (ModelRegenerador)results.get(i);
            ModelRegenerador lineUp = (ModelRegenerador)results.get(i-1);
            lineDown.setTemperaturaSai(lineUp.getTemperaturaSai());
            session.saveOrUpdate(lineDown);
        }
        
        if(!results.isEmpty())
            regenerador = (ModelRegenerador)results.get(0);
        regenerador.setTemperaturaSai(value);
        session.saveOrUpdate(regenerador);

        tx.commit();
    }
    
    public ArrayList<Double> getValuesTempSai(){
        Criteria cr = this.session.createCriteria(ModelRegenerador.class);
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
            value = (Double) viewRegeneradorPanel.getFieldPressaoEntr().getSelectedItem();
        }catch(ClassCastException e){
            String text = (String)viewRegeneradorPanel.getFieldPressaoEntr().getSelectedItem();
            try{
                value = Double.valueOf(text);
            }catch(NumberFormatException e2){
                JOptionPane.showMessageDialog(null, e2);
                return;
            }
        }
        
        if(value == null)
            return;
        Criteria cr = this.session.createCriteria(ModelRegenerador.class);
        List results = cr.list();
        ModelRegenerador regenerador = null;

        if(!results.isEmpty()){
            regenerador = (ModelRegenerador)results.get(0);
            if(value == regenerador.getPressaoEntr()){
                return;
            }
        }
        
        Transaction tx = this.session.beginTransaction();

        if(results.size() < 5){
            regenerador = new ModelRegenerador();
            session.save(regenerador);
        }
                
        for(int i=results.size()-1; i>0; i--){
            ModelRegenerador lineDown = (ModelRegenerador)results.get(i);
            ModelRegenerador lineUp = (ModelRegenerador)results.get(i-1);
            lineDown.setPressaoEntr(lineUp.getPressaoEntr());
            session.saveOrUpdate(lineDown);
        }
        
        if(!results.isEmpty())
            regenerador = (ModelRegenerador)results.get(0);
        regenerador.setPressaoEntr(value);
        session.saveOrUpdate(regenerador);

        tx.commit();
    }
    
    public ArrayList<Double> getValuesPressaoEntr(){
        Criteria cr = this.session.createCriteria(ModelRegenerador.class);
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
            value = (Double) viewRegeneradorPanel.getFieldPressaoSai().getSelectedItem();
        }catch(ClassCastException e){
            String text = (String)viewRegeneradorPanel.getFieldPressaoSai().getSelectedItem();
            try{
                value = Double.valueOf(text);
            }catch(NumberFormatException e2){
                JOptionPane.showMessageDialog(null, e2);
                return;
            }
        }
        
        if(value == null)
            return;
        
        Criteria cr = this.session.createCriteria(ModelRegenerador.class);
        List results = cr.list();
        ModelRegenerador regenerador = null;

        if(!results.isEmpty()){
            regenerador = (ModelRegenerador)results.get(0);
            if(value == regenerador.getPressaoSai()){
                return;
            }
        }
        
        Transaction tx = this.session.beginTransaction();

        if(results.size() < 5){
            regenerador = new ModelRegenerador();
            session.save(regenerador);
        }
                
        for(int i=results.size()-1; i>0; i--){
            ModelRegenerador lineDown = (ModelRegenerador)results.get(i);
            ModelRegenerador lineUp = (ModelRegenerador)results.get(i-1);
            lineDown.setPressaoSai(lineUp.getPressaoSai());
            session.saveOrUpdate(lineDown);
        }
        
        if(!results.isEmpty())
            regenerador = (ModelRegenerador)results.get(0);
        regenerador.setPressaoSai(value);
        session.saveOrUpdate(regenerador);

        tx.commit();
    }
    
    public ArrayList<Double> getValuesPressaoSai(){
        Criteria cr = this.session.createCriteria(ModelRegenerador.class);
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
            value = (Double) viewRegeneradorPanel.getFieldDelta().getSelectedItem();
        }catch(ClassCastException e){
            String text = (String)viewRegeneradorPanel.getFieldDelta().getSelectedItem();
            try{
                value = Double.valueOf(text);
            }catch(NumberFormatException e2){
                JOptionPane.showMessageDialog(null, e2);
                return;
            }
        }
        
        if(value == null)
            return;
        
        Criteria cr = this.session.createCriteria(ModelRegenerador.class);
        List results = cr.list();
        ModelRegenerador regenerador = null;

        if(!results.isEmpty()){
            regenerador = (ModelRegenerador)results.get(0);
            if(value == regenerador.getDelaPressao()){
                return;
            }
        }
        
        Transaction tx = this.session.beginTransaction();

        if(results.size() < 5){
            regenerador = new ModelRegenerador();
            session.save(regenerador);
        }
                
        for(int i=results.size()-1; i>0; i--){
            ModelRegenerador lineDown = (ModelRegenerador)results.get(i);
            ModelRegenerador lineUp = (ModelRegenerador)results.get(i-1);
            lineDown.setDelaPressao(lineUp.getDelaPressao());
            session.saveOrUpdate(lineDown);
        }
        
        if(!results.isEmpty())
            regenerador = (ModelRegenerador)results.get(0);
        regenerador.setDelaPressao(value);
        session.saveOrUpdate(regenerador);

        tx.commit();
    }
    
    public ArrayList<Double> getValuesDeltaPressao(){
        Criteria cr = this.session.createCriteria(ModelRegenerador.class);
        cr.setProjection(Projections.property("delaPressao"));
        List results = cr.list();
        
        deltaPressao = new ArrayList<>();
        for(int i=0;i<results.size();i++){
            Double valor = (Double)results.get(i);
            deltaPressao.add(valor);;
        }
        
        return deltaPressao;
    }
    
    public void saveEfetv(){ 
        
        Double value = null;
        try{          
            value = (Double) viewRegeneradorPanel.getFieldEfetiv().getSelectedItem();
        }catch(ClassCastException e){
            String text = (String)viewRegeneradorPanel.getFieldEfetiv().getSelectedItem();
            try{
                value = Double.valueOf(text);
            }catch(NumberFormatException e2){
                JOptionPane.showMessageDialog(null, e2);
                return;
            }
        }
        
        if(value == null)
            return;
        
        Criteria cr = this.session.createCriteria(ModelRegenerador.class);
        List results = cr.list();
        ModelRegenerador regenerador = null;

        if(!results.isEmpty()){
            regenerador = (ModelRegenerador)results.get(0);
            if(value == regenerador.getEfetividade()){
                return;
            }
        }
        
        Transaction tx = this.session.beginTransaction();

        if(results.size() < 5){
            regenerador = new ModelRegenerador();
            session.save(regenerador);
        }
                
        for(int i=results.size()-1; i>0; i--){
            ModelRegenerador lineDown = (ModelRegenerador)results.get(i);
            ModelRegenerador lineUp = (ModelRegenerador)results.get(i-1);
            lineDown.setEfetividade(lineUp.getEfetividade());
            session.saveOrUpdate(lineDown);
        }
        
        if(!results.isEmpty())
            regenerador = (ModelRegenerador)results.get(0);
        regenerador.setEfetividade(value);
        session.saveOrUpdate(regenerador);

        tx.commit();
    }
    
    public ArrayList<Double> getValuesEfetv(){
        Criteria cr = this.session.createCriteria(ModelRegenerador.class);
        cr.setProjection(Projections.property("efetividade"));
        List results = cr.list();
        
        efetividade = new ArrayList<>();
        for(int i=0;i<results.size();i++){
            Double valor = (Double)results.get(i);
            efetividade.add(valor);;
        }
        
        return efetividade;
    }

    public ViewRegeneradorPanelRankine getViewRegeneradorPanel() {
        return viewRegeneradorPanel;
    }

    public void setViewRegeneradorPanel(ViewRegeneradorPanelRankine viewRegeneradorPanel) {
        this.viewRegeneradorPanel = viewRegeneradorPanel;
    }
    
    
}
