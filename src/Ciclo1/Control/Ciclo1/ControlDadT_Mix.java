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
public class ControlDadT_Mix {
    
    private double da_dT_i, da_dT_j, aTcaw, raizdadTi, raizdadTj, da_dT_mix;
    
    public ControlDadT_Mix(double T, double P, double zi){
        
        ControlConstantes constantes = new ControlConstantes(T, P);
        
        ControlConstantesMix constantesMix = new ControlConstantesMix(T, P, zi);
        
        //da_dT_i=Omegaai*(R^2*Tci/Pci)*(-Fi*(alfai^0.5)/(Tri^0.5));
        da_dT_i = constantes.getOmegaai()*(Math.pow(constantes.getR(), 2)*constantes.getTci()/constantes.getPci())*(-constantes.getFi()*(Math.pow(constantes.getAlfai(), 0.5))/(Math.pow(constantes.getTri(), 0.5)));
        //da_dT_j=Omegaaj*(R^2*Tcj/Pcj)*(-Fj*(alfaj^0.5)/(Trj^0.5));
        da_dT_j = constantes.getOmegaaj()*(Math.pow(constantes.getR(), 2)*constantes.getTcj()/constantes.getPcj())*(-constantes.getFj()*(Math.pow(constantes.getAlfaj(), 0.5))/(Math.pow(constantes.getTrj(), 0.5)));
        
        //aTcaw=((Omegaai*(R^2*Tci^2/Pci))^0.5)*((Omegaaj*(R^2*Tcj^2/Pcj))^0.5);
        aTcaw = Math.pow((constantes.getOmegaai()*(Math.pow(constantes.getR(), 2)*Math.pow(constantes.getTci(),2/constantes.getPci()))),0.5)*(Math.pow(constantes.getOmegaaj() * (Math.pow(constantes.getR(), 2)*Math.pow(constantes.getTcj(), 2)/constantes.getPcj()), 0.5));
        
        //raizdadTi=-(Omegaai*(R^2/Pci))^0.5*(Fi/(2*(Tri)^0.5));
        raizdadTi = -Math.pow((constantes.getOmegaai()*(Math.pow(constantes.getR(), 2)/constantes.getPci())),0.5) * (constantes.getFi()/(2*Math.pow((constantes.getTri()),0.5)));
        //raizdadTj=-(Omegaaj*(R^2/Pcj))^0.5*(Fj/(2*(Trj)^0.5));
        raizdadTj = -Math.pow((constantes.getOmegaaj()*(Math.pow(constantes.getR(), 2)/constantes.getPcj())),0.5) * (constantes.getFj()/(2*Math.pow((constantes.getTrj()),0.5)));
        
        //da_dT_mix=(zi^2*da_dT_i)+(zj^2*da_dT_j)+(((raizdadTi*(aj^0.5))+(raizdadTj*(ai^0.5)))*zi*zj*(1-kij+((kij-kji)*zi)))+(((raizdadTj*(ai^0.5))+(raizdadTi*(aj^0.5)))*zj*zi*(1-kji+((kji-kij)*zj)));
        da_dT_mix = (Math.pow(zi,2)*da_dT_i)+(Math.pow(constantesMix.getzj(), 2)*da_dT_j)+(((raizdadTi*(Math.pow(constantes.getaj(),0.5)))+(raizdadTj*(Math.pow(constantes.getai(),0.5))))*zi*constantesMix.getzj()*(1-constantesMix.getKij()+((constantesMix.getKij()-constantesMix.getKji())*zi)))+(((raizdadTj*(Math.pow(constantes.getai(), 0.5)))+(raizdadTi*(Math.pow(constantes.getaj(), 0.5))))*constantesMix.getzj()*zi*(1-constantesMix.getKji()+((constantesMix.getKji()-constantesMix.getKij())*constantesMix.getzj())));
    }

    public double getDa_dT_mix() {
        return da_dT_mix;
    }

    public void setDa_dT_mix(double da_dT_mix) {
        this.da_dT_mix = da_dT_mix;
    }
}
 