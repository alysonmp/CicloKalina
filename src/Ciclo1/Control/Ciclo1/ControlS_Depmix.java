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
public class ControlS_Depmix {

    private double SDV, SDL;
    
    public ControlS_Depmix(double T, double P, double zi) {
        
        ControlConstantesMix constantesMix = new ControlConstantesMix(T, P, zi);
        
        ControlDadT_Mix da_dT_Mix = new ControlDadT_Mix(T, P, zi);
        
        ControlZeta zeta = new ControlZeta(constantesMix.getAm(), constantesMix.getBm(), constantesMix.getCm());
        
        //SDL=((-R*log(P/(Zl-Bm)))-(da_dT_mix*((1/(2*Nm))*(log((Zl+Mm)/(Zl+Qm))))))*100000;
        SDL=((-constantesMix.getConstantes().getR()*Math.log(P/(zeta.getZl()-constantesMix.getBm())))-(da_dT_Mix.getDa_dT_mix()*((1/(2*constantesMix.getNm()))*(Math.log((zeta.getZl()+constantesMix.getMm())/(zeta.getZl()+constantesMix.getQm()))))))*100000;
        //SDV=((-R*log(P/(Zv-Bm)))-(da_dT_mix*((1/(2*Nm))*(log((Zv+Mm)/(Zv+Qm))))))*100000;
        SDV=((-constantesMix.getConstantes().getR()*Math.log(P/(zeta.getZv()-constantesMix.getBm())))-(da_dT_Mix.getDa_dT_mix()*((1/(2*constantesMix.getNm()))*(Math.log((zeta.getZv()+constantesMix.getMm())/(zeta.getZv()+constantesMix.getQm()))))))*100000;
    }

    public double getSDV() {
        return SDV;
    }

    public void setSDV(double SDV) {
        this.SDV = SDV;
    }

    public double getSDL() {
        return SDL;
    }

    public void setSDL(double SDL) {
        this.SDL = SDL;
    }
} 