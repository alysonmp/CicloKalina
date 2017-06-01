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
public class ControlS_Ideal_Gas {
    
    private double Cpj, Sig, R;
    private int ii;
    
    public ControlS_Ideal_Gas(double T, double Tref, double x, double P, double Pref, Session session){
        Criteria cr = session.createCriteria(ModelConstantesRankineMat.class);
        List results = cr.list();

        //UTILIZA A PRIMEIRA LINHA BUSCADA, VARIÁVEL CC
        ModelConstantesRankineMat constantesMat = (ModelConstantesRankineMat)results.get(2); 
        double[][] valores = constantesMat.getValores();

        R = 8.314;
        
        if(x == 0){
            ii=0;
        }else{
            //%temperatura de 100K até 1500K
            ii = 1;
        }

        //Cpj=cc(1,1)+cc(1,2)*T+cc(1,3)*T^2+cc(1,4)*T^3+cc(1,5)*T^4;
        Cpj = valores[ii][0]+valores[ii][1]*T+valores[ii][2]*Math.pow(T, 2)+valores[ii][3]*Math.pow(T, 3)+valores[ii][4]*Math.pow(T, 4);
        //Sigj=((cc(1,1)*log(T)+cc(1,2)*T+(cc(1,3)/2)*(T^2)+(cc(1,4)/3)*(T^3)+(cc(1,5)/4)*(T^4))- (cc(1,1)*log(Tref)+cc(1,2)*Tref+(cc(1,3)/2)*(Tref^2)+(cc(1,4)/3)*(Tref^3)+(cc(1,5)/4)*(Tref^4)))-(R*(log(zj)));
        Sig = ((valores[ii][0]*Math.log(T)+valores[ii][1]*T+(valores[ii][2]/2)*(Math.pow(T, 2))+(valores[ii][3]/3)*(Math.pow(T, 3))+(valores[ii][4]/4)*(Math.pow(T, 4)))- (valores[ii][0]*Math.log(Tref)+valores[ii][1]*Tref+(valores[ii][2]/2)*(Math.pow(Tref, 2))+(valores[ii][3]/3)*(Math.pow(Tref, 3))+(valores[ii][4]/4)*(Math.pow(Tref, 4))))-(R*(Math.log(P/Pref)));
    }

    public double getSig() {
        return Sig;
    }

    public void setSig(double Sig) {
        this.Sig = Sig;
    }
}

