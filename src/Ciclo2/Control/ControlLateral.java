/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo2.Control;

import Ciclo2.View.ViewLateral;
import Control.ControlPrincipal;
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
        
        for(int i=0;i<results.size();i++){
            ModelLateral lateral = (ModelLateral)results.get(i);
            
            viewLateral.getFieldMassa().addItem(lateral.getMassa());
            viewLateral.getFieldTemp().addItem(lateral.getTemperatura());
            viewLateral.getFieldPressao().addItem(lateral.getPressao());
            viewLateral.getFieldTempCond().addItem(lateral.getTempCond());
            viewLateral.getFieldEfetiv().addItem(lateral.getEfetividade());
            viewLateral.getFieldSup().addItem(lateral.getSuperaquecimento());
            viewLateral.getFieldPinch().addItem(lateral.getPinch());
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
        String tipo = viewLateral.getComboTemp().getSelectedItem().toString();
        
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
    
    public void atualizaPressao(){
        try{
            if(viewLateral.getFieldPressao().getSelectedItem().equals(""))
                return;
        }catch(NullPointerException e){
            return;
        }
        double valor = Double.parseDouble(viewLateral.getFieldPressao().getSelectedItem().toString());
        String tipo = viewLateral.getComboPressao().getSelectedItem().toString();
        
        Vector valores = new Vector();
        
        Criteria cr = this.session.createCriteria(ModelLateral.class);
        List result = cr.list();
        ModelLateral lateral = null;
        
        DefaultComboBoxModel model = (DefaultComboBoxModel)this.viewLateral.getFieldPressao().getModel();
        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelLateral)result.get(i)).getPressao(), Double.parseDouble(this.viewLateral.getFieldPressao().getSelectedItem().toString()))){
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
            lineDown.setPressao(lineUp.getPressao());
            session.saveOrUpdate(lineDown);
            valores.add(lineDown.getPressao());
        }
        
        if(!result.isEmpty())
            lateral = (ModelLateral)result.get(0);
        valores.add(valor);
        lateral.setPressao(valor);
        session.saveOrUpdate(lateral);
        
        atualizaComboBox(valores, viewLateral.getFieldPressao());
        tx.commit();
    }

    public void atualizaTemperaturaCond(){
        try{
            if(viewLateral.getFieldTempCond().getSelectedItem().equals(""))
                return;
        }catch(NullPointerException e){
            return;
        }
        double valor = Double.parseDouble(viewLateral.getFieldTempCond().getSelectedItem().toString());
        String tipo = viewLateral.getComboTempCond().getSelectedItem().toString();
        
        Vector valores = new Vector();
        
        Criteria cr = this.session.createCriteria(ModelLateral.class);
        List result = cr.list();
        ModelLateral lateral = null;
        
        DefaultComboBoxModel model = (DefaultComboBoxModel)this.viewLateral.getFieldTempCond().getModel();
        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelLateral)result.get(i)).getTempCond(), Double.parseDouble(this.viewLateral.getFieldTempCond().getSelectedItem().toString()))){
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
            lineDown.setTempCond(lineUp.getTempCond());
            session.saveOrUpdate(lineDown);
            valores.add(lineDown.getTempCond());
        }
        
        if(!result.isEmpty())
            lateral = (ModelLateral)result.get(0);
        valores.add(valor);
        lateral.setTempCond(valor);
        session.saveOrUpdate(lateral);
        
        atualizaComboBox(valores, viewLateral.getFieldTempCond());
        tx.commit();
    }

    public void atualizaEfetividade(){
        try{
            if(viewLateral.getFieldEfetiv().getSelectedItem().equals(""))
                return;
        }catch(NullPointerException e){
            return;
        }
        double valor = Double.parseDouble(viewLateral.getFieldEfetiv().getSelectedItem().toString());
        
        Vector valores = new Vector();
        
        Criteria cr = this.session.createCriteria(ModelLateral.class);
        List result = cr.list();
        ModelLateral lateral = null;
        
        DefaultComboBoxModel model = (DefaultComboBoxModel)this.viewLateral.getFieldEfetiv().getModel();
        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelLateral)result.get(i)).getEfetividade(), Double.parseDouble(this.viewLateral.getFieldEfetiv().getSelectedItem().toString()))){
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
            lineDown.setEfetividade(lineUp.getEfetividade());
            session.saveOrUpdate(lineDown);
            valores.add(lineDown.getEfetividade());
        }
        
        if(!result.isEmpty())
            lateral = (ModelLateral)result.get(0);
        valores.add(valor);
        lateral.setEfetividade(valor);
        session.saveOrUpdate(lateral);
        
        atualizaComboBox(valores, viewLateral.getFieldEfetiv());
        tx.commit();
    }
    
    public void atualizaSuperaquecimento(){
        try{
            if(viewLateral.getFieldSup().getSelectedItem().equals(""))
                return;
        }catch(NullPointerException e){
            return;
        }
        double valor = Double.parseDouble(viewLateral.getFieldSup().getSelectedItem().toString());
        
        Vector valores = new Vector();
        
        Criteria cr = this.session.createCriteria(ModelLateral.class);
        List result = cr.list();
        ModelLateral lateral = null;
        
        DefaultComboBoxModel model = (DefaultComboBoxModel)this.viewLateral.getFieldSup().getModel();
        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelLateral)result.get(i)).getSuperaquecimento(), Double.parseDouble(this.viewLateral.getFieldSup().getSelectedItem().toString()))){
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
            lineDown.setSuperaquecimento(lineUp.getSuperaquecimento());
            session.saveOrUpdate(lineDown);
            valores.add(lineDown.getSuperaquecimento());
        }
        
        if(!result.isEmpty())
            lateral = (ModelLateral)result.get(0);
        valores.add(valor);
        lateral.setSuperaquecimento(valor);
        session.saveOrUpdate(lateral);
        
        atualizaComboBox(valores, viewLateral.getFieldSup());
        tx.commit();
    }
    
    public void atualizaPinch(){
        try{
            if(viewLateral.getFieldPinch().getSelectedItem().equals(""))
                return;
        }catch(NullPointerException e){
            return;
        }
        double valor = Double.parseDouble(viewLateral.getFieldPinch().getSelectedItem().toString());
        
        Vector valores = new Vector();
        
        Criteria cr = this.session.createCriteria(ModelLateral.class);
        List result = cr.list();
        ModelLateral lateral = null;
        
        DefaultComboBoxModel model = (DefaultComboBoxModel)this.viewLateral.getFieldPinch().getModel();
        for(int i = 0; i < result.size()-1; i++){
            if(Objects.equals(((ModelLateral)result.get(i)).getPinch(), Double.parseDouble(this.viewLateral.getFieldPinch().getSelectedItem().toString()))){
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
            lineDown.setPinch(lineUp.getPinch());
            session.saveOrUpdate(lineDown);
            valores.add(lineDown.getPinch());
        }
        
        if(!result.isEmpty())
            lateral = (ModelLateral)result.get(0);
        valores.add(valor);
        lateral.setPinch(valor);
        session.saveOrUpdate(lateral);
        
        atualizaComboBox(valores, viewLateral.getFieldPinch());
        tx.commit();
    }
}
