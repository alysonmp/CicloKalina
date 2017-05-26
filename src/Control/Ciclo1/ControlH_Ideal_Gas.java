/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo1;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 *
 * @author leonardo
 */
public class ControlH_Ideal_Gas {

    private double Hig;
    
    public ControlH_Ideal_Gas(double T, double Tref, int ii, Session session) {
        
        Criteria cr = session.createCriteria(ModelContantes.class); 
        List results = cr.list();
        
        ModelConstantes constantes = (ModelConstantes)results.get(0); 
        double[][] valores = constantes.getValores();
        
        //Cp=cc(ii,1)+cc(ii,2)*T+cc(ii,3)*T^2+cc(ii,4)*T^3+cc(ii,5)*T^4;
        Hig=((valores[ii][0]*T)+(valores[ii][1]*(Math.pow(T,2))/2)+(valores[ii][2]*(Math.pow(T,3))/3)+(valores[ii][3]*(Math.pow(T,4))/4)+(valores[ii][4]*(Math.pow(T,5))/5))-((valores[ii][0]*Tref)+(valores[ii][1]*(Math.pow(Tref,2))/2)+(valores[ii][2]*(Math.pow(Tref,3))/3)+(valores[ii][3]*(Math.pow(Tref,4))/4)+(valores[ii][4]*(Math.pow(Tref,5))/5));
    }

    public double getHig() {
        return Hig;
    }

    public void setHig(double Hig) {
        this.Hig = Hig;
    }
    
    
}
