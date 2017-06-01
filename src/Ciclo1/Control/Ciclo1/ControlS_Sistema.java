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
public class ControlS_Sistema {
    private double SDrefL, SDrefV, Sig, SDL, SDV, SL, SV;
    
    public ControlS_Sistema(double T, double P, double Pref, double Tref, double x, Session session){
        
        ControlS_Dep Sdep = new ControlS_Dep(Tref, Pref, x, session);
        SDrefL = Sdep.getSDL();
        SDrefV = Sdep.getSDV();
        
        ControlS_Ideal_Gas SIdealgas = new ControlS_Ideal_Gas(T, Tref, x, P, Pref, session);
        Sig = SIdealgas.getSig();
        
        Sdep = new ControlS_Dep(T, P, x, session);
        SDL = Sdep.getSDL();
        SDV = Sdep.getSDV();
        
        SL = -SDrefL+Sig+SDL;
        SV = -SDrefL+Sig+SDV;
    }
}