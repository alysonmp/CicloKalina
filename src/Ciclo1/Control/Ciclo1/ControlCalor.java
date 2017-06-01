/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.Ciclo1;

import Ciclo2.Model.ModelQfpsoRankineMat;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 *
 * @author leonardo
 */
public class ControlCalor {
    
    private double Qfon1;
    
    public ControlCalor(int compressor, double Tf, double Tf2, Session session) {
        int c = compressor-1;
        double T = Tf - Tf2;
        
        Criteria cr = session.createCriteria(ModelQfpsoRankineMat.class); 
        List results = cr.list();
        
        ModelQfpsoRankineMat q = (ModelQfpsoRankineMat)results.get(c);
        double[] Qfpso = q.getQfpso();
        
        switch (compressor) {
            case 1:
                //Qfon1=(Qfpso(c,1)+Qfpso(c,2)*T+Qfpso(c,3)*T.^2+Qfpso(c,4)*T.^3+Qfpso(c,5)*T.^4+Qfpso(c,6)*T.^5+Qfpso(c,7)*T.^6+Qfpso(c,8)*T.^7+Qfpso(c,9)*T.^8+Qfpso(c,10)*T.^9+Qfpso(c,11)*T.^10)
                Qfon1=(Qfpso[0]+Qfpso[1]*T+Qfpso[2]*Math.pow(T,2) + Qfpso[3]*Math.pow(T,3) + Qfpso[4] * Math.pow(T,4) + Qfpso[5]* Math.pow(T,5) +Qfpso[6] * Math.pow(T,6) + Qfpso[7]*Math.pow(T,7)+Qfpso[8]*Math.pow(T,8)+Qfpso[9]*Math.pow(T,9)+Qfpso[10]*Math.pow(T,10));
                break;
            case 2:
                //Qfon1=(Qfpso(c,1)+Qfpso(c,2)*T+Qfpso(c,3)*T.^2+Qfpso(c,4)*T.^3+Qfpso(c,5)*T.^4+Qfpso(c,6)*T.^5+Qfpso(c,7)*T.^6+Qfpso(c,8)*T.^7+Qfpso(c,9)*T.^8+Qfpso(c,10)*T.^9+Qfpso(c,11)*T.^10)
                Qfon1=(Qfpso[0]+Qfpso[1]*T+Qfpso[2]*Math.pow(T,2)+Qfpso[3]*Math.pow(T,3)+Qfpso[4]*Math.pow(T,4)+Qfpso[5]*Math.pow(T,5)+Qfpso[6]*Math.pow(T,6) + Qfpso[7]*Math.pow(T,7)+Qfpso[8]*Math.pow(T,8) +Qfpso[9]*Math.pow(T,9)+Qfpso[10]*Math.pow(T,10));
                break;
            case 3:
                //Qfon1=(Qfpso(c,1)+Qfpso(c,3)*T+Qfpso(c,5)*T.^2+Qfpso(c,7)*T.^3+Qfpso(c,9)*T.^4+Qfpso(c,11)*T.^5)./(1+Qfpso(c,2)*T+Qfpso(c,4)*T.^2+Qfpso(c,6)*T.^3+Qfpso(c,8)*T.^4+Qfpso(c,10)*T.^5)
                Qfon1=(Qfpso[0]+Qfpso[2]*T+Qfpso[4]*Math.pow(T,2)+Qfpso[6]*Math.pow(T,3)+Qfpso[8]*Math.pow(T,4)+Qfpso[10]*Math.pow(T,5))/(1+Qfpso[1]*T+Qfpso[3]*Math.pow(T,2)+Qfpso[5]*Math.pow(T,3)+Qfpso[7]*Math.pow(T,4)+Qfpso[9]*Math.pow(T,5));
                break;
            case 4:
                //Qfon1=(Qfpso(c,1)+Qfpso(c,2)*T+Qfpso(c,3)*T.^2+Qfpso(c,4)*T.^3+Qfpso(c,5)*T.^4+Qfpso(c,6)*T.^5+Qfpso(c,7)*T.^6+Qfpso(c,8)*T.^7+Qfpso(c,9)*T.^8+Qfpso(c,10)*T.^9+Qfpso(c,11)*T.^10)
                Qfon1=(Qfpso[0]+Qfpso[1]*T+Qfpso[2]*Math.pow(T,2)+Qfpso[3]*Math.pow(T,3)+Qfpso[4]*Math.pow(T,4)+Qfpso[5]*Math.pow(T,5)+Qfpso[6]*Math.pow(T,6)+Qfpso[7]*Math.pow(T,7)+Qfpso[8]*Math.pow(T,8)+Qfpso[9]*Math.pow(T,9)+Qfpso[10]*Math.pow(T,10));
                break;
            case 5:
                //Qfon1=(Qfpso(c,1)+Qfpso(c,2)*T+Qfpso(c,3)*T.^2+Qfpso(c,4)*T.^3+Qfpso(c,5)*T.^4+Qfpso(c,6)*T.^5+Qfpso(c,7)*T.^6+Qfpso(c,8)*T.^7+Qfpso(c,9)*T.^8+Qfpso(c,10)*T.^9+Qfpso(c,11)*T.^10)
                Qfon1 = Qfpso[0]+Qfpso[1]*T+Qfpso[2]*Math.pow(T,2)+Qfpso[3]*Math.pow(T,3)+Qfpso[4]*Math.pow(T,4)+Qfpso[5]*Math.pow(T,5)+Qfpso[6]*Math.pow(T,6)+Qfpso[7]*Math.pow(T,7)+Qfpso[8]*Math.pow(T,8)+Qfpso[9]*Math.pow(T,9)+Qfpso[10]*Math.pow(T,10);
                break;
            default:
                break;
        }
    }

    public double getQfon1() {
        return Qfon1;
    }

    public void setQfon1(double Qfon1) {
        this.Qfon1 = Qfon1;
    }
}
