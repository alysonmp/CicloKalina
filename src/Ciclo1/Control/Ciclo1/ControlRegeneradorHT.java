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
public class ControlRegeneradorHT {
    
    private double P12, H12, erro1, Tbol12, Torv12, Test, DT, it, H, burbuja, T12, xx12, xi12, yi12, HV, HL, HV12, HL12;
    
    public ControlRegeneradorHT(double TbolA, double TorvA, double VF1, double H3, double H5, double H11, double T3, double T5, double T11, double P1, double Pref, double Tref, double zi, Session session){
        
        P12 = P1;
        H12 = ((H3-H5)*(1-VF1))+H11;
        
        if(T5 < T11+5){
            //klklkl*JJJJ
        }
        erro1 = 1;
        Tbol12 = TbolA;
        Torv12 = TorvA;
        
        Test = T11;
        DT = Math.abs(Tbol12-T11)/2;
        if(DT < 10){
            DT=10;
        }
        
        it = 0;
        while(erro1 >= 0.001){
            it = it+1;
            if(it > 100){
                 //hshshsh*ksksks
            }
            if(Test >= Torv12){
                ControlH_Sistemamix HSistemamix = new ControlH_Sistemamix(Test, P12, Pref, Tref, zi, session);
                H = HSistemamix.getHV();
                erro1 = Math.abs((H12-H)/H12);
                burbuja=H12-H;
                if(erro1 > 0.001 && burbuja < 0){
                    Test = Test-DT;
                    DT = DT/2;
                    if(DT < 0.005){
                        DT=0.0043978953;
                    }
                }else{
                    if(erro1 > 0.001 && burbuja > 0){
                        Test = Test+DT;
                        DT = DT/2;
                        if(DT < 0.005){
                            DT = 0.00333695;
                        }
                    }
                }
                T12 = Test;
                xx12 = 1;
            }else{
                if(Test <= Tbol12){
                    ControlH_Sistemamix HSistemamix = new ControlH_Sistemamix(Test, P12, Pref, Tref, zi, session);
                    H = HSistemamix.getHL();
                    erro1 = Math.abs((H12-H)/H12);
                    burbuja = H12-H;
                    if(erro1 > 0.001 && burbuja < 0){
                        Test = Test-DT;
                        DT = DT/2;
                        if(DT<0.005){
                            DT=0.0043978953;
                        }
                    }else{
                        if(erro1 > 0.001 && burbuja > 0){
                            Test = Test+DT;
                            DT = DT/2;
                            if(DT < 0.005){
                                DT = 0.00333695;
                            }
                        }
                    }
                    T12=Test;
                    xx12=0;
                }else{
                    ControlCompequi compequi = new ControlCompequi(P12, Test, session);
                    xi12 = compequi.getX();
                    yi12 = compequi.getYi();
                    
                    ControlH_Sistemamix HSistemamix = new ControlH_Sistemamix(Test, P12, Pref, Tref, yi12, session);
                    HL = HSistemamix.getHL();
                    HV12 = HSistemamix.getHV();
                    
                    HSistemamix = new ControlH_Sistemamix(Test, P12, Pref, Tref, xi12, session);
                    HL12 = HSistemamix.getHL();
                    HV = HSistemamix.getHV();
                    
                    ControlFlash flash = new ControlFlash(P12, Test, zi, session);
                    xx12 = flash.getVF();
                    
                    H = (HV12*xx12)+(HL12*(1-xx12));
                    erro1 = Math.abs((H12-H)/H12);
                    burbuja = H12-H;
                    if(erro1 > 0.001 && burbuja < 0){
                        Test = Test-DT;
                        DT = DT/2;
                        if(DT < 0.005){
                            DT=0.0043978953;
                        }
                    }else{
                        if(erro1 > 0.001 && burbuja > 0){
                           Test = Test+DT;
                           DT = DT/2;
                           if(DT < 0.005){
                               DT = 0.5;
                           }
                        }
                    }
                    T12=Test;
                }  
            }
        }

        if(T12 > T3+5){
           //jaljkfsjklfs*jasdkljdsa
        }

        if(T12 < T11+4){
            //jaljkfsjklfs*jasdkljdsa
        }
    }  

    public double getH12() {
        return H12;
    }

    public void setH12(double H12) {
        this.H12 = H12;
    }

    public double getT12() {
        return T12;
    }

    public void setT12(double T12) {
        this.T12 = T12;
    }

    public double getXx12() {
        return xx12;
    }

    public void setXx12(double xx12) {
        this.xx12 = xx12;
    }
}