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
public class ControlPorvalho {
    
    private double zj, Psi1, Psj1, Psi2, Psj2, P, erro, DP, Pe, teste, Porva;
    
    public ControlPorvalho(double T, double zi, Session session){
        zj = 1-zi;
        
        ControlPdeVapor pdevapor = new ControlPdeVapor(T, 0, session);
        Psi1 = pdevapor.getPsi();
        Psj1 = pdevapor.getPsj();
        
        pdevapor = new ControlPdeVapor(T, 1, session);
        Psi2 = pdevapor.getPsi();
        Psj2 = pdevapor.getPsj();
        
        P = (Psi2+Psj1)/2;
        
        erro = 1;
        DP = (Psi2-Psj1)/2.5;
        Pe = P;
        
        while(erro > 0.00001){
            System.out.println("while");
            ControlConequi conequi = new ControlConequi(session);
            conequi.realizaCalculo(T, Pe, zi);
            
            teste = (zi/conequi.getKi())+(zj/conequi.getKj());
            erro = Math.abs((1-teste)/1);
            if(erro > 0.00001 && teste < 1){
                Pe = Pe+DP;
                DP = DP/2;
                if(DP < 0.00025){
                    DP = 0.0001721479535433;
                }else{
                    if(erro > 0.000001 && teste > 1){
                        Pe = Pe-DP;
                        DP = DP/2;
                        if(DP < 0.00025){
                            DP = 0.000113393845211111;
                        }
                    }
                }
            }
        }
        Porva=Pe;
    }   

    public double getPorva() {
        return Porva;
    }

    public void setPorva(double Porva) {
        this.Porva = Porva;
    }
}