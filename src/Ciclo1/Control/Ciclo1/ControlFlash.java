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
public class ControlFlash {
    
    private double zj, erro, VF, DVF, teste;
    
    public ControlFlash(double P, double T, double zi, Session session){
        zj = 1-zi;
        erro = 1;
        VF = 0.5;
        DVF = 0.2;
        
        while(erro > 0.001){
            //[Ki, Kj]=conequi(T, P, zi);
            ControlConequi conequi = new ControlConequi(session);
            conequi.realizaCalculo(T, P, zi);
            
            //teste=(zi*(Ki-1)/((VF*(Ki-1))+1))+(zj*(Kj-1)/((VF*(Kj-1))+1));
            teste = (zi*(conequi.getKi()-1)/((VF*(conequi.getKi()-1))+1))+(zj*(conequi.getKj()-1)/((VF*(conequi.getKj()-1))+1));
            erro = Math.abs((0-teste)/1);
            if(erro > 0.001 & teste <= 0){
                VF = VF-DVF;
                DVF = DVF/2;
                if(DVF<0.0025){
                    DVF = 0.00147953543;
                }
            }else{
                if(erro > 0.001 & teste > 0){
                VF = VF+DVF;
                DVF = DVF/2;
                    if(DVF < 0.0025){
                        DVF = 0.00393845211111;
                    }
                }
            }
        }
        if(VF>1){
            VF = 1;
        }

        if(VF<0){
            VF = 0;
        }
    }

    public double getVF() {
        return VF;
    }

    public void setVF(double VF) {
        this.VF = VF;
    }
}
