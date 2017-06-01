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
public class ControlS_Ideal_Gas_Mix {

    private double zj, R, Cpj, Sigj, Cpi, Sigi, Sigmix;
    private Session session;
    
    public ControlS_Ideal_Gas_Mix(double T, double Tref, double zi, double P, double Pref, Session session) {
        this.session = session;
        
        zj = 1 - zi;
        
        Criteria cr = this.session.createCriteria(ModelConstantesRankineMat.class); 
        List results = cr.list();
        
        R = 8.314;
        
        ModelConstantesRankineMat constantesMat = (ModelConstantesRankineMat)results.get(2); 
        double[][] valores = constantesMat.getValores();
        
        //Cpj=cc(1,1)+cc(1,2)*T+cc(1,3)*T^2+cc(1,4)*T^3+cc(1,5)*T^4;
        Cpj = valores[0][0]+valores[0][1]*T+valores[0][2]*Math.pow(T, 2)+valores[0][3]*Math.pow(T, 3)+valores[0][4]*Math.pow(T, 4);
        //Sigj=((cc(1,1)*log(T)+cc(1,2)*T+(cc(1,3)/2)*(T^2)+(cc(1,4)/3)*(T^3)+(cc(1,5)/4)*(T^4))- (cc(1,1)*log(Tref)+cc(1,2)*Tref+(cc(1,3)/2)*(Tref^2)+(cc(1,4)/3)*(Tref^3)+(cc(1,5)/4)*(Tref^4)))-(R*(log(zj)));
        Sigj=((valores[0][0]*Math.log(T)+valores[0][1]*T+(valores[0][2]/2)*(Math.pow(T, 2))+(valores[0][3]/3)*(Math.pow(T, 3))+(valores[0][4]/4)*(Math.pow(T, 4)))- (valores[0][0]*Math.log(Tref)+valores[0][1]*Tref+(valores[0][2]/2)*(Math.pow(Tref, 2))+(valores[0][3]/3)*(Math.pow(Tref, 3))+(valores[0][4]/4)*(Math.pow(Tref, 4))))-(R*(Math.log(zj)));
        
        //%temperatura de 100K atÈ 1500K
        
        //Cpi = cc(2,1)+cc(2,2)*T+cc(2,3)*T^2+cc(2,4)*T^3+cc(2,5)*T^4;
        Cpi = valores[1][0]+valores[1][1]*T+valores[1][2]*Math.pow(T, 2)+valores[1][3]*Math.pow(T, 3)+valores[1][4]*Math.pow(T, 4);
        //Sigi=((cc(2,1)*log(T)+cc(2,2)*T+(cc(2,3)/2)*(T^2)+(cc(2,4)/3)*(T^3)+(cc(2,5)/4)*(T^4))- (cc(2,1)*log(Tref)+cc(2,2)*Tref+(cc(2,3)/2)*(Tref^2)+(cc(2,4)/3)*(Tref^3)+(cc(2,5)/4)*(Tref^4)))-(R*(log(zi)));
        Sigi = ((valores[1][0]*Math.log(T)+valores[1][1]*T+(valores[1][2]/2)*(Math.pow(T, 2))+(valores[1][3]/3)*(Math.pow(T, 3))+(valores[1][4]/4)*(Math.pow(T, 4)))-(valores[1][0]*Math.log(Tref)+valores[1][1]*Tref+(valores[1][2]/2)*(Math.pow(Tref, 2))+(valores[1][3]/3)*(Math.pow(Tref, 3))+(valores[1][4]/4)*(Math.pow(Tref, 4))))-(R*(Math.log(zi)));
        
        //Sigmix=(zi*Sigi)+(zj*Sigj);
        Sigmix = (zi*Sigi)+(zj*Sigj);
    }   

    public double getSigmix() {
        return Sigmix;
    }

    public void setSigmix(double Sigmix) {
        this.Sigmix = Sigmix;
    }
}