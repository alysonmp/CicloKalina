/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo1;

import Model.ModelCriticasKCSMat_Tc;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author alysonmp
 */
public class ControlT_Ref {
    
    private double Te;
    private Session session;
    private double erro, DT, Ps2, burbuja, Tref;
    
    public ControlT_Ref(double P, int ii, Session session){
        this.session = session;
        
        Criteria cr = this.session.createCriteria(ModelCriticasKCSMat_Tc.class);
        cr.add(Restrictions.eq("cod", ii));
        List results = cr.list();
        
        ModelCriticasKCSMat_Tc Tc = (ModelCriticasKCSMat_Tc) results.get(0);
        
        Te = Tc.getValor()-150;
        
        erro = 1;
        DT = 80;
        
        while(erro > 0.0001){
            ControlPdeVapor pdevapor = new ControlPdeVapor(Te, ii, session);
            Ps2 = pdevapor.getPs2();
            burbuja = Ps2-P;
            erro = Math.abs((Ps2-P)/Ps2);
            if(erro > 0.0001 && burbuja > 0){
                Te = Te-DT;
                DT = DT/2;
                if(DT<0.005){
                   DT = 0.0047953543;
                }
            }else{
                if(erro > 0.0001 && burbuja < 0){
                    Te = Te+DT;
                    DT = DT/2;
                    if(DT < 0.005){
                       DT = 0.00393845211111;
                    }
                }
            }
        }
        Tref = Te;
    } 

    public double getTref() {
        return Tref;
    }

    public void setTref(double Tref) {
        this.Tref = Tref;
    }
}