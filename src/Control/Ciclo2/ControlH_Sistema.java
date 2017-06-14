/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo2;

import org.hibernate.Session;

/**
 *
 * @author leonardo
 */
public class ControlH_Sistema {
    
    private double HL, HV;
    private String mensagem = "";
    
    public ControlH_Sistema(double T, double P, double Pref, double Tref, int ii,Session session) {
        ControlH_Dep hDep = new ControlH_Dep(Tref, Pref, ii, session);
        if(!hDep.getMensagem().equals("")){
            mensagem = hDep.getMensagem();
            return;
        }
        
        ControlH_Ideal_Gas hidelGas = new ControlH_Ideal_Gas(T, Tref, ii, session);
        
        double HDrefL = hDep.getHDL();
        double HDrefV = hDep.getHDV();
        
        hDep = new ControlH_Dep(T, P, ii, session);
        if(!hDep.getMensagem().equals("")){
            mensagem = hDep.getMensagem();
            return;
        }
        
        HL = -HDrefL + hidelGas.getHig() + hDep.getHDL();
        HV = -HDrefL + hidelGas.getHig() + hDep.getHDV();
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

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    
    
}
