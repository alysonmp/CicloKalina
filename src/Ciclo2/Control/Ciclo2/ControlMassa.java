/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo2.Control.Ciclo2;

import org.hibernate.Session;

/**
 *
 * @author leonardo
 */
public class ControlMassa {
    private double m, Q, Tfout, PINCH, Hlat, Hsen, Hsup, T1s, Tf2;

    public ControlMassa(double H4, double H1, double H6, double P1, int ii, double Pref, double Tref, double T1, double T6, double SUP, double PINCH, double mf, double Tf, double Pf, int compressor, Session session) {
        T1s = T1 - SUP;
        Tf2 = T1s + PINCH;
        
        ControlH_Sistema hSistema = new ControlH_Sistema(T1s, P1, Pref, Tref, ii, session);
        double HLsat = hSistema.getHL();
        double HVsat = hSistema.getHV();
        
        double EntEVP = H1 - HLsat;
        
        ControlCalor calor = new ControlCalor(compressor, Tf, Tf2, session);
        
        m = calor.getQfon1()/EntEVP;
        double QTf = m * (H1 - H6);
        double QTfcor = QTf* 1.02;
        ControlTSaida tSaidaF = new ControlTSaida(compressor, Tf, QTfcor,session);
        double Tfout = tSaidaF.getTfout();
        
        //Correcao da massa
        
        int it = 0;
        /*while((T6+5) > tSaidaF.getTfout()){
            PINCH+=0.1;
            it++;
            if(it > 10000)
                break;
            
        }*/
        T1s = T1 - SUP;
        Tf2 = T1s+PINCH;
        
        hSistema = new ControlH_Sistema(T1s, P1, Pref, Tref, ii, session);
        HLsat = hSistema.getHL();
        HVsat = hSistema.getHV();
        
        Hlat = HVsat-HLsat; //%kJ/kmol
        Hsen = HLsat-H6; //%kJ/kmol
        Hsup = H1-HVsat; //%kJ/kmol
        Q = QTf;
    }

    public double getM() {
        return m;
    }

    public void setM(double m) {
        this.m = m;
    }

    public double getQ() {
        return Q;
    }

    public void setQ(double Q) {
        this.Q = Q;
    }

    public double getTfout() {
        return Tfout;
    }

    public void setTfout(double Tfout) {
        this.Tfout = Tfout;
    }

    public double getPINCH() {
        return PINCH;
    }

    public void setPINCH(double PINCH) {
        this.PINCH = PINCH;
    }

    public double getHlat() {
        return Hlat;
    }

    public void setHlat(double Hlat) {
        this.Hlat = Hlat;
    }

    public double getHsen() {
        return Hsen;
    }

    public void setHsen(double Hsen) {
        this.Hsen = Hsen;
    }

    public double getHsup() {
        return Hsup;
    }

    public void setHsup(double Hsup) {
        this.Hsup = Hsup;
    }

    public double getT1s() {
        return T1s;
    }

    public void setT1s(double T1s) {
        this.T1s = T1s;
    }
}