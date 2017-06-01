/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo1;

import Model.ModelConstantesKCSMat_CC;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author leonardo
 */
public class ControlS_Ideal_Gas {
    private double Sig;

    public ControlS_Ideal_Gas(double T, double Tref, double P, double Pref, int ii, Session session) {
        Criteria cr = session.createCriteria(ModelConstantesKCSMat_CC.class); 
        cr.add(Restrictions.eq("cod", ii));
        List results = cr.list();
        
        ModelConstantesKCSMat_CC constantes = (ModelConstantesKCSMat_CC)results.get(0); 
        double[] valores = constantes.getValores();
        double R=8.314;
        
        //Cp=cc(ii,1)+cc(ii,2)*T+cc(ii,3)*T^2+cc(ii,4)*T^3+cc(ii,5)*T^4;
        Sig=((valores[0]*Math.log(T)+valores[1]*T+(valores[2]/2)*(Math.pow(T,2))+(valores[3]/3)*(Math.pow(T,3))+(valores[4]/4)*(Math.pow(T,4)))- (valores[0]*Math.log(Tref)+valores[1]*Tref+(valores[2]/2)*(Math.pow(Tref,2))+(valores[3]/3)*(Math.pow(Tref,3))+(valores[4]/4)*(Math.pow(Tref,4))))-(R*(Math.log(P/Pref)));
    }

    public double getSig() {
        return Sig;
    }

    public void setSig(double Sig) {
        this.Sig = Sig;
    }
    
    
}
