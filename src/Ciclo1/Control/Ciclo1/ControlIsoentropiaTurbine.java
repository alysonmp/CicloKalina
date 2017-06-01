/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo1;

import org.hibernate.Session;

/**
 *
 * @author alysonmp
 */
public class ControlIsoentropiaTurbine {
    
    private double Test, SV2, SL2, S2, erro, DT, burbuja, HL2, HV2, H2s, H2, H, T2, x, x2;
    
    public ControlIsoentropiaTurbine(double Teff, double P2, double Pref, double Tref, double S1, double H1, int ii, Session session){
        
        ControlT_Ref ctrlTref = new ControlT_Ref(P2, ii, session);
        Test = ctrlTref.getTref();
        
        ControlS_Sistema SSistema = new ControlS_Sistema(Test, P2, Pref, Tref, ii, session);
        SL2 = SSistema.getSL();
        SV2 = SSistema.getSV();
        
        if(SV2 <= S1){
            erro = 1;
            DT = 300;
            while(erro >= 0.0001){
                SSistema = new ControlS_Sistema(Test, P2, Pref, Tref, ii, session);
                SL2 = SSistema.getSL();
                SV2 = SSistema.getSV();
            
                S2 = SV2;
                erro = Math.abs((S2-S1)/S2);
                burbuja = S2-S1;
                if(erro > 0.0001 && burbuja < 0){
                    Test = Test+DT;
                    DT = DT/2;
                    if(DT < 0.005){
                        DT = 0.004987569731;
                    }
                }else{
                    if(erro > 0.0001 && burbuja > 0){
                        Test = Test-DT;
                        DT = DT/2;
                        if(DT<0.005){
                            DT=0.002933254;
                        }
                    }
                }
            }
            ControlH_Sistema HSistema = new ControlH_Sistema(Test, P2, Pref, Tref, ii, session);
            HL2 = HSistema.getHL();
            HV2 = HSistema.getHV();
            
            H2s = HV2;
            H2=(-(H1-H2s)*Teff)+H1;
            if(H2 > H2s){
                erro = 1;
                DT = 5;
                while(erro > 0.0001){
                    HSistema = new ControlH_Sistema(Test, P2, Pref, Tref, ii, session);
                    HL2 = HSistema.getHL();
                    HV2 = HSistema.getHV();
                    
                    H = HV2;
                    erro = Math.abs((H2-H)/H2);
                    burbuja = H2-H;
                    if(erro > 0.0001 && burbuja < 0){
                        Test = Test-DT;
                        DT = DT/2;
                        if(DT<0.005){
                            DT=0.004978953;
                        }
                    }else{
                        if(erro > 0.0001 && burbuja > 0){
                            Test = Test+DT;
                            DT = DT/2;
                            if(DT < 0.005){
                                DT = 0.00333695;
                            }
                        }
                    }
                }
            }
            T2=Test;
            HSistema = new ControlH_Sistema(T2, P2, Pref, Tref, ii, session);
            HL2 = HSistema.getHL();
            HV2 = HSistema.getHV();
            
            SSistema = new ControlS_Sistema(T2, P2, Pref, Tref, ii, session);
            SL2 = SSistema.getSL();
            SV2 = SSistema.getSV();
            
            H2 = HV2;
            S2 = SV2; 
        }else{
            x = (S1-SL2)/(SV2-SL2);
            ControlH_Sistema HSistema = new ControlH_Sistema(Test, P2, Pref, Tref, ii, session);
            HL2 = HSistema.getHL();
            HV2 = HSistema.getHV();
            
            H2s = (HV2*x)+(HL2*(1-x));
            H2 = (-(H1-H2s)*Teff)+H1;
            DT = 120;
            if(H2 > HV2){
                erro = 1;
                while(erro > 0.0001){
                    HSistema = new ControlH_Sistema(Test, P2, Pref, Tref, ii, session);
                    HL2 = HSistema.getHL();
                    HV2 = HSistema.getHV();
                    
                    H = HV2;
                    erro = Math.abs((H2-H)/H2);
                    burbuja=H2-H;
                    if(erro > 0.0001 && burbuja < 0){
                        Test = Test-DT;
                        DT = DT/2;
                        if(DT < 0.005){
                            DT = 0.005;
                        }
                    }else{
                        if(erro > 0.0001 && burbuja > 0){
                            Test = Test+DT;
                            DT = DT/2;
                            if(DT < 0.005){
                                DT = 0.005;
                            }
                        }
                    }
                }
                T2=Test;
                HSistema = new ControlH_Sistema(T2, P2, Pref, Tref, ii, session);
                HL2 = HSistema.getHL();
                HV2 = HSistema.getHV();

                SSistema = new ControlS_Sistema(T2, P2, Pref, Tref, ii, session);
                SL2 = SSistema.getSL();
                SV2 = SSistema.getSV();

                H2=HV2;
                S2=SV2;
            }else{
                x2 = (H2-HL2)/(HV2-HL2);
                if(x2 < 0.99){
                    System.out.println("Titulo do vapor da saida na Turbina inferior 0.9");
                    System.out.println("Aumentar a Temperatura de Superaquecimento");
                    //Pasar=oiu*uy6r7*988
                }else{
                    T2 = Test;
                    HSistema = new ControlH_Sistema(T2, P2, Pref, Tref, ii, session);
                    HL2 = HSistema.getHL();
                    HV2 = HSistema.getHV();
                   
                    SSistema = new ControlS_Sistema(T2, P2, Pref, Tref, ii, session);
                    SL2 = SSistema.getSL();
                    SV2 = SSistema.getSV();
                   
                    H2=(HV2*x2)+(HL2*(1-x2));
                    S2=(SV2*x2)+(SL2*(1-x2));    
                }
            }
        }
    }

    public double getS2() {
        return S2;
    }

    public void setS2(double S2) {
        this.S2 = S2;
    }

    public double getH2s() {
        return H2s;
    }

    public void setH2s(double H2s) {
        this.H2s = H2s;
    }

    public double getH2() {
        return H2;
    }

    public void setH2(double H2) {
        this.H2 = H2;
    }

    public double getT2() {
        return T2;
    }

    public void setT2(double T2) {
        this.T2 = T2;
    }
}