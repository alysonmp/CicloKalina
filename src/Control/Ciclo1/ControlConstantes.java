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
public class ControlConstantes {
    private double beta, eps, delta, a, b, A, B, K0, Tr, R, Tc, Pc, K1, w;

    public ControlConstantes(double T, double P, double ii,Session session) {
    
        Criteria cr = session.createCriteria(criticas.class); 
        List results = cr.list();
        
        K0=0.378893+1.489715*w[ii]-0.1713848*(w[ii]^2)+0.0196544*(w[ii]^3);
        Tr=T/Tc[ii];
        alp=(1+K0*(1-(Tr^0.5))+K1[ii]*(1-Tr)*(0.7-Tr))^2;
        a=(0.457235*(R^2)*(Tc[ii]^2)*alp)/Pc[ii];
        b=(0.077796*R*Tc[ii])/Pc[ii];

        A=(a*P)/((R^2)*(T^2));

        B=(b*P)/(R*T);

        beta=B-1;

        eps=A-3*(B^2)-2*B;

        delta=(B^3)+(B^2)-A*B;

        K1=K1[ii];
        Pc=Pc[ii];
        Tc=Tc[ii];
        w=w[ii];
        
    }

    
}
