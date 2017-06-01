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
public class ControlS_SistemaMix {
    
    private double zj, SDrefLj, SDrefVj, SDrefLi, SDrefVi, SL, SV;
    
    public ControlS_SistemaMix(double T, double P, double Pref, double Tref, double zi, Session session){
        zj = 1-zi;
        
        ControlS_Dep sdep1 = new ControlS_Dep(Tref, Pref, 0, session);
        SDrefLj = sdep1.getSDL();
        SDrefVj = sdep1.getSDV();
        
        ControlS_Dep sdep2 = new ControlS_Dep(Tref, Pref, 1, session);
        SDrefLi = sdep2.getSDL();
        SDrefVi = sdep2.getSDV();
        
        ControlS_Ideal_Gas_Mix sideal = new ControlS_Ideal_Gas_Mix(T, Tref, zi, P, Pref, session);
        
        ControlS_Depmix s_depmix = new ControlS_Depmix(T, P, zi);
        
        //SL=-(zi*SDrefLi+zj*SDrefLj)+Sigmix+SDL;
        SL = -(zi*SDrefLi+zj*SDrefLj)+sideal.getSigmix()+s_depmix.getSDL();
        //SV=-(zi*SDrefLi+zj*SDrefLj)+Sigmix+SDV;
        SV = -(zi*SDrefLi+zj*SDrefLj)+sideal.getSigmix()+s_depmix.getSDV();
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