/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo2.Control.Ciclo2;

import Ciclo2.Model.ModelCriticasKCSMat_PM;
import Ciclo2.Model.ModelCriticasKCSMat_Tc;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author alysonmp
 */
public class ControlVolumenEspecifico {
    
    private Session session;
    private double Tf, Tf1, P, Zl, Zv, Z, R, Vl, Vv, Ps2;
    private double[] V = new double[7];
    
    public ControlVolumenEspecifico(int ii, double T1, double T2, double T4, double T5, double T6, double P1, double P2, double P4, double P5, double P6, Session session){
        
        R = 8.314472;
        
        Criteria cr = this.session.createCriteria(ModelCriticasKCSMat_Tc.class);
        cr.add(Restrictions.eq("cod", ii));
        List results = cr.list();
        
        ModelCriticasKCSMat_Tc Tc = (ModelCriticasKCSMat_Tc) results.get(0);
        
        cr = this.session.createCriteria(ModelCriticasKCSMat_PM.class);
        cr.add(Restrictions.eq("cod", ii));
        results = cr.list();
        
        ModelCriticasKCSMat_PM pm = (ModelCriticasKCSMat_PM) results.get(0);
        
        Tf = T1+10;
        Tf1 = Tc.getValor();
        
        double[] T = {T4, T5, T6, T2, T1, Tf, Tf1};
        P = P4;
        
        for(int i = 0; i < T.length; i++){
            ControlConstantes constantes = new ControlConstantes(T[i], P, ii, session);
            ControlZeta zeta = new ControlZeta(constantes.getBeta(), constantes.getEps(), constantes.getDelta());
            Zl = zeta.getZl();
            Zv = zeta.getZv();
            Z = zeta.getZ();
            
            Vl = (R*T[i]*Zl)/P;
            Vv = (R*T[i]*Zv)/P;
            
            ControlPdeVapor pdevapor = new ControlPdeVapor(T[i], ii, session);
            Ps2 = pdevapor.getPs2();

            if(Ps2 >= P){
                V[i]=Vv;
            }else{
                if(Ps2 < P){
                    V[i]=Vl;
                }
            }

            V[i]=(V[i]/pm.getValor());
        }
    }
}