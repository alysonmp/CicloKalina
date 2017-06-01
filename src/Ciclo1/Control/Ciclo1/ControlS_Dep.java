/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.Ciclo1;

import org.hibernate.Session;

/**
 *
 * @author leonardo
 */
public class ControlS_Dep {
    private double SDL, SDV;

    public ControlS_Dep(double T, double P, int ii, Session session) {
        ControlConstantes constantes = new ControlConstantes(T, P, ii, session);
        ControlZeta zeta = new ControlZeta(constantes.getBeta(), constantes.getEps(), constantes.getDelta());
        ControlPdeVapor pdeVapor = new ControlPdeVapor(T, ii, session);
        
        double da_dT=(-0.457235*(Math.pow(constantes.getR(),2))*constantes.getTc()/constantes.getPc())*(1+constantes.getK0()*(1-(Math.pow(constantes.getTr(),0.5)))+constantes.getK1()*(1-constantes.getTr())*(0.7-constantes.getTr()))*((constantes.getK0()/(Math.pow(constantes.getTr(),0.5)))+(constantes.getK1()*(3.4-4*constantes.getTr())));
        double dif = Math.abs((pdeVapor.getPs2()-P)/P);
        
        if(dif >= 0.0001 && pdeVapor.getPs2() < P){
            SDL=((da_dT)/(2*constantes.getb()*(Math.pow(2,0.5))))*Math.log((zeta.getZl()+constantes.getB()*(1+(Math.pow(2,0.5))))/(zeta.getZl()+constantes.getB()*(1-(Math.pow(2,0.5)))))+constantes.getR()*Math.log(zeta.getZl()-constantes.getB());
            SDV = 0;
        }else if(dif >= 0.0001 && pdeVapor.getPs2() > P){
            SDL = 0;
            SDV=((da_dT)/(2*constantes.getb()*(Math.pow(2,0.5))))*Math.log((zeta.getZv()+constantes.getB()*(1+(Math.pow(2,0.5))))/(zeta.getZv()+constantes.getB()*(1-(Math.pow(2,0.5)))))+constantes.getR()*Math.log(zeta.getZv()-constantes.getB());
        }else if(dif < 0.0001){
            SDL=((da_dT)/(2*constantes.getb()*(Math.pow(2,0.5))))*Math.log((zeta.getZl()+constantes.getB()*(1+(Math.pow(2,0.5))))/(zeta.getZl()+constantes.getB()*(1-(Math.pow(2,0.5)))))+constantes.getR()*Math.log(zeta.getZl()-constantes.getB());
            SDV=((da_dT)/(2*constantes.getb()*(Math.pow(2,0.5))))*Math.log((zeta.getZv()+constantes.getB()*(1+(Math.pow(2,0.5))))/(zeta.getZv()+constantes.getB()*(1-(Math.pow(2,0.5)))))+constantes.getR()*Math.log(zeta.getZv()-constantes.getB());
        }
    }

    public double getSDL() {
        return SDL;
    }

    public void setSDL(double SDL) {
        this.SDL = SDL;
    }

    public double getSDV() {
        return SDV;
    }

    public void setSDV(double SDV) {
        this.SDV = SDV;
    }
    
    
}
