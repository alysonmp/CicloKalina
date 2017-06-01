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
public class ControlH_Dep {
    
    private double Pc, Tc, Tr, Pr, F, Omegab, Omegaa, Omegac, A, B, C, N, M, Q, 
                   alfa ,a, da_dT, Ps2, dif, HDL, HDV;
    private Session session;
    private ControlZeta zetamix;
    
    public ControlH_Dep(double T, double P, double x, Session session){
        this.session = session;
        ControlConstantes constantes = new ControlConstantes(T, P);
        
        if(x == 0){
            zetamix = new ControlZeta(constantes.getAj(), constantes.getBj(), constantes.getCj());
            
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
            zetamix = new ControlZeta(constantes.getAi(), constantes.getBi(), constantes.getCi());
           
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
        da_dT=Omegaa*(Math.pow(constantes.getR(), 2)*Tc/Pc)*(-F*(Math.pow(alfa, 0.5))/(Math.pow(Tr,0.5)));
        
        ControlPdeVapor pVapor = new ControlPdeVapor(T, x, this.session);
        
        if(x == 0)
            Ps2 = pVapor.getPsj();
        else
            Ps2 = pVapor.getPsi();
        
        dif = Math.abs((Ps2-P)/P);
        
        if(dif >= 0.0001 & Ps2 < P){
            //HDL=(R*T*(Zl-1)-((T*da_dT)-a)*((1/(2*N))*(log((Zl+M)/(Zl+Q)))))*100000;
            HDL = (constantes.getR()*T*(zetamix.getZl()-1)-((T*da_dT)-a)*((1/(2*N))*(Math.log((zetamix.getZl()+M)/(zetamix.getZl()+Q)))))*100000;
            HDV = 0;
        }else{
            if(dif >= 0.0001 & Ps2 > P){
                HDL=0;
                //HDV=(R*T*(Zv-1)-((T*da_dT)-a)*((1/(2*N))*(log((Zv+M)/(Zv+Q)))))*100000;
                HDV=(constantes.getR()*T*(zetamix.getZv()-1)-((T*da_dT)-a)*((1/(2*N))*(Math.log((zetamix.getZv()+M)/(zetamix.getZv()+Q)))))*100000;
            }else{
                if(dif<0.0001){
                    //HDL=(R*T*(Zl-1)-((T*da_dT)-a)*((1/(2*N))*(log((Zl+M)/(Zl+Q)))))*100000;
                    HDL=(constantes.getR()*T*(zetamix.getZl()-1)-((T*da_dT)-a)*((1/(2*N))*(Math.log((zetamix.getZl()+M)/(zetamix.getZl()+Q)))))*100000;
                    //HDV=(R*T*(Zv-1)-((T*da_dT)-a)*((1/(2*N))*(log((Zv+M)/(Zv+Q)))))*100000;
                    HDV=(constantes.getR()*T*(zetamix.getZv()-1)-((T*da_dT)-a)*((1/(2*N))*(Math.log((zetamix.getZv()+M)/(zetamix.getZv()+Q)))))*100000;
                }
            }
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