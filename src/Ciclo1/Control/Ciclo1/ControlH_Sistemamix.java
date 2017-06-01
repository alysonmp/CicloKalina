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
public class ControlH_Sistemamix {
    
    private double zj, HDrefLj, HDrefVj, HDrefLi, HDrefVi, HL, HV;
    private Session session;
    
    public ControlH_Sistemamix(double T, double P, double Pref, double Tref, double zi, Session session){
        this.session = session;
        zj = 1-zi;
        
        ControlH_Dep hdep = new ControlH_Dep(Tref, Pref, 0, this.session);
        HDrefLj = hdep.getHDL();
        HDrefVj = hdep.getHDV();
        
        hdep = new ControlH_Dep(Tref, Pref, 1, this.session);
        HDrefLi = hdep.getHDL();
        HDrefVi = hdep.getHDV();
        
        ControlH_ideal_Gas_Mix hideal = new ControlH_ideal_Gas_Mix(T, Tref, zi, this.session);
        
        ControlH_Depmix depmix = new ControlH_Depmix(T, P, zi);
        
        //HL=-(zi*HDrefLi+zj*HDrefLj)+Higmix+HDL;
        HL = -(zi*HDrefLi+zj*HDrefLj)+hideal.getHigmix()+depmix.getHDL();
        //HV=-(zi*HDrefLi+zj*HDrefLj)+Higmix+HDV;
        HV = -(zi*HDrefLi+zj*HDrefLj)+hideal.getHigmix()+depmix.getHDV();
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
