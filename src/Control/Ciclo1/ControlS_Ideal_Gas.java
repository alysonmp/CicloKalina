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
public class ControlS_Ideal_Gas {
    private double Sig;

    public ControlS_Ideal_Gas(double T, double Tref, double P, double Pref, int ii, Session session) {
        Criteria cr = session.createCriteria(ModelContantes.class); 
        List results = cr.list();
        
        ModelConstantes constantes = (ModelConstantes)results.get(0); 
        double[][] valores = constantes.getValores();
        double R=8.314;
        
        //Cp=cc(ii,1)+cc(ii,2)*T+cc(ii,3)*T^2+cc(ii,4)*T^3+cc(ii,5)*T^4;
        Sig=((valores[ii][0]*Math.log(T)+valores[ii][1]*T+(valores[ii][2]/2)*(Math.pow(T,2))+(valores[ii][3]/3)*(Math.pow(T,3))+(valores[ii][4]/4)*(Math.pow(T,4)))- (valores[ii][0]*Math.log(Tref)+valores[ii][1]*Tref+(valores[ii][2]/2)*(Math.pow(Tref,2))+(valores[ii][3]/3)*(Math.pow(Tref,3))+(valores[ii][4]/4)*(Math.pow(Tref,4))))-(R*(Math.log(P/Pref)));
    }

    public double getSig() {
        return Sig;
    }

    public void setSig(double Sig) {
        this.Sig = Sig;
    }
    
    
}
