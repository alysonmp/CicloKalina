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
public class ControlRedu {
    
    private double c, Ts, T, T2, T3, Ureg, Qcpla, Tmreg, Afpso;
    
    public ControlRedu(double Te, int compressor, double Q){
        c = compressor;
        Ts = 40+273.15;
        T = Te-Ts;
        T2 = 35+273.15;
        T3 = 40+273.15;
        Ureg = 0.6;
        
        switch(compressor){
            case 1: Qcpla=(11170211.53*(4.184/3600))-(Q);
                    break;
                  
            case 2: Qcpla=(11964395.62*(4.184/3600))-(Q);
                    break;
                    
            case 3: Qcpla=(13882702.37*(4.184/3600))-(Q);
                    break;
                    
            case 4: Qcpla=(15221010.75*(4.184/3600))-(Q);
                    break;
        }
        
        Tmreg= ((T2-Ts)-(T3-Te))/(Math.log((T2-Ts)/(T3-Te))); //%%Revisar%
        Afpso= Qcpla/(Tmreg*Ureg);
    }
}