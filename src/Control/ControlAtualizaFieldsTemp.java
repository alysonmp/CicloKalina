/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Ciclo2.ModelBomba;
import Util.DropdownComboBox;
import View.Bomba.ViewBombaPanelRankine;
import java.awt.Component;
import java.util.List;
import java.util.Vector;
import javax.swing.JPanel;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author alysonmp
 */
public class ControlAtualizaFieldsTemp {
    
    Session session;
    ControlPrincipal ctrlPrincipal;
    
    public ControlAtualizaFieldsTemp(Session session, ControlPrincipal ctrlPrincipal, double valor, int field){
        this.session = session;
        this.ctrlPrincipal = ctrlPrincipal;
        
        switch(field){
            case 7:
                Component[] components = ctrlPrincipal.getViewPrincipal().getTabbedPanelComp().getComponents();
                for(Component c: components){
                    if(c.getName().equals("Bomba")){
                        ViewBombaPanelRankine panel = (ViewBombaPanelRankine)c;
                        panel.getFieldTempEntr().setSelectedItem(valor);
                        break;
                    }
                }
                atualizaCaso7(valor);
                break;
        }
    }

    private void atualizaCaso7(double valor) {
        Vector valores = new Vector();

        Criteria cr = session.createCriteria(ModelBomba.class);
        List result = cr.list();
        Transaction tx = session.beginTransaction();

        if(result.size() < 5){
            ModelBomba m1 = new ModelBomba();
            m1.setTemperaturaEntr(valor);
            session.saveOrUpdate(m1);

            valores.add(m1.getTemperaturaEntr()+"");
        }else{
            for(int i = result.size()-1; i > 0; i--){
                ModelBomba m1 = (ModelBomba) result.get(i);
                ModelBomba m2 = (ModelBomba) result.get(i-1);

                m1.setTemperaturaEntr(m2.getTemperaturaEntr());

                session.saveOrUpdate(m1);
                valores.add(m1.getTemperaturaSai()+"");
            }

            ModelBomba m1 = (ModelBomba) result.get(0);
            m1.setTemperaturaEntr(valor);

            session.saveOrUpdate(m1);
            valores.add(m1.getTemperaturaEntr()+"");
        }

        tx.commit();
    }
    
}
