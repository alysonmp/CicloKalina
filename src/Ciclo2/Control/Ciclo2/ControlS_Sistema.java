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
public class ControlS_Sistema {
    private double SL, SV;

    public ControlS_Sistema(double T, double P, double Pref, double Tref, int ii, Session session) {
        ControlS_Dep sDep = new ControlS_Dep(Tref, Pref, ii, session);
        ControlS_Ideal_Gas sIdealGas = new ControlS_Ideal_Gas(T, Tref, P, Pref, ii, session);
        
        double SDrefL = sDep.getSDL();
        double SDrefV = sDep.getSDV();
        
        sDep = new ControlS_Dep(T, P, ii, session);
        SL = -SDrefL+sIdealGas.getSig()+sDep.getSDL();
        SV = -SDrefL+sIdealGas.getSig()+sDep.getSDV();
    }

    public double getSL() {
        return SL;
    }

    public void setSL(double SL) {
        this.SL = SL;
    }

    public double getSV() {
        return SV;
    }

    public void setSV(double SV) {
        this.SV = SV;
    }
    
    
}
