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
public class ControlH_Sistema {
    
    private double HDrefL, HDrefV, Hig, HDL, HDV, HL, HV;
    
    public ControlH_Sistema(double T, double P, double Pref, double Tref, double x, Session session){
        ControlH_Dep Hdep = new ControlH_Dep(Tref, Pref, x, session);
        HDrefL = Hdep.getHDL();
        HDrefV = Hdep.getHDV();
        
        ControlH_Ideal_Gas HIdealgas = new ControlH_Ideal_Gas(T, Tref, x, session);
        Hig = HIdealgas.getHig();

        Hdep = new ControlH_Dep(T, P, x, session);
        HDL = Hdep.getHDL();
        HDV = Hdep.getHDV();
                
        HL = -HDrefL+Hig+HDL;
        HV = -HDrefL+Hig+HDV;

    }

    public double getHL() {
        return HL;
    }

    public void setHL(double HL) {
        this.HL = HL;
    }

    public double getHV() {
        return HV;
    }

    public void setHV(double HV) {
        this.HV = HV;
    }
}
