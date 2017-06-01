/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.Ciclo1;

import org.hibernate.Session;

/**
 *
 * @author alysonmp
 */
public class ControlTorvalho {
    
    private double zj, erro, Te, DT, teste, Torv, xi;
    private ControlConequi conequi;
    
    public ControlTorvalho(double P, double zi, Session session){
        
        zj = 1-zi;
        erro = 1;
        Te = new ControlT_Ref(P, 0, session).getTref();
        DT = 0.1*Te;
        
        while(erro > 0.001){
            conequi = new ControlConequi(session);
            conequi.realizaCalculo(Te, P, zi);
            
            teste = (zi/conequi.getKi())+(zj/conequi.getKj());
            erro = Math.abs((1-teste)/1);
            if(erro > 0.001 & teste < 1){
                    Te = Te-DT;
                    DT = DT/2;
                    if(DT < 0.005){
                        DT = 0.0047953543;
                    }
            }else{
                if(erro>0.001 & teste>1){
                    Te = Te+DT;
                    DT = DT/2;
                    if(DT < 0.005){
                        DT = 0.00393845211111;
                    }
                }
            }
        }
        Torv = Te;
        xi=zi/conequi.getKi();
    }

    public double getTorv() {
        return Torv;
    }

    public void setTorv(double Torv) {
        this.Torv = Torv;
    }

    public double getXi() {
        return xi;
    }

    public void setXi(double xi) {
        this.xi = xi;
    }
}