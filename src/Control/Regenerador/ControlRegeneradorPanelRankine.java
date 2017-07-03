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
            this.viewRegeneradorPanel.getFieldTempEntr2().addItem(regenerador.getTemperaturaEntr2());
            this.viewRegeneradorPanel.getFieldTempSai2().addItem(regenerador.getTemperaturaSai2());
            this.viewRegeneradorPanel.getFieldPressaoEntr().addItem(regenerador.getPressaoEntr());
            this.viewRegeneradorPanel.getFieldPressaoSai().addItem(regenerador.getPressaoSai());
            this.viewRegeneradorPanel.getFieldPressaoEntr2().addItem(regenerador.getPressaoEntr2());
            this.viewRegeneradorPanel.getFieldPressaoSai2().addItem(regenerador.getPressaoSai2());
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
        Vector<Double> valores = new Vector<>();
        
        if(results.size() < 5){
            regenerador = new ModelRegenerador();
            session.save(regenerador);
        }
         
        for(int i=results.size()-1; i>0; i--){
            ((ModelRegenerador)results.get(i)).setTemperaturaEntr(((ModelRegenerador)results.get(i-1)).getTemperaturaEntr());
            session.saveOrUpdate((ModelRegenerador)results.get(i));
            valores.add(((ModelRegenerador)results.get(i)).getTemperaturaEntr());
        }
        
        if(!results.isEmpty())
            regenerador = (ModelRegenerador)results.get(0);
        valores.add(value);
        regenerador.setTemperaturaEntr(value);
        session.saveOrUpdate(regenerador);
        
        atualizaComboBox(valores, viewRegeneradorPanel.getFieldTempEntr());
        
        tx.commit();        
    }
    
    public void atualizaTempEntrada2(){ 
        if(viewRegeneradorPanel.getFieldTempEntr2().getSelectedItem().equals("")){
            return;
        }
        
        Double value = null;
        try{          
            value = Double.parseDouble(viewRegeneradorPanel.getFieldTempEntr2().getSelectedItem().toString());
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }
        
        Criteria cr = this.session.createCriteria(ModelRegenerador.class);
        List results = cr.list();
        ModelRegenerador regenerador = null;

        for(int i=0;i<results.size();i++){
            if(Objects.equals(value, ((ModelRegenerador)results.get(i)).getTemperaturaEntr2())){
                return;
            }
        }
        
        Transaction tx = this.session.beginTransaction();
        Vector<Double> valores = new Vector<>();
        
        if(results.size() < 5){
            regenerador = new ModelRegenerador();
            session.save(regenerador);
        }
         
        for(int i=results.size()-1; i>0; i--){
            ((ModelRegenerador)results.get(i)).setTemperaturaEntr2(((ModelRegenerador)results.get(i-1)).getTemperaturaEntr2());
            session.saveOrUpdate((ModelRegenerador)results.get(i));
            valores.add(((ModelRegenerador)results.get(i)).getTemperaturaEntr2());
        }
        
        if(!results.isEmpty())
            regenerador = (ModelRegenerador)results.get(0);
        valores.add(value);
        regenerador.setTemperaturaEntr2(value);
        session.saveOrUpdate(regenerador);
        
        atualizaComboBox(valores, viewRegeneradorPanel.getFieldTempEntr2());
        
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
        Vector<Double> valores= new Vector<>();
        
        if(results.size() < 5){
            regenerador = new ModelRegenerador();
            session.save(regenerador);
        }
         
        for(int i=results.size()-1; i>0; i--){
            ((ModelRegenerador)results.get(i)).setTemperaturaSai(((ModelRegenerador)results.get(i-1)).getTemperaturaSai());
            session.saveOrUpdate((ModelRegenerador)results.get(i));
            valores.add(((ModelRegenerador)results.get(i)).getTemperaturaSai());
        }
        
        if(!results.isEmpty())
            regenerador = (ModelRegenerador)results.get(0);
        valores.add(value);
        regenerador.setTemperaturaSai(value);
        session.saveOrUpdate(regenerador);
        
        atualizaComboBox(valores, viewRegeneradorPanel.getFieldTempSai());
        
        tx.commit();        
    }
    
    public void atualizaTempSaida2(){
        if(viewRegeneradorPanel.getFieldTempSai2().getSelectedItem().equals("")){
            return;
        }
        
        Double value = null;
        try{          
            value = Double.parseDouble(viewRegeneradorPanel.getFieldTempSai2().getSelectedItem().toString());
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }
        
        Criteria cr = this.session.createCriteria(ModelRegenerador.class);
        List results = cr.list();
        ModelRegenerador regenerador = null;

        for(int i=0;i<results.size();i++){
            if(Objects.equals(value, ((ModelRegenerador)results.get(i)).getTemperaturaSai2())){
                return;
            }
        }
        
        Transaction tx = this.session.beginTransaction();
        Vector<Double> valores= new Vector<>();
        
        if(results.size() < 5){
            regenerador = new ModelRegenerador();
            session.save(regenerador);
        }
         
        for(int i=results.size()-1; i>0; i--){
            ((ModelRegenerador)results.get(i)).setTemperaturaSai2(((ModelRegenerador)results.get(i-1)).getTemperaturaSai2());
            session.saveOrUpdate((ModelRegenerador)results.get(i));
            valores.add(((ModelRegenerador)results.get(i)).getTemperaturaSai2());
        }
        
        if(!results.isEmpty())
            regenerador = (ModelRegenerador)results.get(0);
        valores.add(value);
        regenerador.setTemperaturaSai2(value);
        session.saveOrUpdate(regenerador);
        
        atualizaComboBox(valores, viewRegeneradorPanel.getFieldTempSai2());
        
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
        Vector<Double> valores= new Vector<>();
    
        if(results.size() < 5){
            regenerador = new ModelRegenerador();
            session.save(regenerador);
        }
                
        for(int i=results.size()-1; i>0; i--){
            ((ModelRegenerador)results.get(i)).setPressaoEntr(((ModelRegenerador)results.get(i-1)).getPressaoEntr());
            session.saveOrUpdate((ModelRegenerador)results.get(i));
            valores.add(((ModelRegenerador)results.get(i)).getPressaoEntr());
        }
        
        if(!results.isEmpty())
            regenerador = (ModelRegenerador)results.get(0);
        valores.add(value);
        regenerador.setPressaoEntr(value);
        session.saveOrUpdate(regenerador);
        
        atualizaComboBox(valores, viewRegeneradorPanel.getFieldPressaoEntr());
        
        tx.commit();        
    }
    
    public void atualizaPressaoEntrada2(){ 
        if(viewRegeneradorPanel.getFieldPressaoEntr2().getSelectedItem().equals("")){
            return;
        }
        
        Double value = null;
        try{          
            value = Double.parseDouble(viewRegeneradorPanel.getFieldPressaoEntr2().getSelectedItem().toString());
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }
        
        Criteria cr = this.session.createCriteria(ModelRegenerador.class);
        List results = cr.list();
        ModelRegenerador regenerador = null;

        for(int i=0;i<results.size();i++){
            if(Objects.equals(value, ((ModelRegenerador)results.get(i)).getPressaoEntr2())){
                return;
            }
        }
        
        Transaction tx = this.session.beginTransaction();
        Vector<Double> valores= new Vector<>();
    
        if(results.size() < 5){
            regenerador = new ModelRegenerador();
            session.save(regenerador);
        }
                
        for(int i=results.size()-1; i>0; i--){
            ((ModelRegenerador)results.get(i)).setPressaoEntr2(((ModelRegenerador)results.get(i-1)).getPressaoEntr2());
            session.saveOrUpdate((ModelRegenerador)results.get(i));
            valores.add(((ModelRegenerador)results.get(i)).getPressaoEntr2());
        }
        
        if(!results.isEmpty())
            regenerador = (ModelRegenerador)results.get(0);
        valores.add(value);
        regenerador.setPressaoEntr2(value);
        session.saveOrUpdate(regenerador);
        
        atualizaComboBox(valores, viewRegeneradorPanel.getFieldPressaoEntr2());
        
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
        Vector<Double> valores= new Vector<>();

        if(results.size() < 5){
            regenerador = new ModelRegenerador();
            session.save(regenerador);
        }
        
        for(int i=results.size()-1; i>0; i--){
            ((ModelRegenerador)results.get(i)).setPressaoSai(((ModelRegenerador)results.get(i-1)).getPressaoSai());
            session.saveOrUpdate((ModelRegenerador)results.get(i));
            valores.add(((ModelRegenerador)results.get(i)).getPressaoSai());
        }
        
        if(!results.isEmpty())
            regenerador = (ModelRegenerador)results.get(0);
        valores.add(value);
        regenerador.setPressaoSai(value);
        session.saveOrUpdate(regenerador);
        
        atualizaComboBox(valores, viewRegeneradorPanel.getFieldPressaoSai());
        
        tx.commit();        
    }
    
    public void atualizaPressaoSaida2(){  
        if(viewRegeneradorPanel.getFieldPressaoSai2().getSelectedItem().equals("")){
            return;
        }
        
        Double value = null;
        try{          
            value = Double.parseDouble(viewRegeneradorPanel.getFieldPressaoSai2().getSelectedItem().toString());
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, e);
            return;
        }

        Criteria cr = this.session.createCriteria(ModelRegenerador.class);
        List results = cr.list();
        ModelRegenerador regenerador = null;

        for(int i=0;i<results.size();i++){
            if(Objects.equals(value, ((ModelRegenerador)results.get(i)).getPressaoSai2())){
                return;
            }
        }
        
        Transaction tx = this.session.beginTransaction();
        Vector<Double> valores= new Vector<>();

        if(results.size() < 5){
            regenerador = new ModelRegenerador();
            session.save(regenerador);
        }
        
        for(int i=results.size()-1; i>0; i--){
            ((ModelRegenerador)results.get(i)).setPressaoSai2(((ModelRegenerador)results.get(i-1)).getPressaoSai2());
            session.saveOrUpdate((ModelRegenerador)results.get(i));
            valores.add(((ModelRegenerador)results.get(i)).getPressaoSai2());
        }
        
        if(!results.isEmpty())
            regenerador = (ModelRegenerador)results.get(0);
        valores.add(value);
        regenerador.setPressaoSai2(value);
        session.saveOrUpdate(regenerador);
        
        atualizaComboBox(valores, viewRegeneradorPanel.getFieldPressaoSai2());
        
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
        Vector<Double> valores= new Vector<>();

        if(results.size() < 5){
            regenerador = new ModelRegenerador();
            session.save(regenerador);
        }
         
        for(int i=results.size()-1; i>0; i--){
            ((ModelRegenerador)results.get(i)).setDelaPressao(((ModelRegenerador)results.get(i-1)).getDelaPressao());
            session.saveOrUpdate((ModelRegenerador)results.get(i));
            valores.add(((ModelRegenerador)results.get(i)).getDelaPressao());
        }
        
        if(!results.isEmpty())
            regenerador = (ModelRegenerador)results.get(0);
        valores.add(value);
        regenerador.setDelaPressao(value);
        session.saveOrUpdate(regenerador);
        
        atualizaComboBox(valores, viewRegeneradorPanel.getFieldDelta());
        
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
        Vector<Double> valores = new Vector<>();

        if(results.size() < 5){
            regenerador = new ModelRegenerador();
            session.save(regenerador);
        }
         
        for(int i=results.size()-1; i>0; i--){
            ((ModelRegenerador)results.get(i)).setEfetividade(((ModelRegenerador)results.get(i-1)).getEfetividade());
            session.saveOrUpdate((ModelRegenerador)results.get(i));
            valores.add(((ModelRegenerador)results.get(i)).getEfetividade());
        }
        
        if(!results.isEmpty())
            regenerador = (ModelRegenerador)results.get(0);
        valores.add(value);
        regenerador.setEfetividade(value);
        session.saveOrUpdate(regenerador);
        
        atualizaComboBox(valores, viewRegeneradorPanel.getFieldEfetiv());
        
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
