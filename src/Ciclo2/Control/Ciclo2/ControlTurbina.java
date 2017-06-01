/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo2.Control.Ciclo2;

import org.hibernate.Session;

/**
 *
 * @author alysonmp
 */
public class ControlTurbina {
    
    private double HL1, HV1, SL1, SV1, H1, S1, P2;
    private ControlIsoentropiaTurbine isoTurbina;
    
    public ControlTurbina(double Teff, double P1, double T1, double Pconop, double Pref, double Tref, int ii, Session session){
        ControlH_Sistema HSistema = new ControlH_Sistema(T1, P1, Pref, Tref, ii, session);
        HL1 = HSistema.getHL();
        HV1 = HSistema.getHV();
        
        ControlS_Sistema SSistema = new ControlS_Sistema(T1, P1, Pref, Tref, ii, session);
        SL1 = SSistema.getSL();
        SV1 = SSistema.getSV();
        
        H1 = HV1;
        S1 = SV1;
        P2 = Pconop;
        
        isoTurbina = new ControlIsoentropiaTurbine(Teff, P2, Pref, Tref, S1, H1, ii, session);
    }

    public ControlIsoentropiaTurbine getIsoTurbina() {
        return isoTurbina;
    }

    public void setIsoTurbina(ControlIsoentropiaTurbine isoTurbina) {
        this.isoTurbina = isoTurbina;
    }   

    public double getH1() {
        return H1;
    }

    public void setH1(double H1) {
        this.H1 = H1;
    }

    public double getS1() {
        return S1;
    }

    public void setS1(double S1) {
        this.S1 = S1;
    }
}