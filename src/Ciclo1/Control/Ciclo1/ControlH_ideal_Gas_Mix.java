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
public class ControlH_ideal_Gas_Mix {
    
    private double zj, Cp, Higj, Higi, Higmix;
    private Session session;
    
    public ControlH_ideal_Gas_Mix(double T, double Tref, double zi, Session session){
        this.session = session;
        
        zj = 1-zi;
        
        //RECUPERA TODOS OS DADOS DA TABELA DE CONSTANTES
        Criteria cr = this.session.createCriteria(ModelConstantesRankineMat.class); 
        List results = cr.list();
        
        //UTILIZA A PRIMEIRA LINHA BUSCADA, VARIÁVEL CC
        ModelConstantesRankineMat constantesMat = (ModelConstantesRankineMat)results.get(2); 
        double[][] valores = constantesMat.getValores();
        
        //Cp=cc(1,1)+cc(1,2)*T+cc(1,3)*T^2+cc(1,4)*T^3+cc(1,5)*T^4;
        Cp = valores[0][0] + valores[0][1] * T + valores[0][2] * Math.pow(T, 2) + valores[0][3] * Math.pow(T, 3) + valores[0][4] * Math.pow(T, 4);
        //Higj=((cc(1,1)*T)+(cc(1,2)*(T^2)/2)+(cc(1,3)*(T^3)/3)+(cc(1,4)*(T^4)/4)+(cc(1,5)*(T^5)/5))-((cc(1,1)*Tref)+(cc(1,2)*(Tref^2)/2)+(cc(1,3)*(Tref^3)/3)+(cc(1,4)*(Tref^4)/4)+(cc(1,5)*(Tref^5)/5));
        Higj = ((valores[0][0]*T)+(valores[0][1]*(Math.pow(T,2))/2)+(valores[0][2]*(Math.pow(T, 3))/3)+(valores[0][3]*(Math.pow(T,4))/4)+(valores[0][4]*(Math.pow(T, 5))/5))-((valores[0][0]*Tref)+(valores[0][1]*(Math.pow(Tref, 2))/2)+(valores[0][2]*(Math.pow(Tref,3))/3)+(valores[0][3]*(Math.pow(Tref,4))/4)+(valores[0][4]*(Math.pow(Tref, 5))/5));
        
        //%temperatura de 100K até 1500K
        
        //Cp=cc(2,1)+cc(2,2)*T+cc(2,3)*T^2+cc(2,4)*T^3+cc(2,5)*T^4;
        Cp = valores[1][0] + valores[1][1] * T + valores[1][2] * Math.pow(T, 2) + valores[1][3] * Math.pow(T, 3) + valores[1][4] * Math.pow(T, 4);
        //Higi=((cc(2,1)*T)+(cc(2,2)*(T^2)/2)+(cc(2,3)*(T^3)/3)+(cc(2,4)*(T^4)/4)+(cc(2,5)*(T^5)/5))-((cc(2,1)*Tref)+(cc(2,2)*(Tref^2)/2)+(cc(2,3)*(Tref^3)/3)+(cc(2,4)*(Tref^4)/4)+(cc(2,5)*(Tref^5)/5));
        Higi = ((valores[1][0]*T)+(valores[1][1]*(Math.pow(T,2))/2)+(valores[1][2]*(Math.pow(T, 3))/3)+(valores[1][3]*(Math.pow(T,4))/4)+(valores[1][4]*(Math.pow(T, 5))/5))-((valores[1][0]*Tref)+(valores[1][1]*(Math.pow(Tref, 2))/2)+(valores[1][2]*(Math.pow(Tref,3))/3)+(valores[1][3]*(Math.pow(Tref,4))/4)+(valores[1][4]*(Math.pow(Tref, 5))/5));
        
        Higmix = (zi*Higi)+(zj*Higj);
    }

    public double getHigmix() {
        return Higmix;
    }

    public void setHigmix(double Higmix) {
        this.Higmix = Higmix;
    }
}