/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.Ciclo1;

/**
 *
 * @author alysonmp
 */
public class ControlH_Depmix {
    
    private double HDL, HDV;
    
    public ControlH_Depmix(double T, double P, double zi){
        ControlConstantesMix constantesMix = new ControlConstantesMix(T, P, zi);
        
        ControlDadT_Mix dadt_mix = new ControlDadT_Mix(T, P, zi);
        ControlZeta zeta = new ControlZeta(constantesMix.getAm(), constantesMix.getBm(), constantesMix.getCm());
        
        //HDL=(R*T*(Zl-1)-((T*da_dT_mix)-am)*((1/(2*Nm))*(log((Zl+Mm)/(Zl+Qm)))))*100000;
        HDL=(constantesMix.getConstantes().getR()*T*(zeta.getZl()-1)-((T*dadt_mix.getDa_dT_mix())-constantesMix.getam())*((1/(2*constantesMix.getNm()))*(Math.log((zeta.getZl()+constantesMix.getMm())/(zeta.getZl()+constantesMix.getQm())))))*100000;
        //HDV=(R*T*(Zv-1)-((T*da_dT_mix)-am)*((1/(2*Nm))*(log((Zv+Mm)/(Zv+Qm)))))*100000;
        HDV=(constantesMix.getConstantes().getR()*T*(zeta.getZv()-1)-((T*dadt_mix.getDa_dT_mix())-constantesMix.getam())*((1/(2*constantesMix.getNm()))*(Math.log((zeta.getZv()+constantesMix.getMm())/(zeta.getZv()+constantesMix.getQm())))))*100000;
        
    }

    public double getHDL() {
        return HDL;
    }

    public void setHDL(double HDL) {
        this.HDL = HDL;
    }

    public double getHDV() {
        return HDV;
    }

    public void setHDV(double HDV) {
        this.HDV = HDV;
    }
 
    
}