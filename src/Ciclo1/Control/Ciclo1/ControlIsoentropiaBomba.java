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
public class ControlIsoentropiaBomba {
    
    private double Test, erro, DT, it, S, burbuja, erro1, xx10, T10s, H10s, H10, HL10, H, T10, S10;
    
    public ControlIsoentropiaBomba(double Beff, double P10, double Pref, double Tref, double S9, double H9, double zi, double T9, Session session){
        Test=T9; 
        erro=1;
        DT=0.6;
        it=0;
        
        while(erro >= 0.0001){
            it = it+1;
            if(it > 100){
               // hshshsh*ksksks
            }
            ControlS_SistemaMix SSistemaMix = new ControlS_SistemaMix(Test, P10, Pref, Tref, zi, session);
            S = SSistemaMix.getSL();
            erro = Math.abs((S9-S)/S9);
            burbuja = S9-S;
            if(erro > 0.0001 && burbuja < 0){
                Test = Test-DT;
                DT = DT/2;
                if(DT < 0.005){
                    DT = 0.004987569731;
                }
            }else{
                if(erro > 0.0001 && burbuja > 0){
                    Test = Test+DT;
                    DT = DT/2;
                    if(DT<0.005){
                        DT = 0.002933254;
                    }
                }
            }
        }
        
        erro1 = 1;
        DT = 2;
        it = 0;
    
        while(erro1 >= 0.001){
            xx10 = 0;
            T10s = Test;
            ControlH_Sistemamix HSistemamix = new ControlH_Sistemamix(T10s, P10, Pref, Tref, zi, session);
            H10s = HSistemamix.getHL();
            H10 = ((H10s-H9)/Beff)+H9;
            while(erro1 > 0.001){
                it = it+1;
                if(it > 2000){
                    //hshshsh*ksksks;
                }
                HSistemamix = new ControlH_Sistemamix(Test, P10, Pref, Tref, zi, session);
                HL10 = HSistemamix.getHL();
                H = HL10;
                erro1 = Math.abs((H10-H)/H10);
                burbuja = H10-H;
                if(erro1 > 0.0001 && burbuja < 0){
                    Test = Test-DT;
                    DT = DT/2;
                    if(DT < 0.005){
                        DT = 0.0043978953;
                    }
                }else{
                    if(erro1 > 0.001 && burbuja > 0){
                    Test = Test+DT;
                    DT = DT/2;
                        if(DT < 0.005){
                            DT=0.00333695;
                        }
                    }
                }
            }
        }
        T10=Test;
        
        ControlS_SistemaMix SSitemamix = new ControlS_SistemaMix(T10, P10, Pref, Tref, zi, session);
        S10 = SSitemamix.getSL();
        
        ControlH_Sistemamix HSistemamix = new ControlH_Sistemamix(T10, P10, Pref, Tref, zi, session);
        H10 = HSistemamix.getHL();
    }

    public double getH10() {
        return H10;
    }

    public void setH10(double H10) {
        this.H10 = H10;
    }

    public double getT10() {
        return T10;
    }

    public void setT10(double T10) {
        this.T10 = T10;
    }

    public double getS10() {
        return S10;
    }

    public void setS10(double S10) {
        this.S10 = S10;
    }
}
