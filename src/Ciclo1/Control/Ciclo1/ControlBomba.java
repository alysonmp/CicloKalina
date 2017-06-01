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
public class ControlBomba {
    
    private double T9, P9, P10, H9, S9, T10s, S10, H10s, H10, T10;
    
    public ControlBomba(double Beff, double P1, double Pcon, double Tcon, double Pref, double Tref, double zi, Session session){
        T9 = Tcon;
        P9 = Pcon;
        P10 = P1;
        
        ControlH_Sistemamix HSistemamix = new ControlH_Sistemamix(T9, P9, Pref, Tref, zi, session);
        H9 = HSistemamix.getHL();
        
        ControlS_SistemaMix SSistemamix = new ControlS_SistemaMix(T9, P9, Pref, Tref, zi, session);
        S9 = SSistemamix.getSL();
        
        ControlIsoentropiaBomba isoBomba = new ControlIsoentropiaBomba(Beff, P10, Pref, Tref, S9, H9, zi, T9, session);
        T10s = isoBomba.getT10();
        S10 = isoBomba.getS10();
        
        HSistemamix = new ControlH_Sistemamix(T10s, P10, Pref, Tref, zi, session);
        H10s = HSistemamix.getHL();
        H10 = ((H10s-H9)/Beff)+H9;
        
        T10 = T10s;
    }

    public double getP10() {
        return P10;
    }

    public void setP10(double P10) {
        this.P10 = P10;
    }

    public double getH9() {
        return H9;
    }

    public void setH9(double H9) {
        this.H9 = H9;
    }

    public double getS9() {
        return S9;
    }

    public void setS9(double S9) {
        this.S9 = S9;
    }

    public double getS10() {
        return S10;
    }

    public void setS10(double S10) {
        this.S10 = S10;
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
}