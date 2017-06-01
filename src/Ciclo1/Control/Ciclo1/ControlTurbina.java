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
public class ControlTurbina {
    
    private double S2, T4, S4, H4, H4s, xx4, xi4, yi4, P4, RP;
    
    public ControlTurbina(double H2, double Teff, double P2, double T2, double Pcon, double Pref, double Tref, double yi, Session session){
        ControlH_Sistemamix sistemaMix = new ControlH_Sistemamix(T2, P2, Pref, Tref, yi, session);
        
        S2 = sistemaMix.getHV();
        P4 = Pcon;
        RP = P2/P4;
        if(RP<1.5){
            //hdhdh*kfhf
        }
        
        ControlIsoentropiaTurbina controlIsoTurbina = new ControlIsoentropiaTurbina(Teff, P4, Pref, Tref, S2, H2, yi, T2, session);
        
        T4 = controlIsoTurbina.getT4();
        S4 = controlIsoTurbina.getS4();
        H4 = controlIsoTurbina.getH4();
        H4s = controlIsoTurbina.getH4s();
        xx4 = controlIsoTurbina.getXx4();
        xi4 = controlIsoTurbina.getXi4();
        yi4 = controlIsoTurbina.getYi4();
    }

    public double getT4() {
        return T4;
    }

    public void setT4(double T4) {
        this.T4 = T4;
    }

    public double getS4() {
        return S4;
    }

    public void setS4(double S4) {
        this.S4 = S4;
    }

    public double getH4() {
        return H4;
    }

    public void setH4(double H4) {
        this.H4 = H4;
    }

    public double getH4s() {
        return H4s;
    }

    public void setH4s(double H4s) {
        this.H4s = H4s;
    }

    public double getXx4() {
        return xx4;
    }

    public void setXx4(double xx4) {
        this.xx4 = xx4;
    }

    public double getXi4() {
        return xi4;
    }

    public void setXi4(double xi4) {
        this.xi4 = xi4;
    }

    public double getYi4() {
        return yi4;
    }

    public void setYi4(double yi4) {
        this.yi4 = yi4;
    }
}