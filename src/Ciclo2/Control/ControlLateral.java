/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo2.Control;

import Ciclo2.View.ViewLateral;
import Control.ControlPrincipal;
import Control.Conversao.ControlConverte;
import Ciclo2.Model.ModelLateral;
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
public class ControlLateral {
    
    private ViewLateral viewLateral;
    private Session session;

    public ControlLateral(ControlPrincipal ctrlPrincipal,ViewLateral viewLateral) {
        this.session = ctrlPrincipal.getSession();
        this.viewLateral = viewLateral;
    }
    
    public void atualizaComboBox(Vector valor,JComboBox combo){
        Collections.reverse(valor);
        DefaultComboBoxModel model = new DefaultComboBoxModel(valor);
        combo.setModel(model);
    }
    
    public void getValues(){
        Criteria cr = this.session.createCriteria((ModelLateral.class));
        List results = cr.list();
        ControlConverte controlConverte = new ControlConverte();
        
        for(int i=0;i<results.size();i++){
            ModelLateral lateral = (ModelLateral)results.get(i);
            viewLateral.getFieldMassa().addItem(controlConverte.converte(lateral.getTipoMassa(),lateral.getMassa()));
            viewLateral.getFieldMassa().addItem(controlConverte.converte(lateral.getTipoTemp(),lateral.getMassa()));
            viewLateral.getFieldMassa().addItem(controlConverte.converte(lateral.getTipoPressao(),lateral.getMassa()));
            viewLateral.getFieldMassa().addItem(controlConverte.converte(lateral.getTipoTempCond(),lateral.getMassa()));
            viewLateral.getFieldMassa().addItem(lateral.getEfetividade());
            viewLateral.getFieldMassa().addItem(lateral.getSuperaquecimento());
            viewLateral.getFieldMassa().addItem(lateral.getPinch());
        }
    }
 
    public void atualizaMassa(){
        try{
            if(viewLateral.getFieldMassa().getSelectedItem().equals(""))
                return;
        }catch(NullPointerException e){
            return;
        }
        double valor = Double.parseDouble(viewLateral.getFieldMassa().getSelectedItem().toString());
        
        Vector valores = new Vector();
        
        Criteria cr = this.session.createCriteria(ModelLateral.class);
        List result = cr.list();
        ModelLateral lateral = null;
        
        DefaultComboBoxModel model = (DefaultComboBoxModel)this.viewLateral.getFieldMassa().getModel();
        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelLateral)result.get(i)).getMassa(), Double.parseDouble(this.viewLateral.getFieldMassa().getSelectedItem().toString()))){
                return;
            }
        }
    
        Transaction tx = session.beginTransaction();
        
        if(result.size() < 5){
            lateral = new ModelLateral();
            session.save(lateral);
        }
        
        for(int i=result.size()-1; i>0;i--){
            ModelLateral lineDown = (ModelLateral)result.get(i);
            ModelLateral lineUp = (ModelLateral)result.get(i-1);
            lineDown.setMassa(lineUp.getMassa());
            session.saveOrUpdate(lineDown);
            valores.add(lineDown.getMassa());
        }
        
        if(!result.isEmpty())
            lateral = (ModelLateral)result.get(0);
        valores.add(valor);
        lateral.setMassa(valor);
        session.saveOrUpdate(lateral);
        
        atualizaComboBox(valores, viewLateral.getFieldMassa());
        tx.commit();
    }
    
    public void atualizaTemperatura(){
        try{
            if(viewLateral.getFieldTemp().getSelectedItem().equals(""))
                return;
        }catch(NullPointerException e){
            return;
        }
        double valor = Double.parseDouble(viewLateral.getFieldTemp().getSelectedItem().toString());
        
        Vector valores = new Vector();
        
        Criteria cr = this.session.createCriteria(ModelLateral.class);
        List result = cr.list();
        ModelLateral lateral = null;
        
        DefaultComboBoxModel model = (DefaultComboBoxModel)this.viewLateral.getFieldTemp().getModel();
        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelLateral)result.get(i)).getTemperatura(), Double.parseDouble(this.viewLateral.getFieldTemp().getSelectedItem().toString()))){
                return;
            }
        }
    
        Transaction tx = session.beginTransaction();
        
        if(result.size() < 5){
            lateral = new ModelLateral();
            session.save(lateral);
        }
        
        for(int i=result.size()-1; i>0;i--){
            ModelLateral lineDown = (ModelLateral)result.get(i);
            ModelLateral lineUp = (ModelLateral)result.get(i-1);
            lineDown.setTemperatura(lineUp.getTemperatura());
            session.saveOrUpdate(lineDown);
            valores.add(lineDown.getTemperatura());
        }
        
        if(!result.isEmpty())
            lateral = (ModelLateral)result.get(0);
        valores.add(valor);
        lateral.setTemperatura(valor);
        session.saveOrUpdate(lateral);
        
        atualizaComboBox(valores, viewLateral.getFieldTemp());
        tx.commit();
    }
}
