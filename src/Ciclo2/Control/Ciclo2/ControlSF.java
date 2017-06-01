/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo2.Control.Ciclo2;

import Ciclo1.Model.ModelCriticasKCSMat_PM;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author leonardo
 */
public class ControlSF {
    
    private double sp, v2, DHT;
    
    public ControlSF(double T2, double P2, int ii, double m, double H2s, Session session) {
        ControlConstantes constantes = new ControlConstantes(T2, P2, ii, session);
        ControlZeta zeta = new ControlZeta(constantes.getBeta(), constantes.getEps(), constantes.getDelta());
        
        Criteria cr = session.createCriteria(ModelCriticasKCSMat_PM.class); 
        cr.add(Restrictions.eq("id", ii));
        List results = cr.list();
        double PM = ((ModelCriticasKCSMat_PM)results.get(0)).getValor();
        
        
        v2 = (zeta.getZv() * constantes.getR()* T2)/P2;
        v2 *= m;
        H2s = (H2s/PM)*1000;
        DHT = H2s;
        sp = (Math.pow(v2, 0.5))/(Math.pow(H2s, 0.25));
        
    }

    public double getSp() {
        return sp;
    }

    public void setSp(double sp) {
        this.sp = sp;
    }

    public double getV2() {
        return v2;
    }

    public void setV2(double v2) {
        this.v2 = v2;
    }

    public double getDHT() {
        return DHT;
    }

    public void setDHT(double DHT) {
        this.DHT = DHT;
    }
    
    
    
}
