/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Regenerador;

import Control.ControlPrincipal;
import Model.Ciclo2.ModelMassa;
import Model.Ciclo2.ModelRegenerador;
import View.Regenerador.ViewRegeneradorPanelRankine;
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
    private ControlPrincipal ctrlPrincipal;
    
    public ControlRegeneradorPanelRankine(ControlPrincipal ctrlPrincipal) {
        this.ctrlPrincipal = ctrlPrincipal;
        this.session = ctrlPrincipal.getSession();
        viewRegeneradorPanel = new ViewRegeneradorPanelRankine(this);
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
    
    public void atualizaTempEntrada(){ 
        if(viewRegeneradorPanel.getFieldTempEntr().getSelectedItem().equals("")){
            return;
        }
        
        Double value = null;
        try{          
            value = Double.parseDouble(viewRegeneradorPanel.getFieldTempEntr().getSelectedItem().toString());
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }
        
        Criteria cr = this.session.createCriteria(ModelRegenerador.class);
        List results = cr.list();
        ModelRegenerador regenerador = null;

        for(int i=0;i<results.size();i++){
            if(Objects.equals(value, ((ModelRegenerador)results.get(i)).getTemperaturaEntr())){
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
        
        atualizaComboBox(tempEntr, viewRegeneradorPanel.getFieldTempEntr());
        
        tx.commit();        
    }
    
    public void atualizaTempSaida(){
        if(viewRegeneradorPanel.getFieldTempSai().getSelectedItem().equals("")){
            return;
        }
        
        Double value = null;
        try{          
            value = Double.parseDouble(viewRegeneradorPanel.getFieldTempSai().getSelectedItem().toString());
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }
        
        Criteria cr = this.session.createCriteria(ModelRegenerador.class);
        List results = cr.list();
        ModelRegenerador regenerador = null;

        for(int i=0;i<results.size();i++){
            if(Objects.equals(value, ((ModelRegenerador)results.get(i)).getTemperaturaSai())){
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
        
        atualizaComboBox(tempSai, viewRegeneradorPanel.getFieldTempSai());
        
        tx.commit();        
    }
    
    public void atualizaPressaoEntrada(){ 
        if(viewRegeneradorPanel.getFieldPressaoEntr().getSelectedItem().equals("")){
            return;
        }
        
        Double value = null;
        try{          
            value = Double.parseDouble(viewRegeneradorPanel.getFieldPressaoEntr().getSelectedItem().toString());
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }
        
        Criteria cr = this.session.createCriteria(ModelRegenerador.class);
        List results = cr.list();
        ModelRegenerador regenerador = null;

        for(int i=0;i<results.size();i++){
            if(Objects.equals(value, ((ModelRegenerador)results.get(i)).getPressaoEntr())){
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
        
        atualizaComboBox(pressaoEntr, viewRegeneradorPanel.getFieldPressaoEntr());
        
        tx.commit();        
    }
    
    public void atualizaPressaoSaida(){  
        if(viewRegeneradorPanel.getFieldPressaoSai().getSelectedItem().equals("")){
            return;
        }
        
        Double value = null;
        try{          
            value = Double.parseDouble(viewRegeneradorPanel.getFieldPressaoSai().getSelectedItem().toString());
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }

        Criteria cr = this.session.createCriteria(ModelRegenerador.class);
        List results = cr.list();
        ModelRegenerador regenerador = null;

        for(int i=0;i<results.size();i++){
            if(Objects.equals(value, ((ModelRegenerador)results.get(i)).getPressaoSai())){
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
        
        atualizaComboBox(pressaoSai, viewRegeneradorPanel.getFieldPressaoSai());
        
        tx.commit();        
    }
      
    public void atualizaDeltaPressao(){ 
        if(viewRegeneradorPanel.getFieldDelta().getSelectedItem().equals("")){
            return;
        }
        
        Double value = null;
        try{          
            value = Double.parseDouble(viewRegeneradorPanel.getFieldDelta().getSelectedItem().toString());
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }
        
        Criteria cr = this.session.createCriteria(ModelRegenerador.class);
        List results = cr.list();
        ModelRegenerador regenerador = null;

        for(int i=0;i<results.size();i++){
            if(Objects.equals(value, ((ModelRegenerador)results.get(i)).getDelaPressao())){
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
        
        atualizaComboBox(deltaPressao, viewRegeneradorPanel.getFieldDelta());
        
        tx.commit();        
    }
    
    public void atualizaEfetividade(){ 
        if(viewRegeneradorPanel.getFieldEfetiv().getSelectedItem().equals("")){
            return;
        }
        
        Double value = null;
        try{          
            value = Double.parseDouble(viewRegeneradorPanel.getFieldEfetiv().getSelectedItem().toString());
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }
        
        Criteria cr = this.session.createCriteria(ModelRegenerador.class);
        List results = cr.list();
        ModelRegenerador regenerador = null;

        for(int i=0;i<results.size();i++){
            if(Objects.equals(value, ((ModelRegenerador)results.get(i)).getEfetividade())){
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
        
        atualizaComboBox(efetv, viewRegeneradorPanel.getFieldEfetiv());
        
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
