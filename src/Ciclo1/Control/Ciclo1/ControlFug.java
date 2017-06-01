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
public class ControlFug {
    
    private double Pc, Tc, Tr, Pr, F, Omegab, Omegaa, Omegac, A, B, C, N, M, Q, alfa ,a, da_dT;
    private double fil, fiv;
    private ControlZetamix zetamix ;
    
    public ControlFug(double T, double P, double x){
        
        ControlConstantes constantes = new ControlConstantes(T, P);
        
        if(x == 0){
            zetamix = new ControlZetamix(constantes.getAj(), constantes.getBj(), constantes.getCj());
            
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
            zetamix = new ControlZetamix(constantes.getAi(), constantes.getBi(), constantes.getCi());
            
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
        
        //fil=exp(Zl-1-(log(Zl-B))+(a/(2*R*T*N))*(log((Zl+M)/(Zl+Q))));
        fil = Math.exp(zetamix.getZl()-1-(Math.log(zetamix.getZl()-B))+(a/(2*constantes.getR()*T*N))*(Math.log((zetamix.getZl()+M)/(zetamix.getZl()+Q))));
                
        //fiv=exp(Zv-1-(log(Zv-B))+(a/(2*R*T*N))*(log((Zv+M)/(Zv+Q))));
        fiv = Math.exp(zetamix.getZv()-1-(Math.log(zetamix.getZv()-B))+(a/(2*constantes.getR()*T*N))*(Math.log((zetamix.getZv()+M)/(zetamix.getZv()+Q))));
    }

    public double getFil() {
        return fil;
    }

    public void setFil(double fil) {
        this.fil = fil;
    }

    public double getFiv() {
        return fiv;
    }

    public void setFiv(double fiv) {
        this.fiv = fiv;
    }   
}
