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
public class ControlS_Dep {
    
    private double Pc, Tc, Tr, Pr, F, Omegab, Omegaa, Omegac, A, B, C, N, M, Q, alfa, a, da_dT, Ps2, dif,
                   SDL, SDV;
    private ControlZeta zeta;
    
    public ControlS_Dep(double T, double P, double x, Session session){
        
        ControlConstantes constantes = new ControlConstantes(T, P);
        
        if(x == 0){
            zeta = new ControlZeta(constantes.getAj(), constantes.getBj(), constantes.getCj());
            Pc = constantes.getPcj();
            Tc = constantes.getTcj();
            Tr = constantes.getTrj();
            Pr = constantes.getPrj();
            F = constantes.getFj();
            Omegab = constantes.getOmegabj();
            Omegaa = constantes.getOmegaaj();
            Omegac = constantes.getOmegacj();
            A = constantes.getAj();
            B = constantes.getBj();
            C = constantes.getCj();
            N = constantes.getNj();
            M = constantes.getMj();
            Q = constantes.getQj();
            alfa = constantes.getAlfaj();   
            a = constantes.getaj();
        }else{
            zeta = new ControlZeta(constantes.getAi(), constantes.getBi(), constantes.getCi());
            Pc = constantes.getPci();
            Tc = constantes.getTci();
            Tr = constantes.getTri();
            Pr = constantes.getPri();
            F = constantes.getFi();
            Omegab = constantes.getOmegabi();
            Omegaa = constantes.getOmegaai();
            Omegac = constantes.getOmegaci();
            A = constantes.getAi();
            B = constantes.getBi();
            C = constantes.getCi();
            N = constantes.getNi();
            M = constantes.getMi();
            Q = constantes.getQi();
            alfa = constantes.getAlfai();   
            a = constantes.getai();
        }
        
        //da_dT=Omegaa*(R^2*Tc/Pc)*(-F*(alfa^0.5)/(Tr^0.5));
        da_dT = Omegaa*(Math.pow(constantes.getR(),2)*Tc/Pc)*(-F*(Math.pow(alfa, 0.5))/(Math.pow(Tr, 0.5)));
        ControlPdeVapor pdevapor = new ControlPdeVapor(T, x, session);
        
        if(x == 0){
            Ps2 = pdevapor.getPsj();
        }else{
            Ps2 = pdevapor.getPsi();
        }
        
        dif = Math.abs((Ps2-P)/P);
        if(dif >= 0.0001 && Ps2 < P){
            //SDL=((-R*log(P/(Zl-B)))-(da_dT*((1/(2*N))*(log((Zl+M)/(Zl+Q))))))*100000;
            SDL = ((-constantes.getR()*Math.log(P/(zeta.getZl()-B)))-(da_dT*((1/(2*N))*(Math.log((zeta.getZl()+M)/(zeta.getZl()+Q))))))*100000;
            SDV = 0;
        }else{ 
            if(dif >= 0.0001 & Ps2 > P){
                SDL=0;
                //SDV=((-R*log(P/(Zv-B)))-(da_dT*((1/(2*N))*(log((Zv+M)/(Zv+Q))))))*100000;
                SDV=((-constantes.getR()*Math.log(P/(zeta.getZv()-B)))-(da_dT*((1/(2*N))*(Math.log((zeta.getZv()+M)/(zeta.getZv()+Q))))))*100000;
            }else{
                if(dif<0.0001){
                    //SDL=((-R*log(P/(Zl-B)))-(da_dT*((1/(2*N))*(log((Zl+M)/(Zl+Q))))))*100000;
                    SDL=((-constantes.getR()*Math.log(P/(zeta.getZl()-B)))-(da_dT*((1/(2*N))*(Math.log((zeta.getZl()+M)/(zeta.getZl()+Q))))))*100000;
                    //SDV=((-R*log(P/(Zv-B)))-(da_dT*((1/(2*N))*(log((Zv+M)/(Zv+Q))))))*100000;
                    SDV=((-constantes.getR()*Math.log(P/(zeta.getZv()-B)))-(da_dT*((1/(2*N))*(Math.log((zeta.getZv()+M)/(zeta.getZv()+Q))))))*100000;
                }
            }
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
