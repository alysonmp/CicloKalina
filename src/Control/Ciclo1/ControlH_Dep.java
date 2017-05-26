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
public class ControlH_Dep {

    private double HDL,HDV;
    
    public ControlH_Dep(double T, double P, int ii,Session session) {
        ControlConstantes constantes = new ControlConstantes(T, P, ii, session);
        ControlZeta zeta = new ControlZeta(constantes.getBeta(), constantes.getEps(), constantes.getDelta());
        ControlPdeVapor pdevapor = new ControlPdeVapor(T, ii, session);
        
        double da_dT= (-0.457235*(Math.pow(constantes.getR(),2))*constantes.getTc()/constantes.getPc())*(1+constantes.getK0()*(1-(Math.pow(constantes.getTr(),0.5)))+constantes.getK1()*(1-constantes.getTr())*(0.7-constantes.getTr()))*((constantes.getK0()/(Math.pow(constantes.getTr(),0.5)))+(constantes.getK1()*(3.4-4*constantes.getTr())));
        double dif= Math.abs((pdevapor.getPs2()-P)/P);
        if(dif >= 0.0001 && pdevapor.getPs2() < P){
            //Z=Zl;
            HDL=-((T*(da_dT)-constantes.geta())/(2*constantes.getb()*(Math.pow(2,0.5))))*Math.log((zeta.getZl()+constantes.getB()*(1-(Math.pow(2,0.5))))/(zeta.getZl()+constantes.getB()*(1+(Math.pow(2,0.5)))))+ (zeta.getZl()-1)*constantes.getR()*T;
            HDV=0;
        }else if(dif >= 0.0001 && pdevapor.getPs2() > P){
                //Z=Zv;
                HDL=0;
                HDV=-((T*(da_dT)-constantes.geta())/(2*constantes.getb()*(Math.pow(2,0.5))))*Math.log((zeta.getZv()+constantes.getB()*(1-(Math.pow(2,0.5))))/(zeta.getZv()+constantes.getB()*(1+(Math.pow(2,0.5)))))+ (zeta.getZv()-1)*constantes.getR()*T;
        }else if(dif < 0.0001){
                HDL=-((T*(da_dT)-constantes.geta())/(2*constantes.getb()*(Math.pow(2,0.5))))*Math.log((zeta.getZl()+constantes.getB()*(1-(Math.pow(2,0.5))))/(zeta.getZl()+constantes.getB()*(1+(Math.pow(2,0.5)))))+ (zeta.getZl()-1)*constantes.getR()*T;
                HDV=-((T*(da_dT)-constantes.geta())/(2*constantes.getb()*(Math.pow(2,0.5))))*Math.log((zeta.getZv()+constantes.getB()*(1-(Math.pow(2,0.5))))/(zeta.getZv()+constantes.getB()*(1+(Math.pow(2,0.5)))))+ (zeta.getZv()-1)*constantes.getR()*T;
        }

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
