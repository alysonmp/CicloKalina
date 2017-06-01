/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.Ciclo1;

import Ciclo1.Model.ModelConstantesKCSMat_CC;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author leonardo
 */
public class ControlH_Ideal_Gas {

    private double Hig;
    
    public ControlH_Ideal_Gas(double T, double Tref, int ii, Session session) {
        
        Criteria cr = session.createCriteria(ModelConstantesKCSMat_CC.class); 
        cr.add(Restrictions.eq("cod", ii));
        List results = cr.list();
        
        ModelConstantesKCSMat_CC constantes = (ModelConstantesKCSMat_CC)results.get(0); 
        double[] valores = constantes.getValores();
        
        //Cp=cc(ii,1)+cc(ii,2)*T+cc(ii,3)*T^2+cc(ii,4)*T^3+cc(ii,5)*T^4;
        Hig=((valores[0]*T)+(valores[1]*(Math.pow(T,2))/2)+(valores[2]*(Math.pow(T,3))/3)+(valores[3]*(Math.pow(T,4))/4)+(valores[4]*(Math.pow(T,5))/5))-((valores[0]*Tref)+(valores[1]*(Math.pow(Tref,2))/2)+(valores[2]*(Math.pow(Tref,3))/3)+(valores[3]*(Math.pow(Tref,4))/4)+(valores[4]*(Math.pow(Tref,5))/5));
    }

    public double getHig() {
        return Hig;
    }

    public void setHig(double Hig) {
        this.Hig = Hig;
    }
    
    
}
