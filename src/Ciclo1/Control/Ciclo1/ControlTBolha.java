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
public class ControlTBolha {
    
    private double zj, erro, Te, DT, teste, Tbol, yi;
    private ControlConequi conequi;
    
    public ControlTBolha(double P, double zi, Session session){
        zj = 1-zi;
        erro = 1;

        Te = new ControlT_Ref(P, 0, session).getTref();
        DT=0.15*Te;
        
        while(erro > 0.0001){
            conequi = new ControlConequi(session);
            conequi.realizaCalculo(Te, P, zi);
            teste = (conequi.getKi()*zi)+(conequi.getKj()*zj);
            erro = Math.abs((1-teste)/1);
            if(erro > 0.0001 && teste < 1){
                Te=Te+DT;
                DT=DT/2;
                if(DT < 0.005){
                    DT=0.0047953543;
                }
            }else{
                if(erro>0.0001 & teste>1){
                    Te=Te-DT;
                    DT=DT/2;
                    if(DT<0.005){
                        DT=0.00393845211111;
                    }
                }   
            }
        }
        
        Tbol = Te;
        yi = conequi.getKi()*zi;
    }

    public double getTbol() {
        return Tbol;
    }

    public void setTbol(double Tbol) {
        this.Tbol = Tbol;
    }

    public double getYi() {
        return yi;
    }

    public void setYi(double yi) {
        this.yi = yi;
    }
}