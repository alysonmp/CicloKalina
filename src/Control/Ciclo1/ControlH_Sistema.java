/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo1;

import org.hibernate.Session;

/**
 *
 * @author leonardo
 */
public class ControlH_Sistema {
    
    private double HL, HV;
    
    public ControlH_Sistema(double T, double P, double Pref, double Tref, int ii,Session session) {
        ControlH_Dep hDep = new ControlH_Dep(T, Pref, ii, session);
        ControlH_Ideal_Gas hidelGas = new ControlH_Ideal_Gas(T, Tref, ii, session);
        
        double HDrefL = hDep.getHDL();
        double HDrefV = hDep.getHDV();
        
        hDep = new ControlH_Dep(T, P, ii, session);
        
        HL = -HDrefL + hidelGas.getHig()+ hDep.getHDL();
        HV = -HDrefV + hidelGas.getHig() + hDep.getHDV();
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
