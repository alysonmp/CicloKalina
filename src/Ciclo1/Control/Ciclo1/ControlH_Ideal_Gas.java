/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.Ciclo1;

import Ciclo2.Model.ModelConstantesRankineMat;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 *
 * @author alysonmp
 */
public class ControlH_Ideal_Gas {
 
    private double Cp, Hig;
    private int ii;
    
    public ControlH_Ideal_Gas(double T, double Tref, double x, Session session){
        
        Criteria cr = session.createCriteria(ModelConstantesRankineMat.class);
        List results = cr.list();
        
        //UTILIZA A PRIMEIRA LINHA BUSCADA, VARIÁVEL CC
        ModelConstantesRankineMat constantesMat = (ModelConstantesRankineMat)results.get(2); 
        double[][] valores = constantesMat.getValores();
        
        if(x == 0){
            ii=0;
        }else{
            //%temperatura de 100K até 1500K
            ii = 1;
        }
        
        //Cp=cc(ii,1)+cc(ii,2)*T+cc(ii,3)*T^2+cc(ii,4)*T^3+cc(ii,5)*T^4;
        Cp = valores[ii][0] + valores[ii][1] * T + valores[ii][2] * Math.pow(T, 2) + valores[ii][3] * Math.pow(T, 3) + valores[ii][4] * Math.pow(T, 4);
        //Hig=((cc(ii,1)*T)+(cc(ii,2)*(T^2)/2)+(cc(ii,3)*(T^3)/3)+(cc(ii,4)*(T^4)/4)+(cc(ii,5)*(T^5)/5))-((cc(ii,1)*Tref)+(cc(ii,2)*(Tref^2)/2)+(cc(ii,3)*(Tref^3)/3)+(cc(ii,4)*(Tref^4)/4)+(cc(ii,5)*(Tref^5)/5));
        Hig = ((valores[ii][0]*T)+(valores[ii][1]*(Math.pow(T,2))/2)+(valores[ii][2]*(Math.pow(T, 3))/3)+(valores[ii][3]*(Math.pow(T,4))/4)+(valores[ii][4]*(Math.pow(T, 5))/5))-((valores[ii][0]*Tref)+(valores[ii][1]*(Math.pow(Tref, 2))/2)+(valores[ii][2]*(Math.pow(Tref,3))/3)+(valores[ii][3]*(Math.pow(Tref,4))/4)+(valores[ii][4]*(Math.pow(Tref, 5))/5));
    }   

    public double getHig() {
        return Hig;
    }

    public void setHig(double Hig) {
        this.Hig = Hig;
    }
}