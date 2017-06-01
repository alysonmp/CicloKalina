/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.Ciclo1;

import org.hibernate.Session;

/**
 *
 * @author leonardo
 */
public class ControlChiller {
    private double PF, Tout, Houlet, Soulet;

    public ControlChiller(double m, double H3, double h3sv, double s3sv, double T3, double P3, double Pref, double Tref, int ii, Session session) {
        double COP = 0.5;
        double PF = 210;
        double PFmax = m*(H3-h3sv)*COP;
        
        if(PFmax <= PF){
            PF = PFmax;
            Tout = Tref;
            Houlet = h3sv;
            Soulet = s3sv;
        }else{
            Houlet = H3 - (PF/(m*COP));
            double Test = T3;
            double erro = 1;
            double DT = 55;
            double H, Burbuja;
            
            while(erro>0.0001){
                ControlH_Sistema hSistem= new ControlH_Sistema(Test, P3, Pref, Tref, ii,session);
                H = hSistem.getHV();
                erro= Math.abs((Houlet-H)/Houlet);
                Burbuja=Houlet-H;
                if(erro>0.0001 & Burbuja<0){
                    Test=Test-DT;
                    DT=DT/2;
                    if(DT<0.005)
                        DT=0.005;
                }else if(erro>0.0001 && Burbuja>0){
                        Test=Test+DT;
                        DT=DT/2;
                        if(DT<0.005)
                            DT=0.003;
                        
                }
            }
            
            Tout = Test;
            ControlS_Sistema sSistema = new ControlS_Sistema(Tout, P3, Pref, Tref, ii, session);
            Soulet = sSistema.getSV();
        }
    }

    public double getPF() {
        return PF;
    }

    public void setPF(double PF) {
        this.PF = PF;
    }

    public double getTout() {
        return Tout;
    }

    public void setTout(double Tout) {
        this.Tout = Tout;
    }

    public double getHoulet() {
        return Houlet;
    }

    public void setHoulet(double Houlet) {
        this.Houlet = Houlet;
    }

    public double getSoulet() {
        return Soulet;
    }

    public void setSoulet(double Soulet) {
        this.Soulet = Soulet;
    }
    
}
